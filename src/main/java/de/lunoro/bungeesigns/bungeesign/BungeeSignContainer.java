package de.lunoro.bungeesigns.bungeesign;

import de.lunoro.bungeesigns.config.ConfigContainer;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BungeeSignContainer {

    private static BungeeSignContainer instance;

    private final List<BungeeSign> bungeeSignList;


    private BungeeSignContainer() {
        this.bungeeSignList = loadBungeeSigns();
    }

    public static BungeeSignContainer getInstance() {
        if (instance == null) {
            instance = new BungeeSignContainer();
        }
        return instance;
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

    public void save() {
        int i = 0;
        ConfigContainer.getInstance().getFile("signLocations").clear();
        for (BungeeSign sign : bungeeSignList) {
            sign.save(Objects.requireNonNull(ConfigContainer.getInstance().getFile("signLocations").getFileConfiguration().createSection(String.valueOf(i))));
            i++;
        }
    }

    public void deleteSign(Sign sign) {
        bungeeSignList.removeIf(bungeeSign -> bungeeSign.getSign().getLocation().equals(sign.getLocation()));
    }

    public void add(BungeeSign bungeeSign) {
        bungeeSignList.add(bungeeSign);
    }

    public BungeeSign get(Location location) {
        for (BungeeSign bungeeSign : bungeeSignList) {
            if (bungeeSign.getSign().getLocation().equals(location)) return bungeeSign;
        }
        return null;
    }
}
