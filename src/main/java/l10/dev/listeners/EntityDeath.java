package l10.dev.listeners;

import l10.dev.fivehundredcigrettes.TobaccoSheep;
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

        TobaccoSheep.sheepMap.remove(ent.getLocation());
    }
}
