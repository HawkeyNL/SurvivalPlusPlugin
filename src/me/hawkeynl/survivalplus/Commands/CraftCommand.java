package me.hawkeynl.survivalplus.Commands;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CraftCommand implements CommandExecutor {
    private final String PREFIX = SurvivalPlus.PREFIX;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        try {
            Player player = (Player) commandSender;
            player.openWorkbench(null, true);

            return false;
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.RED + "Command can not be executed in the console, please join the server to execute the command.");
            return false;
        }
    }
}
