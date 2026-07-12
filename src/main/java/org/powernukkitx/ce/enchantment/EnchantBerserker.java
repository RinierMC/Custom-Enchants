package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantBerserker extends Enchantment implements CustomEnchant {
    public static final double BONUS_PER_LEVEL = 0.10; // 10% per level
    public static final double HP_THRESHOLD = 0.50;    // 50% health

    public EnchantBerserker() {
        super(new Identifier("enchant:berserker"), "Berserker", Rarity.RARE, EnchantmentType.SWORD);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 20 + (level - 1) * 15;
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
        return "Deals extra damage when your health is below 50%.";
    }
}