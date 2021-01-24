package me.hawkeynl.survivalplus.Enchantments;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public class HasteEnchantment extends Enchantment implements Listener {

    public HasteEnchantment(int key) {
        super(NamespacedKey.minecraft(String.valueOf(key)));
    }

    @EventHandler
    public void haste(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if(canEnchantItem(item) && item.getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.hasteEnchantment.getKey()))) {
            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
            int level = Objects.requireNonNull(item.getItemMeta()).getEnchantLevel(SurvivalPlus.hasteEnchantment) == 2 ? 1 : 0;
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1000000000, level));
        } else {
            player.removePotionEffect(PotionEffectType.FAST_DIGGING);
        }
    }

    @Override
    public String getName() {
        return "Haste";
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
        return EnchantmentTarget.TOOL;
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
        return itemStack.getType() == Material.NETHERITE_PICKAXE ||
                itemStack.getType() == Material.DIAMOND_PICKAXE ||
                itemStack.getType() == Material.IRON_PICKAXE ||
                itemStack.getType() == Material.GOLDEN_PICKAXE ||
                itemStack.getType() == Material.STONE_PICKAXE ||
                itemStack.getType() == Material.WOODEN_PICKAXE ||
                itemStack.getType() == Material.NETHERITE_AXE ||
                itemStack.getType() == Material.DIAMOND_AXE ||
                itemStack.getType() == Material.IRON_AXE ||
                itemStack.getType() == Material.GOLDEN_AXE ||
                itemStack.getType() == Material.STONE_AXE ||
                itemStack.getType() == Material.WOODEN_AXE ||
                itemStack.getType() == Material.NETHERITE_SHOVEL ||
                itemStack.getType() == Material.DIAMOND_SHOVEL ||
                itemStack.getType() == Material.IRON_SHOVEL ||
                itemStack.getType() == Material.GOLDEN_SHOVEL ||
                itemStack.getType() == Material.STONE_SHOVEL ||
                itemStack.getType() == Material.WOODEN_SHOVEL;
    }
}
