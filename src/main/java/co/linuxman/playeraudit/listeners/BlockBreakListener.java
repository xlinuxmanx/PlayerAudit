package co.linuxman.playeraudit.listeners;

import co.linuxman.playeraudit.PlayerAudit;
import co.linuxman.playeraudit.configmanager.ConfigManager;
import co.linuxman.playeraudit.items.AuditWand;
import co.linuxman.playeraudit.loggers.ProfileLogger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent blockBreak){
        //Cancel block break if player is holding a wand
        AuditWand wand = new AuditWand();
        if(wand.checkWand(blockBreak.getPlayer())){
            blockBreak.setCancelled(true);
        }

        ConfigManager cm  = new ConfigManager();
        if(cm.logBlockBreak()){
            ProfileLogger pl = new ProfileLogger();

            LocalDate date = LocalDate.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            String logName = PlayerAudit.plugin().getDataFolder().getPath() + "//players//" + blockBreak.getPlayer().getName() + "//BlockBreak//" + date.format(dateFormat) + ".txt";
            String globalLogName = PlayerAudit.plugin().getDataFolder().getPath() + "//players//" + blockBreak.getPlayer().getName() + "//global.txt";
            File blockLog = new File(logName);
            File globalLog = new File(globalLogName);

            Material blockMat = blockBreak.getBlock().getType();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            Location location = blockBreak.getBlock().getLocation();
            int blockX = location.getBlockX();
            int blockY = location.getBlockY();
            int blockZ = location.getBlockZ();

            String logMsg = "[" + timestamp + "] - " + blockBreak.getPlayer().getName() + " broke " + blockMat + " @ " + blockX + ", " + blockY + ", " + blockZ;

            pl.recordEvent(logMsg, blockLog, globalLog);
        }
    }
}
