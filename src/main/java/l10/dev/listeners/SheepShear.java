package l10.dev.listeners;

import l10.dev.fivehundredcigrettes.TobaccoItem;
import l10.dev.fivehundredcigrettes.TobaccoSheep;
import org.bukkit.Sound;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class SheepShear implements Listener {
    @EventHandler
    public void onSheepShear(PlayerShearEntityEvent e) {
        if(!TobaccoSheep.IsTobaccoSheep(e.getEntity())) return;
        e.setCancelled(true);

        ((Sheep) e.getEntity()).setSheared(true);

        e.getItem().setDurability((short) (e.getItem().getDurability() + 1));
        e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.ENTITY_SHEEP_SHEAR, 1, 1);
        e.getPlayer().getWorld().dropItemNaturally(((Sheep) e.getEntity()).getEyeLocation(), TobaccoItem.Tobacco.clone());
    }
}
