package co.linuxman.playeraudit.listeners;

import co.linuxman.playeraudit.PlayerAudit;
import co.linuxman.playeraudit.configmanager.ConfigManager;
import co.linuxman.playeraudit.loggers.DatabaseLogger;
import co.linuxman.playeraudit.loggers.ProfileLogger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent blockPlace){
        ConfigManager cm = new ConfigManager();
        if(cm.logBlockPlace()){
            ProfileLogger pl = new ProfileLogger();

            LocalDate date = LocalDate.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            String logName = PlayerAudit.plugin().getDataFolder().getPath() + "//players//" + blockPlace.getPlayer().getName() + "//BlockPlace//" + date.format(dateFormat) + ".txt";
            String globalLogName = PlayerAudit.plugin().getDataFolder().getPath() + "//players//" + blockPlace.getPlayer().getName() + "//global.txt";
            File blockLog = new File(logName);
            File globalLog = new File(globalLogName);

            Material blockMat = blockPlace.getBlockPlaced().getType();

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            String world = blockPlace.getBlockPlaced().getWorld().getName();

            Location location = blockPlace.getBlockPlaced().getLocation();
            int blockX = location.getBlockX();
            int blockY = location.getBlockY();
            int blockZ = location.getBlockZ();

            String logMsg = "[" + timestamp + "] - " + blockPlace.getPlayer().getName() + " placed " + blockMat + " @ " + world + ": " + blockX + ", " + blockY + ", " + blockZ;

            //Record event to player profile
            pl.recordEvent(logMsg, blockLog, globalLog);

            //Update Database
            DatabaseLogger dbl = new DatabaseLogger();
            if(dbl.checkBlockExistance(location)){
                //If block location exists in database, update new location info
                dbl.updateBlockData(location, blockPlace.getPlayer(), blockMat);
            }else{
                //Else, add new block data to database
                dbl.addBlock(location, blockPlace.getPlayer(), blockMat);
            }
        }
    }
}
