package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantVampiric extends Enchantment implements CustomEnchant {
    public static final double LIFESTEAL_PERCENT_PER_LEVEL = 0.10;

    public EnchantVampiric() {
        super(new Identifier("enchant:vampiric"), "Vampiric", Rarity.RARE, EnchantmentType.SWORD);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 20 + (level - 1) * 20;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return getMinEnchantAbility(level) + 30;
    }

    @Override
    public String getCategory() {
        return "Weapon (Sword/Axe)";
    }

    @Override
    public String getDescription() {
        return "Heals you for a percentage of damage dealt.";
    }
}