package co.linuxman.playeraudit.listeners;

import co.linuxman.playeraudit.PlayerAudit;
import co.linuxman.playeraudit.loggers.LogConsole;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PlayerJoinListener implements Listener {
    private LocalDate date;
    private DateTimeFormatter dateFormat;
    private String dataFolder;
    private LogConsole console;

    public PlayerJoinListener(){
        date = LocalDate.now();
        dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        dataFolder = PlayerAudit.plugin().getDataFolder().getPath();
        console = new LogConsole();
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent join){
        String playerName = join.getPlayer().getName();

        //Check if player has profile
        File profile = new File(dataFolder + "//players//" + playerName);
        File profileGlobal = new File(profile.getPath() + "//global.txt");


        //Create profile
        try{
            if(!profile.exists()) {
                console.logInfoToConsole(playerName + " has no PlayerAudit profile, creating one...");
                profile.mkdir();
            }
            if(!profileGlobal.exists()) { profileGlobal.createNewFile(); }
        }catch (IOException e){
            console.logSevereToConsole(("Could not create " + profileGlobal.getPath() + " : " + e.getMessage()));
        }

        //Create log files if don't exist
        blockPlaceConfig(playerName);
        blockBreakConfig(playerName);
        playerChatConfig(playerName);
        playerCmdConfig(playerName);
        playerTeleportConfig(playerName);
    }

    private void playerTeleportConfig(String playerName){
        File playerTPDir = new File(dataFolder + "//players//" + playerName + "//Teleport");
        String playerTPLogPath = playerTPDir.getPath() + "//" + date.format(dateFormat) + ".txt";
        File playerTPLog = new File(playerTPLogPath);

        try {
            if(!playerTPDir.exists()) { playerTPDir.mkdir(); }
            if(!playerTPLog.exists()){ playerTPLog.createNewFile(); }
        } catch (IOException e) {
            console.logSevereToConsole(("Could not create " + playerTPLog.getPath() + " : " + e.getMessage()));
        }
    }

    private void playerCmdConfig(String playerName){
        File playerCmdDir = new File(dataFolder + "//players//" + playerName + "//Command");
        String playerCmdLogPath = playerCmdDir.getPath() + "//" + date.format(dateFormat) + ".txt";
        File playerCmdLog = new File(playerCmdLogPath);

        try {
            if(!playerCmdDir.exists()) { playerCmdDir.mkdir(); }
            if(!playerCmdLog.exists()){ playerCmdLog.createNewFile(); }
        } catch (IOException e) {
            console.logSevereToConsole(("Could not create " + playerCmdLog.getPath() + " : " + e.getMessage()));
        }
    }

    private void playerChatConfig(String playerName){
        File playerChatDir = new File(dataFolder + "//players//" + playerName + "//Chat");
        String playerChatLogPath = playerChatDir.getPath() + "//" + date.format(dateFormat) + ".txt";
        File playerChatLog = new File(playerChatLogPath);

        try {
            if(!playerChatDir.exists()) { playerChatDir.mkdir(); }
            if(!playerChatLog.exists()){ playerChatLog.createNewFile(); }
        } catch (IOException e) {
            console.logSevereToConsole(("Could not create " + playerChatLog.getPath() + " : " + e.getMessage()));
        }
    }

    private void blockBreakConfig(String playerName){
        File blockBreakDir = new File(dataFolder + "//players//" + playerName + "//BlockBreak");
        String blockBreakLogPath = blockBreakDir.getPath() + "//" + date.format(dateFormat) + ".txt";
        File blockBreakLog = new File(blockBreakLogPath);

        try {
            if(!blockBreakDir.exists()) { blockBreakDir.mkdir(); }
            if(!blockBreakLog.exists()){ blockBreakLog.createNewFile(); }
        } catch (IOException e) {
            console.logSevereToConsole(("Could not create " + blockBreakLog.getPath() + " : " + e.getMessage()));
        }
    }

    private void blockPlaceConfig(String playerName){
        File blockPlaceDir = new File(dataFolder + "//players//" + playerName + "//BlockPlace");
        String blockPlaceLogPath = blockPlaceDir.getPath() + "//" + date.format(dateFormat) + ".txt";
        File blockPlaceLog = new File(blockPlaceLogPath);

        try {
            if(!blockPlaceDir.exists()) { blockPlaceDir.mkdir(); }
            if(!blockPlaceLog.exists()){ blockPlaceLog.createNewFile(); }
        } catch (IOException e) {
            console.logSevereToConsole("Could not create " + blockPlaceLog.getPath() + " : " + e.getMessage());
        }
    }
}
