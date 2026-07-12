package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantGlutton extends Enchantment implements CustomEnchant {
    public static final int EXTRA_SATURATION_PER_LEVEL = 2;

    public EnchantGlutton() {
        super(new Identifier("enchant:glutton"), "Glutton", Rarity.UNCOMMON, EnchantmentType.ARMOR_HEAD);
    }

    @Override
    public int getMaxLevel() {
        return 2;
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
        return "Helmet only";
    }

    @Override
    public String getDescription() {
        return "Increases saturation gained from food.";
    }
}