package me.hawkeynl.survivalplus.Enchantments;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class TeleportationEnchantment extends Enchantment implements Listener {

    public TeleportationEnchantment(int key) {
        super(NamespacedKey.minecraft(String.valueOf(key)));
    }

    @EventHandler
    public void onProjectileShoot(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        LivingEntity shooter = (LivingEntity) projectile.getShooter();
        if (shooter instanceof Player) {
            ItemStack item = ((Player) shooter).getInventory().getItemInMainHand();
            if(item.getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.teleportationEnchantment.getKey()))) {
                projectile.setMetadata("Teleportation", new FixedMetadataValue(SurvivalPlus.getPlugin(), true));
                projectile.remove();
            }
        }
    }

    @EventHandler
    public void onProjectileInAir(EntityShootBowEvent event) {
        Projectile entity = (Projectile) event.getProjectile();
        ItemStack bow = event.getBow();
        LivingEntity shooter = (LivingEntity) entity.getShooter();

        if (bow != null && bow.getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.teleportationEnchantment.getKey())) && shooter != null) {
            Entity ent = entity.getWorld().spawn(entity.getLocation().add(0, 1, 0), Arrow.class);
            ent.setVelocity(entity.getVelocity());
            ent.setPassenger(shooter);
        }
    }

    @Override
    public String getName() {
        return "Teleportation";
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
        return itemStack.getType() == Material.BOW || itemStack.getType() == Material.CROSSBOW;
    }
}
