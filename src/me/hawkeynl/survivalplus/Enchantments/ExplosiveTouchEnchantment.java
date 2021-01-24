package me.hawkeynl.survivalplus.Enchantments;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class ExplosiveTouchEnchantment extends Enchantment implements Listener {

    public ExplosiveTouchEnchantment(int key) {
        super(NamespacedKey.minecraft(String.valueOf(key)));
    }

    @EventHandler
    public void onProjectileShoot(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        LivingEntity shooter = (LivingEntity) projectile.getShooter();
        if (shooter instanceof Player) {
            ItemStack item = ((Player) shooter).getInventory().getItemInMainHand();
            if(item.getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.explosiveTouchEnchantment.getKey()))) {
                projectile.setMetadata("ExplosiveTouch", new FixedMetadataValue(SurvivalPlus.getPlugin(), true));
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile entity = event.getEntity();
        LivingEntity shooter = (LivingEntity) entity.getShooter();
        if (entity.hasMetadata("ExplosiveTouch")) {
            Location loc = entity.getLocation();
            ((Player) shooter).setNoDamageTicks(10);
            entity.getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), (float) 2.5, false, true);
            entity.remove();
        }
    }

    @Override
    public String getName() {
        return "Explosive Touch";
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
        return EnchantmentTarget.BOW;
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
        return itemStack.getType() == Material.BOW;
    }
}
