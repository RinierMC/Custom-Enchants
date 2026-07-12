package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.item.Item;
import org.powernukkitx.utils.Identifier;

public class EnchantReflect extends Enchantment implements CustomEnchant {
    public static final double REFLECT_PERCENT_PER_LEVEL = 0.15;

    public EnchantReflect() {
        super(new Identifier("enchant:reflect"), "Reflect", Rarity.RARE, EnchantmentType.ARMOR_TORSO);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 15 + (level - 1) * 20;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return getMinEnchantAbility(level) + 25;
    }

    @Override
    public boolean canEnchant(Item item) {
        return item.getId().toString().toLowerCase().contains("chestplate");
    }

    @Override
    public String getCategory() {
        return "Chestplate only";
    }

    @Override
    public String getDescription() {
        return "Reflects a percentage of melee damage back to the attacker.";
    }
}