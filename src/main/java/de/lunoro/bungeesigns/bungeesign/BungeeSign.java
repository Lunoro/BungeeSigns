package de.lunoro.bungeesigns.bungeesign;

import de.lunoro.bungeesigns.config.Config;
import de.lunoro.bungeesigns.config.ConfigContainer;
import de.lunoro.bungeesigns.listeners.PluginMessageEventListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeSign {

    private String serverName;
    private final Sign sign;


    public BungeeSign(String serverName, Sign sign) {
        this.serverName = serverName;
        this.sign = sign;

    }

    public BungeeSign(String serverName, Location location) {
        this.serverName = serverName;
        this.sign = (Sign) location.getBlock().getState();
    }

    public void save(ConfigurationSection section) {
        section.set("servername", serverName);
        section.set("location", sign.getLocation());
    }

    //TODO fix max player!

    public void updateSignText(Plugin plugin) {
        Config messages = ConfigContainer.getInstance().getFile("messages");
        int i = 1;
        for (int line = 0; line < 4; line++) {
            String content = messages.getString("line" + i)
                    .replace("%s", String.valueOf(getPlayerCountOnServer(plugin)))
                    .replace("%m", String.valueOf(Bukkit.getMaxPlayers()));
            if (content.equals("")) {
                i++;
                continue;
            }
            sign.setLine(line, content);
            sign.update();
            i++;
        }
    }

    private int getPlayerCountOnServer(Plugin plugin) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeUTF("PlayerCount");
            dataOutputStream.writeUTF(getServerName());
            Bukkit.getServer().sendPluginMessage(plugin, "BungeeCord", byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PluginMessageEventListener.getInstance().getAmountOfPlayersOnServer();
    }

    public Sign getSign() {
        return sign;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
