package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.item.Item;
import org.powernukkitx.utils.Identifier;

public class EnchantMagnet extends Enchantment implements CustomEnchant {
    public static final double BASE_RADIUS = 4.0;
    public static final double RADIUS_PER_LEVEL = 3.0;

    public EnchantMagnet() {
        super(new Identifier("enchant:magnet"), "Magnet", Rarity.UNCOMMON, EnchantmentType.ARMOR_FEET);
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
        return getMinEnchantAbility(level) + 20;
    }

    @Override
    public boolean canEnchant(Item item) {
        return item.getId().toString().toLowerCase().contains("boots");
    }

    public double getRadius(int level) {
        return BASE_RADIUS + (level - 1) * RADIUS_PER_LEVEL;
    }

    @Override
    public String getCategory() {
        return "Boots only";
    }

    @Override
    public String getDescription() {
        return "Pulls dropped items toward you within a radius.";
    }
}