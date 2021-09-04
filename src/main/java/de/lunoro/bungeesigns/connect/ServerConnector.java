package de.lunoro.bungeesigns.connect;

import de.lunoro.bungeesigns.BungeeSigns;
import de.lunoro.bungeesigns.config.Config;
import de.lunoro.bungeesigns.config.ConfigContainer;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ServerConnector {

    @Getter
    private final static ServerConnector instance = new ServerConnector();
    private final ByteArrayOutputStream byteArrayOutputStream;
    private final DataOutputStream dataOutputStream;

    private final Plugin plugin = BungeeSigns.getInstance();
    private final Config messageConfig = ConfigContainer.getInstance().getFile("config");

    private ServerConnector() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        dataOutputStream = new DataOutputStream(byteArrayOutputStream);
    }

    public void connectPlayerToServer(Player player, String serverName) {
        try {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(serverName);
            player.sendPluginMessage(plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
            player.sendMessage(messageConfig.getString("conMessage").replace("%server%", serverName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
