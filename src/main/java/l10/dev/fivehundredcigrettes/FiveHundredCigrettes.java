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

    public static TobaccoItem tobaccoItem;
    public static CigarItem cigarItem;
    public static StubItem stubItem;
    public static EggItem eggItem;

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new SheepShear(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDeath(), this);
        Bukkit.getPluginManager().registerEvents(new NoOffhandCig(), this);

        getCommand("spawnegg").setExecutor(new SpawnEgg());

        TobaccoSheep.LoadSheep();
        TobaccoSheep.SheepRunnable();

        tobaccoItem = new TobaccoItem("isTobacco", "Tobacco", Material.BROWN_DYE);
        tobaccoItem.CreateRecipe(Material.BROWN_MUSHROOM);

        cigarItem = new CigarItem("isCigar", "Cigarette", Material.CROSSBOW);
        cigarItem.CreateRecipe("PDP;PDP;POP", Map.of(
                'P', new ItemStack(Material.PAPER),
                'D', tobaccoItem.Item.clone(),
                'O', new ItemStack(Material.OAK_LEAVES)
        ));
        Bukkit.getPluginManager().registerEvents(cigarItem, this);

        stubItem = new StubItem("isStub", "Stub", Material.BLACK_DYE);
        stubItem.SetLore("Throw me in the trash pleawse :3");

        eggItem = new EggItem("isEgg", "Egg", Material.SHEEP_SPAWN_EGG);
        eggItem.SetLore("Spawn a sheep who has tobacco instead of wool!");
        eggItem.CreateRecipe("DDD;DDD;DDD", Map.of(
                'D', tobaccoItem.Item.clone()
        ));

        Bukkit.getPluginManager().registerEvents(eggItem, this);
    }

    @Override
    public void onDisable() {
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
}
