package me.kandangalwi.kahub.listener;

import me.kandangalwi.kahub.KaHub;
import me.kandangalwi.kahub.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ServerSelector implements Listener {

    private final KaHub plugin;
    private final ItemStack serverSelectorItem;
    private final List<String> actions;
    private final List<Player> interactingPlayers;

    public ServerSelector(KaHub plugin) {
        this.plugin = plugin;
        this.actions = loadActions();
        this.serverSelectorItem = createServerSelectorItem();
        this.interactingPlayers = new ArrayList<>();
    }

    public void openServerSelector(Player player) {
        // Implement code to open the server selector menu here
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();

        // Check if the player already has the server selector item in their inventory
        if (!hasServerSelectorItem(inventory)) {
            // Check if the main hand is empty
            ItemStack itemInMainHand = inventory.getItemInMainHand();
            if (itemInMainHand == null || itemInMainHand.getType() == Material.AIR) {
                inventory.setItemInMainHand(serverSelectorItem.clone());
            } else {
                // Check if there is an empty slot in the inventory to place the server selector item
                int emptySlot = inventory.firstEmpty();
                if (emptySlot != -1) {
                    inventory.setItem(emptySlot, serverSelectorItem.clone());
                } else {
                    // Drop the server selector item on the ground if no empty slot is available
                    player.getWorld().dropItem(player.getLocation(), serverSelectorItem.clone());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.isSimilar(serverSelectorItem)) {
            // Check if the player is already interacting with the server selector item
            if (interactingPlayers.contains(player)) {
                return;
            }

            interactingPlayers.add(player);
            executeActions(player);
            openServerSelector(player);
            interactingPlayers.remove(player);
        }
    }

    private ItemStack createServerSelectorItem() {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();

        String displayName = Utils.colorize("&eServer Selector");
        meta.setDisplayName(displayName);

        List<String> lore = new ArrayList<>();
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    private List<String> loadActions() {
        return KaHub.getMainConfig().getConfiguration().getStringList("items.server_selector.actions");
    }

    private void executeActions(Player player) {
        for (String action : actions) {
            if (action.startsWith("/")) {
                String command = action.substring(1);
                plugin.getServer().dispatchCommand(player, command);
            } else {
                plugin.getLogger().warning("Invalid action defined in server selector item: " + action);
            }
        }
    }

    private boolean hasServerSelectorItem(PlayerInventory inventory) {
        ItemStack[] contents = inventory.getContents();
        for (ItemStack item : contents) {
            if (item != null && item.isSimilar(serverSelectorItem)) {
                return true;
            }
        }
        return false;
    }
}




