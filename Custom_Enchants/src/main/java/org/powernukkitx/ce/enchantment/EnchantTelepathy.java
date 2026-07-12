package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.item.Item;
import org.powernukkitx.utils.Identifier;

public class EnchantTelepathy extends Enchantment implements CustomEnchant {
    public EnchantTelepathy() {
        super(new Identifier("enchant:telepathy"), "Telepathy", Rarity.UNCOMMON, EnchantmentType.DIGGER);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getMinEnchantAbility(int level) {
        return 15;
    }

    @Override
    public int getMaxEnchantAbility(int level) {
        return 45;
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
        return "Mined items go directly into your inventory.";
    }
}