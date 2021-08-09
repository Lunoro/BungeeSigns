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

public class editSignCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (!(player.hasPermission("bungeesigns.permissions.commands.editsign"))) {
            player.sendMessage(ConfigContainer.getInstance().getFile("messages.yml").getString("permError"));
            return false;
        }
        if (args.length != 1) {
            player.sendMessage("Usage: /editSign [Name of server in bungeecord config.yml].");
            return false;
        }
        Block block = player.getTargetBlockExact(5);
        Location loc = Objects.requireNonNull(block).getLocation();
        if (!(loc.getBlock().getState() instanceof Sign)) return false;
        BungeeSign bungeeSign = BungeeSignContainer.getInstance().get(loc);
        if (bungeeSign == null) {
            player.sendMessage("Not a valid sign... fine!");
            return false;
        }
        if (!(bungeeSign.getSign().getLocation().equals(loc))) return false;
        bungeeSign.setServerName(args[0]);
        player.sendMessage("Sign server edited to " + ChatColor.RED + args[0]);
        return true;
    }
}