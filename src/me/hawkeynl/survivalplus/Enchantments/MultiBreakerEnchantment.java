package me.hawkeynl.survivalplus.Enchantments;

import me.hawkeynl.survivalplus.SurvivalPlus;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MultiBreakerEnchantment extends Enchantment implements Listener {
    public static HashMap<String, Integer> stop = new HashMap<>();
    public static HashMap<String, Integer> blockFace = new HashMap<>();
    public static ArrayList<Material> blocksNotToBreak = new ArrayList<>();

    public MultiBreakerEnchantment(int key) {
        super(NamespacedKey.minecraft(String.valueOf(key)));
    }

    static {
        blocksNotToBreak.add(Material.COAL_ORE);
        blocksNotToBreak.add(Material.IRON_ORE);
        blocksNotToBreak.add(Material.GOLD_ORE);
        blocksNotToBreak.add(Material.LAPIS_ORE);
        blocksNotToBreak.add(Material.REDSTONE_ORE);
        blocksNotToBreak.add(Material.EMERALD_ORE);
        blocksNotToBreak.add(Material.DIAMOND_ORE);
        blocksNotToBreak.add(Material.NETHER_GOLD_ORE);
        blocksNotToBreak.add(Material.NETHER_QUARTZ_ORE);
        blocksNotToBreak.add(Material.ANCIENT_DEBRIS);
        blocksNotToBreak.add(Material.CHEST);
        blocksNotToBreak.add(Material.ENDER_CHEST);
        blocksNotToBreak.add(Material.TRAPPED_CHEST);
    }

    @EventHandler
    public void getBlockFace(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        BlockFace bFace = event.getBlockFace();
        if (itemInHand.getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.multiBreakerEnchantment.getKey())) && !player.isSneaking()) {
            if ((bFace == BlockFace.UP) || (bFace == BlockFace.DOWN)) {
                blockFace.put(player.getName(), Integer.valueOf(1));
            }
            if ((bFace == BlockFace.NORTH) || (bFace == BlockFace.SOUTH)) {
                blockFace.put(player.getName(), Integer.valueOf(2));
            }
            if ((bFace == BlockFace.WEST) || (bFace == BlockFace.EAST)) {
                blockFace.put(player.getName(), Integer.valueOf(3));
            }
        }
    }

    @EventHandler
    public void onPickAxeBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        ItemStack itemInHand = player.getItemInHand();
        if (itemInHand.getEnchantments().containsKey(Enchantment.getByKey(SurvivalPlus.multiBreakerEnchantment.getKey())) && !player.isSneaking()) {
            threeByThree(blocksNotToBreak, event);
        }
    }

    public void threeByThree(ArrayList<Material> blocksNotToBreak, BlockBreakEvent event) {
        if (!event.isCancelled()) {
            Player player = event.getPlayer();
            ItemStack itemInHand = player.getItemInHand();
            Block mainBlock = event.getBlock();
            int total = 0;
            int enchant = 0;
            stop.put(player.getName(), Integer.valueOf(1));
            if (itemInHand.containsEnchantment(Enchantment.SILK_TOUCH)) {
                enchant = 1;
            }

            ArrayList<Block> blocks = new ArrayList<Block>();
            if ((Integer) blockFace.get(player.getName()) == 1) {
                blocks.add(mainBlock.getRelative(BlockFace.NORTH_WEST));
                blocks.add(mainBlock.getRelative(BlockFace.NORTH));
                blocks.add(mainBlock.getRelative(BlockFace.NORTH_EAST));
                blocks.add(mainBlock.getRelative(BlockFace.WEST));
                blocks.add(mainBlock.getRelative(BlockFace.EAST));
                blocks.add(mainBlock.getRelative(BlockFace.SOUTH_WEST));
                blocks.add(mainBlock.getRelative(BlockFace.SOUTH));
                blocks.add(mainBlock.getRelative(BlockFace.SOUTH_EAST));
            }
            if ((Integer) blockFace.get(player.getName()) == 2) {
                blocks.add(mainBlock.getRelative(BlockFace.UP).getRelative(BlockFace.WEST));
                blocks.add(mainBlock.getRelative(BlockFace.UP));
                blocks.add(mainBlock.getRelative(BlockFace.UP).getRelative(BlockFace.EAST));
                blocks.add(mainBlock.getRelative(BlockFace.WEST));
                blocks.add(mainBlock.getRelative(BlockFace.EAST));
                blocks.add(mainBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.WEST));
                blocks.add(mainBlock.getRelative(BlockFace.DOWN));
                blocks.add(mainBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.EAST));
            }
            if ((Integer) blockFace.get(player.getName()) == 3) {
                blocks.add(mainBlock.getRelative(BlockFace.UP).getRelative(BlockFace.NORTH));
                blocks.add(mainBlock.getRelative(BlockFace.UP));
                blocks.add(mainBlock.getRelative(BlockFace.UP).getRelative(BlockFace.SOUTH));
                blocks.add(mainBlock.getRelative(BlockFace.NORTH));
                blocks.add(mainBlock.getRelative(BlockFace.SOUTH));
                blocks.add(mainBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.NORTH));
                blocks.add(mainBlock.getRelative(BlockFace.DOWN));
                blocks.add(mainBlock.getRelative(BlockFace.DOWN).getRelative(BlockFace.SOUTH));
            }
            for (Block block : blocks) {
                if (blocksNotToBreak.contains(block.getType())) {
                    return;
                }

                if (enchant == 0) {
                    block.breakNaturally();
                    total++;
                }
                if (enchant == 1) {
                    byte data = block.getData();
                    Material drop = block.getType();
                    block.setType(Material.AIR);
                    Objects.requireNonNull(mainBlock.getLocation().getWorld()).dropItemNaturally(mainBlock.getLocation(),
                            new ItemStack(drop, 1, data));
                    total++;
                }

                if(itemInHand.containsEnchantment(Enchantment.DURABILITY)) {
                    itemInHand.setDurability((short)(itemInHand.getDurability() + (Math.random() < 0.5 ? (Math.random() < 0.7 ? 1 : 0) : 0)));
                } else {
                    itemInHand.setDurability((short)(itemInHand.getDurability() + 1));
                }
            }
        }
    }


    @Override
    public String getName() {
        return "Multi Breaker";
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
                itemStack.getType() == Material.NETHERITE_SHOVEL ||
                itemStack.getType() == Material.DIAMOND_SHOVEL ||
                itemStack.getType() == Material.IRON_SHOVEL ||
                itemStack.getType() == Material.GOLDEN_SHOVEL ||
                itemStack.getType() == Material.STONE_SHOVEL ||
                itemStack.getType() == Material.WOODEN_SHOVEL;
    }
}
