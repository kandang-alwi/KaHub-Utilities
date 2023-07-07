package me.kandangalwi.kahub.essentials.command.player;

import me.kandangalwi.kahub.KaHub;
import me.kandangalwi.kahub.api.BaseCommand;
import me.kandangalwi.kahub.api.Command;
import me.kandangalwi.kahub.api.CommandArgs;
import me.kandangalwi.kahub.utils.Utils;
import org.bukkit.entity.Player;

public class SpawnCommand extends BaseCommand {

    @Command(name = "spawn")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        KaHub.getInstance().getEssentials().teleportToSpawn(player);
        player.sendMessage(Utils.colorize(KaHub.getPrefix() + "&aYou teleported to spawn."));
    }
}

