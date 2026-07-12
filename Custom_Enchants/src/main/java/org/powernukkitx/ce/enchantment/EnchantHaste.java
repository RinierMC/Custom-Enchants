package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantHaste extends Enchantment implements CustomEnchant {
    public EnchantHaste() {
        super(new Identifier("enchant:haste"), "Haste", Rarity.UNCOMMON, EnchantmentType.ALL);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 12 + (level - 1) * 20;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return getMinEnchantAbility(level) + 30;
    }

    @Override
    public String getCategory() {
        return "Universal (any item)";
    }

    @Override
    public String getDescription() {
        return "Gives Haste effect while held or worn.";
    }
}