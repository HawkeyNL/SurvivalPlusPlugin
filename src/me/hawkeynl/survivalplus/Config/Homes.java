package me.hawkeynl.survivalplus.Config;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Homes {
    private static final FileConfiguration config = SurvivalPlus.getPlugin().getConfig();

    public static void teleportToHomeLocation(Player player, String name) throws InterruptedException {
        String uuid = player.getUniqueId().toString();
        String con = "Users." + uuid + ".Homes." + name.toLowerCase();
        String homeExists = config.getString(con);

        if(homeExists != null) {
            World w = Bukkit.getServer().getWorld(Objects.requireNonNull(config.getString(con + ".WORLD")));

            double x = config.getDouble(con + ".X");
            double y = config.getDouble(con + ".Y");
            double z = config.getDouble(con + ".Z");
            double yaw = config.getDouble(con + ".YAW");
            double pitch = config.getDouble(con + ".PITCH");

            player.sendMessage(SurvivalPlus.PREFIX + ChatColor.GRAY + "Teleporting in " + ChatColor.YELLOW + 2);
            Thread.sleep(1000);
            player.sendMessage(SurvivalPlus.PREFIX + ChatColor.GRAY + "Teleporting in " + ChatColor.YELLOW + 1);
            Thread.sleep(1000);

            player.sendMessage(SurvivalPlus.PREFIX + ChatColor.GREEN + "Teleporting to " + ChatColor.YELLOW + name);
            Thread.sleep(500);
            player.teleport(new Location(w, x, y, z, (float)yaw, (float)pitch));
        } else {
            player.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "No home found with the name " + ChatColor.YELLOW + name);
        }
    }

    public static void storeHomeLocation(Player player, String name) {
        String uuid = player.getUniqueId().toString();
        String con = "Users." + uuid + ".Homes." + name.toLowerCase();
        String homeExists = config.getString(con);

        if(homeExists != null) {
            player.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "Home with the name " + ChatColor.YELLOW + name + ChatColor.RED + " already exists.");
        } else {
            config.set(con + ".WORLD", player.getWorld().getName());
            config.set(con + ".X", player.getLocation().getX());
            config.set(con + ".Y", player.getLocation().getY());
            config.set(con + ".Z", player.getLocation().getZ());
            config.set(con + ".YAW", player.getEyeLocation().getYaw());
            config.set(con + ".PITCH", player.getEyeLocation().getPitch());

            player.sendMessage(SurvivalPlus.PREFIX + ChatColor.GREEN + "Successfully set " + ChatColor.YELLOW + name + ChatColor.GREEN + " as a home");

            SurvivalPlus.getPlugin().saveConfig();
        }
    }

    public static void deleteHomeLocation(Player player, String name) {
        String uuid = player.getUniqueId().toString();
        String con = "Users." + uuid + ".Homes." + name.toLowerCase();
        String homeExists = config.getString(con);

        if(homeExists != null) {
            config.set(con, null);

            player.sendMessage(SurvivalPlus.PREFIX + ChatColor.GREEN + "Successfully deleted home " + ChatColor.YELLOW + name);

            SurvivalPlus.getPlugin().saveConfig();
        } else {
            player.sendMessage(SurvivalPlus.PREFIX + ChatColor.RED + "No home found with the name " + ChatColor.YELLOW + name);
        }
    }

    public static Object[] getHomes(Player player) {
        return config.getConfigurationSection("Users." + player.getUniqueId().toString() + ".Homes").getKeys(false).toArray();
    }
}
