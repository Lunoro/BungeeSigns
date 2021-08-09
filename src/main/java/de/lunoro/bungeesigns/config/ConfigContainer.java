package de.lunoro.bungeesigns.config;

import de.lunoro.bungeesigns.BungeeSigns;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.HashMap;

public class ConfigContainer {

    private final static ConfigContainer instance = new ConfigContainer();
    private final HashMap<String, Config> files = new HashMap<>();

    public static ConfigContainer getInstance() {
        return instance;
    }

    public Config getFile(String name) {
        if (files.containsKey(name)) {
            return files.get(name);
        }
        final Config config = create(BungeeSigns.getProvidingPlugin(BungeeSigns.class), name + ".yml");
        files.put(name, config);
        return config;
    }

    public void reloadFiles() {
        files.values().forEach(Config::load);
        files.values().forEach(Config::save);
    }

    public Config create(Plugin plugin, String configName) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        java.io.File configFile = new java.io.File(plugin.getDataFolder(), configName);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new Config(configFile, YamlConfiguration.loadConfiguration(configFile));
    }
}