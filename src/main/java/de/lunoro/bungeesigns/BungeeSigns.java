package de.lunoro.bungeesigns;

import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import de.lunoro.bungeesigns.commands.EditSignCommand;
import de.lunoro.bungeesigns.config.ConfigContainer;
import de.lunoro.bungeesigns.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BungeeSigns extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginMessageEventListener pluginMessageEventListener = PluginMessageEventListener.getInstance();
        saveResource("messages.yml", false);
        registerEvents();
        registerCommands();
        registerPluginChannel(pluginMessageEventListener);
    }

    @Override
    public void onDisable() {
        unregisterPluginChannel();
        BungeeSignContainer.getInstance().saveSign();
        ConfigContainer.getInstance().getFile("signLocations").save();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        Bukkit.getPluginManager().registerEvents(new SignChangeListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new UpdateSignListeners(this), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(Bukkit.getPluginCommand("editSign")).setExecutor(new EditSignCommand());
    }

    private void registerPluginChannel(PluginMessageEventListener pluginMessageEventListener) {
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", pluginMessageEventListener);
    }

    private void unregisterPluginChannel() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
        getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }
}
