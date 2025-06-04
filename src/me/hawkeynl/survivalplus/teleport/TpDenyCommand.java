package me.hawkeynl.survivalplus.teleport;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Handles /tpdeny command.
 */
public class TpDenyCommand implements CommandExecutor {
    private final TeleportRequestManager manager;

    public TpDenyCommand(TeleportRequestManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "Command can not be executed in the console.");
            return true;
        }

        Player player = (Player) sender;
        if (!manager.denyRequest(player)) {
            sender.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "You do not have any teleport requests.");
        }
        return true;
    }
}
