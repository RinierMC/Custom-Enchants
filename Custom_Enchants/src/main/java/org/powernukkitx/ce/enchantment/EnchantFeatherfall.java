package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.item.Item;
import org.powernukkitx.utils.Identifier;

public class EnchantFeatherfall extends Enchantment implements CustomEnchant {
    public static final double REDUCTION_PER_LEVEL = 0.40;

    public EnchantFeatherfall() {
        super(new Identifier("enchant:featherfall"), "Featherfall", Rarity.UNCOMMON, EnchantmentType.ARMOR_FEET);
    }

    @Override
    public int getMaxLevel() {
        return 3;
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
    public boolean canEnchant(Item item) {
        return item.getId().toString().toLowerCase().contains("boots");
    }

    @Override
    public String getCategory() {
        return "Boots only";
    }

    @Override
    public String getDescription() {
        return "Reduces fall damage by 40% per level.";
    }
}