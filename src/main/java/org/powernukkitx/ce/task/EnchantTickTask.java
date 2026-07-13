package org.powernukkitx.ce.task;

import org.powernukkitx.Server;
import org.powernukkitx.entity.Entity;
import org.powernukkitx.entity.effect.EffectType;
import org.powernukkitx.entity.effect.Effect;
import org.powernukkitx.entity.item.EntityItem;
import org.powernukkitx.item.Item;
import org.powernukkitx.Player;
import org.powernukkitx.scheduler.Task;
import org.powernukkitx.ce.Main;
import org.powernukkitx.ce.enchantment.EnchantMagnet;
import org.powernukkitx.ce.enchantment.EnchantOverload;
import org.powernukkitx.ce.util.EnchantUtil;

public class EnchantTickTask extends Task {

    private static final int BASE_MAX_HEALTH = 20;

    private final Main plugin;

    public EnchantTickTask(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onRun(int currentTick) {
        for (Player player : Server.getInstance().getOnlinePlayers().values()) {
            if (player == null || !player.isOnline()) continue;
            if (!player.isAlive() || !player.spawned) {
                continue;
            }

            applyOverload(player);
            applyHaste(player);
            applyVitality(player);
            applyMagnet(player);
            applyQuickstep(player);
            applyLeaper(player);
            applyAquatic(player);
            applyNightVision(player);
            applyFireResistance(player);
        }
    }

    private void applyOverload(Player player) {
        int totalLevels = 0;
        for (Item piece : player.getInventory().getArmorContents()) {
            totalLevels += EnchantUtil.getLevel(piece, plugin.overload);
        }
        int targetMaxHealth = BASE_MAX_HEALTH + totalLevels * EnchantOverload.EXTRA_HP_PER_LEVEL;
        int currentMax = player.getHealthMax();

        if (currentMax != targetMaxHealth) {
            int healthDifference = targetMaxHealth - currentMax;
            player.setHealthMax(targetMaxHealth);

            float newHealth = player.getHealthCurrent() + healthDifference;
            if (newHealth < 1) newHealth = 1;
            if (newHealth > targetMaxHealth) newHealth = targetMaxHealth;
            player.setHealthCurrent(newHealth);
        }
    }

    private void applyHaste(Player player) {
        int level = EnchantUtil.getLevel(player.getInventory().getItemInMainHand(), plugin.haste);
        for (Item piece : player.getInventory().getArmorContents()) {
            level = Math.max(level, EnchantUtil.getLevel(piece, plugin.haste));
        }
        if (level > 0) {
            Effect haste = Effect.get(EffectType.HASTE)
                    .setAmplifier(level - 1)
                    .setDuration(40)
                    .setVisible(false);
            player.addEffect(haste);
        }
    }

    private void applyVitality(Player player) {
        int totalLevels = 0;
        for (Item piece : player.getInventory().getArmorContents()) {
            totalLevels += EnchantUtil.getLevel(piece, plugin.vitality);
        }
        if (totalLevels > 0 && player.getHealthCurrent() < player.getHealthMax()) {
            float heal = 0.5f * totalLevels;
            player.setHealthCurrent(Math.min(player.getHealthMax(), player.getHealthCurrent() + heal));
        }
    }

    private void applyMagnet(Player player) {
        Item boots = player.getInventory().getBoots();
        int level = EnchantUtil.getLevel(boots, plugin.magnet);
        if (level <= 0) return;
        double radius = plugin.magnet.getRadius(level);
        for (Entity entity : player.getLevel().getNearbyEntities(
                player.getBoundingBox().grow(radius, radius, radius), player)) {
            if (entity instanceof EntityItem && entity.isAlive()) {
                entity.setMotion(player.subtract(entity).normalize().multiply(0.35));
            }
        }
    }

    private void applyQuickstep(Player player) {
        Item boots = player.getInventory().getBoots();
        int level = EnchantUtil.getLevel(boots, plugin.quickstep);
        if (level > 0) {
            Effect speed = Effect.get(EffectType.SPEED)
                    .setAmplifier(level - 1)
                    .setDuration(40)
                    .setVisible(false);
            player.addEffect(speed);
        }
    }

    private void applyLeaper(Player player) {
        Item boots = player.getInventory().getBoots();
        int level = EnchantUtil.getLevel(boots, plugin.leaper);
        if (level > 0) {
            Effect jump = Effect.get(EffectType.JUMP_BOOST)
                    .setAmplifier(level - 1)
                    .setDuration(40)
                    .setVisible(false);
            player.addEffect(jump);
        }
    }

    private void applyAquatic(Player player) {
        Item helmet = player.getInventory().getHelmet();
        if (EnchantUtil.has(helmet, plugin.aquatic)) {
            Effect water = Effect.get(EffectType.WATER_BREATHING)
                    .setAmplifier(0)
                    .setDuration(40)
                    .setVisible(false);
            player.addEffect(water);
        }
    }

    private void applyNightVision(Player player) {
        Item helmet = player.getInventory().getHelmet();
        if (EnchantUtil.has(helmet, plugin.nightVision)) {
            Effect night = Effect.get(EffectType.NIGHT_VISION)
                    .setAmplifier(0)
                    .setDuration(40)
                    .setVisible(false);
            player.addEffect(night);
        }
    }

    private void applyFireResistance(Player player) {
        int totalLevels = 0;
        for (Item piece : player.getInventory().getArmorContents()) {
            totalLevels += EnchantUtil.getLevel(piece, plugin.fireResistance);
        }
        if (totalLevels > 0) {
            Effect fire = Effect.get(EffectType.FIRE_RESISTANCE)
                    .setAmplifier(0)
                    .setDuration(40)
                    .setVisible(false);
            player.addEffect(fire);
        }
    }
}