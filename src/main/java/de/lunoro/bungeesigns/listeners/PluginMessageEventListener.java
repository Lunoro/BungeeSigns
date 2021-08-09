package de.lunoro.bungeesigns.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.Arrays;
import java.util.List;

public class PluginMessageEventListener implements PluginMessageListener {
    private List<String> serverList;
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subChannel = input.readUTF();
        System.out.println(subChannel);
        if (subChannel.equals("GetServers")) {
            serverList = Arrays.asList(input.readUTF().split(", "));
            System.out.println(serverList.toString());
        }
    }

    public List<String> getList() {
        return serverList;
    }
}
