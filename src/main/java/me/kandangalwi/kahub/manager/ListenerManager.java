package me.kandangalwi.kahub.manager;

import me.kandangalwi.kahub.KaHub;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class ListenerManager implements Listener {

    private final KaHub plugin;

    public ListenerManager(KaHub plugin) {
        this.plugin = plugin;
    }

    public void reloadConfig() {
        plugin.reloadConfig(); // Menggunakan reloadConfig() dari plugin untuk me-reload konfigurasi
    }

    @EventHandler
    public void onPlayerHungerLoss(FoodLevelChangeEvent event) {
        Player player = (Player) event.getEntity();
        if (player.isOp()) return;

        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Player.disable_hunger_loss")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerFallDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.isOp()) return;
        }

        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Player.disable_fall_damage")) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerPvP(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.isOp()) return;
        }

        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Player.disable_player_pvp")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerVoidDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (player.isOp()) return;

        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Player.disable_void_death")) {
            if (player.getLocation().getY() < 0) {
                event.setKeepInventory(true);
                event.setKeepLevel(true);
                event.getDrops().clear();
            }
        }
    }

    @EventHandler
    public void onPlayerFireDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.isOp()) return;
        }

        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Player.disable_fire_damage")) {
            if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK
                    || event.getCause() == EntityDamageEvent.DamageCause.LAVA) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerDrowning(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.isOp()) return;
        }

        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Player.disable_drowning")) {
            if (event.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerOffHandSwap(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;

        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Player.disable_off_hand_swap")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Misc.disable_weather_change")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Misc.disable_death_message")) {
            event.setDeathMessage(null);
        }
    }

    @EventHandler
    public void onMobSpawning(CreatureSpawnEvent event) {
        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Misc.disable_mob_spawning")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;

        if (KaHub.getMainConfig().getListenerConfig().getBoolean("ItemEntities.disable_item_drop")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        if (KaHub.getMainConfig().getListenerConfig().getBoolean("ItemEntities.disable_item_pickup")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;

        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Blocks.disable_block_break")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;

        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Blocks.disable_block_place")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.isOp()) return;

        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Blocks.disable_block_interact")) {
            Block clickedBlock = event.getClickedBlock();
            if (clickedBlock != null) {
                Material blockType = clickedBlock.getType();
                // Check if the clicked block type is one that should be disabled for interaction
                if (blockType == Material.CHEST || blockType == Material.FURNACE || blockType == Material.CROPS) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event) {
        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Blocks.disable_block_burn")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockSpread(BlockSpreadEvent event) {
        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Blocks.disable_block_fire_spread")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLeafDecay(LeavesDecayEvent event) {
        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Blocks.disable_block_leaf_decay")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInventoryMove(InventoryMoveItemEvent event) {
        Player player = (Player) event.getDestination().getHolder();
        if (player.isOp()) return;

        if (KaHub.getMainConfig().getListenerConfig().getBoolean("Player.disable_item_move")) {
            event.setCancelled(true);
        }
    }
}


