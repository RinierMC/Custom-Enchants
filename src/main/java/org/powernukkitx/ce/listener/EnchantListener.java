package org.powernukkitx.ce.listener;

import org.powernukkitx.Player;
import org.powernukkitx.block.Block;
import org.powernukkitx.entity.Entity;
import org.powernukkitx.entity.EntityLiving;
import org.powernukkitx.entity.effect.Effect;
import org.powernukkitx.entity.effect.EffectType;
import org.powernukkitx.event.EventHandler;
import org.powernukkitx.event.EventPriority;
import org.powernukkitx.event.Listener;
import org.powernukkitx.event.block.BlockBreakEvent;
import org.powernukkitx.event.entity.EntityDamageByEntityEvent;
import org.powernukkitx.event.entity.EntityDamageEvent;
import org.powernukkitx.event.player.PlayerFoodLevelChangeEvent;
import org.powernukkitx.event.player.PlayerRespawnEvent;
import org.powernukkitx.item.Item;
import org.powernukkitx.level.Level;
import org.powernukkitx.math.Vector3;
import org.powernukkitx.ce.Main;
import org.powernukkitx.ce.enchantment.*;
import org.powernukkitx.ce.util.EnchantUtil;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class EnchantListener implements Listener {

    private final Main plugin;

    public EnchantListener(Main plugin) {
        this.plugin = plugin;
    }

    // ------------------------------------------------------------------
    // Combat
    // ------------------------------------------------------------------

    @EventHandler(priority = EventPriority.HIGH)
    public void onDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player attacker)) return;
        Item weapon = attacker.getInventory().getItemInMainHand();

        if (event.getEntity() instanceof Player victim) {
            int voidLevel = highestVoidwalkerLevel(victim);
            if (voidLevel > 0 && Math.random() < voidLevel * EnchantVoidwalker.DODGE_CHANCE_PER_LEVEL) {
                event.setCancelled(true);
                return;
            }
        }

        double finalDamage = event.getFinalDamage();

        int executionerLevel = EnchantUtil.getLevel(weapon, plugin.executioner);
        if (executionerLevel > 0 && event.getEntity() instanceof EntityLiving living) {
            double hpPercent = living.getHealthCurrent() / (double) living.getHealthMax();
            if (hpPercent <= EnchantExecutioner.LOW_HP_THRESHOLD) {
                double bonus = finalDamage * executionerLevel * EnchantExecutioner.BONUS_DAMAGE_PERCENT_PER_LEVEL;
                event.setDamage((float) (finalDamage + bonus));
                finalDamage = event.getFinalDamage();
            }
        }

        int berserkerLevel = EnchantUtil.getLevel(weapon, plugin.berserker);
        if (berserkerLevel > 0) {
            double hpPercent = attacker.getHealthCurrent() / (double) attacker.getHealthMax();
            if (hpPercent <= EnchantBerserker.HP_THRESHOLD) {
                double bonus = finalDamage * berserkerLevel * EnchantBerserker.BONUS_PER_LEVEL;
                event.setDamage((float) (finalDamage + bonus));
                finalDamage = event.getFinalDamage();
            }
        }

        int vampiricLevel = EnchantUtil.getLevel(weapon, plugin.vampiric);
        if (vampiricLevel > 0) {
            double heal = finalDamage * vampiricLevel * EnchantVampiric.LIFESTEAL_PERCENT_PER_LEVEL;
            attacker.setHealthCurrent((float) Math.min(attacker.getHealthMax(), attacker.getHealthCurrent() + heal));
        }

        if (event.getEntity() instanceof Player victim) {
            Item chestplate = victim.getInventory().getChestplate();
            int reflectLevel = EnchantUtil.getLevel(chestplate, plugin.reflect);
            if (reflectLevel > 0) {
                double reflected = finalDamage * reflectLevel * EnchantReflect.REFLECT_PERCENT_PER_LEVEL;
                attacker.attack(new EntityDamageByEntityEvent(victim, attacker,
                        EntityDamageEvent.DamageCause.ENTITY_ATTACK, (float) reflected));
            }
        }

        if (event.getEntity() instanceof EntityLiving target) {
            int venomLevel = EnchantUtil.getLevel(weapon, plugin.venom);
            if (venomLevel > 0) {
                int duration = venomLevel * EnchantVenom.DURATION_PER_LEVEL;
                Effect poison = Effect.get(EffectType.POISON).setAmplifier(0).setDuration(duration).setVisible(true);
                target.addEffect(poison);
            }
            int witheringLevel = EnchantUtil.getLevel(weapon, plugin.withering);
            if (witheringLevel > 0) {
                int duration = witheringLevel * EnchantWithering.DURATION_PER_LEVEL;
                Effect wither = Effect.get(EffectType.WITHER).setAmplifier(0).setDuration(duration).setVisible(true);
                target.addEffect(wither);
            }
            int crippleLevel = EnchantUtil.getLevel(weapon, plugin.cripple);
            if (crippleLevel > 0) {
                int duration = crippleLevel * EnchantCripple.DURATION_PER_LEVEL;
                Effect slow = Effect.get(EffectType.SLOWNESS).setAmplifier(0).setDuration(duration).setVisible(true);
                target.addEffect(slow);
            }
        }

        if (event.getEntity() instanceof Player victim) {
            int totalFortitude = 0;
            for (Item piece : victim.getInventory().getArmorContents()) {
                totalFortitude += EnchantUtil.getLevel(piece, plugin.fortitude);
            }
            if (totalFortitude > 0) {
                double reduction = totalFortitude * EnchantFortitude.REDUCTION_PER_LEVEL_PER_PIECE;
                reduction = Math.min(reduction, 0.8);
                float newDamage = (float) (event.getDamage() * (1.0 - reduction));
                event.setDamage(newDamage);
            }
        }
    }

    @EventHandler
    public void onFallDamage(EntityDamageEvent event) {
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        if (!(event.getEntity() instanceof Player player)) return;
        Item boots = player.getInventory().getBoots();
        int level = EnchantUtil.getLevel(boots, plugin.featherfall);
        if (level <= 0) return;
        double reduction = Math.min(1.0, level * EnchantFeatherfall.REDUCTION_PER_LEVEL);
        event.setDamage((float) (event.getDamage() * (1.0 - reduction)));
    }

    private int highestVoidwalkerLevel(Player player) {
        int best = 0;
        best = Math.max(best, EnchantUtil.getLevel(player.getInventory().getItemInMainHand(), plugin.voidwalker));
        for (Item piece : player.getInventory().getArmorContents()) {
            best = Math.max(best, EnchantUtil.getLevel(piece, plugin.voidwalker));
        }
        return best;
    }

    // ------------------------------------------------------------------
    // Mining / tool enchants
    // ------------------------------------------------------------------

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Item tool = event.getItem();
        Block origin = event.getBlock();

        int explosiveLevel = EnchantUtil.getLevel(tool, plugin.explosive);
        int excavatorLevel = EnchantUtil.getLevel(tool, plugin.excavator);
        int lumberjackLevel = EnchantUtil.getLevel(tool, plugin.lumberjack);
        boolean autoSmelt = EnchantUtil.has(tool, plugin.autoSmelt);
        boolean telepathy = EnchantUtil.has(tool, plugin.telepathy);
        int harvestLevel = EnchantUtil.getLevel(tool, plugin.harvest);
        boolean scythe = EnchantUtil.has(tool, plugin.scythe);
        boolean drilling = EnchantUtil.has(tool, plugin.drilling);
        boolean tunnel = EnchantUtil.has(tool, plugin.tunnel);

        if (autoSmelt) {
            Item smelted = smeltedFormOf(origin);
            if (smelted != null) {
                event.setDrops(new Item[]{smelted});
            }
        }

        if (telepathy) {
            for (Item drop : event.getDrops()) {
                player.getInventory().addItem(drop);
            }
            event.setDrops(new Item[0]);
        }

        if (harvestLevel > 0 && isCrop(origin)) {
            Item[] drops = event.getDrops();
            if (drops.length > 0) {
                Item extra = drops[0].clone();
                extra.setCount(extra.getCount() + harvestLevel * EnchantHarvest.EXTRA_DROPS_PER_LEVEL);
                event.setDrops(new Item[]{extra});
            }
        }

        if (explosiveLevel > 0 && Math.random() < explosiveLevel * EnchantExplosive.TRIGGER_CHANCE_PER_LEVEL) {
            breakArea(player, tool, origin, 1);
        } else if (excavatorLevel > 0 && isShovelBlock(origin)) {
            breakArea(player, tool, origin, 1);
        } else if (scythe && (isCrop(origin) || isLeaves(origin))) {
            breakArea(player, tool, origin, 1);
        } else if (drilling) {
            breakVerticalLine(player, tool, origin);
        } else if (tunnel) {
            breakTunnel(player, tool, origin);
        } else if (lumberjackLevel > 0 && isLog(origin)) {
            chainBreakLogs(player, tool, origin);
        }
    }

    private void breakArea(Player player, Item tool, Block origin, int radius) {
        Level level = origin.getLevel();
        Vector3 direction = player.getDirectionVector();
        boolean horizontalFace = Math.abs(direction.y) < 0.5;

        for (int a = -radius; a <= radius; a++) {
            for (int b = -radius; b <= radius; b++) {
                if (a == 0 && b == 0) continue;
                Vector3 target = horizontalFace
                        ? origin.add(a, b, 0)
                        : origin.add(a, 0, b);
                Block block = level.getBlock(target);
                if (block.getId().equals(origin.getId()) ||
                        (isCrop(block) && isCrop(origin)) ||
                        (isLeaves(block) && isLeaves(origin))) {
                    level.useBreakOn(target, tool, player, true);
                }
            }
        }
    }

    private void breakVerticalLine(Player player, Item tool, Block origin) {
        Level level = origin.getLevel();
        for (int dy = -1; dy <= 1; dy++) {
            if (dy == 0) continue;
            Vector3 target = origin.add(0, dy, 0);
            Block block = level.getBlock(target);
            if (block.getId().equals(origin.getId())) {
                level.useBreakOn(target, tool, player, true);
            }
        }
    }

    private void breakTunnel(Player player, Item tool, Block origin) {
        Level level = origin.getLevel();
        Vector3 dir = player.getDirectionVector();
        Vector3 up = new Vector3(0, 1, 0);
        Vector3 right = dir.cross(up).normalize();
        if (right.lengthSquared() < 0.1) {
            right = new Vector3(1, 0, 0);
        }
        up = right.cross(dir).normalize();
        for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
                if (a == 0 && b == 0) continue;
                Vector3 offset = right.multiply(a).add(up.multiply(b));
                Vector3 target = origin.add(offset);
                Block block = level.getBlock(target);
                if (block.getId().equals(origin.getId())) {
                    level.useBreakOn(target, tool, player, true);
                }
            }
        }
    }

    private void chainBreakLogs(Player player, Item tool, Block origin) {
        Level level = origin.getLevel();
        Set<Long> visited = new HashSet<>();
        ArrayDeque<Vector3> queue = new ArrayDeque<>();
        queue.add(origin);
        int broken = 0;

        while (!queue.isEmpty() && broken < EnchantLumberjack.MAX_LOGS_PER_TRIGGER) {
            Vector3 pos = queue.poll();
            long key = Level.blockHash((int) pos.x, (int) pos.y, (int) pos.z, level);
            if (!visited.add(key)) continue;
            Block block = level.getBlock(pos);
            if (!isLog(block)) continue;
            if (!pos.equals(origin)) {
                level.useBreakOn(pos, tool, player, true);
            }
            broken++;
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dy == 0 && dz == 0) continue;
                        queue.add(pos.add(dx, dy, dz));
                    }
                }
            }
        }
    }

    // ------------------------------------------------------------------
    // Food
    // ------------------------------------------------------------------

    @EventHandler
    public void onFoodLevelChange(PlayerFoodLevelChangeEvent event) {
        Player player = event.getPlayer();
        Item helmet = player.getInventory().getHelmet();
        int gluttonLevel = EnchantUtil.getLevel(helmet, plugin.glutton);
        if (gluttonLevel > 0 && event.getFoodLevel() > player.getFoodData().getFood()) {
            float extraSaturation = gluttonLevel * EnchantGlutton.EXTRA_SATURATION_PER_LEVEL;
            player.getFoodData().setSaturation(player.getFoodData().getSaturation() + extraSaturation);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        for (int slot = 0; slot < player.getInventory().getSize(); slot++) {
            Item item = player.getInventory().getItem(slot);
            if (!item.isNull() && EnchantUtil.has(item, plugin.soulbound)) {
                int level = EnchantUtil.getLevel(item, plugin.soulbound);
                if (level <= 1) {
                    EnchantUtil.remove(item, plugin.soulbound);
                    item.setKeepOnDeath(false);
                    player.getInventory().setItem(slot, item);
                } else {
                    EnchantUtil.apply(item, plugin.soulbound, level - 1);
                    player.getInventory().setItem(slot, item);
                }
            }
        }
    }

    // ------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------

    private boolean isLog(Block block) {
        String name = block.getName().toLowerCase();
        return name.contains("log") || name.contains("wood");
    }

    private boolean isCrop(Block block) {
        String name = block.getName().toLowerCase();
        return name.contains("wheat") || name.contains("carrot") || name.contains("potato") ||
                name.contains("beetroot") || name.contains("melon") || name.contains("pumpkin") ||
                name.contains("cocoa") || name.contains("nether_wart");
    }

    private boolean isLeaves(Block block) {
        return block.getName().toLowerCase().contains("leaves");
    }

    private boolean isShovelBlock(Block block) {
        String name = block.getName().toLowerCase();
        return name.contains("dirt") || name.contains("grass") || name.contains("sand") ||
                name.contains("gravel") || name.contains("clay") || name.contains("snow");
    }

    private Item smeltedFormOf(Block block) {
        String name = block.getName().toLowerCase();
        if (name.contains("iron_ore")) return Item.get(Item.IRON_INGOT);
        if (name.contains("gold_ore")) return Item.get(Item.GOLD_INGOT);
        if (name.contains("copper_ore")) return Item.get(Item.COPPER_INGOT);
        if (name.contains("ancient_debris")) return Item.get(Item.NETHERITE_SCRAP);
        if (name.contains("sand")) return Item.get(Block.GLASS);
        if (name.contains("cobblestone")) return Item.get(Block.STONE);
        if (name.contains("clay")) return Item.get(Item.CLAY_BALL);
        return null;
    }
}