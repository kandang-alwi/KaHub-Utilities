package me.kandangalwi.kahub;

import me.kandangalwi.kahub.essentials.event.SpawnTeleportEvent;
import me.kandangalwi.kahub.listener.ServerSelector;
import me.kandangalwi.kahub.manager.CommandManager;
import me.kandangalwi.kahub.essentials.Essentials;
import me.kandangalwi.kahub.essentials.command.main.KaHubCommand;
import me.kandangalwi.kahub.essentials.command.player.*;
import me.kandangalwi.kahub.essentials.command.staff.*;
import me.kandangalwi.kahub.manager.*;
import me.kandangalwi.kahub.utils.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public class KaHub extends JavaPlugin {
    private static KaHub instance;
    private static MainConfig mainConfig;
    private static ListenerManager listenerManager;
    private static FilterManager filterManager;
    private static ServerSelector serverSelector;
    private Essentials essentials;
    private static String prefix;

    @Override
    public void onEnable() {
        instance = this;
        mainConfig = new MainConfig(this);
        essentials = new Essentials(this);
        listenerManager = new ListenerManager(this);
        filterManager = new FilterManager(this);
        serverSelector = new ServerSelector(this);

        // register database
        try {
            String host = mainConfig.getConfiguration().getString("mysql.host");
            String port = mainConfig.getConfiguration().getString("mysql.port");
            String database = mainConfig.getConfiguration().getString("mysql.database");
            String username = mainConfig.getConfiguration().getString("mysql.username");
            String password = mainConfig.getConfiguration().getString("mysql.password");

            DatabaseManager databaseManager = new DatabaseManager(host, port, database, username, password);
            databaseManager.connect();
            databaseManager.createTables();
        } catch (SQLException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        MaterialRegistry.registerMaterials();

        // register methods
        loadConfigValues();
        loadListenerConfigValues();
        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable() {
        // Unregister the KaHubCommand
        CommandManager commandManager = new CommandManager(this);
        commandManager.unregisterCommands(new KaHubCommand());

        getLogger().info("[KaHub-Logger] KaHub-Utilities disabling...");
    }

    public void registerCommands() {
        new CommandManager(this);

        // Main Command
        new KaHubCommand();

        // Essentials Command
        new FlyCommand();
        new SpawnCommand();
        new ClearCommand();
        new GamemodeCommand();
        new SetSpawnCommand();
        new SudoCommand();
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(listenerManager, this);
        getServer().getPluginManager().registerEvents(filterManager, this);
        getServer().getPluginManager().registerEvents(serverSelector, this);
        getServer().getPluginManager().registerEvents(new SpawnTeleportEvent(this, null, null), this);
    }

    public static void loadConfigValues() {
        prefix = Utils.colorize(mainConfig.getConfiguration().getString("PREFIX"));
    }

    public static void reloadConfigValues() {
        mainConfig.reload();
        loadConfigValues();
        loadListenerConfigValues();
    }

    public static void loadListenerConfigValues() {
        mainConfig.saveListenerConfig();
        listenerManager.reloadConfig();
    }

    // List Methods
    public static KaHub getInstance() {
        return instance;
    }

    public static MainConfig getMainConfig() {
        return mainConfig;
    }

    public Essentials getEssentials() {
        return essentials;
    }

    public static String getPrefix() {
        return prefix;
    }

    public static ServerSelector getServerSelector() {
        return serverSelector;
    }
}
