package l10.dev.listeners;

import l10.dev.fivehundredcigrettes.FiveHundredCigrettes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class NoOffhandCig implements Listener {
    @EventHandler
    public void OnPlayerOffhandChange(PlayerSwapHandItemsEvent e){
        if(e.getOffHandItem() == null) return;
        if(!FiveHundredCigrettes.cigarItem.Is(e.getOffHandItem())) return;

        Player p = e.getPlayer();
        e.setCancelled(true);

        p.updateInventory();
    }
}
