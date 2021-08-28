package de.lunoro.bungeesigns.listeners;

import de.lunoro.bungeesigns.bungeesign.BungeeSign;
import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class SignChangeListener implements Listener {

    private final Plugin plugin;

    public SignChangeListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (!(event.getPlayer().hasPermission("bungeesigns.permissions.events.signcreate"))) return;
        if (Objects.equals(event.getLine(0), "[BungeeSign]")) {
            BungeeSignContainer.getInstance().addSign(new BungeeSign(event.getLine(1), (Sign) event.getBlock().getState()));
            BungeeSign bungeeSign = BungeeSignContainer.getInstance().getSign(event.getBlock().getLocation());
            System.out.println(bungeeSign.getSign().getLocation());
            bungeeSign.updateSignText(plugin);
        }
    }
}
