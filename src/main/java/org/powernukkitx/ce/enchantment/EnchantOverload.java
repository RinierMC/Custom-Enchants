package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantOverload extends Enchantment implements CustomEnchant {
    public static final int EXTRA_HP_PER_LEVEL = 2;

    public EnchantOverload() {
        super(new Identifier("enchant:overload"), "Overload", Rarity.RARE, EnchantmentType.ARMOR);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 10 + (level - 1) * 15;
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
        return "Grants +2 max health per level per piece.";
    }
}