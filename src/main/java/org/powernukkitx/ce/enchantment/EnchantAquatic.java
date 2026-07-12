package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantAquatic extends Enchantment implements CustomEnchant {
    public EnchantAquatic() {
        super(new Identifier("enchant:aquatic"), "Aquatic", Rarity.UNCOMMON, EnchantmentType.ARMOR_HEAD);
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
        return "Helmet only";
    }

    @Override
    public String getDescription() {
        return "Grants Water Breathing while worn.";
    }
}