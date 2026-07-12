package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.Item;
import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public abstract class BaseCustomEnchant extends Enchantment implements CustomEnchant {

    public BaseCustomEnchant(Identifier identifier, String name, Rarity rarity, EnchantmentType type) {
        super(identifier, name, rarity, type);
    }

    /**
     * The maximum level this enchantment can have.
     */
    @Override
    public abstract int getMaxLevel();

    /**
     * The minimum enchantability for a given level (used by the enchanting table).
     */
    @Override
    public abstract int getMinEnchantAbility(int level);

    /**
     * The maximum enchantability for a given level (used by the enchanting table).
     */
    @Override
    public abstract int getMaxEnchantAbility(int level);

    /**
     * Override this to restrict which items can receive this enchantment.
     * Default: returns true (all items).
     */
    @Override
    public boolean canEnchant(Item item) {
        return true;
    }

    /**
     * Provide a short category string, e.g. "Weapon", "Armor", "Tool".
     */
    @Override
    public abstract String getCategory();

    /**
     * Provide a brief description of what the enchantment does.
     */
    @Override
    public abstract String getDescription();
}