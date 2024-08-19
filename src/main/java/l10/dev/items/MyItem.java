package l10.dev.items;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import l10.dev.fivehundredcigrettes.FiveHundredCigrettes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.StonecuttingRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyItem {
    private final List<NamespacedKey> NamespacedKeys = new ArrayList<NamespacedKey>();

    //The custom NBT tag to identify the item
    protected String CustomNBT;

    //The display name of the item
    protected String DisplayName;

    //The material of the item
    protected Material ItemMat;

    public ItemStack Item;

    //Constructor for the custom item
    public MyItem(String CustomNBT, String DisplayName, Material ItemMat) {
        this.CustomNBT = CustomNBT;
        this.DisplayName = DisplayName;
        this.ItemMat = ItemMat;

        Item = CreateItem(ItemMat, 1, DisplayName, CustomNBT);
    }

    //Check if the i ItemStack is the custom item
    public boolean Is(ItemStack i){
        if(i == null) return false;
        if(i.getItemMeta() == null) return false;
        if(!i.getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) return false;
        if (!NBTEditor.contains(i, NBTEditor.CUSTOM_DATA, CustomNBT ) ) return false;

        return true;
    }

    public void CreateRecipe(Material RecipeMat){
        CreateStonecuttingRecipe(RecipeMat);
    }

    public void CreateRecipe(String CraftPattern, Map<Character, ItemStack> CraftDesc) {
        CreateShapedRecipe(CraftPattern, CraftDesc);
    }

    /*
    public void CreateRecipe(@NotNull RecipeType type,
                             Material RecipeMat,
                             String CraftPattern,
                             HashMap<Character, Material> CraftDesc)
    {
        switch (type) {
            case STONECUTTER:
                CreateStonecuttingRecipe(RecipeMat);
                break;
            case SHAPED:
                break;
        }
    }
    */

    public List<NamespacedKey> GetRecipeKeys() {return NamespacedKeys;}

    public static boolean RecipeExists(String nk) { return Bukkit.getRecipe(new NamespacedKey(FiveHundredCigrettes.getPlugin(), nk)) != null; }

    public void SetLore(String lore) {
        ItemMeta meta = Item.getItemMeta();
        List<String> Lore = new ArrayList<String>();
        Lore.add(lore);
        meta.setLore(Lore);
        Item.setItemMeta(meta);
    }

    private void CreateStonecuttingRecipe(Material RecipeMat){
        String KeyName = CustomNBT+NamespacedKeys.size();
        NamespacedKey NamespacedKey = new NamespacedKey(FiveHundredCigrettes.getPlugin(), KeyName);

        if (RecipeExists(KeyName))
            Bukkit.removeRecipe(NamespacedKey);

        StonecuttingRecipe recipe = new StonecuttingRecipe(NamespacedKey, Item, RecipeMat);

        Bukkit.addRecipe(recipe);
    }

    private void CreateShapedRecipe(String craftPattern, Map<Character, ItemStack> craftDesc) {
        String[] pattern = craftPattern.split(";");

        String KeyName = CustomNBT+NamespacedKeys.size();
        NamespacedKey NamespacedKey = new NamespacedKey(FiveHundredCigrettes.getPlugin(), KeyName);

        if (RecipeExists(KeyName))
            Bukkit.removeRecipe(NamespacedKey);

        ShapedRecipe recipe = new ShapedRecipe(NamespacedKey, Item);

        recipe.shape(pattern[0], pattern[1], pattern[2]);

        for (Character c : craftDesc.keySet()){
            recipe.setIngredient(c, craftDesc.get(c));
        }

        Bukkit.addRecipe(recipe);
    }

    private static ItemStack CreateItem(Material mat, int amount, String displayName, String customNbt){
        ItemStack item = new ItemStack(mat, amount);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(displayName);
        meta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        item = NBTEditor.set(item, true, customNbt);

        return item;
    }
}
