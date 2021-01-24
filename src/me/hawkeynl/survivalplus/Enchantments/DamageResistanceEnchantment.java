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

public class DamageResistanceEnchantment extends Enchantment implements Listener {

    public DamageResistanceEnchantment(int key) {
        super(NamespacedKey.minecraft(String.valueOf(key)));
    }

    @EventHandler
    public void invClick(InventoryClickEvent event) {
        if(event.getClickedInventory() != null) {
            if(event.getClickedInventory().getType() == InventoryType.PLAYER && event.getSlotType() == InventoryType.SlotType.ARMOR) {
                if(event.getSlot() == 38) { //38 is chestplate slot
                    if(Objects.requireNonNull(event.getCursor()).getType().toString().toLowerCase().contains("chestplate") && event.getCursor().getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.damageResistanceEnchantment.getKey()))) {
                        event.getWhoClicked().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                        int level = Objects.requireNonNull(event.getCursor().getItemMeta()).getEnchantLevel(SurvivalPlus.damageResistanceEnchantment) == 2 ? 1 : 0;
                        event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000000, level));
                    } else {
                        event.getWhoClicked().removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack chestplate = player.getInventory().getChestplate();

        if(chestplate == null) {
            return;
        }

        if(canEnchantItem(chestplate) && chestplate.getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.damageResistanceEnchantment.getKey()))) {
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
            int level = Objects.requireNonNull(chestplate.getItemMeta()).getEnchantLevel(SurvivalPlus.damageResistanceEnchantment) == 2 ? 1 : 0;
            player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 1000000000, level));
        } else {
            player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        }
    }

    @Override
    public String getName() {
        return "Damage Resistance";
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
        return EnchantmentTarget.ARMOR_TORSO;
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
        return itemStack.getType() == Material.NETHERITE_CHESTPLATE ||
                itemStack.getType() == Material.DIAMOND_CHESTPLATE ||
                itemStack.getType() == Material.IRON_CHESTPLATE ||
                itemStack.getType() == Material.GOLDEN_CHESTPLATE ||
                itemStack.getType() == Material.CHAINMAIL_CHESTPLATE ||
                itemStack.getType() == Material.LEATHER_CHESTPLATE;
    }
}
