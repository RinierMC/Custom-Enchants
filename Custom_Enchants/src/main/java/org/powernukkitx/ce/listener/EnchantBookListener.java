package org.powernukkitx.ce.listener;

import org.powernukkitx.Player;
import org.powernukkitx.event.EventHandler;
import org.powernukkitx.event.Listener;
import org.powernukkitx.event.inventory.ItemStackRequestActionEvent;
import org.powernukkitx.item.Item;
import org.powernukkitx.item.enchantment.Enchantment;
import org.powernukkitx.utils.TextFormat;
import org.powernukkitx.ce.Main;
import org.powernukkitx.ce.util.EnchantUtil;

import java.lang.reflect.Method;

@SuppressWarnings("unused")
public class EnchantBookListener implements Listener {
    private final Main plugin;

    public EnchantBookListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemStackRequestAction(ItemStackRequestActionEvent event) {
        Player player = event.getPlayer();
        if (player == null) return;

        Object action = event.getAction();
        if (action == null) return;

        String actionName = action.getClass().getSimpleName();
        if (!actionName.equals("PlaceAction") && !actionName.equals("SwapAction")) return;

        try {
            SlotInfo sourceInfo = getSlotInfo(action, "getSource");
            SlotInfo destInfo = getSlotInfo(action, "getDestination");
            if (sourceInfo == null || destInfo == null) return;

            Item sourceItem = getItemFromSlotInfo(player, sourceInfo);
            Item destItem = getItemFromSlotInfo(player, destInfo);
            if (sourceItem == null || destItem == null || sourceItem.isNull() || destItem.isNull()) return;

            Enchantment enchant = EnchantUtil.getEnchantFromBook(sourceItem, plugin);
            if (enchant == null) {
                enchant = EnchantUtil.getEnchantFromBook(destItem, plugin);
                if (enchant != null) {
                    Item temp = sourceItem;
                    sourceItem = destItem;
                    destItem = temp;
                    SlotInfo tempInfo = sourceInfo;
                    sourceInfo = destInfo;
                    destInfo = tempInfo;
                } else {
                    return;
                }
            }

            int level = EnchantUtil.getLevelFromBook(sourceItem);
            if (level < 1) return;

            if (!isPlayerInventorySlot(destInfo)) return;

            if (!enchant.canEnchant(destItem)) return;

            if (!EnchantUtil.apply(destItem, enchant, level)) return;

            setItemFromSlotInfo(player, destInfo, destItem);

            setItemFromSlotInfo(player, sourceInfo, Item.AIR);

            event.setCancelled(true);

            player.sendMessage(TextFormat.GREEN + "Applied " + TextFormat.YELLOW + enchant.getName()
                    + " " + level + TextFormat.GREEN + " to the item.");

        } catch (Exception ignored) {

        }
    }

    private static class SlotInfo {
        int slot;
        Object container;
        SlotInfo(int slot, Object container) {
            this.slot = slot;
            this.container = container;
        }
    }

    private SlotInfo getSlotInfo(Object action, String getterName) {
        try {
            Method m = action.getClass().getMethod(getterName);
            Object info = m.invoke(action);
            if (info == null) return null;

            Method slotMethod = info.getClass().getMethod("getSlot");
            int slot = (int) slotMethod.invoke(info);

            Object container = null;
            try {
                Method containerMethod = info.getClass().getMethod("getContainer");
                container = containerMethod.invoke(info);
            } catch (Exception ignored) {}

            return new SlotInfo(slot, container);
        } catch (Exception e) {
            return null;
        }
    }

    private Item getItemFromSlotInfo(Player player, SlotInfo info) {
        if (info == null) return null;
        int slot = info.slot;
        Object container = info.container;
        String containerStr = container != null ? container.toString() : "";

        if (containerStr.contains("CURSOR") || containerStr.contains("UI") || slot == -1) {
            return player.getCursorInventory().getItem(0);
        }

        if (slot >= 0 && slot < 40) {
            return player.getInventory().getItem(slot);
        }
        return null;
    }

    private void setItemFromSlotInfo(Player player, SlotInfo info, Item item) {
        if (info == null) return;
        int slot = info.slot;
        Object container = info.container;
        String containerStr = container != null ? container.toString() : "";

        if (containerStr.contains("CURSOR") || containerStr.contains("UI") || slot == -1) {
            player.getCursorInventory().setItem(0, item);
        } else if (slot >= 0 && slot < 40) {
            player.getInventory().setItem(slot, item);
        }
    }

    private boolean isPlayerInventorySlot(SlotInfo info) {
        if (info == null) return false;
        int slot = info.slot;
        Object container = info.container;
        String containerStr = container != null ? container.toString() : "";

        if (containerStr.contains("CURSOR") || containerStr.contains("UI")) return false;
        if (containerStr.contains("CHEST") || (containerStr.contains("CONTAINER") && !containerStr.contains("INVENTORY"))) {
            return false;
        }
        return slot >= 0 && slot < 40;
    }
}