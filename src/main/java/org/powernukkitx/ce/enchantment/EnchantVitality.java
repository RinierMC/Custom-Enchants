package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantVitality extends Enchantment implements CustomEnchant {
    public EnchantVitality() {
        super(new Identifier("enchant:vitality"), "Vitality", Rarity.UNCOMMON, EnchantmentType.ARMOR);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 15 + (level - 1) * 20;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return getMinEnchantAbility(level) + 30;
    }

    @Override
    public String getCategory() {
        return "Armor (any piece)";
    }

    @Override
    public String getDescription() {
        return "Slowly regenerates health over time while worn.";
    }
}