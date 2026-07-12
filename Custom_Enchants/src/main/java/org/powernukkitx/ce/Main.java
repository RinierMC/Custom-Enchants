package org.powernukkitx.ce;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.plugin.PluginBase;
import org.powernukkitx.registry.RegisterException;
import org.powernukkitx.utils.TextFormat;

import org.powernukkitx.ce.command.CustomEnchantCommand;
import org.powernukkitx.ce.enchantment.*;
import org.powernukkitx.ce.listener.EnchantBookListener;
import org.powernukkitx.ce.listener.EnchantListener;
import org.powernukkitx.ce.task.EnchantTickTask;

import java.util.LinkedHashMap;
import java.util.Map;

public class Main extends PluginBase {
    public static Main INSTANCE;

    public EnchantOverload overload;
    public EnchantHaste haste;
    public EnchantVitality vitality;
    public EnchantVampiric vampiric;
    public EnchantExecutioner executioner;
    public EnchantVoidwalker voidwalker;
    public EnchantExplosive explosive;
    public EnchantAutoSmelt autoSmelt;
    public EnchantExcavator excavator;
    public EnchantLumberjack lumberjack;
    public EnchantTelepathy telepathy;
    public EnchantFeatherfall featherfall;
    public EnchantMagnet magnet;
    public EnchantReflect reflect;
    public EnchantBerserker berserker;
    public EnchantFortitude fortitude;
    public EnchantQuickstep quickstep;
    public EnchantLeaper leaper;
    public EnchantAquatic aquatic;
    public EnchantNightVision nightVision;
    public EnchantGlutton glutton;
    public EnchantHarvest harvest;
    public EnchantScythe scythe;
    public EnchantDrilling drilling;
    public EnchantTunnel tunnel;
    public EnchantFireResistance fireResistance;
    public EnchantSoulbound soulbound;
    public EnchantVenom venom;
    public EnchantWithering withering;
    public EnchantCripple cripple;

    public final Map<String, Enchantment> enchantsByKey = new LinkedHashMap<>();

    @Override
    public void onLoad() {
        this.getLogger().info(TextFormat.WHITE + "Custom_Enchants has been loaded!");
        INSTANCE = this;
        registerEnchants();
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new EnchantListener(this), this);
        this.getServer().getPluginManager().registerEvents(new EnchantBookListener(this), this);
        this.getServer().getScheduler().scheduleRepeatingTask(this, new EnchantTickTask(this), 20);

        this.getServer().getCommandMap().register("customenchants", new CustomEnchantCommand(this));

        this.getLogger().info(TextFormat.DARK_GREEN + "Custom_Enchants has been successfully launched with "
                + TextFormat.YELLOW + "30" + TextFormat.DARK_GREEN + " custom enchantments!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info(TextFormat.DARK_RED + "Custom_Enchants has been disabled!");
    }

    public void registerCustomEnchant(Enchantment enchant, String key) {
        if (enchant == null || key == null || key.isEmpty()) {
            throw new IllegalArgumentException("Enchantment and key must not be null/empty");
        }
        Enchantment.register(enchant, true);
        enchantsByKey.put(key.toLowerCase(), enchant);
        getLogger().info("Registered custom enchantment: " + key + " -> " + enchant.getName());
    }

    private void registerEnchants() {
        // Universal
        Enchantment.register(haste = new EnchantHaste(), true);
        Enchantment.register(voidwalker = new EnchantVoidwalker(), true);
        Enchantment.register(soulbound = new EnchantSoulbound(), true);

        // Armor (any piece)
        Enchantment.register(overload = new EnchantOverload(), true);
        Enchantment.register(vitality = new EnchantVitality(), true);
        Enchantment.register(fortitude = new EnchantFortitude(), true);
        Enchantment.register(fireResistance = new EnchantFireResistance(), true);

        // Armor (single slot)
        Enchantment.register(featherfall = new EnchantFeatherfall(), true);
        Enchantment.register(magnet = new EnchantMagnet(), true);
        Enchantment.register(reflect = new EnchantReflect(), true);
        Enchantment.register(quickstep = new EnchantQuickstep(), true);
        Enchantment.register(leaper = new EnchantLeaper(), true);
        Enchantment.register(aquatic = new EnchantAquatic(), true);
        Enchantment.register(nightVision = new EnchantNightVision(), true);
        Enchantment.register(glutton = new EnchantGlutton(), true);

        // Weapons
        Enchantment.register(vampiric = new EnchantVampiric(), true);
        Enchantment.register(executioner = new EnchantExecutioner(), true);
        Enchantment.register(berserker = new EnchantBerserker(), true);
        Enchantment.register(venom = new EnchantVenom(), true);
        Enchantment.register(withering = new EnchantWithering(), true);
        Enchantment.register(cripple = new EnchantCripple(), true);

        // Tools (any digger)
        Enchantment.register(autoSmelt = new EnchantAutoSmelt(), true);

        // Tools (single tool)
        Enchantment.register(explosive = new EnchantExplosive(), true);
        Enchantment.register(telepathy = new EnchantTelepathy(), true);
        Enchantment.register(excavator = new EnchantExcavator(), true);
        Enchantment.register(lumberjack = new EnchantLumberjack(), true);
        Enchantment.register(harvest = new EnchantHarvest(), true);
        Enchantment.register(scythe = new EnchantScythe(), true);
        Enchantment.register(drilling = new EnchantDrilling(), true);
        Enchantment.register(tunnel = new EnchantTunnel(), true);

        enchantsByKey.put("haste", haste);
        enchantsByKey.put("voidwalker", voidwalker);
        enchantsByKey.put("soulbound", soulbound);
        enchantsByKey.put("overload", overload);
        enchantsByKey.put("vitality", vitality);
        enchantsByKey.put("fortitude", fortitude);
        enchantsByKey.put("fireresistance", fireResistance);
        enchantsByKey.put("featherfall", featherfall);
        enchantsByKey.put("magnet", magnet);
        enchantsByKey.put("reflect", reflect);
        enchantsByKey.put("quickstep", quickstep);
        enchantsByKey.put("leaper", leaper);
        enchantsByKey.put("aquatic", aquatic);
        enchantsByKey.put("nightvision", nightVision);
        enchantsByKey.put("glutton", glutton);
        enchantsByKey.put("vampiric", vampiric);
        enchantsByKey.put("executioner", executioner);
        enchantsByKey.put("berserker", berserker);
        enchantsByKey.put("venom", venom);
        enchantsByKey.put("withering", withering);
        enchantsByKey.put("cripple", cripple);
        enchantsByKey.put("autosmelt", autoSmelt);
        enchantsByKey.put("explosive", explosive);
        enchantsByKey.put("telepathy", telepathy);
        enchantsByKey.put("excavator", excavator);
        enchantsByKey.put("lumberjack", lumberjack);
        enchantsByKey.put("harvest", harvest);
        enchantsByKey.put("scythe", scythe);
        enchantsByKey.put("drilling", drilling);
        enchantsByKey.put("tunnel", tunnel);
    }
}