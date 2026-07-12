package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.item.Item;
import org.powernukkitx.utils.Identifier;

public class EnchantExcavator extends Enchantment implements CustomEnchant {
    public EnchantExcavator() {
        super(new Identifier("enchant:excavator"), "Excavator", Rarity.UNCOMMON, EnchantmentType.DIGGER);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 15;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return 45;
    }

    @Override
    public boolean canEnchant(Item item) {
        return item.getId().toString().toLowerCase().contains("shovel");
    }

    @Override
    public String getCategory() {
        return "Tool (Shovel only)";
    }

    @Override
    public String getDescription() {
        return "Breaks a 3×3 area of dirt/sand/gravel.";
    }
}