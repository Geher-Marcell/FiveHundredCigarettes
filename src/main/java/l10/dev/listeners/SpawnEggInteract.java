package l10.dev.listeners;

import l10.dev.fivehundredcigrettes.FiveHundredCigrettes;
import l10.dev.fivehundredcigrettes.TobaccoSheep;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpawnEggInteract implements Listener {
    @EventHandler
    public void onSpawnEggInteract(PlayerInteractEvent e) {
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(e.getItem() == null) return;
        if(e.getItem().getType() != Material.SHEEP_SPAWN_EGG) return;
        if(e.getItem().getItemMeta().getLore() == null) return;
        e.setCancelled(true);

        Player p = e.getPlayer();

        Location spawnLocation = e.getClickedBlock().getLocation().toCenterLocation().add(0, 0.5, 0);

        //rotate sheep to face player
        spawnLocation.setYaw(p.getLocation().getYaw() + 180);

        TobaccoSheep ts = new TobaccoSheep(spawnLocation);

        if(ts.sheep == null) return;

        FiveHundredCigrettes.getPlugin().sheepMap.put(spawnLocation, ts);

        if(p.getGameMode() != org.bukkit.GameMode.CREATIVE){
            p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
        }
    }
}
