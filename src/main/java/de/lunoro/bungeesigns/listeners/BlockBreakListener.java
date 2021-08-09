package de.lunoro.bungeesigns.listeners;

import de.lunoro.bungeesigns.bungeesign.BungeeSign;
import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import de.lunoro.bungeesigns.config.Config;
import de.lunoro.bungeesigns.config.ConfigContainer;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private final Config messages = ConfigContainer.getInstance().getFile("messages");

    @EventHandler
    public void onSignDestroy(BlockBreakEvent event) {
        if (!(event.getBlock().getState() instanceof Sign)) return;
        if (!(event.getPlayer().hasPermission("bungeesigns.permissions.events.signbreak"))) {
            event.setCancelled(true);
            return;
        }
        Sign breakingSign = (Sign) event.getBlock().getState();
        BungeeSign bungeeSign = BungeeSignContainer.getInstance().get(breakingSign.getLocation());
        if (bungeeSign == null) return;
        if (bungeeSign.getSign().getLocation().equals(breakingSign.getLocation())) {
            BungeeSignContainer.getInstance().deleteSign(breakingSign);
            System.out.println("Schild entfernt!");

        }
    }
}
