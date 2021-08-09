package de.lunoro.bungeesigns.listeners;

import de.lunoro.bungeesigns.bungeesign.BungeeSign;
import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import de.lunoro.bungeesigns.config.Config;
import de.lunoro.bungeesigns.config.ConfigContainer;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.util.Objects;

public class SignChangeListener implements Listener {

    private final Config messages = ConfigContainer.getInstance().getFile("messages");

    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (!(event.getPlayer().hasPermission("bungeesigns.permissions.events.signcreate"))) return;
        if (Objects.equals(event.getLine(1), "[Server]")) {
            BungeeSignContainer.getInstance().add(new BungeeSign(event.getLine(2), (Sign) event.getBlock().getState()));
            System.out.println(event.getLines().length);
            int i = 1;
            for (int line = 0; line < 4; line++) {
                System.out.println(i);
                String content = messages.getString("line" + i);
                if (content.equals("")) {
                    i++;
                    continue;
                }
                System.out.println(content);
                event.setLine(line, content);
                i++;
            }
        }
    }
}
