package co.linuxman.playeraudit.listeners;

import co.linuxman.playeraudit.configmanager.ConfigManager;
import co.linuxman.playeraudit.items.AuditWand;
import co.linuxman.playeraudit.loggers.DatabaseLogger;
import co.linuxman.playeraudit.loggers.LogConsole;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import java.util.List;

public class WandInteractListener implements Listener {
    @EventHandler
    public void BlockCheck(PlayerInteractEvent interact){
        AuditWand aw = new AuditWand();
        Player player = interact.getPlayer();

        if(interact.getAction() == Action.LEFT_CLICK_BLOCK && aw.checkWand(player) && player.hasPermission("playeraudit.admin")) {
            String playerName = interact.getPlayer().getName();

            //Get block location
            Location blockLocation = interact.getClickedBlock().getLocation();
            int blockX = blockLocation.getBlockX();
            int blockY = blockLocation.getBlockY();
            int blockZ = blockLocation.getBlockZ();

            //Query block
            DatabaseLogger dl = new DatabaseLogger();
            List<String> blockData = dl.queryBlock(blockLocation);

            if(blockData == null){
                interact.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis block has no owner, perhaps naturally generated?"));
            }else{
                //Send block info to Admin
                sendAdminInfo(blockData, player);
            }

            //Logging
            ConfigManager cm = new ConfigManager();
            if(cm.logToConsole()){
                LogConsole console = new LogConsole();
                console.logInfoToConsole(playerName + " checked block at " + blockX + ", " + blockY + ", " + blockZ + "using Audit Wand");
            }
        }
    }

    private void sendAdminInfo(List<String> blockData, Player player){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f--------------------"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Owner: &f" + blockData.get(0)));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Material: &f" + blockData.get(1)));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Time: &f" + blockData.get(2)));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Date: &f" + blockData.get(3)));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Location: &f" + blockData.get(4)));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f--------------------"));
    }
}
