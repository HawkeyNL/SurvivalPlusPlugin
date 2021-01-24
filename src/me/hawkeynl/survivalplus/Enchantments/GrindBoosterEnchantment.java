package me.hawkeynl.survivalplus.Enchantments;

import me.hawkeynl.survivalplus.SurvivalPlus;
import net.minecraft.server.v1_16_R3.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class GrindBoosterEnchantment extends Enchantment implements Listener {

    public GrindBoosterEnchantment(int key) {
        super(NamespacedKey.minecraft(String.valueOf(key)));
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        try {
            Player player = (Player) event.getEntity().getKiller();
            if (player != null && event.getEntity().getType() != EntityType.PLAYER) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (item.getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.grindBoosterEnchantment.getKey()))) {
                    EntityLiving entity = (EntityLiving) ((CraftEntity) event.getEntity()).getHandle();
                    List<ItemStack> drops = event.getDrops();
                    int dropSize = (drops.size() + 1);
                    drops.clear();
                    event.setDroppedExp((entity.getExpReward() * Objects.requireNonNull(item.getItemMeta()).getEnchantLevel(SurvivalPlus.grindBoosterEnchantment)) * (dropSize * dropSize));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName() {
        return "Grind Booster";
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.WEAPON;
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
        return itemStack.getType() == Material.NETHERITE_SWORD ||
                itemStack.getType() == Material.DIAMOND_SWORD ||
                itemStack.getType() == Material.IRON_SWORD ||
                itemStack.getType() == Material.GOLDEN_SWORD ||
                itemStack.getType() == Material.STONE_SWORD ||
                itemStack.getType() == Material.WOODEN_SWORD;
    }
}
