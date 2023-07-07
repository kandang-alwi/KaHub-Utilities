package me.kandangalwi.kahub.essentials.command.staff;

import me.kandangalwi.kahub.KaHub;
import me.kandangalwi.kahub.api.BaseCommand;
import me.kandangalwi.kahub.api.Command;
import me.kandangalwi.kahub.api.CommandArgs;
import me.kandangalwi.kahub.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand extends BaseCommand {
    @Command(name = "gamemode", aliases = {"gm"}, permission = "kahub.command.gamemode")
    @Override
    public void onCommand(CommandArgs args) {
        CommandSender sender = args.getSender();
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.colorize(KaHub.getPrefix() + "&cThis command can only be executed by a player."));
            return;
        }

        Player player = (Player) sender;
        String[] cmdArgs = args.getArgs();

        if (cmdArgs.length < 1) {
            sender.sendMessage(Utils.colorize(KaHub.getPrefix() + "&cUsage: /gamemode <mode>"));
            return;
        }

        String modeStr = cmdArgs[0].toLowerCase();

        GameMode mode = null;

        if (modeStr.equals("survival") || modeStr.equals("s")) {
            mode = GameMode.SURVIVAL;
        } else if (modeStr.equals("creative") || modeStr.equals("c")) {
            mode = GameMode.CREATIVE;
        } else if (modeStr.equals("adventure") || modeStr.equals("a")) {
            mode = GameMode.ADVENTURE;
        } else if (modeStr.equals("spectator") || modeStr.equals("sp")) {
            mode = GameMode.SPECTATOR;
        }

        if (mode == null) {
            sender.sendMessage(Utils.colorize(KaHub.getPrefix() + "&cInvalid gamemode."));
            return;
        }

        player.setGameMode(mode);
        sender.sendMessage(Utils.colorize(KaHub.getPrefix() + "&aYour game mode has been set to &e" + mode.toString().toUpperCase() + "."));
    }
}

