package de.lunoro.bungeesigns.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Arrays;
import java.util.List;

public class PluginMessageEventListener implements PluginMessageListener {
    private static PluginMessageEventListener instance;
    private List<String> serverList;
    private int amountOfPlayersOnServer;

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subChannel = input.readUTF();
        if (subChannel.equals("GetServers")) {
            serverList = Arrays.asList(input.readUTF().split(", "));
        }

        if (subChannel.equals("PlayerCount")) {
            String server = input.readUTF();
            amountOfPlayersOnServer = input.readInt();
            System.out.println(server);
            System.out.println(amountOfPlayersOnServer);
        }
    }

    public List<String> getList() {
        return serverList;
    }

    public int getAmountOfPlayersOnServer() {
        return amountOfPlayersOnServer;
    }

    public static PluginMessageEventListener getInstance() {
        if (instance == null) {
            instance = new PluginMessageEventListener();
        }
        return instance;
    }
}
