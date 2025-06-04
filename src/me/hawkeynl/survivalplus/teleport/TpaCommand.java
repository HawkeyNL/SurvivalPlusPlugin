package me.hawkeynl.survivalplus.teleport;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Handles /tpa command.
 */
public class TpaCommand implements CommandExecutor {
    private final TeleportRequestManager manager;

    public TpaCommand(TeleportRequestManager manager) {
        this.manager = manager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "Command can not be executed in the console.");
            return true;
        }

        Player player = (Player) sender;

        if (manager.hasRequest(player.getName())) {
            player.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "You already have a request running, please wait...");
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(SurvivalPlus.PREFIX + ChatColor.YELLOW + "/tpa <player>" + ChatColor.GRAY + " to send a teleport request.");
            return true;
        }

        Player target = player.getServer().getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "Not a valid player.");
            return true;
        }

        if (target == player) {
            player.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "You can not teleport to yourself.");
            return true;
        }

        manager.sendRequest(player, target);
        return true;
    }
}
