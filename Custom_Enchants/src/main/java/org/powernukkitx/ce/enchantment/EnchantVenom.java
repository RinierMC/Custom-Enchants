package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantVenom extends Enchantment implements CustomEnchant {
    public static final int DURATION_PER_LEVEL = 40; // ticks, 2 seconds per level

    public EnchantVenom() {
        super(new Identifier("enchant:venom"), "Venom", Rarity.RARE, EnchantmentType.SWORD);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 15 + (level - 1) * 20;
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
        return "Applies Poison to the target on hit.";
    }
}