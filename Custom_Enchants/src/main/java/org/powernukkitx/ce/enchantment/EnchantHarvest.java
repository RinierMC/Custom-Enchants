package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantHarvest extends Enchantment implements CustomEnchant {
    public static final int EXTRA_DROPS_PER_LEVEL = 1;

    public EnchantHarvest() {
        super(new Identifier("enchant:harvest"), "Harvest", Rarity.UNCOMMON, EnchantmentType.DIGGER);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 10 + (level - 1) * 10;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return getMinEnchantAbility(level) + 25;
    }

    @Override
    public String getCategory() {
        return "Tool (Hoe only)";
    }

    @Override
    public String getDescription() {
        return "Increases crop drops when harvesting.";
    }
}