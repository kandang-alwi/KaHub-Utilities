package me.kandangalwi.kahub.essentials.command.staff;

import me.kandangalwi.kahub.KaHub;
import me.kandangalwi.kahub.api.BaseCommand;
import me.kandangalwi.kahub.api.Command;
import me.kandangalwi.kahub.api.CommandArgs;
import me.kandangalwi.kahub.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoCommand extends BaseCommand {
    @Command(name = "sudo", permission = "kahub.command.sudo")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.length() < 2) {
            sender.sendMessage(Utils.colorize(KaHub.getPrefix() + "&cUsage: /sudo <player> <command>"));
            return;
        }

        String targetPlayerName = args.getArgs(0);
        Player targetPlayer = Bukkit.getPlayer(targetPlayerName);

        if (targetPlayer == null) {
            sender.sendMessage(Utils.colorize(KaHub.getPrefix() + "&cPlayer &e" + targetPlayerName + " &cis not online."));
            return;
        }

        String command = StringUtils.join(args.getArgs(), " ", 1, args.length());

        if (command.startsWith("/")) {
            // Jika perintah dimulai dengan '/', eksekusi sebagai teks
            targetPlayer.chat(command.substring(1));
        } else {
            // Jika perintah tidak dimulai dengan '/', eksekusi sebagai perintah yang sebenarnya
            Bukkit.dispatchCommand(targetPlayer, command);
        }

        sender.sendMessage(Utils.colorize(KaHub.getPrefix() + "&aExecuted command &f'" + command + "' &ato player &e" + targetPlayer.getName() + "."));
    }
}
