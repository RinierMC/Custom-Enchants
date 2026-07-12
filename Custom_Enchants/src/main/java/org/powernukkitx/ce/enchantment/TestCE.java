package org.powernukkitx.ce.enchantment;

import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.item.enchantment.EnchantmentType;
import org.powernukkitx.utils.Identifier;

public class TestCE extends Enchantment {
    public TestCE() {
        super(new Identifier("enchant:test"), "Test", Rarity.COMMON, EnchantmentType.ALL);
    }
}
