package org.powernukkitx.ce.util;

import org.powernukkitx.ce.Main;
import org.powernukkitx.ce.enchantment.CustomEnchant;
import org.powernukkitx.ce.enchantment.EnchantSoulbound;
import org.powernukkitx.item.Item;
import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.nbt.tag.CompoundTag;
import org.powernukkitx.nbt.tag.ListTag;
import org.powernukkitx.utils.TextFormat;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EnchantUtil {
    private EnchantUtil() {}

    private static final Pattern MARKER = Pattern.compile("\\((?<key>[a-zA-Z0-9_]+)\\)$");
    private static final String CE_COLOR = "" + TextFormat.RESET + TextFormat.AQUA + TextFormat.BOLD;

    public static String keyOf(Enchantment ec) {
        assert ec.getIdentifier() != null;
        String id = ec.getIdentifier().toString();
        int idx = id.indexOf(':');
        return idx >= 0 ? id.substring(idx + 1) : id;
    }

    static Enchantment cloneWithLevel(Enchantment ec, int level) {
        try {
            Method cloneMethod = Enchantment.class.getDeclaredMethod("clone");
            cloneMethod.setAccessible(true);
            Enchantment clone = (Enchantment) cloneMethod.invoke(ec);
            return clone.setLevel(level, true);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    public static boolean apply(Item item, Enchantment ec, int level) {
        if (item == null || item.isNull() || ec == null) return false;
        if (!ec.canEnchant(item)) return false;

        Enchantment leveled = cloneWithLevel(ec, level);
        if (leveled == null) return false;

        assert ec.getIdentifier() != null;
        // Remove any existing entry for this enchantment first
        item.removeEnchantment(ec.getIdentifier());
        // Add the new entry
        item.addEnchantment(leveled);

        // Soulbound: mark keepOnDeath
        if (ec instanceof EnchantSoulbound) {
            item.setKeepOnDeath(true);
        }

        // Clear any custom name set by the client (we manage lore ourselves)
        item.clearCustomName();

        // Add/update lore
        String key = keyOf(ec);
        List<String> lore = currentLore(item);
        lore.removeIf(line -> matchesKey(line, key));
        lore.add(displayLine(ec, level));
        item.setLore(lore.toArray(new String[0]));
        return true;
    }

    public static void remove(Item item, Enchantment ec) {
        if (item == null || item.isNull() || ec == null) return;
        assert ec.getIdentifier() != null;
        String id = ec.getIdentifier().toString();

        item.removeEnchantment(id);

        if (ec instanceof EnchantSoulbound) {
            item.setKeepOnDeath(false);
        }

        String key = keyOf(ec);
        List<String> lore = currentLore(item);
        if (lore.removeIf(line -> matchesKey(line, key))) {
            item.setLore(lore.toArray(new String[0]));
        }

        CompoundTag tag = item.getNbt();
        if (tag != null && tag.contains("custom_ench")) {
            ListTag<CompoundTag> custom = tag.getList("custom_ench", CompoundTag.class);
            if (custom.size() == 0) {
                tag.remove("custom_ench");
                item.setNbt(tag);
            }
        }
        item.clearCustomName();
    }

    public static int getLevel(Item item, Enchantment ec) {
        if (item == null || item.isNull() || ec == null) return 0;
        assert ec.getIdentifier() != null;
        return item.getCustomEnchantmentLevel(ec.getIdentifier());
    }

    public static boolean has(Item item, Enchantment ec) {
        if (item == null || item.isNull() || ec == null) return false;
        assert ec.getIdentifier() != null;
        return item.hasCustomEnchantment(ec.getIdentifier());
    }

    // -------- Book creation ----------
    public static Item createEnchantBook(Enchantment enchant, int level) {
        if (enchant == null || level < 1) return null;
        Item book = Item.get(Item.BOOK);
        book.setCustomName(TextFormat.RESET + "" + TextFormat.BOLD + TextFormat.AQUA + enchant.getName());

        List<String> lore = new ArrayList<>();
        lore.add(TextFormat.RESET + "" + TextFormat.GRAY + "Level: " + TextFormat.WHITE + level);
        if (enchant instanceof CustomEnchant ce) {
            lore.add(TextFormat.RESET + "" + TextFormat.GRAY + "Category: " + TextFormat.WHITE + ce.getCategory());
            lore.add(TextFormat.RESET + "" + TextFormat.GRAY + "Description: " + TextFormat.WHITE + ce.getDescription());
        }
        lore.add(TextFormat.RESET + "" + TextFormat.GRAY + "Rarity: " + TextFormat.WHITE + enchant.getRarity().name());
        book.setLore(lore.toArray(new String[0]));

        CompoundTag tag = book.getOrCreateNbt();
        tag.putString("ce_enchant_key", keyOf(enchant));
        tag.putInt("ce_enchant_level", level);
        book.setNbt(tag);
        return book;
    }

    public static Enchantment getEnchantFromBook(Item book, Main plugin) {
        if (book == null || book.isNull()) return null;
        CompoundTag tag = book.getNbt();
        if (tag == null) return null;
        String key = tag.getString("ce_enchant_key");
        if (key.isEmpty()) return null;
        return plugin.enchantsByKey.get(key);
    }

    public static int getLevelFromBook(Item book) {
        if (book == null || book.isNull()) return 0;
        CompoundTag tag = book.getNbt();
        if (tag == null) return 0;
        return tag.getInt("ce_enchant_level");
    }

    // -------- Internal helpers ----------
    private static boolean matchesKey(String line, String key) {
        Matcher m = MARKER.matcher(TextFormat.clean(line));
        return m.find() && m.group("key").equals(key);
    }

    private static List<String> currentLore(Item item) {
        String[] existing = item.getLore();
        List<String> lore = new ArrayList<>();
        if (existing != null) {
            lore.addAll(Arrays.asList(existing));
        }
        return lore;
    }

    private static String displayLine(Enchantment ec, int level) {
        String key = keyOf(ec);
        return CE_COLOR + ec.getName().toUpperCase() + " " + toRoman(level)
                + TextFormat.RESET + " " + TextFormat.DARK_GRAY + "(" + key + ")";
    }

    private static String toRoman(int level) {
        String[] romans = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
        return level > 0 && level < romans.length ? romans[level] : String.valueOf(level);
    }
}