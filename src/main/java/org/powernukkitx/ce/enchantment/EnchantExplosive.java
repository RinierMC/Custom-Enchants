package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.item.Item;
import org.powernukkitx.utils.Identifier;

public class EnchantExplosive extends Enchantment implements CustomEnchant {
    public static final double TRIGGER_CHANCE_PER_LEVEL = 0.15;

    public EnchantExplosive() {
        super(new Identifier("enchant:explosive"), "Explosive", Rarity.RARE, EnchantmentType.DIGGER);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 20 + (level - 1) * 25;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return getMinEnchantAbility(level) + 30;
    }

    @Override
    public boolean canEnchant(Item item) {
        return item.getId().toString().toLowerCase().contains("pickaxe");
    }

    @Override
    public String getCategory() {
        return "Tool (Pickaxe only)";
    }

    @Override
    public String getDescription() {
        return "Chance to break a 3×3 area when mining.";
    }
}