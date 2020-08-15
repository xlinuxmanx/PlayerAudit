package co.linuxman.playeraudit.listeners;

import co.linuxman.playeraudit.PlayerAudit;
import co.linuxman.playeraudit.configmanager.ConfigManager;
import co.linuxman.playeraudit.loggers.ProfileLogger;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PlayerSignListener implements Listener {
    @EventHandler
    public void onPlayerInteract(SignChangeEvent sign){
        ConfigManager cm = new ConfigManager();
        if(cm.logSignMsg()){
            ProfileLogger pl = new ProfileLogger();

            LocalDate date = LocalDate.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            String logName = PlayerAudit.plugin().getDataFolder().getPath() + "//players//" + sign.getPlayer().getName() + "//BlockPlace//" + date.format(dateFormat) + ".txt";
            String globalLogName = PlayerAudit.plugin().getDataFolder().getPath() + "//players//" + sign.getPlayer().getName() + "//global.txt";
            File blockLog = new File(logName);
            File globalLog = new File(globalLogName);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            Location location = sign.getBlock().getLocation();
            int blockX = location.getBlockX();
            int blockY = location.getBlockY();
            int blockZ = location.getBlockZ();

            String world = sign.getBlock().getWorld().getName();

            String lines[] = sign.getLines();

            String logMsg = "[" + timestamp + "] - "
                    + sign.getPlayer().getName() + " placed sign @ "
                    + world + ": " + blockX + ", " + blockY + ", " + blockZ + " : "
                    + "(0)" + lines[0] + " (1)" + lines[1] + " (2)" + lines[2] + " (3)" + lines[3];

            pl.recordEvent(logMsg, blockLog, globalLog);
        }
    }
}
