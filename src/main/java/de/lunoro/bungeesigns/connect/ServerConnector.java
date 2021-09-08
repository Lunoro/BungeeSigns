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

    public static void connectPlayerToServer(Player player, String serverName) {
        try {
            connect(player, serverName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void connect(Player player, String serverName) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        Config messageConfig = ConfigContainer.getInstance().getFile("config");
        dataOutputStream.writeUTF("Connect");
        dataOutputStream.writeUTF(serverName);
        player.sendPluginMessage(BungeeSigns.getInstance(), "BungeeCord", byteArrayOutputStream.toByteArray());
        player.sendMessage(messageConfig.getString("conMessage").replace("%server%", serverName));
    }
}
