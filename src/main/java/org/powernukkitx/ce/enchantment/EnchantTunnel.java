package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.item.Item;
import org.powernukkitx.utils.Identifier;

public class EnchantTunnel extends Enchantment implements CustomEnchant {
    public EnchantTunnel() {
        super(new Identifier("enchant:tunnel"), "Tunnel", Rarity.RARE, EnchantmentType.DIGGER);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 25;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return 55;
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
        return "Breaks a 3×1 line in the direction you are facing.";
    }
}