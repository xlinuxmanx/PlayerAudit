package co.linuxman.playeraudit.configmanager;

import co.linuxman.playeraudit.PlayerAudit;
import org.bukkit.plugin.Plugin;
import java.io.Serializable;

public class ConfigManager implements Serializable {
    private Plugin plugin;
    private boolean getLogLavaBucket;
    private boolean getLogWaterBucket;
    private boolean getLogBlockPlace;
    private boolean getLogBlockBreak;
    private boolean getLogChatMsg;
    private boolean getLogPlayerCmd;
    private boolean getLogSignMsg;
    private boolean getLogPlayerTP;
    private boolean getLogToConsole;

    public ConfigManager(){
        plugin = PlayerAudit.plugin();

        getLogLavaBucket = plugin.getConfig().getBoolean("log-lava-bucket");
        getLogWaterBucket = plugin.getConfig().getBoolean("log-water-bucket");
        getLogBlockPlace = plugin.getConfig().getBoolean("log-block-place");
        getLogBlockBreak = plugin.getConfig().getBoolean("log-block-break");
        getLogChatMsg = plugin.getConfig().getBoolean("log-chat-messages");
        getLogPlayerCmd = plugin.getConfig().getBoolean("log-player-commands");
        getLogSignMsg = plugin.getConfig().getBoolean("log-sign-messages");
        getLogPlayerTP = plugin.getConfig().getBoolean("log-player-teleport");
        getLogToConsole = plugin.getConfig().getBoolean("log-to-console");
    }

    public boolean logLavaBucket(){
        if(getLogLavaBucket){
            return true;
        }else{
            return false;
        }
    }

    public boolean logWaterBucket(){
        if(getLogWaterBucket){
            return true;
        }else{
            return false;
        }
    }

    public boolean logBlockPlace(){
        if(getLogBlockPlace){
            return true;
        }else{
            return false;
        }
    }

    public boolean logBlockBreak(){
        if(getLogBlockBreak){
            return true;
        }else{
            return false;
        }
    }

    public boolean logChatMsg(){
        if(getLogChatMsg){
            return true;
        }else{
            return false;
        }
    }

    public boolean logPlayerCmd(){
        if(getLogPlayerCmd){
            return true;
        }else{
            return false;
        }
    }

    public boolean logSignMsg(){
        if(getLogSignMsg){
            return true;
        }else{
            return false;
        }
    }

    public boolean logPlayerTP(){
        if(getLogPlayerTP){
            return true;
        }else{
            return false;
        }
    }

    public boolean logToConsole(){
        if(getLogToConsole){
            return true;
        }else{
            return false;
        }
    }
}
