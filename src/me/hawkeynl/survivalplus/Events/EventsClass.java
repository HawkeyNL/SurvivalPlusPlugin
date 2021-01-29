package me.hawkeynl.survivalplus.Events;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class EventsClass implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "+ " + ChatColor.RESET + ChatColor.WHITE + player.getDisplayName() + ChatColor.RESET);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        event.setQuitMessage(ChatColor.RED + "" + ChatColor.BOLD + "- " + ChatColor.RESET + ChatColor.WHITE + player.getDisplayName() + ChatColor.RESET);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(ChatColor.GREEN + "" + ChatColor.BOLD + player.getLevel() + ChatColor.RESET + " " + ChatColor.WHITE + player.getDisplayName() + " » " + ChatColor.RESET + ChatColor.GRAY + event.getMessage());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage(ChatColor.RED + event.getDeathMessage());
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        try {
            Player player = event.getPlayer();

            String world = player.getWorld().getName().equals("world") ?
                    ChatColor.GREEN + "" + ChatColor.BOLD + " EARTH  " :
                    (player.getWorld().getName().equals("world_nether") ?
                            ChatColor.DARK_RED + "" + ChatColor.BOLD + " NETHER  " :
                            ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + " END     ");

            player.setPlayerListName(world + ChatColor.RESET + " | " + ChatColor.WHITE + player.getDisplayName() + " " + ChatColor.GRAY + "(" + ChatColor.RED + player.getStatistic(Statistic.DEATHS) + ChatColor.GRAY + ")");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void playerRenameItem(InventoryClickEvent event) {
        if(event.getView().getType() == InventoryType.GRINDSTONE) {
            if(event.getRawSlot() == 2) {
                ItemStack item0 = Objects.requireNonNull(event.getView().getItem(0));
                ItemStack item1 = Objects.requireNonNull(event.getView().getItem(1));
                ItemStack item2 = Objects.requireNonNull(event.getView().getItem(2));
                if(item0.getType() != Material.AIR && item2.getType() != Material.AIR || item1.getType() != Material.AIR && item2.getType() != Material.AIR) {
                    for (Enchantment e : SurvivalPlus.custom_enchants) {
                        if(item0.getEnchantments().containsKey(Enchantment.getByKey(e.getKey())) && item1.getEnchantments().containsKey(Enchantment.getByKey(e.getKey()))) {
                            event.setCancelled(true);
                            event.getView().setItem(2, new ItemStack(Material.AIR));
                        } else if(item0.getEnchantments().containsKey(Enchantment.getByKey(e.getKey())) || item1.getEnchantments().containsKey(Enchantment.getByKey(e.getKey()))) {
                            ItemMeta meta = item2.getItemMeta();
                            meta.setLore(null);
                            item2.setItemMeta(meta);
                        }
                    }
                }
            }
        }

        if(event.getView().getType() == InventoryType.ANVIL) {
            if(event.getRawSlot() == 2) {
                ItemStack item0 = Objects.requireNonNull(event.getView().getItem(0));
                ItemStack item1 = Objects.requireNonNull(event.getView().getItem(1));
                ItemStack item2 = Objects.requireNonNull(event.getView().getItem(2));
                if(item0.getType() != Material.AIR && item2.getType() != Material.AIR || item1.getType() != Material.AIR && item2.getType() != Material.AIR) {
                    for (Enchantment e : SurvivalPlus.custom_enchants) {
                        int level = 0;
                        if(item0.getEnchantments().containsKey(Enchantment.getByKey(e.getKey())) && item1.getEnchantments().containsKey(Enchantment.getByKey(e.getKey()))) {
                            event.setCancelled(true);
                            event.getView().setItem(2, new ItemStack(Material.AIR));
                        } else if(item0.getEnchantments().containsKey(Enchantment.getByKey(e.getKey()))) {
                            level = Objects.requireNonNull(item0.getItemMeta()).getEnchantLevel(e);
                            item2.removeEnchantment(e);
                            item2.addUnsafeEnchantment(e, level);
                        } else if(item1.getEnchantments().containsKey(Enchantment.getByKey(e.getKey()))) {
                            level = Objects.requireNonNull(item1.getItemMeta()).getEnchantLevel(e);
                            item2.removeEnchantment(e);
                            item2.addUnsafeEnchantment(e, level);
                        }
                    }
                }
            }
        }
    }
}
