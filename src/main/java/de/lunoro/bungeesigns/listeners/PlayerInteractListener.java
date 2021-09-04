package de.lunoro.bungeesigns.listeners;

import de.lunoro.bungeesigns.bungeesign.BungeeSign;
import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import de.lunoro.bungeesigns.connect.ServerConnector;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class PlayerInteractListener implements Listener {

    ServerConnector serverConnector = ServerConnector.getInstance();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        if (!isValidAction(event.getAction())) return;
        if (!isSign(clickedBlock)) return;
        Sign clickedSign = (Sign) clickedBlock.getState();
        BungeeSign bungeeSign = BungeeSignContainer.getInstance().getSign(clickedSign.getLocation());
        if (bungeeSign != null && bungeeSign.getSign().getLocation().equals(clickedSign.getLocation())) {
            serverConnector.connectPlayerToServer(event.getPlayer(), bungeeSign.getServerName());
        }
    }

    private boolean isSign(Block block) {
        return block != null && !block.getType().equals(Material.AIR) && block instanceof Sign;
    }

    private boolean isValidAction(Action action) {
        return action.equals(Action.RIGHT_CLICK_BLOCK);
    }
}