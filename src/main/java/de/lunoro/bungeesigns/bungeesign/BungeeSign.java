package de.lunoro.bungeesigns.bungeesign;

import de.lunoro.bungeesigns.config.Config;
import de.lunoro.bungeesigns.config.ConfigContainer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.ConfigurationSection;

public class BungeeSign {

    @Setter
    @Getter
    private String serverName;
    @Getter
    private final Sign sign;
    private int maxPlayers, playerCount;

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

    public void updatePlayerCount(int playerCount) {
        Config signText = ConfigContainer.getInstance().getFile("config");
        int i = 1;
        for (int line = 0; line < 4; line++) {
            String content = signText.getString("line" + i)
                    .replace("%s", String.valueOf(playerCount));
            if (content.equals("")) {
                String writtenTextOnSign = sign.getLine(i);
                sign.setLine(i, content + writtenTextOnSign);
                i++;
                continue;
            }
            sign.setLine(line, content);
            sign.update();
            i++;
        }
    }
}
