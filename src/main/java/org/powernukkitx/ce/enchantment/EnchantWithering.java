package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantWithering extends Enchantment implements CustomEnchant {
    public static final int DURATION_PER_LEVEL = 40;

    public EnchantWithering() {
        super(new Identifier("enchant:withering"), "Withering", Rarity.VERY_RARE, EnchantmentType.SWORD);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 25 + (level - 1) * 25;
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
        return "Applies Wither to the target on hit.";
    }
}