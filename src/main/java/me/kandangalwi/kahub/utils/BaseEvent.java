package me.kandangalwi.kahub.utils;

import me.kandangalwi.kahub.KaHub;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BaseEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public void call() {
        KaHub.getInstance().getServer().getPluginManager().callEvent(this);
    }

}