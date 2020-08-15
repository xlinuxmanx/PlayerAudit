package co.linuxman.playeraudit.loggers;

import co.linuxman.playeraudit.PlayerAudit;
import java.io.Serializable;
import java.util.logging.Logger;

public class LogConsole implements Serializable {
    private Logger console;

    public LogConsole(){
        console = PlayerAudit.plugin().getLogger();
    }

    public void logInfoToConsole(String logMsg){
        console.info(logMsg);
    }

    public void logSevereToConsole(String logMsg){
        console.severe(logMsg);
    }
}
