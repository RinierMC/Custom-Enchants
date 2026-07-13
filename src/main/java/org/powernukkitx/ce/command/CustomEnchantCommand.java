package org.powernukkitx.ce.command;

import org.powernukkitx.Player;
import org.powernukkitx.ce.Main;
import org.powernukkitx.ce.enchantment.CustomEnchant;
import org.powernukkitx.ce.util.EnchantUtil;
import org.powernukkitx.command.Command;
import org.powernukkitx.command.CommandSender;
import org.powernukkitx.item.Item;
import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.utils.TextFormat;

import java.util.Map;

@SuppressWarnings("unused")
public class CustomEnchantCommand extends Command {
    private final Main plugin;

    public CustomEnchantCommand(Main plugin) {
        super("customenchant", "Apply or list custom enchantments",
                "/customenchant <enchant> <level|remove> | /customenchant list",
                new String[]{"ce"});
        this.plugin = plugin;
        this.setPermission("customenchants.command.enchant");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.testPermission(sender)) return true;

        if (!(sender instanceof Player player)) {
            sender.sendMessage(TextFormat.RED + "This command can only be used in-game.");
            return true;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
            sendList(sender);
            return true;
        }

        if (args.length < 2) {
            sendUsage(sender);
            return true;
        }

        boolean removing = args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("0");

        Enchantment enchant = plugin.enchantsByKey.get(args[0].toLowerCase());
        if (enchant == null) {
            sender.sendMessage(TextFormat.RED + "Unknown enchantment '" + args[0] + "'.");
            sendAvailable(sender);
            return true;
        }

        Item item = player.getInventory().getItemInMainHand();
        boolean hasItem = item != null && !item.isNull();

        if (removing) {
            if (!hasItem) {
                sender.sendMessage(TextFormat.RED + "You must hold an item to remove an enchant.");
                return true;
            }
            EnchantUtil.remove(item, enchant);
            player.getInventory().setItemInMainHand(item);
            sender.sendMessage(TextFormat.YELLOW + "Removed " + enchant.getName() + " from your held item.");
            return true;
        }

        int level;
        try {
            level = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(TextFormat.RED + "Level must be a whole number (or 'remove').");
            return true;
        }
        if (level < 1) {
            sender.sendMessage(TextFormat.RED + "Level must be at least 1.");
            return true;
        }

        // Enforce max level
        if (level > enchant.getMaxLevel()) {
            sender.sendMessage(TextFormat.RED + enchant.getName() + " has a maximum level of " + enchant.getMaxLevel() + ".");
            return true;
        }

        if (hasItem && enchant.canEnchant(item)) {
            if (!EnchantUtil.apply(item, enchant, level)) {
                sender.sendMessage(TextFormat.RED + enchant.getName()
                        + " can't be applied to that item (wrong item type for this enchant).");
                return true;
            }
            player.getInventory().setItemInMainHand(item);
            sender.sendMessage(TextFormat.GREEN + "Applied " + TextFormat.YELLOW + enchant.getName()
                    + " " + level + TextFormat.GREEN + " to your held item.");
            return true;
        }

        Item book = EnchantUtil.createEnchantBook(enchant, level);
        if (book == null) {
            sender.sendMessage(TextFormat.RED + "Failed to create enchantment book.");
            return true;
        }

        Item[] remaining = player.getInventory().addItem(book);
        if (remaining.length > 0) {
            sender.sendMessage(TextFormat.RED + "Your inventory is full.");
            return true;
        }

        sender.sendMessage(TextFormat.GREEN + "You received a " + TextFormat.YELLOW + enchant.getName()
                + " " + level + TextFormat.GREEN + " book.");
        sender.sendMessage(TextFormat.GRAY + "Hold the book and drag it onto the item you want to enchant.");
        return true;
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(TextFormat.YELLOW + "Usage: /customenchant <enchant> <level|remove>");
        sender.sendMessage(TextFormat.YELLOW + "       /customenchant list");
        sendAvailable(sender);
    }

    private void sendAvailable(CommandSender sender) {
        sender.sendMessage(TextFormat.GRAY + "Available: " + String.join(", ", plugin.enchantsByKey.keySet()));
    }

    private void sendList(CommandSender sender) {
        sender.sendMessage(TextFormat.GOLD + "=== Custom Enchantments ===");
        for (Map.Entry<String, Enchantment> entry : plugin.enchantsByKey.entrySet()) {
            Enchantment ench = entry.getValue();
            String key = entry.getKey();
            String name = ench.getName();
            String category = ench instanceof CustomEnchant ? ((CustomEnchant) ench).getCategory() : "Unknown";
            String desc = ench instanceof CustomEnchant ? ((CustomEnchant) ench).getDescription() : "";
            sender.sendMessage(TextFormat.AQUA + name + TextFormat.WHITE + " (" + key + ")" +
                    TextFormat.GRAY + " — " + category + TextFormat.DARK_GRAY + " | " + TextFormat.GRAY + desc);
        }
    }
}