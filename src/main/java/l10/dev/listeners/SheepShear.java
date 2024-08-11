package l10.dev.listeners;

import l10.dev.fivehundredcigrettes.TobaccoSheep;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class SheepShear implements Listener {
    @EventHandler
    public void onSheepShear(PlayerShearEntityEvent e) {
        if(!TobaccoSheep.isTobaccoSheep(e.getEntity())) return;

        e.setCancelled(true);

        ((Sheep) e.getEntity()).setSheared(true);

        e.getItem().setDurability((short) (e.getItem().getDurability() + 1));
    }
}
