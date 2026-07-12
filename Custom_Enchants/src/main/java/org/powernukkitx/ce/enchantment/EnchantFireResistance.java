package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantFireResistance extends Enchantment implements CustomEnchant {
    public EnchantFireResistance() {
        super(new Identifier("enchant:fireresistance"), "Fire Resistance", Rarity.UNCOMMON, EnchantmentType.ARMOR);
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
    public String getCategory() {
        return "Armor (any piece)";
    }

    @Override
    public String getDescription() {
        return "Grants Fire Resistance while worn.";
    }
}