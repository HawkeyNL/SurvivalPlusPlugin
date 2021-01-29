package me.hawkeynl.survivalplus.Commands;

import me.hawkeynl.survivalplus.Config.Homes;
import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelHomeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        try {
            Player player = (Player) commandSender;

            if(args.length >= 1) {
                Homes.deleteHomeLocation(player, args[0]);
            } else {
                player.sendMessage(SurvivalPlus.PREFIX + ChatColor.YELLOW + "/delhome <name>" + ChatColor.GRAY + " to delete a home with a custom name.");
            }

            return false;
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "Command can not be executed in the console, please join the server to execute the command.");
            return false;
        }
    }
}
