package me.hawkeynl.survivalplus.Enchantments;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class FireResistanceEnchantment extends Enchantment implements Listener {

    public FireResistanceEnchantment(int key) {
        super(NamespacedKey.minecraft(String.valueOf(key)));
    }

    @EventHandler
    public void invClick(InventoryClickEvent event) {
        if(event.getClickedInventory() != null) {
            if(event.getClickedInventory().getType() == InventoryType.PLAYER && event.getSlotType() == InventoryType.SlotType.ARMOR) {
                if(event.getSlot() == 37) { //37 is leggings slot
                    if(Objects.requireNonNull(event.getCursor()).getType().toString().toLowerCase().contains("leggings") && event.getCursor().getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.fireResistanceEnchantment.getKey()))) {
                        event.getWhoClicked().removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                        int level = Objects.requireNonNull(event.getCursor().getItemMeta()).getEnchantLevel(SurvivalPlus.fireResistanceEnchantment) == 2 ? 1 : 0;
                        event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000000, level));
                    } else {
                        event.getWhoClicked().removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack leggings = player.getInventory().getLeggings();

        if(leggings == null) {
            return;
        }

        if(canEnchantItem(leggings) && leggings.getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.fireResistanceEnchantment.getKey()))) {
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
            int level = Objects.requireNonNull(leggings.getItemMeta()).getEnchantLevel(SurvivalPlus.fireResistanceEnchantment) == 2 ? 1 : 0;
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 1000000000, level));
        } else {
            player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        }
    }

    @Override
    public String getName() {
        return "Fire Resistance";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR_LEGS;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return itemStack.getType() == Material.NETHERITE_LEGGINGS ||
                itemStack.getType() == Material.DIAMOND_LEGGINGS ||
                itemStack.getType() == Material.IRON_LEGGINGS ||
                itemStack.getType() == Material.GOLDEN_LEGGINGS ||
                itemStack.getType() == Material.CHAINMAIL_LEGGINGS ||
                itemStack.getType() == Material.LEATHER_LEGGINGS;
    }
}
