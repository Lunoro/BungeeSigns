package de.lunoro.bungeesigns.commands;

import de.lunoro.bungeesigns.bungeesign.BungeeSign;
import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import de.lunoro.bungeesigns.config.ConfigContainer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class EditSignCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Sign sign = (Sign) Objects.requireNonNull(player.getTargetBlockExact(5)).getState();
        Location signLocation = Objects.requireNonNull(sign).getLocation();
        if (!(signLocation instanceof Sign)) return false;
        if (!(player.hasPermission("bungeesigns.permissions.commands.editsign"))) {
            player.sendMessage(ConfigContainer.getInstance().getFile("config.yml").getString("permError"));
            return false;
        }
        if (args.length != 1) {
            player.sendMessage("Usage: /editSign [Name of server in bungeecord config.yml].");
            return false;
        }
        BungeeSign bungeeSign = BungeeSignContainer.getInstance().getSign(signLocation);
        if (signIsBungeeSign(sign, bungeeSign)) {
            bungeeSign.setServerName(args[0]);
            player.sendMessage("Sign server edited to " + ChatColor.RED + args[0]);
        }
        return true;
    }

    private boolean signIsBungeeSign(Sign sign, BungeeSign bungeeSign) {
        return !(bungeeSign == null) || bungeeSign.getSign().getLocation().equals(sign.getLocation());
    }
}