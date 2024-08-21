package l10.dev.fivehundredcigrettes;

import l10.dev.commands.SpawnEgg;
import l10.dev.items.CigarItem;
import l10.dev.items.EggItem;
import l10.dev.items.StubItem;
import l10.dev.items.TobaccoItem;
import l10.dev.listeners.EntityDeath;
import l10.dev.listeners.NoOffhandCig;
import l10.dev.listeners.SheepShear;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class FiveHundredCigrettes extends JavaPlugin {

    private static FiveHundredCigrettes plugin;

    public static final String SETPATH = "settings";

    public static boolean SheepEnabled = true;

    public static TobaccoItem tobaccoItem;
    public static CigarItem cigarItem;
    public static StubItem stubItem;
    public static EggItem eggItem;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        try{
            CreateSettings(); //May throw an error if config doesn't exist yet
        }catch (Exception e){
            CreateSettings();
        }

        Bukkit.getPluginManager().registerEvents(new NoOffhandCig(), this);

        SheepEnabled = getConfig().getBoolean(SETPATH + ".enableSheep");

        tobaccoItem = new TobaccoItem(
                "isTobacco",
                getConfig().getString(SETPATH + ".tobacco.Name"),
                Material.matchMaterial(getConfig().getString(SETPATH + ".tobacco.Item"))
        );
        tobaccoItem.CreateRecipe(Material.getMaterial(getConfig().getString(SETPATH + ".tobacco.craftItem")));

        cigarItem = new CigarItem(
                "isCigar",
                getConfig().getString(SETPATH + ".cigarette.Name"),
                Material.CROSSBOW
        );
        cigarItem.SetLore(getConfig().getString(SETPATH + ".cigarette.Lore"));

        cigarItem.CreateRecipe("PDP;PDP;POP", Map.of(
                'P', new ItemStack(Material.PAPER),
                'D', tobaccoItem.Item.clone(),
                'O', new ItemStack(Material.OAK_LEAVES)
        ));
        Bukkit.getPluginManager().registerEvents(cigarItem, this);

        stubItem = new StubItem(
                "isStub",
                getConfig().getString(SETPATH + ".stub.Name"),
                Material.getMaterial(getConfig().getString(SETPATH + ".stub.Item")));
        stubItem.SetLore(getConfig().getString(SETPATH + ".stub.Lore"));

        if(SheepEnabled) {
            Bukkit.getPluginManager().registerEvents(new SheepShear(), this);
            Bukkit.getPluginManager().registerEvents(new EntityDeath(), this);

            getCommand("spawnegg").setExecutor(new SpawnEgg());

            TobaccoSheep.LoadSheep();
            TobaccoSheep.SheepRunnable();

            eggItem = new EggItem(
                    "isEgg",
                    getConfig().getString(SETPATH + ".egg.Name"),
                    Material.SHEEP_SPAWN_EGG
            );

            eggItem.SetLore(getConfig().getString(SETPATH + ".egg.Lore"));

            eggItem.CreateRecipe("DDD;DDD;DDD", Map.of(
                    'D', tobaccoItem.Item.clone()
            ));

            Bukkit.getPluginManager().registerEvents(eggItem, this);
        }
    }

    @Override
    public void onDisable() {
        if(SheepEnabled)
            TobaccoSheep.SaveSheepConfig();
    }

    public static FiveHundredCigrettes getPlugin() { return plugin; }

    public static List<Entity> NearbyEntities(Location location, double radius, Entity ignore) {
        List<Entity> entities = new ArrayList<Entity>();

        //Bukkit.broadcastMessage(ChatColor.GRAY + "Checking for entities in radius " + radius + " around " + location.toString() + " ignoring " + (ignore == null ? "none" : ignore.toString()));

        for (Entity entity : location.getWorld().getEntities()) {
            if (entity instanceof Player) continue; //Don't want players
            if (entity.getWorld() != location.getWorld()) continue;
            if (entity.getLocation().distanceSquared(location) > radius * radius) continue;
            if (ignore != null && entity == ignore) continue;

            entities.add(entity);
        }
        return entities;
    }

    private void CreateSettings() {
        if(getConfig().contains(SETPATH)) return;

        Bukkit.broadcastMessage("Setting config!");

        getConfig().set(SETPATH + ".enableSheep", true);

        getConfig().set(SETPATH + ".tobacco.Name", "Tobacco"); //
        getConfig().set(SETPATH + ".tobacco.Item", Material.BROWN_DYE.name()); //
        getConfig().set(SETPATH + ".tobacco.craftItem", Material.BROWN_MUSHROOM.name()); //

        getConfig().set(SETPATH + ".cigarette.Name", "Cigarette"); //
        getConfig().set(SETPATH + ".cigarette.Lore", "");//
        getConfig().set(SETPATH + ".cigarette.hurtChance", 0.05); //
        getConfig().set(SETPATH + ".cigarette.hurtDamage", 0.5); //

        getConfig().set(SETPATH + ".stub.Name", "Stub"); //
        getConfig().set(SETPATH + ".stub.CanCraftWith", "true");
        getConfig().set(SETPATH + ".stub.Item", Material.BLACK_DYE.name()); //
        getConfig().set(SETPATH + ".stub.Lore", "Throw me in the trash pleawse :3"); //

        getConfig().set(SETPATH + ".egg.Name", "Egg"); //
        getConfig().set(SETPATH + ".egg.Lore", "Spawn a sheep who has tobacco instead of wool!"); //
        getConfig().set(SETPATH + ".egg.SheepDrops", 0); //
        getConfig().set(SETPATH + ".egg.PlaceDistance", 1f); //

        saveConfig();
    }
}
