package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.item.Item;
import org.powernukkitx.utils.Identifier;

public class EnchantScythe extends Enchantment implements CustomEnchant {
    public EnchantScythe() {
        super(new Identifier("enchant:scythe"), "Scythe", Rarity.RARE, EnchantmentType.DIGGER);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 20;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return 50;
    }

    @Override
    public boolean canEnchant(Item item) {
        return item.getId().toString().toLowerCase().contains("hoe");
    }

    @Override
    public String getCategory() {
        return "Tool (Hoe only)";
    }

    @Override
    public String getDescription() {
        return "Breaks a 3×3 area of crops and leaves.";
    }
}