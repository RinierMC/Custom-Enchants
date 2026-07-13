package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantSoulbound extends Enchantment implements CustomEnchant {
    public EnchantSoulbound() {
        super(new Identifier("enchant:soulbound"), "Soulbound", Rarity.VERY_RARE, EnchantmentType.ALL);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 30;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return 60;
    }

    @Override
    public String getCategory() {
        return "Universal (any item)";
    }

    @Override
    public String getDescription() {
        return "Item is not dropped on death.";
    }
}