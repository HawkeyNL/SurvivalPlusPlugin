package me.hawkeynl.survivalplus.teleport;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Handles /tpaccept command.
 */
public class TpAcceptCommand implements CommandExecutor {
    private final TeleportRequestManager manager;

    public TpAcceptCommand(TeleportRequestManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "Command can not be executed in the console.");
            return true;
        }

        Player player = (Player) sender;
        if (!manager.acceptRequest(player)) {
            sender.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "You do not have any teleport requests.");
        }
        return true;
    }
}
