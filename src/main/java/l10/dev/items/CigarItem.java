package l10.dev.items;

import l10.dev.fivehundredcigrettes.FiveHundredCigrettes;
import l10.dev.items.MyItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CigarItem extends MyItem implements Listener {
    public CigarItem(String CustomNBT, String DisplayName, Material ItemMat) { super(CustomNBT, DisplayName, ItemMat); }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent e){
        if(!Is(e.getItem())) return;
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        e.setCancelled(true);

        if(e.getPlayer().getInventory().getItemInMainHand().getType() != Item.getType()) return;

        Player p = e.getPlayer();
        ItemStack i = e.getItem();

        Location mouthLocation = p.getEyeLocation().add(p.getEyeLocation().getDirection().multiply(0.5));

        for(int j = 0; j < 5; j++){
            float x = (float) Math.random() * 0.1f - 0.05f; // -0.05f és 0.05f közötti random szám
            float z = (float) Math.random() * 0.1f - 0.05f; // -0.05f és 0.05f közötti random szám

            float y = (float) Math.random() * 0.1f + 0.05f;

            p.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, mouthLocation, 0, x, y, z);
        }

        //random number between 0.4 and 1.2
        float pitch = (float) (Math.random() * 0.8 + 0.4);

        p.getWorld().playSound(p.getLocation(), Sound.ENTITY_PLAYER_BREATH, 1, pitch);

        i.setDurability((short)(i.getDurability() + 50));

        if(i.getDurability() >= i.getType().getMaxDurability()){
            p.getInventory().remove(i);
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);

            p.getInventory().addItem(FiveHundredCigrettes.stubItem.Item.clone());

            p.updateInventory();

            return;
        }

        p.updateInventory();

        //Damage player

        if(Math.random() < 0.05){
            p.damage(0.5);
            p.getWorld().playSound(p.getLocation(), Sound.ENTITY_VILLAGER_HURT, 1, .81f);
        }
    }
}
