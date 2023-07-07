package me.kandangalwi.kahub.essentials.command.staff;

import me.kandangalwi.kahub.KaHub;
import me.kandangalwi.kahub.api.BaseCommand;
import me.kandangalwi.kahub.api.Command;
import me.kandangalwi.kahub.api.CommandArgs;
import me.kandangalwi.kahub.utils.Utils;
import org.bukkit.entity.Player;

public class SetSpawnCommand extends BaseCommand {

    @Command(name = "setspawn", permission = "kahub.command.setspawn")
    @Override
    public void onCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        KaHub.getInstance().getEssentials().setSpawnAndSave(player.getLocation());
        player.sendMessage(Utils.colorize(KaHub.getPrefix() + "&aYou have set the spawn location."));
    }
}

