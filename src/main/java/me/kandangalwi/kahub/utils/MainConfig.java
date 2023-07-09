package me.kandangalwi.kahub.utils;

import me.kandangalwi.kahub.KaHub;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainConfig {
    private final FileConfiguration configuration;
    private final FileConfiguration listenerConfig;
    private final File configFile;
    private final File listenerFile;
    private final Map<String, FileConfiguration> menuConfigs;
    private final KaHub plugin;

    public MainConfig(KaHub plugin) {
        this.plugin = plugin;
        configFile = new File(plugin.getDataFolder(), "config.yml");
        listenerFile = new File(plugin.getDataFolder(), "listener.yml");
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("config.yml", false);
        }
        if (!listenerFile.exists()) {
            listenerFile.getParentFile().mkdirs();
            plugin.saveResource("listener.yml", false);
        }
        configuration = new YamlConfiguration();
        listenerConfig = new YamlConfiguration();
        menuConfigs = new HashMap<>();
        loadConfigurations();
        loadListenerConfiguration();
        createMenuFiles();
        loadMenuConfigurations(); // Load menu configurations after creating the menu files
        createDefaultMenuConfigs();
    }

    private void loadConfigurations() {
        try {
            configuration.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void loadListenerConfiguration() {
        try {
            listenerConfig.load(listenerFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void createMenuFiles() {
        File menusFolder = new File(plugin.getDataFolder(), "menus");
        if (!menusFolder.exists()) {
            menusFolder.mkdirs();
        }
    }

    private void loadMenuConfigurations() {
        File menusFolder = new File(plugin.getDataFolder(), "menus");
        if (!menusFolder.exists()) {
            menusFolder.mkdirs();
        }

        for (File file : Objects.requireNonNull(menusFolder.listFiles())) {
            if (file.isFile() && file.getName().endsWith(".yml")) {
                String menuName = file.getName().replace(".yml", "");
                FileConfiguration menuConfig = new YamlConfiguration();
                try {
                    menuConfig.load(file);
                    menuConfigs.put(menuName, menuConfig);
                } catch (IOException | InvalidConfigurationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public FileConfiguration getConfiguration() {
        return configuration;
    }

    public FileConfiguration getListenerConfig() {
        return listenerConfig;
    }

    public FileConfiguration getMenuConfig(String menuName) {
        return menuConfigs.get(menuName);
    }

    public void saveConfig() {
        try {
            configuration.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveListenerConfig() {
        try {
            listenerConfig.save(listenerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveMenuConfig(String menuName) {
        FileConfiguration menuConfig = getMenuConfig(menuName);
        File menusFolder = new File(plugin.getDataFolder(), "menus");
        File menuFile = new File(menusFolder, menuName + ".yml");

        try {
            menuConfig.save(menuFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDefaultMenuConfigs() {
        File menusFolder = new File(plugin.getDataFolder(), "menus");
        if (!menusFolder.exists()) {
            menusFolder.mkdirs();
        }

        File defaultMenuFile = new File(plugin.getDataFolder(), "menus/default.yml");

        if (!defaultMenuFile.exists()) {
            plugin.saveResource("menus/default.yml", false);
            FileConfiguration defaultMenuConfig = new YamlConfiguration();

            try {
                defaultMenuConfig.load(defaultMenuFile);
                menuConfigs.put("default", defaultMenuConfig);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public void reload() {
        loadConfigurations();
        loadListenerConfiguration();
        loadMenuConfigurations();
    }
}




