package l10.dev.fivehundredcigrettes;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Tag;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TobaccoSheep {

    public static final Integer maturityAge = 100;

    public static HashMap<Location ,TobaccoSheep> sheepMap = new HashMap<>();
    public static BukkitTask mainTask;

    public Sheep sheep;
    Integer age = 0;

    public TobaccoSheep(Location loc){
        this(loc, loc.getWorld().spawn(loc, Sheep.class), 0, 1);
    }

    /**
     * Constructor for loading sheep from config
     * @param loc Location of the sheep*
     * @param a Age of the sheep
     * @param h Health of the sheep
     * @param s Sheep entity
     */
    public TobaccoSheep(Location loc, Sheep s, int a, int h){
        if(!FiveHundredCigrettes.NearbyEntities(loc, 1, s).isEmpty()) {
            s.remove();
            sheepMap.remove(s.getLocation());
            return;
        }
        sheep = s;

        sheep.setSilent(true);
        sheep.setCustomNameVisible(true);
        sheep.setAI(false);
        sheep.setCollidable(false);
        sheep.setPersistent(true);
        sheep.setRemoveWhenFarAway(false);
        sheep.setBaby();
        sheep.setColor(DyeColor.BROWN);

        sheep.setHealth(h);
        this.age = a;
    }

    public static boolean IsTobaccoSheep(Entity e){
        if(!(e instanceof Sheep)) return false;
        if(((Sheep) e).hasAI()) return false;
        if(!e.isSilent()) return false;

        return true;
    }

    /// <summary>
    /// Main runnable for the TobaccoSheep
    /// </summary>
    public static BukkitTask SheepRunnable(){
        if(mainTask != null) return mainTask;
        mainTask = new BukkitRunnable() {
            @Override
            public void run() {

                //Bukkit.broadcastMessage("sheepMap size: " + sheepMap.size() + " sheepMap: " + sheepMap.toString());
                for(Map.Entry<Location, TobaccoSheep> entry : sheepMap.entrySet()){
                    Location loc = entry.getKey();
                    TobaccoSheep ts = entry.getValue();

                    if(ts.age < TobaccoSheep.maturityAge)
                        ts.age += 1;

                    //Checking for the sheep being loaded and unloaded
                    if(ts.sheep != null) {
                        ts.sheep.setCustomName(ts.age + "%");

                        if(ts.age == TobaccoSheep.maturityAge && !ts.sheep.isAdult())
                            ts.sheep.setAdult();

                        if(ts.sheep.isSheared()){
                            ts.sheep.setSheared(false);
                            ts.age = 0;
                            ts.sheep.setBaby();
                        }

                        if(ts.sheep.isDead())
                            ts.sheep = null; //Setting sheep to null if unloaded (isdead triggers when unloaded)
                    }
                    else{
                        List<Entity> nb = FiveHundredCigrettes.NearbyEntities(loc, 0.2, null); //Check for nearby entities

                        if(!nb.isEmpty())
                            ts.sheep = (Sheep) nb.get(0); //If there are entities nearby, set the sheep to the first one
                    }
                }
            }
        }.runTaskTimer(FiveHundredCigrettes.getPlugin(), 0, 1); //Repeats every second

        return mainTask;
    }

    /// <summary>
    /// Load the sheep from the config and create the TobaccoSheep objects
    /// </summary>
    public static void LoadSheep(){
        //Check if config exists and read the sheep from it
        if(FiveHundredCigrettes.getPlugin().getConfig().getConfigurationSection("sheep") != null){
            for(String key : FiveHundredCigrettes.getPlugin().getConfig().getConfigurationSection("sheep").getKeys(false)) {
                Location loc = (Location) FiveHundredCigrettes.getPlugin().getConfig().get("sheep."+key+".loc");
                int age = FiveHundredCigrettes.getPlugin().getConfig().getInt("sheep."+key+".age");

                //Check if sheep is present at the saved location, if so then create the TobaccoSheep object
                Sheep s = !FiveHundredCigrettes.NearbyEntities(loc, 0.2, null).isEmpty() ? (Sheep) FiveHundredCigrettes.NearbyEntities(loc, 0.2, null).get(0) : null;

                if(s != null){
                    TobaccoSheep ts = new TobaccoSheep(loc, s, age, 1);
                    TobaccoSheep.sheepMap.put(loc, ts);
                }
            }
        }
    }

    /// <summary>
    /// Save the sheep to the config from the hashmap
    /// </summary>
    public static void SaveSheepConfig(){
        Bukkit.broadcastMessage("Saving sheep");
        FiveHundredCigrettes.getPlugin().getConfig().set("sheep", null); //Remove all previous config
        int i = 0;
        for(Map.Entry<Location, TobaccoSheep> entry : TobaccoSheep.sheepMap.entrySet()){
            Location loc = entry.getKey();
            TobaccoSheep ts = entry.getValue();

            FiveHundredCigrettes.getPlugin().getConfig().set("sheep."+i+".loc", loc);
            FiveHundredCigrettes.getPlugin().getConfig().set("sheep."+i+".age", ts.age);
            FiveHundredCigrettes.getPlugin().saveConfig();
            i++;
        }
        FiveHundredCigrettes.getPlugin().saveConfig();
    }
}
