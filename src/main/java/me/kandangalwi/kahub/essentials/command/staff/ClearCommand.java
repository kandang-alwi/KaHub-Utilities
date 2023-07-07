package me.kandangalwi.kahub.essentials.command.staff;

import me.kandangalwi.kahub.KaHub;
import me.kandangalwi.kahub.api.BaseCommand;
import me.kandangalwi.kahub.api.Command;
import me.kandangalwi.kahub.api.CommandArgs;
import me.kandangalwi.kahub.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClearCommand extends BaseCommand {
    @Command(name = "clearinv", aliases = {"clear", "ci"}, permission = "kahub.command.clearinv")
    @Override

    public void onCommand(CommandArgs args) {
        Player player = args.getPlayer();

        if (args.length() > 0) {
            // Clear another player's inventory
            if (player.hasPermission("kahub.command.clearinv.others")) {
                String targetPlayerName = args.getArgs(0);
                Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

                if (targetPlayer != null) {
                    targetPlayer.getInventory().clear();
                    player.sendMessage(Utils.colorize(KaHub.getPrefix() + "&aCleared the inventory of player &e" + targetPlayer.getName() + "."));
                } else {
                    player.sendMessage(Utils.colorize(KaHub.getPrefix() + "&cPlayer &e" + targetPlayerName + " &cis not online."));
                }
            } else {
                player.sendMessage(Utils.colorize(KaHub.getPrefix() + "&cYou don't have permission to clear another player's inventory."));
            }
        } else {
            // Clear own inventory
            player.getInventory().clear();
            player.sendMessage(Utils.colorize(KaHub.getPrefix() + "&aYour inventory has been cleared!"));
        }
    }
}
