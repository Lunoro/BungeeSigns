package de.lunoro.bungeesigns;

import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import de.lunoro.bungeesigns.commands.EditSignCommand;
import de.lunoro.bungeesigns.config.ConfigContainer;
import de.lunoro.bungeesigns.listeners.BlockBreakListener;
import de.lunoro.bungeesigns.listeners.PlayerInteractListener;
import de.lunoro.bungeesigns.listeners.PluginMessageEventListener;
import de.lunoro.bungeesigns.listeners.SignChangeListener;
import de.lunoro.bungeesigns.updater.UpdaterThread;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BungeeSigns extends JavaPlugin {

    @Getter
    private static BungeeSigns instance;

    @Override
    public void onEnable() {
        saveResource("config.yml", false);
        instance = this;
        registerEvents();
        registerCommands();
        registerPluginChannel();
        UpdaterThread.getInstance().start();
    }

    @Override
    public void onDisable() {
        unregisterPluginChannel();
        BungeeSignContainer.getInstance().saveSign();
        ConfigContainer.getInstance().getFile("signLocations").save();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new SignChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(Bukkit.getPluginCommand("editSign")).setExecutor(new EditSignCommand());
    }

    private void registerPluginChannel() {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessageEventListener());
    }

    private void unregisterPluginChannel() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }
}
