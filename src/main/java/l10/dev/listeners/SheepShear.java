package l10.dev.listeners;

import l10.dev.fivehundredcigrettes.FiveHundredCigrettes;
import l10.dev.fivehundredcigrettes.TobaccoSheep;
import org.bukkit.Sound;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockShearEntityEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class SheepShear implements Listener {
    @EventHandler
    public void onSheepShear(PlayerShearEntityEvent e) {
        if(!TobaccoSheep.IsTobaccoSheep(e.getEntity())) return;
        e.setCancelled(true);

        ((Sheep) e.getEntity()).setSheared(true);

        e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
        e.getPlayer().getWorld().dropItem(((Sheep) e.getEntity()).getLocation(), FiveHundredCigrettes.tobaccoItem.Item.clone());
    }

    @EventHandler
    public void onSheepShear(BlockShearEntityEvent e) {
        if(!TobaccoSheep.IsTobaccoSheep(e.getEntity())) return;
        e.setCancelled(true);

        ((Sheep) e.getEntity()).setSheared(true);

        e.getBlock().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
        e.getBlock().getWorld().dropItem(((Sheep) e.getEntity()).getLocation(), FiveHundredCigrettes.tobaccoItem.Item.clone());
    }
}
