package de.lunoro.bungeesigns.bungeesign;

import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;

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
