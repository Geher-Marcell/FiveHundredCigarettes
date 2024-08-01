package l10.dev.fivehundredcigrettes;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import net.minecraft.world.item.trading.MerchantRecipe;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class FiveHundredCigrettes extends JavaPlugin implements Listener  {

    /*
    public static final String dohanyKey = "dohany";

    public static final String cigiKey = "cigi";

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        NamespacedKey dkey = new NamespacedKey(this, dohanyKey);
        NamespacedKey ckey = new NamespacedKey(this, cigiKey);

        if(Bukkit.getRecipe(dkey) != null) { Bukkit.removeRecipe(dkey); }
        if(Bukkit.getRecipe(ckey) != null) { Bukkit.removeRecipe(ckey); }

        //DOHANY RECEPT

        ItemStack dohany = CreateItem(Material.BROWN_DYE, 1, "Dohány", "isDohany");

        StonecuttingRecipe dohanyRecipe = new StonecuttingRecipe(dkey, dohany, Material.BROWN_MUSHROOM);

        Bukkit.addRecipe(dohanyRecipe);

        //CIGI RECEPT

        ItemStack cigi = CreateItem(Material.CROSSBOW, 1, "Cigaretta", "isCigi");

        ShapedRecipe cigiRecipe = new ShapedRecipe(ckey, cigi);

        cigiRecipe.shape("PDP", "PDP", "POP");

        cigiRecipe.setIngredient('P', Material.PAPER);
        cigiRecipe.setIngredient('D', dohany);
        cigiRecipe.setIngredient('O', Material.OAK_LEAVES);

        Bukkit.addRecipe(cigiRecipe);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.getPlayer().discoverRecipe(new NamespacedKey(this, dohanyKey));
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent e){
        if(!isCigi(e.getItem())) return;
        if(e.getAction() != Action.RIGHT_CLICK_AIR && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        e.setCancelled(true);

        if(e.getPlayer().getInventory().getItemInMainHand().getType() != GetCigi().getType()) return;

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

            ItemStack csikk = CreateItem(Material.BLACK_DYE, 1, "Csikk", "isCsikk");

            p.getInventory().addItem(csikk);
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

    @EventHandler
    public void OnPlayerOffhandChange(PlayerSwapHandItemsEvent e){
        if(e.getOffHandItem() == null) return;
        if(!isCigi(e.getOffHandItem())) return;

        Player p = e.getPlayer();
        e.setCancelled(true);

        p.updateInventory();
    }

    @EventHandler
    public void CraftEvent(CraftItemEvent e){
        for(ItemStack i : e.getInventory().getMatrix()){
            if(i == null) continue;
            if(i.getItemMeta() == null) continue;
            if(!i.getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) continue;
            if (!NBTEditor.contains(i, NBTEditor.CUSTOM_DATA, "isCsikk" ) ) continue;

            e.setCancelled(true);
            return;
        }
    }

    private boolean isCigi(ItemStack i){
        if(i == null) return false;
        if(i.getItemMeta() == null) return false;
        if(!i.getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) return false;
        if (!NBTEditor.contains(i, NBTEditor.CUSTOM_DATA, "isCigi" ) ) return false;

        return true;
    }

    private ItemStack GetCigi(){
        return Bukkit.getRecipe(new NamespacedKey(this, cigiKey)).getResult();
    }

    private ItemStack CreateItem(Material mat, int amount, String displayName, String customNbt){
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta cigiMeta = item.getItemMeta();

        cigiMeta.setDisplayName(displayName);
        cigiMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        cigiMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(cigiMeta);

        item = NBTEditor.set(item, true, customNbt);

        return item;
    }
    */
}
