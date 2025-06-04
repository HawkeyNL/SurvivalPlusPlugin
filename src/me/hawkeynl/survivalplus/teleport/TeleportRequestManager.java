package me.hawkeynl.survivalplus.teleport;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles pending teleport requests between players.
 */
public class TeleportRequestManager {
    private final Map<String, String> requests = new HashMap<>();
    private final SurvivalPlus plugin;

    public TeleportRequestManager(SurvivalPlus plugin) {
        this.plugin = plugin;
    }

    /**
     * Check if a player already has a pending request.
     */
    public boolean hasRequest(String playerName) {
        return requests.containsKey(playerName);
    }

    /**
     * Send a teleport request and schedule expiration.
     */
    public void sendRequest(Player sender, Player recipient) {
        sender.sendMessage(SurvivalPlus.PREFIX + ChatColor.GREEN + "Sent teleport request to " + ChatColor.YELLOW + recipient.getDisplayName());

        recipient.sendMessage(SurvivalPlus.PREFIX + ChatColor.YELLOW + sender.getDisplayName() + ChatColor.GRAY + " has sent a teleport request to you.");
        recipient.sendMessage(SurvivalPlus.PREFIX + ChatColor.YELLOW + "/tpaccept" + ChatColor.GRAY + " to accept the teleport request.");
        recipient.sendMessage(SurvivalPlus.PREFIX + ChatColor.YELLOW + "/tpdeny" + ChatColor.GRAY + "  to deny the teleport request.");

        requests.put(recipient.getName(), sender.getName());

        Bukkit.getScheduler().runTaskLater(plugin, () -> killRequest(recipient.getName()), 500L);
    }

    /**
     * Accept a pending request for the given recipient.
     */
    public boolean acceptRequest(Player recipient) {
        if (requests.containsKey(recipient.getName())) {
            Player target = plugin.getServer().getPlayer(requests.get(recipient.getName()));
            requests.remove(recipient.getName());

            if (target != null) {
                target.teleport(recipient);
                target.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Teleporting to " + ChatColor.YELLOW + recipient.getDisplayName());
                return true;
            } else {
                recipient.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "Can not teleport player, they might have gone offline.");
                return false;
            }
        }
        return false;
    }

    /**
     * Deny a pending request for the given recipient.
     */
    public boolean denyRequest(Player recipient) {
        if (requests.containsKey(recipient.getName())) {
            Player target = plugin.getServer().getPlayer(requests.get(recipient.getName()));
            requests.remove(recipient.getName());

            if (target != null) {
                target.sendMessage(SurvivalPlus.PREFIX + ChatColor.YELLOW + recipient.getDisplayName() + ChatColor.RED + " rejected your teleport request.");
                recipient.sendMessage(SurvivalPlus.PREFIX + ChatColor.YELLOW + target.getDisplayName() + ChatColor.GRAY + " was rejected.");
                return true;
            } else {
                recipient.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "Can not deny teleport from player, they might have gone offline.");
                return false;
            }
        }
        return false;
    }

    /**
     * Remove a request if it still exists and notify the sender.
     */
    public boolean killRequest(String key) {
        if (requests.containsKey(key)) {
            Player player = plugin.getServer().getPlayer(requests.get(key));
            if (player != null) {
                player.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "Your teleport request timed out.");
            }

            requests.remove(key);
            return true;
        }
        return false;
    }
}
