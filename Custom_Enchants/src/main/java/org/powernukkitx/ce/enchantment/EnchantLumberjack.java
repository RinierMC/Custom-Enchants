package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.item.Item;
import org.powernukkitx.utils.Identifier;

public class EnchantLumberjack extends Enchantment implements CustomEnchant {
    public static final int MAX_LOGS_PER_TRIGGER = 128;

    public EnchantLumberjack() {
        super(new Identifier("enchant:lumberjack"), "Lumberjack", Rarity.RARE, EnchantmentType.DIGGER);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 20;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return 50;
    }

    @Override
    public boolean canEnchant(Item item) {
        return item.getId().toString().toLowerCase().contains("axe")
                && !item.getId().toString().toLowerCase().contains("pickaxe");
    }

    @Override
    public String getCategory() {
        return "Tool (Axe only)";
    }

    @Override
    public String getDescription() {
        return "Breaks an entire tree when chopping a log.";
    }
}