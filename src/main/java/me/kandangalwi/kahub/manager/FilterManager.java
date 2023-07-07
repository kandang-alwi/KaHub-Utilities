package me.kandangalwi.kahub.manager;

import me.kandangalwi.kahub.KaHub;
import me.kandangalwi.kahub.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilterManager implements Listener {

    private final KaHub plugin;
    private final List<String> blockedWords;
    private final boolean antiSwearEnabled;
    private final Set<String> handledCommands;

    public FilterManager(KaHub plugin) {
        this.plugin = plugin;
        this.blockedWords = KaHub.getMainConfig().getConfiguration().getStringList("anti_swear.blocked_words");
        this.antiSwearEnabled = KaHub.getMainConfig().getConfiguration().getBoolean("anti_swear.enabled");
        this.handledCommands = new HashSet<>();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        String joinMessage = KaHub.getMainConfig().getConfiguration().getString("PLAYER_JOIN");
        joinMessage = joinMessage.replace("%player%", event.getPlayer().getName());
        event.setJoinMessage(Utils.colorize(joinMessage));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        String quitMessage = KaHub.getMainConfig().getConfiguration().getString("PLAYER_QUIT");
        quitMessage = quitMessage.replace("%player%", event.getPlayer().getName());
        event.setQuitMessage(Utils.colorize(quitMessage));
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (antiSwearEnabled) {
            String message = event.getMessage();
            if (containsBlockedWord(message)) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Utils.colorize(KaHub.getPrefix() + "&cYour message contains blocked words!"));
            }
        }
    }

    private boolean containsBlockedWord(String message) {
        for (String word : blockedWords) {
            if (message.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
