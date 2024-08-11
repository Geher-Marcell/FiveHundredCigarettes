package l10.dev.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class SpawnEgg implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String str, String[] args) {
        if(!(sender instanceof Player)) return false;

        ItemStack egg = new ItemStack(Material.SHEEP_SPAWN_EGG, 1);
        ItemMeta meta = egg.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN + "Dohány Bárány");
        meta.setLore(Collections.singletonList("Egy bárány, ami dohányt termel."));
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        egg.setItemMeta(meta);
        ((Player) sender).getInventory().addItem(egg);

        return true;
    }
}
