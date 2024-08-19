package l10.dev.listeners;

import l10.dev.fivehundredcigrettes.FiveHundredCigrettes;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        for (NamespacedKey k : FiveHundredCigrettes.cigarItem.GetRecipeKeys()) {
            e.getPlayer().discoverRecipe(k);
        }

        for (NamespacedKey k : FiveHundredCigrettes.eggItem.GetRecipeKeys()) {
            e.getPlayer().discoverRecipe(k);
        }
    }
}
