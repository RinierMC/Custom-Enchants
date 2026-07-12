package org.powernukkitx.ce.enchantment;

/**
 * Implemented by every custom enchantment to provide metadata for
 * listing, books, and command feedback.
 */
public interface CustomEnchant {

    String getCategory();

    String getDescription();
}