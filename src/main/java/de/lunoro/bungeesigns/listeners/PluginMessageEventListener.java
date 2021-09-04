package de.lunoro.bungeesigns.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Arrays;
import java.util.List;

public class PluginMessageEventListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subChannel = input.readUTF();
        if (subChannel.equals("GetServers")) {
            List<String> serverList = Arrays.asList(input.readUTF().split(", "));
        }

        if (subChannel.equals("PlayerCount")) {
            String server = input.readUTF();
            int amountOfPlayersOnServer = input.readInt();
            BungeeSignContainer.getInstance().updatePlayerCount(server, amountOfPlayersOnServer);
        }
    }
}
