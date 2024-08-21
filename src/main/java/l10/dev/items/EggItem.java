package l10.dev.items;

import l10.dev.fivehundredcigrettes.FiveHundredCigrettes;
import l10.dev.fivehundredcigrettes.TobaccoSheep;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class EggItem extends MyItem implements Listener {
    public EggItem(String CustomNBT, String DisplayName, Material ItemMat) {
        super(CustomNBT, DisplayName, ItemMat);
    }

    @EventHandler
    public void onSpawnEggInteract(PlayerInteractEvent e) {
        if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        if(!Is(e.getItem())) return;

        e.setCancelled(true);

        Player p = e.getPlayer();

        Location spawnLocation = e.getClickedBlock().getLocation().toCenterLocation().add(0, 0.5, 0);

        //rotate sheep to face player
        spawnLocation.setYaw(p.getLocation().getYaw() + 180);

        TobaccoSheep ts = new TobaccoSheep(spawnLocation);

        if(ts.sheep == null) return;

        TobaccoSheep.sheepMap.put(spawnLocation, ts);

        if(p.getGameMode() != org.bukkit.GameMode.CREATIVE){
            p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount() - 1);
        }
    }
}
