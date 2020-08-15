package co.linuxman.playeraudit.listeners;

import co.linuxman.playeraudit.PlayerAudit;
import co.linuxman.playeraudit.configmanager.ConfigManager;
import co.linuxman.playeraudit.loggers.ProfileLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PlayerTeleportListener implements Listener {
    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent teleport){
        ConfigManager cm = new ConfigManager();
        if(cm.logPlayerTP()){
            ProfileLogger pl = new ProfileLogger();

            LocalDate date = LocalDate.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            String logName = PlayerAudit.plugin().getDataFolder().getPath() + "//players//" + teleport.getPlayer().getName() + "//Teleport//" + date.format(dateFormat) + ".txt";
            String globalLogName = PlayerAudit.plugin().getDataFolder().getPath() + "//players//" + teleport.getPlayer().getName() + "//global.txt";
            File blockLog = new File(logName);
            File globalLog = new File(globalLogName);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            String prevWorld = teleport.getFrom().getWorld().getName();
            double prevX = teleport.getFrom().getX();
            double prevY = teleport.getFrom().getY();
            double prevZ = teleport.getFrom().getZ();

            String newWorld = teleport.getTo().getWorld().getName();
            double newX = teleport.getTo().getX();
            double newY = teleport.getTo().getY();
            double newZ = teleport.getTo().getZ();

            String logMsg = "[" + timestamp + "] - "
                    + teleport.getPlayer().getName() + " teleported from "
                    + prevWorld + ": " + prevX + ", " + prevY + ", " + prevZ
                    + " to "
                    + newWorld + ": " + newX + ", " + newY + ", " + newZ;

            pl.recordEvent(logMsg, blockLog, globalLog);
        }
    }
}
