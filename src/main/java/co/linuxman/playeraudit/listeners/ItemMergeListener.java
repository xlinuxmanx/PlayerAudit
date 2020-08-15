package co.linuxman.playeraudit.listeners;

import co.linuxman.playeraudit.items.AuditWand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemMergeEvent;

public class ItemMergeListener implements Listener {
    @EventHandler
    public void onItemMerge(ItemMergeEvent merge){
        AuditWand aw = new AuditWand();
        if(merge.getTarget().getItemStack().equals(aw.wand())){
            merge.setCancelled(true);
        }
    }
}
