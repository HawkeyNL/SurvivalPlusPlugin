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

public class NightVisionEnchantment extends Enchantment implements Listener {

    public NightVisionEnchantment(int key) {
        super(NamespacedKey.minecraft(String.valueOf(key)));
    }

    @EventHandler
    public void invClick(InventoryClickEvent event) {
        if(event.getClickedInventory() != null) {
            if(event.getClickedInventory().getType() == InventoryType.PLAYER && event.getSlotType() == InventoryType.SlotType.ARMOR) {
                if(event.getSlot() == 39) { //39 is helmet slot
                    if(Objects.requireNonNull(event.getCursor()).getType().toString().toLowerCase().contains("helmet") && event.getCursor().getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.nightVisionEnchantment.getKey()))) {
                        event.getWhoClicked().removePotionEffect(PotionEffectType.NIGHT_VISION);
                        int level = Objects.requireNonNull(event.getCursor().getItemMeta()).getEnchantLevel(SurvivalPlus.nightVisionEnchantment) == 2 ? 1 : 0;
                        event.getWhoClicked().addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000000, level));
                    } else {
                        event.getWhoClicked().removePotionEffect(PotionEffectType.NIGHT_VISION);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ItemStack helmet = player.getInventory().getHelmet();

        if(helmet == null) {
            return;
        }

        if(canEnchantItem(helmet) && helmet.getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.nightVisionEnchantment.getKey()))) {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            int level = Objects.requireNonNull(helmet.getItemMeta()).getEnchantLevel(SurvivalPlus.nightVisionEnchantment) == 2 ? 1 : 0;
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1000000000, level));
        } else {
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
        }
    }

    @Override
    public String getName() {
        return "Night Vision";
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
        return EnchantmentTarget.ARMOR_HEAD;
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
        return itemStack.getType() == Material.NETHERITE_HELMET ||
                itemStack.getType() == Material.DIAMOND_HELMET ||
                itemStack.getType() == Material.IRON_HELMET ||
                itemStack.getType() == Material.GOLDEN_HELMET ||
                itemStack.getType() == Material.CHAINMAIL_HELMET ||
                itemStack.getType() == Material.LEATHER_HELMET;
    }
}
