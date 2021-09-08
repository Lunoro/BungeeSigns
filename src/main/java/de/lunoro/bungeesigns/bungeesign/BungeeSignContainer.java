package de.lunoro.bungeesigns.bungeesign;

import de.lunoro.bungeesigns.config.Config;
import de.lunoro.bungeesigns.config.ConfigContainer;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BungeeSignContainer {

    @Getter
    private final static BungeeSignContainer instance = new BungeeSignContainer();
    private final List<BungeeSign> bungeeSignList;
    @Getter
    private final List<String> serverNameList;

    private BungeeSignContainer() {
        this.bungeeSignList = loadBungeeSigns();
        this.serverNameList = loadServernameList();
    }

    private List<BungeeSign> loadBungeeSigns() {
        List<BungeeSign> list = new ArrayList<>();
        FileConfiguration config = ConfigContainer.getInstance().getFile("signLocations").getFileConfiguration();
        for (String id : config.getKeys(false)) {
            Location location = (Location) Objects.requireNonNull(config.getConfigurationSection(id)).get("location");
            String servername = Objects.requireNonNull(config.getConfigurationSection(id)).getString("servername");
            assert location != null;
            if (!(location.getBlock().getState() instanceof Sign)) continue;
            BungeeSign sign = new BungeeSign(servername, Objects.requireNonNull(location));
            list.add(sign);
        }
        return list;
    }

    private List<String> loadServernameList() {
        List<String> list = new ArrayList<>();
        for (BungeeSign bungeeSign : bungeeSignList) {
            if (!list.contains(bungeeSign.getServerName())) {
                list.add(bungeeSign.getServerName());
            }
        }
        return list;
    }

    public void saveSign() {
        int i = 0;
        Config signConfig = ConfigContainer.getInstance().getFile("signLocations");
        signConfig.clear();
        for (BungeeSign sign : bungeeSignList) {
            saveInConfigurationSection(sign, signConfig.getFileConfiguration().createSection(String.valueOf(i)));
            i++;
        }
    }

    private void saveInConfigurationSection(BungeeSign bungeeSign, ConfigurationSection section) {
        section.set("servername", bungeeSign.getServerName());
        section.set("location", bungeeSign.getSign().getLocation());
    }

    public void deleteSign(Sign sign) {
        bungeeSignList.removeIf(bungeeSign -> bungeeSign.getSign().getLocation().equals(sign.getLocation()));
    }

    public void addSign(BungeeSign bungeeSign) {
        bungeeSignList.add(bungeeSign);
    }

    public void updatePlayerCount(String servername, int playerCount) {
        for (BungeeSign bungeeSign : bungeeSignList) {
            if (bungeeSign.getServerName().equals(servername)) {
                bungeeSign.updatePlayerCount(playerCount);
            }
        }
    }

    public BungeeSign getSign(Location location) {
        for (BungeeSign bungeeSign : bungeeSignList) {
            if (bungeeSign.getSign().getLocation().equals(location)) return bungeeSign;
        }
        return null;
    }
}
