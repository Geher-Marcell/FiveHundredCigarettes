package l10.dev.fivehundredcigrettes;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Sheep;

public class TobaccoSheep {

    public static final Integer maturityAge = 100;

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
        //if(!FiveHundredCigrettes.NearbyEntities(loc, 1, s).isEmpty()) { return; }
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

    public static boolean isTobaccoSheep(Entity e){
        if(!(e instanceof Sheep)) return false;
        if(((Sheep) e).hasAI()) return false;
        if(!e.isSilent()) return false;

        return true;
    }
}
