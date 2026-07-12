package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantVoidwalker extends Enchantment implements CustomEnchant {
    public static final double DODGE_CHANCE_PER_LEVEL = 0.06;

    public EnchantVoidwalker() {
        super(new Identifier("enchant:voidwalker"), "Voidwalker", Rarity.VERY_RARE, EnchantmentType.ALL);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 30 + (level - 1) * 25;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return getMinEnchantAbility(level) + 40;
    }

    @Override
    public String getCategory() {
        return "Universal (any item)";
    }

    @Override
    public String getDescription() {
        return "Gives a chance to completely dodge incoming damage.";
    }
}