package de.lunoro.bungeesigns.listeners;

import de.lunoro.bungeesigns.bungeesign.BungeeSign;
import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.Objects;

public class SignChangeListener implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        Sign targetSign = (Sign) event.getBlock().getState();
        String targetLine = event.getLine(0);
        if (!(event.getPlayer().hasPermission("bungeesigns.permissions.events.signcreate"))) return;
        if (Objects.equals(targetLine, "[BungeeSign]")) {
            BungeeSignContainer bungeeSignContainer = BungeeSignContainer.getInstance();
            BungeeSign createdBungeeSign = new BungeeSign(targetLine, targetSign);
            bungeeSignContainer.addSign(createdBungeeSign);
            createdBungeeSign.updatePlayerCount(Bukkit.getServer().getOnlinePlayers().size());
        }
    }
}
