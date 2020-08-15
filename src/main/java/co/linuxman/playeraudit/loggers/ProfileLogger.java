package co.linuxman.playeraudit.loggers;

import co.linuxman.playeraudit.configmanager.ConfigManager;
import java.io.*;

public class ProfileLogger implements Serializable {
    public ProfileLogger(){}

    public void recordEvent(String logMsg, File log, File globalLog){
        LogConsole console = new LogConsole();
        //Log event
        try {
            FileWriter logFW = new FileWriter(log, true);
            BufferedWriter logBW = new BufferedWriter(logFW);

            FileWriter globalLogFW = new FileWriter(globalLog, true);
            BufferedWriter globalLogBW = new BufferedWriter(globalLogFW);

            logBW.write(logMsg);
            logBW.write("\n");
            logBW.close();

            globalLogBW.write(logMsg);
            globalLogBW.write("\n");
            globalLogBW.close();

            //Log event to console if enabled
            ConfigManager cm = new ConfigManager();
            if(cm.logToConsole()){
                console.logInfoToConsole(logMsg);
            }
        } catch (IOException e) {
            console.logSevereToConsole(("Could not write to " + log.getName() + " : " + e.getMessage()));
        }
    }
}
