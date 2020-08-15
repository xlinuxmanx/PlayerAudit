package co.linuxman.playeraudit;

import co.linuxman.playeraudit.commands.PlayerAuditCommands;
import co.linuxman.playeraudit.commands.PlayerAuditTabCompleter;
import co.linuxman.playeraudit.listeners.*;
import co.linuxman.playeraudit.loggers.LogConsole;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerAudit extends JavaPlugin {
    private static Plugin plugin;
    private LogConsole console;
    private static Connection dbConn;
    public static String db;

    @Override
    public void onEnable() {
        //Initial assignments
        plugin = this;
        console = new LogConsole();
        db = "jdbc:sqlite:" + getDataFolder() + "/BlockData.db";

        getCommand("playeraudit").setExecutor(new PlayerAuditCommands());
        getCommand("playeraudit").setTabCompleter(new PlayerAuditTabCompleter());
        getCommand("pa").setExecutor(new PlayerAuditCommands());
        getCommand("pa").setTabCompleter(new PlayerAuditTabCompleter());


        //Create plugin config files
        configSetup();

        //Register events
        getServer().getPluginManager().registerEvents(new WandInteractListener(), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerChatListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerSignListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerBucketListener(), this);
        getServer().getPluginManager().registerEvents(new ItemPickupListener(), this);
        getServer().getPluginManager().registerEvents(new ItemMergeListener(), this);
    }

    @Override
    public void onDisable() {
        try {
            dbConn.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void configSetup(){
        File profileDirectory = new File(getDataFolder().getAbsolutePath() + "//players");
        File defaultConfig = new File(getDataFolder(), "config.yml");
        File lavaLog = new File(getDataFolder(), "Lava.txt");
        File waterLog = new File(getDataFolder(), "Water.txt");
        File blockDB = new File(getDataFolder(), "BlockData.db");

        try{
            if(!getDataFolder().exists()) {getDataFolder().mkdir(); }
            if(!profileDirectory.exists()) { profileDirectory.mkdir(); }
            if(!defaultConfig.exists()) { saveDefaultConfig(); }
            if(!lavaLog.exists()) { lavaLog.createNewFile(); }
            if(!waterLog.exists()) { waterLog.createNewFile(); }
            if(!blockDB.exists()) {
                createNewBlockDB();
            }else{
                dbConn = DriverManager.getConnection(db);
            }
        }catch (IOException e){
            console.logSevereToConsole(("Could not create file" + " : " + e.getMessage()));
            e.printStackTrace();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createNewBlockDB(){
        try{
            dbConn = DriverManager.getConnection(db);
            String createTable = "CREATE TABLE IF NOT EXISTS placedblocks (" +
                    "owner text NOT NULL," +
                    "material text NOT NULL," +
                    "timestamp text NOT NULL," +
                    "date text NOT NULL," +
                    "location text NOT NULL);";
            Statement tableStmt = dbConn.createStatement();
            tableStmt.execute(createTable);
            tableStmt.close();
        } catch (SQLException throwables) {
            console.logSevereToConsole(db + " : " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public static Plugin plugin(){
        return plugin;
    }

    public static Connection getBlockDatabase(){
        return dbConn;
    }
}
