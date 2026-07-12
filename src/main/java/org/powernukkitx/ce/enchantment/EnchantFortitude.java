package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantFortitude extends Enchantment implements CustomEnchant {
    public static final double REDUCTION_PER_LEVEL_PER_PIECE = 0.05; // 5% per level per piece

    public EnchantFortitude() {
        super(new Identifier("enchant:fortitude"), "Fortitude", Rarity.RARE, EnchantmentType.ARMOR);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 15 + (level - 1) * 15;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return getMinEnchantAbility(level) + 25;
    }

    @Override
    public String getCategory() {
        return "Armor (any piece)";
    }

    @Override
    public String getDescription() {
        return "Reduces all incoming damage by 5% per level per piece.";
    }
}