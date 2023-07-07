package me.kandangalwi.kahub.essentials.command.main;

import lombok.Getter;
import me.kandangalwi.kahub.KaHub;
import me.kandangalwi.kahub.api.BaseCommand;
import me.kandangalwi.kahub.api.Command;
import me.kandangalwi.kahub.api.CommandArgs;
import me.kandangalwi.kahub.utils.MainConfig;
import me.kandangalwi.kahub.utils.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class KaHubCommand extends BaseCommand {
    @Getter
    private final MainConfig mainConfig;

    public KaHubCommand() {
        this.mainConfig = KaHub.getMainConfig();
    }

    @Command(name = "kahub", permission = "kahub.command.kahub")
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();

        if (args.getArgs().length == 0) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (player.hasPermission("kahub.admin") || player.isOp()) {
                    sendAllCommands(player);
                } else if (player.hasPermission("kahub.command.kahub.player")) {
                    sendPlayerCommands(player);
                } else if (player.hasPermission("kahub.command.kahub.staff")) {
                    sendStaffCommands(player);
                }
            } else {
                sendStaffCommands(sender);
            }
        } else if (args.getArgs().length == 1) {
            if (args.getArgs()[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("kahub.command.reload")) {
                    reloadPlugin(sender);
                } else {
                    sender.sendMessage(Utils.colorize(KaHub.getPrefix() + "&cNo permission."));
                }
            } else {
                sender.sendMessage(Utils.colorize(KaHub.getPrefix() + "&cArgument not found."));
            }
        } else {
            sender.sendMessage(Utils.colorize(KaHub.getPrefix() + "&cUsage: /kahub"));
        }
    }

    private void sendAllCommands(CommandSender sender) {
        sender.sendMessage(Utils.colorize("&7&lKaHub Commands:"));

        List<String> commands = mainConfig.getConfiguration().getStringList("commands.all");
        for (String command : commands) {
            sender.sendMessage(Utils.colorize(command));
        }
    }

    private void sendPlayerCommands(CommandSender sender) {
        sender.sendMessage(Utils.colorize("&7&lKaHub Commands:"));

        List<String> commands = mainConfig.getConfiguration().getStringList("commands.player");
        for (String command : commands) {
            sender.sendMessage(Utils.colorize(command));
        }
    }

    private void sendStaffCommands(CommandSender sender) {
        sender.sendMessage(Utils.colorize("&7&lKaHub Commands:"));

        List<String> commands = mainConfig.getConfiguration().getStringList("commands.staff");
        for (String command : commands) {
            sender.sendMessage(Utils.colorize(command));
        }
    }

    private void reloadPlugin(CommandSender sender) {
        Plugin plugin = KaHub.getInstance();

        plugin.reloadConfig();
        KaHub.reloadConfigValues();

        sender.sendMessage(Utils.colorize(KaHub.getPrefix() + "&aPlugin reloaded successfully."));
    }
}


