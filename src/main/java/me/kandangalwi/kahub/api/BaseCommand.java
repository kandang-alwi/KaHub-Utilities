package me.kandangalwi.kahub.api;

import me.kandangalwi.kahub.manager.CommandManager;

public abstract class BaseCommand {

    public BaseCommand() {
        CommandManager.getInstance().registerCommands(this, null);
    }

    public abstract void onCommand(CommandArgs command);
}