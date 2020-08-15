package co.linuxman.playeraudit.listeners;

import co.linuxman.playeraudit.items.AuditWand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemPickupListener implements Listener {
    @EventHandler
    public void onItemPickup(EntityPickupItemEvent item){
        //Protects Audit Wand from being picked up by non-admins
        AuditWand aw = new AuditWand();
        Player player = (Player)item.getEntity();

        if(item.getItem().getItemStack().equals(aw.wand())){
            if(!player.hasPermission("playeraudit.admin")){
                item.setCancelled(true);
            }
        }

        Inventory adminInv = player.getInventory();
        ItemStack[] invItems = adminInv.getContents();

        boolean hasWand = false;
        for(ItemStack i : invItems){
            try{
                if(i.equals(aw.wand())){
                    hasWand = true;
                    break;
                }
            }catch (NullPointerException npe){}
        }

        if(hasWand){
            item.setCancelled(true);
        }
    }
}
