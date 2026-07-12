package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantExecutioner extends Enchantment implements CustomEnchant {
    public static final double LOW_HP_THRESHOLD = 0.25;
    public static final double BONUS_DAMAGE_PERCENT_PER_LEVEL = 0.25;

    public EnchantExecutioner() {
        super(new Identifier("enchant:executioner"), "Executioner", Rarity.RARE, EnchantmentType.SWORD);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 25 + (level - 1) * 20;
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
        return "Deals extra damage to targets below 25% health.";
    }
}