package me.kandangalwi.kahub;

// no import xD

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

        // register database (mehewhehewe)
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI

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

        // Essentials Command (Hide)
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
    }

    public void registerListeners() {
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
        IlilILILILILLILILLILILLILLILILLILILLILILLILILLILILLILILILILLI
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
}
