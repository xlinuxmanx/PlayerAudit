package co.linuxman.playeraudit.listeners;

import co.linuxman.playeraudit.PlayerAudit;
import co.linuxman.playeraudit.configmanager.ConfigManager;
import co.linuxman.playeraudit.loggers.ProfileLogger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import java.io.File;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PlayerCommandListener implements Listener {
    @EventHandler
    public void onCommandRun(PlayerCommandPreprocessEvent cmd){
        ConfigManager cm = new ConfigManager();
        if(cm.logPlayerCmd()){
            ProfileLogger pl = new ProfileLogger();

            LocalDate date = LocalDate.now();
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

            String logName = PlayerAudit.plugin().getDataFolder().getPath() + "//players//" + cmd.getPlayer().getName() + "//Command//" + date.format(dateFormat) + ".txt";
            String globalLogName = PlayerAudit.plugin().getDataFolder().getPath() + "//players//" + cmd.getPlayer().getName() + "//global.txt";
            File blockLog = new File(logName);
            File globalLog = new File(globalLogName);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            String logMsg = "[" + timestamp + "] - " + cmd.getPlayer().getName() + " executed command: " + cmd.getMessage();

            pl.recordEvent(logMsg, blockLog, globalLog);
        }
    }
}
