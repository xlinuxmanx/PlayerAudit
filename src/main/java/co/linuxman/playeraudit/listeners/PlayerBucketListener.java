package co.linuxman.playeraudit.listeners;

import co.linuxman.playeraudit.PlayerAudit;
import co.linuxman.playeraudit.configmanager.ConfigManager;
import co.linuxman.playeraudit.loggers.ProfileLogger;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PlayerBucketListener implements Listener {
    @EventHandler
    public void onBucketUse(PlayerBucketEmptyEvent bucket){
        ConfigManager cm = new ConfigManager();
        ProfileLogger pl = new ProfileLogger();

        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        String lavaLog = PlayerAudit.plugin().getDataFolder().getPath() + "//Lava.txt";
        String waterLog = PlayerAudit.plugin().getDataFolder().getPath() + "//Water.txt";
        String globalLogName = PlayerAudit.plugin().getDataFolder().getPath() + "//players//" + bucket.getPlayer().getName() + "//global.txt";
        File blockLog = null;
        File globalLog = new File(globalLogName);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String world = bucket.getBlockClicked().getWorld().getName();

        Location location = bucket.getBlockClicked().getLocation();
        int blockX = location.getBlockX();
        int blockY = location.getBlockY();
        int blockZ = location.getBlockZ();

        String logMsg = "";
        if(bucket.getBucket() == Material.LAVA_BUCKET && cm.logLavaBucket()){
            blockLog = new File(lavaLog);
            logMsg = "[" + timestamp + "] - " + bucket.getPlayer().getName() + " placed LAVA" + " @ " + world + ": " + blockX + ", " + blockY + ", " + blockZ;
            pl.recordEvent(logMsg, blockLog, globalLog);
        }else if(bucket.getBucket() == Material.WATER_BUCKET && cm.logWaterBucket()){
            blockLog = new File(waterLog);
            logMsg = "[" + timestamp + "] - " + bucket.getPlayer().getName() + " placed WATER" + " @ " + world + ": " + blockX + ", " + blockY + ", " + blockZ;
            pl.recordEvent(logMsg, blockLog, globalLog);
        }
    }
}
