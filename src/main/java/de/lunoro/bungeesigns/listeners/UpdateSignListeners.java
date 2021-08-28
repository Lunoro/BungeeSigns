package de.lunoro.bungeesigns.listeners;

import de.lunoro.bungeesigns.bungeesign.BungeeSign;
import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;


public class UpdateSignListeners implements Listener {

    private final Plugin plugin;

    public UpdateSignListeners(Plugin plugin) {
        this.plugin = plugin;
    }

    List<BungeeSign> allBungeeSignsOnServer = BungeeSignContainer.getInstance().getBungeeSignList();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        updateAllSignsOnServer();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        updateAllSignsOnServer();
    }

    private void updateAllSignsOnServer() {
        for (BungeeSign bungeeSign : allBungeeSignsOnServer) {
            bungeeSign.updateSignText(plugin);
        }
    }
}
