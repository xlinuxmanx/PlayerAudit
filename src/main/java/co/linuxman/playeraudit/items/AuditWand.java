package co.linuxman.playeraudit.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuditWand implements Serializable {
    private ItemStack wand;
    private List<String> wandLore;
    private ItemMeta wandMeta;

    public AuditWand(){
        wand = new ItemStack(Material.STICK);

        wandLore = new ArrayList<String>();
        wandLore.add(ChatColor.translateAlternateColorCodes('&',"&bClick to inspect block"));

        wandMeta = wand.getItemMeta();
        wandMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&6&lAudit Wand"));
        wandMeta.setLore(wandLore);
        wandMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_PLACED_ON,
                ItemFlag.HIDE_POTION_EFFECTS,
                ItemFlag.HIDE_UNBREAKABLE);

        wand.setItemMeta(wandMeta);
    }

    public void giveWand(Player player){
        Inventory adminInv = player.getInventory();
        ItemStack[] invItems = adminInv.getContents();

        boolean hasWand = false;
        for(ItemStack item : invItems){
            try{
                if(item.equals(wand)){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou already have an &6&lAudit Wand"));
                    hasWand = true;
                    break;
                }
            }catch (NullPointerException npe){}
        }

        if(!hasWand){
            adminInv.addItem(wand);
        }
    }

    public boolean checkWand(Player player){
        ItemStack item = player.getInventory().getItemInMainHand();
        if(item.equals(wand)){
            return true;
        }else{
            return false;
        }
    }

    public ItemStack wand(){
        return wand;
    }
}
