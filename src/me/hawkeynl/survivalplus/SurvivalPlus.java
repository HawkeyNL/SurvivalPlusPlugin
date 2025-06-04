package me.hawkeynl.survivalplus;

import me.hawkeynl.survivalplus.Commands.*;
import me.hawkeynl.survivalplus.Enchantments.*;
import me.hawkeynl.survivalplus.Events.EventsClass;
import me.hawkeynl.survivalplus.Events.NameTagsEvents;
import me.hawkeynl.survivalplus.teleport.*;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class SurvivalPlus extends JavaPlugin {
    public static final String PREFIX = ChatColor.AQUA + "[SurvivalPlus] " + ChatColor.RESET;
    public static SurvivalPlus PLUGIN;

    private TeleportRequestManager teleportRequestManager;

    public static ArrayList<Enchantment> custom_enchants = new ArrayList<>();
    public static HasteEnchantment hasteEnchantment = new HasteEnchantment(101);
    public static ExplosiveTouchEnchantment explosiveTouchEnchantment = new ExplosiveTouchEnchantment(102);
    public static SpeedEnchantment speedEnchantment = new SpeedEnchantment(103);
    public static FireResistanceEnchantment fireResistanceEnchantment = new FireResistanceEnchantment(104);
    public static DamageResistanceEnchantment damageResistanceEnchantment = new DamageResistanceEnchantment(105);
    public static GrindBoosterEnchantment grindBoosterEnchantment = new GrindBoosterEnchantment(106);
    public static RegenerationEnchantment regenerationEnchantment = new RegenerationEnchantment(107);
    public static NightVisionEnchantment nightVisionEnchantment = new NightVisionEnchantment(108);
    public static TeleportationEnchantment teleportationEnchantment = new TeleportationEnchantment(109);
    public static MultiBreakerEnchantment multiBreakerEnchantment = new MultiBreakerEnchantment(110);

    @Override
    public void onEnable() {
        PLUGIN = this;

        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + getDescription().getName() + " has started on " + ChatColor.BLUE + "v" + getDescription().getVersion() + ChatColor.RESET);

        custom_enchants.add(hasteEnchantment);
        custom_enchants.add(explosiveTouchEnchantment);
        custom_enchants.add(speedEnchantment);
        custom_enchants.add(fireResistanceEnchantment);
        custom_enchants.add(damageResistanceEnchantment);
        custom_enchants.add(grindBoosterEnchantment);
        custom_enchants.add(regenerationEnchantment);
        custom_enchants.add(nightVisionEnchantment);
        custom_enchants.add(teleportationEnchantment);
        custom_enchants.add(multiBreakerEnchantment);
        loadEnchantments(hasteEnchantment);
        loadEnchantments(explosiveTouchEnchantment);
        loadEnchantments(speedEnchantment);
        loadEnchantments(fireResistanceEnchantment);
        loadEnchantments(damageResistanceEnchantment);
        loadEnchantments(grindBoosterEnchantment);
        loadEnchantments(regenerationEnchantment);
        loadEnchantments(nightVisionEnchantment);
        loadEnchantments(teleportationEnchantment);
        loadEnchantments(multiBreakerEnchantment);

        teleportRequestManager = new TeleportRequestManager(this);
        getCommand("enchants").setExecutor(new EnchantsCommand());
        getCommand("repair").setExecutor(new RepairCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("craft").setExecutor(new CraftCommand());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand());
        getCommand("delhome").setExecutor(new DelHomeCommand());
        getCommand("tpa").setExecutor(new TpaCommand(teleportRequestManager));
        getCommand("tpaccept").setExecutor(new TpAcceptCommand(teleportRequestManager));
        getCommand("tpdeny").setExecutor(new TpDenyCommand(teleportRequestManager));

        getServer().getPluginManager().registerEvents(new EventsClass(), this);
        getServer().getPluginManager().registerEvents(new NameTagsEvents(), this);
        getServer().getPluginManager().registerEvents(hasteEnchantment, this);
        getServer().getPluginManager().registerEvents(explosiveTouchEnchantment, this);
        getServer().getPluginManager().registerEvents(speedEnchantment, this);
        getServer().getPluginManager().registerEvents(fireResistanceEnchantment, this);
        getServer().getPluginManager().registerEvents(damageResistanceEnchantment, this);
        getServer().getPluginManager().registerEvents(grindBoosterEnchantment, this);
        getServer().getPluginManager().registerEvents(regenerationEnchantment, this);
        getServer().getPluginManager().registerEvents(nightVisionEnchantment, this);
        getServer().getPluginManager().registerEvents(teleportationEnchantment, this);
        getServer().getPluginManager().registerEvents(multiBreakerEnchantment, this);

        loadConfig();
    }

    @Override
    public void onDisable() {
        try {
            Field keyField = Enchantment.class.getDeclaredField("byKey");

            keyField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

            for (Enchantment enchantment : custom_enchants){
                byKey.remove(enchantment.getKey());
            }

            Field nameField = Enchantment.class.getDeclaredField("byName");

            nameField.setAccessible(true);
            @SuppressWarnings("unchecked")
            HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

            for (Enchantment enchantment : custom_enchants){
                byName.remove(enchantment.getName());
            }
        } catch (Exception ignored) { }

        getServer().getConsoleSender().sendMessage(ChatColor.RED + getDescription().getName() + " has stopped on " + ChatColor.BLUE + "v" + getDescription().getVersion() + ChatColor.RESET);
    }


    public static SurvivalPlus getPlugin() { return PLUGIN; }

    public void loadEnchantments(Enchantment enchantment) {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
