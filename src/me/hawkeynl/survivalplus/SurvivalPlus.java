package me.hawkeynl.survivalplus;

import com.mojang.datafixers.kinds.IdF;
import me.hawkeynl.survivalplus.Commands.CraftCommand;
import me.hawkeynl.survivalplus.Commands.EnchantsCommand;
import me.hawkeynl.survivalplus.Commands.EnderchestCommand;
import me.hawkeynl.survivalplus.Commands.RepairCommand;
import me.hawkeynl.survivalplus.Enchantments.*;
import me.hawkeynl.survivalplus.Events.EventsClass;
import me.hawkeynl.survivalplus.Events.NameTagsEvents;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.help.CommandAliasHelpTopic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SurvivalPlus extends JavaPlugin {
    public static final String PREFIX = ChatColor.BOLD + "" + ChatColor.YELLOW + "[SurvivalPlus] " + ChatColor.RESET;
    public static SurvivalPlus PLUGIN;

    Map<String, String> currentTpaRequests = new HashMap<>();

    public static ArrayList<Enchantment> custom_enchants = new ArrayList<>();
    public static HasteEnchantment hasteEnchantment = new HasteEnchantment(101);
    public static ExplosiveTouchEnchantment explosiveTouchEnchantment = new ExplosiveTouchEnchantment(102);
    public static SpeedEnchantment speedEnchantment = new SpeedEnchantment(103);
    public static FireResistanceEnchantment fireResistanceEnchantment = new FireResistanceEnchantment(104);
    public static DamageResistanceEnchantment damageResistanceEnchantment = new DamageResistanceEnchantment(105);
    public static GrindBoosterEnchantment grindBoosterEnchantment = new GrindBoosterEnchantment(106);
    public static RegenerationEnchantment regenerationEnchantment = new RegenerationEnchantment(107);
    public static NightVisionEnchantment nightVisionEnchantment = new NightVisionEnchantment(108);
    public static TeleportationEnchantment teleportationEnchantment = new TeleportationEnchantment(109);
    public static MultiBreakerEnchantment multiBreakerEnchantment = new MultiBreakerEnchantment(110);

    @Override
    public void onEnable() {
        PLUGIN = this;

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getDescription().getName() + " has started on " + ChatColor.BLUE + "v" + getDescription().getVersion() + ChatColor.RESET);

        custom_enchants.add(hasteEnchantment);
        custom_enchants.add(explosiveTouchEnchantment);
        custom_enchants.add(speedEnchantment);
        custom_enchants.add(fireResistanceEnchantment);
        custom_enchants.add(damageResistanceEnchantment);
        custom_enchants.add(grindBoosterEnchantment);
        custom_enchants.add(regenerationEnchantment);
        custom_enchants.add(nightVisionEnchantment);
        custom_enchants.add(teleportationEnchantment);
        custom_enchants.add(multiBreakerEnchantment);
        loadEnchantments(hasteEnchantment);
        loadEnchantments(explosiveTouchEnchantment);
        loadEnchantments(speedEnchantment);
        loadEnchantments(fireResistanceEnchantment);
        loadEnchantments(damageResistanceEnchantment);
        loadEnchantments(grindBoosterEnchantment);
        loadEnchantments(regenerationEnchantment);
        loadEnchantments(nightVisionEnchantment);
        loadEnchantments(teleportationEnchantment);
        loadEnchantments(multiBreakerEnchantment);

        getCommand("enchants").setExecutor(new EnchantsCommand());
        getCommand("repair").setExecutor(new RepairCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("craft").setExecutor(new CraftCommand());
        getServer().getPluginManager().registerEvents(new EventsClass(), this);
        getServer().getPluginManager().registerEvents(new NameTagsEvents(), this);
        getServer().getPluginManager().registerEvents(hasteEnchantment, this);
        getServer().getPluginManager().registerEvents(explosiveTouchEnchantment, this);
        getServer().getPluginManager().registerEvents(speedEnchantment, this);
        getServer().getPluginManager().registerEvents(fireResistanceEnchantment, this);
        getServer().getPluginManager().registerEvents(damageResistanceEnchantment, this);
        getServer().getPluginManager().registerEvents(grindBoosterEnchantment, this);
        getServer().getPluginManager().registerEvents(regenerationEnchantment, this);
        getServer().getPluginManager().registerEvents(nightVisionEnchantment, this);
        getServer().getPluginManager().registerEvents(teleportationEnchantment, this);
        getServer().getPluginManager().registerEvents(multiBreakerEnchantment, this);
    }

    @Override
    public void onDisable() {
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            for (Enchantment enchantment : custom_enchants){
                byKey.remove(enchantment.getKey());
            }

            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            for (Enchantment enchantment : custom_enchants){
                byName.remove(enchantment.getName());
            }
        } catch (Exception ignored) { }

        getServer().getConsoleSender().sendMessage(ChatColor.RED + getDescription().getName() + " has stopped on " + ChatColor.BLUE + "v" + getDescription().getVersion() + ChatColor.RESET);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if(command.getName().equalsIgnoreCase("tpa")) {
                if(player != null) {
                    if(currentTpaRequests.containsKey(player.getName())) {
                        player.sendMessage(PREFIX + ChatColor.RED + "You already have a request running, please wait...");
                        return false;
                    }

                    if(args.length >= 1) {
                        final Player target = getServer().getPlayer(args[0]);

                        if(target == null) {
                            player.sendMessage(PREFIX + ChatColor.RED + "Not a valid player.");
                            return false;
                        }

                        if(target == player) {
                            player.sendMessage(PREFIX + ChatColor.RED + "You can not teleport to yourself.");
                            return false;
                        }

                        sendRequest(player, target);

                        getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
                            @Override
                            public void run() {
                                killRequest(target.getName());
                            }
                        }, 500);
                    } else {
                        player.sendMessage(PREFIX + ChatColor.YELLOW + "/tpa <player>" + ChatColor.GRAY + " to send a teleport request.");
                        return false;
                    }
                }
                return true;
            }

            if(command.getName().equalsIgnoreCase("tpaccept")) {
                if(player != null) {
                    if(currentTpaRequests.containsKey(player.getName())) {
                        Player target = getServer().getPlayer(currentTpaRequests.get(player.getName()));
                        currentTpaRequests.remove(player.getName());

                        if(target != null) {
                            target.teleport(player);
                            target.sendMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "Teleporting to " + ChatColor.YELLOW + player.getDisplayName());
                            return true;
                        } else {
                            player.sendMessage(PREFIX + ChatColor.RED + "Can not teleport to target, player might have gone offline.");
                            return false;
                        }
                    } else {
                        commandSender.sendMessage(PREFIX + ChatColor.RED + "You do not have any teleport requests.");
                    }
                }
                return true;
            }

            if(command.getName().equalsIgnoreCase("tpdeny")) {
                if(player != null) {
                    if(currentTpaRequests.containsKey(player.getName())) {
                        Player target = getServer().getPlayer(currentTpaRequests.get(player.getName()));
                        currentTpaRequests.remove(player.getName());

                        if(target != null) {
                            target.sendMessage(PREFIX + ChatColor.YELLOW + player.getDisplayName() + ChatColor.RED + " rejected your teleport request.");
                            player.sendMessage(PREFIX + ChatColor.YELLOW + target.getDisplayName() + ChatColor.GRAY + " was rejected.");
                            return true;
                        } else {
                            commandSender.sendMessage(PREFIX + ChatColor.RED + "Can not deny teleport from player, player might have gone offline.");
                            return false;
                        }
                    } else {
                        commandSender.sendMessage(PREFIX + ChatColor.RED + "You do not have any teleport requests.");
                    }
                }
            }
        }

        return false;
    }

    public boolean killRequest(String key) {
        if (currentTpaRequests.containsKey(key)) {
            Player player = getServer().getPlayer(currentTpaRequests.get(key));
            if (!(player == null)) {
                player.sendMessage(PREFIX + ChatColor.RED + "Your teleport request timed out.");
            }

            currentTpaRequests.remove(key);

            return true;
        } else {
            return false;
        }
    }

    public void sendRequest(Player sender, Player recipient) {
        sender.sendMessage(PREFIX + ChatColor.GREEN + "Sent teleport request to " + ChatColor.YELLOW + recipient.getDisplayName());

        recipient.sendMessage(PREFIX + ChatColor.YELLOW + sender.getDisplayName() + ChatColor.GRAY + " has sent a teleport request to you.");
        recipient.sendMessage(PREFIX + ChatColor.YELLOW + "/tpaccept" + ChatColor.GRAY + " to accept the teleport request.");
        recipient.sendMessage(PREFIX + ChatColor.YELLOW + "/tpadeny" + ChatColor.GRAY + "  to deny the teleport request.");

        currentTpaRequests.put(recipient.getName(), sender.getName());
    }

    public static SurvivalPlus getPlugin() { return PLUGIN; }

    public void loadEnchantments(Enchantment enchantment) {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
