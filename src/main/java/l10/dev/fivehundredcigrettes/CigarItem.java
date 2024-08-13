package l10.dev.fivehundredcigrettes;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public class CigarItem {

    public static final String TobaccoKey = "tobacco";

    private static final String CustomNBT = "isTobacco"; //The custom NBT tag to identify the item
    private static final String DisplayName = "Tobacco"; //The display name of the item
    private static final Material ItemMat = Material.BROWN_DYE; //The material of the item
    private static final Material RecipeMat = Material.BROWN_MUSHROOM; //The material used in to craft the item

    public static ItemStack Tobacco;

    public CigarItem() {
        if(Tobacco != null) return;

        NamespacedKey dkey = new NamespacedKey(FiveHundredCigrettes.getPlugin(), TobaccoKey);

        if (Bukkit.getRecipe(dkey) != null)
            Bukkit.removeRecipe(dkey);

        Tobacco = CreateItem(ItemMat, 1, DisplayName, CustomNBT);

        StonecuttingRecipe tobaccoRecipe = new StonecuttingRecipe(dkey, Tobacco, RecipeMat);

        Bukkit.addRecipe(tobaccoRecipe);
    }

    private ItemStack CreateItem(Material mat, int amount, String displayName, String customNbt){
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        item = NBTEditor.set(item, true, customNbt);

        return item;
    }

    public static boolean IsTobacco(ItemStack i){
        if(i == null) return false;
        if(i.getItemMeta() == null) return false;
        if(!i.getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) return false;
        if (!NBTEditor.contains(i, NBTEditor.CUSTOM_DATA, CustomNBT ) ) return false;

        return true;
    }
}
