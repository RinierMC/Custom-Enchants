package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantCripple extends Enchantment implements CustomEnchant {
    public static final int DURATION_PER_LEVEL = 60;

    public EnchantCripple() {
        super(new Identifier("enchant:cripple"), "Cripple", Rarity.UNCOMMON, EnchantmentType.SWORD);
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
        return getMinEnchantAbility(level) + 25;
    }

    @Override
    public String getCategory() {
        return "Weapon (Sword/Axe)";
    }

    @Override
    public String getDescription() {
        return "Applies Slowness to the target on hit.";
    }
}