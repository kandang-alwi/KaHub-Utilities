package me.kandangalwi.kahub.essentials.event;

import lombok.Getter;
import lombok.Setter;
import me.kandangalwi.kahub.KaHub;
import me.kandangalwi.kahub.utils.BaseEvent;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpawnTeleportEvent extends BaseEvent implements Cancellable, Listener {
    @Getter
    private final KaHub plugin;
    @Getter
    private final Player player;
    @Getter @Setter
    private Location location;
    @Getter @Setter
    private boolean cancelled;

    public SpawnTeleportEvent(KaHub plugin, Player player, Location location) {
        this.plugin = plugin;
        this.player = player;
        this.location = location;
    }

    public void teleportToSpawnLocation() {
        if (!cancelled && location != null) {
            player.teleport(location);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getEssentials().teleportToSpawn(player);
    }
}
