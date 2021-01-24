package me.hawkeynl.survivalplus.Commands;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class RepairCommand implements CommandExecutor {
    private final String PREFIX = SurvivalPlus.PREFIX;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        try {
            Player player = (Player) commandSender;
            ItemStack item = player.getInventory().getItemInMainHand();
            int level = player.getLevel();
            int durability = item.getDurability();

            double cost = (double) durability / 200;

            if(args.length == 0) {
                if(level >= cost) {
                    player.setLevel((player.getLevel() - (int)cost));
                    item.setDurability((short)0);
                } else {
                    player.sendMessage(PREFIX + ChatColor.RED + "You do not have enough levels to repair this item.");
                    return false;
                }
                return false;
            } else {
                if(args[0].equalsIgnoreCase("set") && player.isOp()) {
                    if(!args[1].isEmpty()) {
                        item.setDurability(Short.parseShort(args[1]));
                        return false;
                    }
                }

                return false;
            }
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.RED + "Command can not be executed in the console, please join the server to execute the command.");
            return false;
        }
    }
}
