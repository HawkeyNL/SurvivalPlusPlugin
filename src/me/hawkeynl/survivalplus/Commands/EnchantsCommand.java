package me.hawkeynl.survivalplus.Commands;

import me.hawkeynl.survivalplus.Enchantments.*;
import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class EnchantsCommand implements CommandExecutor {
    private static final String arrow_string = " » ";

    //Enchantments
    private static final ExplosiveTouchEnchantment explosiveTouchEnchantment = SurvivalPlus.explosiveTouchEnchantment;
    private static final HasteEnchantment hasteEnchantment = SurvivalPlus.hasteEnchantment;
    private static final SpeedEnchantment speedEnchantment = SurvivalPlus.speedEnchantment;
    private static final FireResistanceEnchantment fireResistanceEnchantment = SurvivalPlus.fireResistanceEnchantment;
    private static final DamageResistanceEnchantment damageResistanceEnchantment = SurvivalPlus.damageResistanceEnchantment;
    private static final GrindBoosterEnchantment grindBoosterEnchantment = SurvivalPlus.grindBoosterEnchantment;
    private static final RegenerationEnchantment regenerationEnchantment = SurvivalPlus.regenerationEnchantment;
    private static final NightVisionEnchantment nightVisionEnchantment = SurvivalPlus.nightVisionEnchantment;
    private static final TeleportationEnchantment teleportationEnchantment = SurvivalPlus.teleportationEnchantment;
    private static final MultiBreakerEnchantment multiBreakerEnchantment = SurvivalPlus.multiBreakerEnchantment;

    //Prefix
    private final String PREFIX = SurvivalPlus.PREFIX;

    //HashMaps
    private static final HashMap<String, Enchantment> ENCHANTMENTS = new HashMap<>();
    private static final HashMap<String, Integer> ENCHANTMENT_COST = new HashMap<>();
    private static final HashMap<String, String> COMMANDS = new HashMap<>();

    static {
        ENCHANTMENTS.put("explosivetouch", explosiveTouchEnchantment);
        ENCHANTMENT_COST.put("explosivetouch", 100);
        ENCHANTMENTS.put("haste", hasteEnchantment);
        ENCHANTMENT_COST.put("haste", 30);
        ENCHANTMENTS.put("speed", speedEnchantment);
        ENCHANTMENT_COST.put("speed", 25);
        ENCHANTMENTS.put("damageresistance", damageResistanceEnchantment);
        ENCHANTMENT_COST.put("damageresistance", 35);
        ENCHANTMENTS.put("fireresistance", fireResistanceEnchantment);
        ENCHANTMENT_COST.put("fireresistance", 50);
        ENCHANTMENTS.put("grindbooster", grindBoosterEnchantment);
        ENCHANTMENT_COST.put("grindbooster", 25);
        ENCHANTMENTS.put("regeneration", regenerationEnchantment);
        ENCHANTMENT_COST.put("regeneration", 30);
        ENCHANTMENTS.put("nightvision", nightVisionEnchantment);
        ENCHANTMENT_COST.put("nightvision", 50);
        ENCHANTMENTS.put("teleportation", teleportationEnchantment);
        ENCHANTMENT_COST.put("teleportation", 50);
        ENCHANTMENTS.put("multibreaker", multiBreakerEnchantment);
        ENCHANTMENT_COST.put("multibreaker", 50);
        COMMANDS.put("list", "displays all the available enchantments.");
        COMMANDS.put("[get | buy] <enchantment>", "buy an enchantment given to your mainhand item.");
        COMMANDS.put("upgrade <enchantment>", "upgrade an enchantment on your mainhand item.");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        try {
            Player player = (Player) commandSender;

            if(args.length == 0 || args[0].equalsIgnoreCase("help")) {
                player.sendMessage(PREFIX + ChatColor.WHITE + "Enchantments Help");
                for (String s : COMMANDS.keySet()) {
                    player.sendMessage(ChatColor.BOLD + "" + ChatColor.GREEN + "/" + command.getName() + " " + s + ChatColor.RESET + ChatColor.GRAY + arrow_string + ChatColor.WHITE + COMMANDS.get(s));
                }
            } else if(args[0].equalsIgnoreCase("list")) {
                player.sendMessage(PREFIX + ChatColor.WHITE + "Enchantments List");
                for (String e : ENCHANTMENTS.keySet()) {
                    player.sendMessage(ChatColor.GREEN + String.valueOf(ENCHANTMENT_COST.get(e)) + " " + ChatColor.WHITE + ENCHANTMENTS.get(e).getName() + ChatColor.DARK_PURPLE + " (" + ENCHANTMENTS.get(e).getStartLevel() + "-" + ENCHANTMENTS.get(e).getMaxLevel() + ") " + ChatColor.GRAY + ENCHANTMENTS.get(e).getItemTarget().name().replace("_", " ").toLowerCase());
                }
            } else {
                ItemStack item = player.getInventory().getItemInMainHand();
                int level = player.getLevel();

                Enchantment upgradeEnchantment = null;

                ArrayList<String> enchantmentLore = new ArrayList<>();
                int enchantmentLevel = 0;

                if(args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("buy")) {
                    for (String e : ENCHANTMENTS.keySet()) {
                        if(e.equalsIgnoreCase(args[1])) {
                            if(!item.getEnchantments().containsKey(Enchantment.getByKey(ENCHANTMENTS.get(e).getKey()))) {
                                if(ENCHANTMENTS.get(e).canEnchantItem(item)) {
                                    if(level >= ENCHANTMENT_COST.get(e)) {
                                        enchantmentLevel = 1;
                                        enchantmentLore.add(ChatColor.DARK_PURPLE + ENCHANTMENTS.get(e).getName() + " " + parseEnchantmentLevel(enchantmentLevel));
                                        item.addUnsafeEnchantment(ENCHANTMENTS.get(e), enchantmentLevel);
                                        player.setLevel((player.getLevel() - ENCHANTMENT_COST.get(e)));
                                        Bukkit.getServer().broadcastMessage(PREFIX + ChatColor.WHITE + player.getDisplayName() + ChatColor.GRAY + " has bought "+ ChatColor.DARK_PURPLE + ENCHANTMENTS.get(e).getName() + " " + enchantmentLevel + ChatColor.GRAY + " for their " + item.getType().name().replace("_", " ").toLowerCase());
                                    } else {
                                        player.sendMessage(PREFIX + ChatColor.RED + "You do not have enough levels to buy this enchantment.");
                                        return false;
                                    }
                                } else {
                                    player.sendMessage(PREFIX + ChatColor.RED + "Item can not be enchanted.");
                                    return false;
                                }
                            } else {
                                player.sendMessage(PREFIX + ChatColor.RED + "Enchantment " + ChatColor.BOLD + "" + ChatColor.WHITE + ENCHANTMENTS.get(e).getName() + ChatColor.RESET + ChatColor.RED + " is already on this item, please use " + ChatColor.WHITE + "/enchants upgrade <enchantment>" + ChatColor.RED + " to upgrade your enchantment.");
                                return false;
                            }
                        }
                    }
                } else if(args[0].equalsIgnoreCase("upgrade")) {
                    for (String e : ENCHANTMENTS.keySet()) {
                        int currentLevel = Objects.requireNonNull(item.getItemMeta()).getEnchantLevel(ENCHANTMENTS.get(e));

                        if(e.equalsIgnoreCase(args[1])) {
                            if(ENCHANTMENTS.get(e).canEnchantItem(item)) {
                                if(item.getEnchantments().containsKey(Enchantment.getByKey(ENCHANTMENTS.get(e).getKey()))) {
                                    if(ENCHANTMENTS.get(e).getStartLevel() <= currentLevel && ENCHANTMENTS.get(e).getMaxLevel() > currentLevel) {
                                        if(player.getLevel() >= (currentLevel * ENCHANTMENT_COST.get(e))) {
                                            item.removeEnchantment(ENCHANTMENTS.get(e));
                                            item.addUnsafeEnchantment(ENCHANTMENTS.get(e), ++currentLevel);
                                            enchantmentLore.add(ChatColor.DARK_PURPLE + ENCHANTMENTS.get(e).getName() + " " + parseEnchantmentLevel(currentLevel));
                                            upgradeEnchantment = ENCHANTMENTS.get(e);
                                            enchantmentLevel = currentLevel;
                                            player.setLevel((player.getLevel() - (currentLevel * ENCHANTMENT_COST.get(e))));
                                            Bukkit.getServer().broadcastMessage(PREFIX + ChatColor.WHITE + player.getDisplayName() + ChatColor.GRAY + " has upgraded " + ChatColor.DARK_PURPLE + ENCHANTMENTS.get(e).getName() + " " + currentLevel + ChatColor.GRAY + " for their " + item.getType().name().replace("_", " ").toLowerCase());
                                        } else {
                                            player.sendMessage(PREFIX + ChatColor.RED + "Enchantment " + ChatColor.DARK_PURPLE + ENCHANTMENTS.get(e).getName() + ChatColor.RED + " has a cost of " + ChatColor.GREEN + "" + ChatColor.BOLD + ((currentLevel + 1) * ENCHANTMENT_COST.get(e)) + ChatColor.RED + " for an upgrade.");
                                            return false;
                                        }
                                    } else {
                                        player.sendMessage(PREFIX + ChatColor.RED + "Enchantment " + ChatColor.DARK_PURPLE + ENCHANTMENTS.get(e).getName() + ChatColor.RED + " is already max level.");
                                        return false;
                                    }
                                } else {
                                    player.sendMessage(PREFIX + ChatColor.RED + "Enchantment " + ChatColor.BOLD + "" + ChatColor.WHITE + ENCHANTMENTS.get(e).getName() + ChatColor.RESET + ChatColor.RED + " isn't on this item, please use " + ChatColor.WHITE + "/enchants [get | buy] <enchantment>" + ChatColor.RED + " to buy an enchantment.");
                                    return false;
                                }
                            } else {
                                player.sendMessage(PREFIX + ChatColor.RED + "Item can not be enchanted.");
                                return false;
                            }
                        }
                    }
                }

                if(args[0].equalsIgnoreCase("upgrade")) {
                    ItemMeta meta = item.getItemMeta();
                    if(meta != null) {
                        if(meta.getLore() != null) {
                            for (String s : meta.getLore()) {
                                if(!s.equalsIgnoreCase(ChatColor.DARK_PURPLE + upgradeEnchantment.getName() + " " + parseEnchantmentLevel(enchantmentLevel - 1))) {
                                    enchantmentLore.add(s);
                                }
                            }
                        }

                        meta.setLore(enchantmentLore);
                        item.setItemMeta(meta);
                    }
                    return false;
                }

                ItemMeta meta = item.getItemMeta();
                if(meta != null) {
                    if(meta.getLore() != null) {
                        enchantmentLore.addAll(meta.getLore());
                    }

                    meta.setLore(enchantmentLore);
                    item.setItemMeta(meta);
                }
            }

            return false;
        } catch (Exception e) {
            Bukkit.getServer().getConsoleSender().sendMessage(PREFIX + ChatColor.RED + "Command can not be executed in the console, please join the server to execute the command.");
            return false;
        }
    }

    public String parseEnchantmentLevel(int level) {
        switch (level) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            default:
                return "0";
        }
    }
}
