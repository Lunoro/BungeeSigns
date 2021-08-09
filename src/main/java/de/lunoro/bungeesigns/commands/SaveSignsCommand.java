package de.lunoro.bungeesigns.commands;

import de.lunoro.bungeesigns.bungeesign.BungeeSignContainer;
import de.lunoro.bungeesigns.config.ConfigContainer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SaveSignsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        BungeeSignContainer.getInstance().save();
        ConfigContainer.getInstance().getFile("SignLocations").save();
        return true;
    }
}
