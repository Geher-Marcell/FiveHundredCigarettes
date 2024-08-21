package l10.dev.listeners;

import l10.dev.fivehundredcigrettes.FiveHundredCigrettes;
import l10.dev.fivehundredcigrettes.TobaccoSheep;
import l10.dev.items.EggItem;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeath implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        Entity ent = e.getEntity();
        if(!TobaccoSheep.IsTobaccoSheep(ent)) return;

        e.getDrops().clear();

        // 0 - Drops nothing
        // 1 - Drops back a spawnegg
        // 2 - Drops a tobacco item
        int dropSet = FiveHundredCigrettes.getPlugin().getConfig().getInt(FiveHundredCigrettes.SETPATH + ".egg.SheepDrops");
        switch (dropSet) {
            case 1:
                e.getDrops().add(FiveHundredCigrettes.eggItem.Item.clone());
                break;
            case 2:
                e.getDrops().add(FiveHundredCigrettes.tobaccoItem.Item.clone());
                break;
        }

        TobaccoSheep.sheepMap.remove(ent.getLocation());
    }
}
