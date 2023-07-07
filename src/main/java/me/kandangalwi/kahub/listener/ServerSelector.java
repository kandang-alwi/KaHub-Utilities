package me.kandangalwi.kahub.listener;

import me.kandangalwi.kahub.KaHub;
import me.kandangalwi.kahub.utils.MaterialRegistry;
import me.kandangalwi.kahub.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ServerSelector implements Listener {

    private final KaHub plugin;
    private final ItemStack serverSelectorItem;
    private final List<String> actions;

    public ServerSelector(KaHub plugin) {
        this.plugin = plugin;
        this.serverSelectorItem = createServerSelectorItem();
        this.actions = loadActions();
    }

    public void openServerSelector(Player player) {
        // Implement code to open the server selector menu here
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.isSimilar(serverSelectorItem)) {
            executeActions(player);
            openServerSelector(player);
        }
    }

    private ItemStack createServerSelectorItem() {
        String materialId = KaHub.getMainConfig().getConfiguration().getString("items.server_selector.material");

        if (materialId == null || materialId.isEmpty()) {
            plugin.getLogger().warning("Material ID not specified for the server selector item.");
            return new ItemStack(Material.AIR);
        }

        Material material = MaterialRegistry.getMaterialByName(materialId);

        if (material == null) {
            plugin.getLogger().warning("Invalid material ID '" + materialId + "' specified for the server selector item.");
            return new ItemStack(Material.AIR);
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        String displayName = Utils.colorize(KaHub.getMainConfig().getConfiguration().getString("items.server_selector.display_name"));
        meta.setDisplayName(displayName);

        List<String> lore = KaHub.getMainConfig().getConfiguration().getStringList("items.server_selector.lore");
        if (lore.isEmpty()) {
            lore.add(""); // Add an empty line to avoid an empty lore list
        }
        List<String> coloredLore = new ArrayList<>();
        for (String line : lore) {
            coloredLore.add(Utils.colorize(line));
        }
        meta.setLore(coloredLore);

        item.setItemMeta(meta);

        return item;
    }

    private List<String> loadActions() {
        return KaHub.getMainConfig().getConfiguration().getStringList("items.server_selector.actions");
    }

    private void executeActions(Player player) {
        for (String action : actions) {
            if (action.startsWith("/")) {
                String command = action.substring(1).replace("{player}", player.getName());
                plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
            } else {
                plugin.getLogger().warning("Invalid action defined in server selector item: " + action);
            }
        }
    }
}
