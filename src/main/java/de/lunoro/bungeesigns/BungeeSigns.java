package de.lunoro.bungeesigns;

import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import de.lunoro.bungeesigns.commands.editSignCommand;
import de.lunoro.bungeesigns.commands.SaveSignsCommand;
import de.lunoro.bungeesigns.config.ConfigContainer;
import de.lunoro.bungeesigns.listeners.BlockBreakListener;
import de.lunoro.bungeesigns.listeners.PlayerInteractListener;
import de.lunoro.bungeesigns.listeners.PluginMessageEventListener;
import de.lunoro.bungeesigns.listeners.SignChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class BungeeSigns extends JavaPlugin {

    @Override
    public void onEnable() {
        PluginMessageEventListener pluginMessageEventListener = new PluginMessageEventListener();
        saveResource("messages.yml", false);
        registerEvents(pluginMessageEventListener);
        registerCommands();
        registerPluginChannel(pluginMessageEventListener);
    }

    @Override
    public void onDisable() {
        unregisterPluginChannel();
        BungeeSignContainer.getInstance().save();
        ConfigContainer.getInstance().getFile("SignLocations").save();
    }

    private void registerEvents(PluginMessageEventListener pluginMessageEventListener) {
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(this, pluginMessageEventListener), this);
        Bukkit.getPluginManager().registerEvents(new SignChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(Bukkit.getPluginCommand("save")).setExecutor(new SaveSignsCommand());
        Objects.requireNonNull(Bukkit.getPluginCommand("editSign")).setExecutor(new editSignCommand());
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
