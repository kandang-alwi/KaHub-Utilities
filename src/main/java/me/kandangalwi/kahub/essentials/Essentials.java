package me.kandangalwi.kahub.essentials;

import lombok.Getter;
import lombok.Setter;
import me.kandangalwi.kahub.KaHub;
import me.kandangalwi.kahub.essentials.event.SpawnTeleportEvent;
import me.kandangalwi.kahub.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class Essentials {

    private final KaHub plugin;
    @Setter @Getter
    private Location spawn;
    private final boolean teleportOnJoin;

    public Essentials(KaHub plugin) {
        this.plugin = plugin;
        this.spawn = deserializeLocation(KaHub.getMainConfig().getConfiguration().getString("ESSENTIAL_SPAWN_LOCATION"));
        this.teleportOnJoin = KaHub.getMainConfig().getConfiguration().getBoolean("SPAWN_JOIN", true);
        if (spawn != null) {
            Bukkit.getWorlds().get(0).setSpawnLocation(spawn.getBlockX(), spawn.getBlockY(), spawn.getBlockZ());
        } else {
            Bukkit.getLogger().log(Level.WARNING, "[KaHub-Warnings] World spawn not found");
        }
    }

    public void setSpawnAndSave(Location location) {
        spawn = location;

        FileConfiguration mainConfig = KaHub.getMainConfig().getConfiguration();
        if (spawn == null) {
            mainConfig.set("ESSENTIAL_SPAWN_LOCATION", null);
        } else {
            mainConfig.set("ESSENTIAL_SPAWN_LOCATION", LocationUtil.serialize(this.spawn));
        }

        KaHub.getMainConfig().saveConfig();
    }

    public void teleportToSpawn(Player player) {
        Location location = spawn == null ? plugin.getServer().getWorlds().get(0).getSpawnLocation() : spawn;

        SpawnTeleportEvent event = new SpawnTeleportEvent(plugin, player, location);
        event.call();

        if (!event.isCancelled() && event.getLocation() != null) {
            player.teleport(event.getLocation());
        }
    }

    public void clearEntities(World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getType() == EntityType.PLAYER) continue;

            entity.remove();
        }
    }

    public int clearEntities(World world, EntityType... excluded) {
        int removed = 0;

        entityLoop:
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Item) {
                removed++;
                entity.remove();
                continue;
            }

            for (EntityType type : excluded) {
                if (entity.getType() == EntityType.PLAYER) continue entityLoop;

                if (entity.getType() == type) continue entityLoop;
            }

            removed++;
            entity.remove();
        }

        return removed;
    }


    private String serializeLocation(Location location) {
        if (location == null) return null;
        return location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + ","
                + location.getYaw() + "," + location.getPitch();
    }

    private Location deserializeLocation(String string) {
        if (string == null) return null;
        String[] parts = string.split(",");
        if (parts.length < 6) return null;
        World world = Bukkit.getWorld(parts[0]);
        if (world == null) return null;
        double x, y, z;
        float yaw, pitch;
        try {
            x = Double.parseDouble(parts[1]);
            y = Double.parseDouble(parts[2]);
            z = Double.parseDouble(parts[3]);
            yaw = Float.parseFloat(parts[4]);
            pitch = Float.parseFloat(parts[5]);
        } catch (NumberFormatException e) {
            return null;
        }
        return new Location(world, x, y, z, yaw, pitch);
    }
}

