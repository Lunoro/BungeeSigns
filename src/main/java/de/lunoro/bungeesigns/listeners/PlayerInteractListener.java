package de.lunoro.bungeesigns.listeners;

import de.lunoro.bungeesigns.BungeeSigns;
import de.lunoro.bungeesigns.bungeesign.BungeeSign;
import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import de.lunoro.bungeesigns.config.ConfigContainer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.io.*;

public class PlayerInteractListener implements Listener {

    private final Plugin plugin;

    public PlayerInteractListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block clickedBlock = event.getClickedBlock();
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR))
            return;
        if (clickedBlock == null) return;
        if (clickedBlock.getType().equals(Material.AIR)) return;
        if (!(clickedBlock.getState() instanceof Sign)) return;
        Sign clickedSign = (Sign) clickedBlock.getState();
        BungeeSign bungeeSign = BungeeSignContainer.getInstance().get(clickedSign.getLocation());
        if (bungeeSign == null) return;
        if (!bungeeSign.getSign().getLocation().equals(clickedSign.getLocation())) {
            return;
        }
        fetchServers();
        Bukkit.getScheduler().runTaskLaterAsynchronously(BungeeSigns.getProvidingPlugin(BungeeSigns.class), () ->{
            connectToServer(event.getPlayer(), bungeeSign.getServerName());
        }, 1);


    }

    private void connectToServer(Player player, String serverName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            if (!serverExists(serverName)) {
                player.sendMessage("This server doesn't exist.");
                return;
            }
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(serverName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
        player.sendMessage(ConfigContainer.getInstance().getFile("messages").getString("conMessage").replace("%server%", serverName));
    }

    private void fetchServers() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("GetServers");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bukkit.getServer().sendPluginMessage(plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
    }

    private boolean serverExists(String serverName) {
        return PluginMessageEventListener.getInstance().getList().contains(serverName);
    }
}