package de.lunoro.bungeesigns.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.IOException;

public class Config {

    private final java.io.File file;
    private FileConfiguration config;

    public Config(java.io.File file, FileConfiguration config) {
        this.file = file;
        this.config = config;
    }

    @Deprecated
    public <T> T get(String path) {
        return (T) config.get(path);
    }

    public void set(String path, Object value) {
        config.set(path, value);
        save();
    }

    private void setDefault(String path, Object def) {
        if (config.get(path) == null) {
            config.set(path, def);
            save();
        }
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException("Cant save the file: " + file.getAbsolutePath(), e);
        }
    }

    public String getString(String path) {
        return config.getString(path);
    }

    public String getString(String path, String def) {
        setDefault(path, def);
        return config.getString(path, def);
    }

    public Integer getInt(String path) {
        return config.getInt(path);
    }

    public Integer getInt(String path, int def) {
        setDefault(path, def);
        return config.getInt(path, def);
    }

    public Boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    public Boolean getBoolean(String path, boolean def) {
        setDefault(path, def);
        return config.getBoolean(path, def);
    }

    public void load() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void clear() {
        for (String key : config.getKeys(false)) {
            config.set(key, null);
        }

    }

    public FileConfiguration getFileConfiguration() {
        return config;
    }
}