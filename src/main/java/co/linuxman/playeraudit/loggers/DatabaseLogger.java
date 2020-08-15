package co.linuxman.playeraudit.loggers;

import co.linuxman.playeraudit.PlayerAudit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseLogger implements Serializable {
    private Connection dbConn;
    private String db;

    public DatabaseLogger(){
        db = PlayerAudit.db;
        dbConn = PlayerAudit.getBlockDatabase();
    }

    public void addBlock(Location location, Player player, Material material){
        LogConsole console = new LogConsole();

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        String locFormat = String.format("(%d, %d, %d)", x, y, z);

        String timestamp = new SimpleDateFormat("HH:mm z").format(new Date());

        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        String blockQuery = "INSERT INTO placedblocks(owner,material,timestamp,date,location) VALUES(?,?,?,?,?)";

        try {
            PreparedStatement prep = dbConn.prepareStatement(blockQuery);
            prep.setString(1, player.getName());
            prep.setString(2, material.toString());
            prep.setString(3, timestamp);
            prep.setString(4, date.format(dateFormat));
            prep.setString(5, locFormat);

            prep.executeUpdate();
        } catch (SQLException throwables) {
            console.logSevereToConsole(db + " : " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public void updateBlockData(Location location, Player player, Material material){
        LogConsole console = new LogConsole();

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        String locFormat = String.format("(%d, %d, %d)", x, y, z);

        String timestamp = new SimpleDateFormat("HH:mm z").format(new Date());

        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        String blockQuery = String.format("UPDATE placedblocks SET owner = ?,material = ?,timestamp = ?,date = ? WHERE location = '%s'", locFormat);

        try {
            PreparedStatement prep = dbConn.prepareStatement(blockQuery);
            prep.setString(1, player.getName());
            prep.setString(2, material.toString());
            prep.setString(3, timestamp);
            prep.setString(4, date.format(dateFormat));

            prep.executeUpdate();
        } catch (SQLException throwables) {
            console.logSevereToConsole(db + " : " + throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    public List<String> queryBlock(Location blockLocation){
        LogConsole console = new LogConsole();
        List<String> blockData = new ArrayList<String>();

        int blockX = blockLocation.getBlockX();
        int blockY = blockLocation.getBlockY();
        int blockZ = blockLocation.getBlockZ();
        String locFormat = String.format("(%d, %d, %d)", blockX, blockY, blockZ);

        String blockQuery = String.format("SELECT owner,material,timestamp,date,location FROM placedblocks WHERE location = '%s'", locFormat);

        try {
            Statement stmt = dbConn.createStatement();
            ResultSet result = stmt.executeQuery(blockQuery);

            if(!result.isClosed()){
                blockData.add(result.getString("owner"));
                blockData.add(result.getString("material"));
                blockData.add(result.getString("timestamp"));
                blockData.add(result.getString("date"));
                blockData.add(result.getString("location"));
            }else{
                return null;
            }
        } catch (SQLException throwables) {
            console.logSevereToConsole(db + " : " + throwables.getMessage());
            throwables.printStackTrace();
        }
        return blockData;
    }

    public boolean checkBlockExistance(Location blockLocation){
        LogConsole console = new LogConsole();

        int blockX = blockLocation.getBlockX();
        int blockY = blockLocation.getBlockY();
        int blockZ = blockLocation.getBlockZ();
        String locFormat = String.format("(%d, %d, %d)", blockX, blockY, blockZ);

        String blockQuery = String.format("SELECT owner,material,timestamp,date,location FROM placedblocks WHERE location = '%s'", locFormat);

        try {
            Statement stmt = dbConn.createStatement();
            ResultSet result = stmt.executeQuery(blockQuery);

            //Does exist
            if(!result.isClosed()){
                return true;
            }
        } catch (SQLException throwables) {
            console.logSevereToConsole(db + " : " + throwables.getMessage());
            throwables.printStackTrace();
        }
        return false;
    }
}
