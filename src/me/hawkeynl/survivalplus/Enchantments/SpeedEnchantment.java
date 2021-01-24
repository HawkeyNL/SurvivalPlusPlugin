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
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class SpeedEnchantment extends Enchantment implements Listener {

    public SpeedEnchantment(int key) {
        super(NamespacedKey.minecraft(String.valueOf(key)));
    }

    @EventHandler
    public void invClick(InventoryClickEvent event) {
        if(event.getClickedInventory() != null) {
            if(event.getClickedInventory().getType() == InventoryType.PLAYER && event.getSlotType() == InventoryType.SlotType.ARMOR) {
                if(event.getSlot() == 36) { //36 is boots slote
                    if(Objects.requireNonNull(event.getCursor()).getType().toString().toLowerCase().contains("boots") && event.getCursor().getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.speedEnchantment.getKey()))) {
                        event.getWhoClicked().removePotionEffect(PotionEffectType.SPEED);
                        int level = Objects.requireNonNull(event.getCursor().getItemMeta()).getEnchantLevel(SurvivalPlus.speedEnchantment) == 2 ? 1 : 0;
                        event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000000, level));
                    } else {
                        event.getWhoClicked().removePotionEffect(PotionEffectType.SPEED);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack boots = player.getInventory().getBoots();

        if(boots == null) {
            return;
        }

        if(canEnchantItem(boots) && boots.getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.speedEnchantment.getKey()))) {
            player.removePotionEffect(PotionEffectType.SPEED);
            int level = Objects.requireNonNull(boots.getItemMeta()).getEnchantLevel(SurvivalPlus.speedEnchantment) == 2 ? 1 : 0;
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000000, level));
        } else {
            player.removePotionEffect(PotionEffectType.SPEED);
        }
    }

    @Override
    public String getName() {
        return "Speed";
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ARMOR_FEET;
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
        return itemStack.getType() == Material.NETHERITE_BOOTS ||
                itemStack.getType() == Material.DIAMOND_BOOTS ||
                itemStack.getType() == Material.IRON_BOOTS ||
                itemStack.getType() == Material.GOLDEN_BOOTS ||
                itemStack.getType() == Material.CHAINMAIL_BOOTS ||
                itemStack.getType() == Material.LEATHER_BOOTS;
    }
}
