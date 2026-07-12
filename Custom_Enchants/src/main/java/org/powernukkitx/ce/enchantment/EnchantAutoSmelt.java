package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class EnchantAutoSmelt extends Enchantment implements CustomEnchant {
    public EnchantAutoSmelt() {
        super(new Identifier("enchant:autosmelt"), "Auto Smelt", Rarity.RARE, EnchantmentType.DIGGER);
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
    public String getCategory() {
        return "Tool (all diggers)";
    }

    @Override
    public String getDescription() {
        return "Automatically smelts mined ores and blocks.";
    }
}