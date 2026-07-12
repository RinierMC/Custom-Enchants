package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantQuickstep extends Enchantment implements CustomEnchant {
    public EnchantQuickstep() {
        super(new Identifier("enchant:quickstep"), "Quickstep", Rarity.UNCOMMON, EnchantmentType.ARMOR_FEET);
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
        return getMinEnchantAbility(level) + 20;
    }

    @Override
    public String getCategory() {
        return "Boots only";
    }

    @Override
    public String getDescription() {
        return "Increases movement speed while worn.";
    }
}