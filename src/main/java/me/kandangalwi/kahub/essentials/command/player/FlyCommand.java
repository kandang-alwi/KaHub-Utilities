package me.kandangalwi.kahub.essentials.command.player;

import me.kandangalwi.kahub.KaHub;
import me.kandangalwi.kahub.api.BaseCommand;
import me.kandangalwi.kahub.api.Command;
import me.kandangalwi.kahub.api.CommandArgs;
import me.kandangalwi.kahub.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand extends BaseCommand {
    @Command(name = "fly", permission = "kahub.command.fly")

    @Override
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.getAllowFlight()) {
                player.setAllowFlight(false);
                player.setFlying(false);
                player.sendMessage(Utils.colorize(KaHub.getPrefix() + "&cFlight mode disabled!"));
            } else {
                player.setAllowFlight(true);
                player.setFlying(true);
                player.sendMessage(Utils.colorize(KaHub.getPrefix() + "&aFlight mode enabled!"));
            }
        } else {
            sender.sendMessage(Utils.colorize("&cThis command can only be executed in-game."));
        }
    }
}
