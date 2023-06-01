package me.shafi.moderator_plugin.manager;

import me.shafi.moderator_plugin.BanStatus;
import me.shafi.moderator_plugin.Moderator_plugin;
import me.shafi.moderator_plugin.MuteStatus;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class BanManager {
    private final Moderator_plugin plugin;

    public BanManager(Moderator_plugin plugin) {
        this.plugin = plugin;
        createTable();
    }

    public void createTable() {
        PreparedStatement statement;
        try{
            statement = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS BannedPlayers (id INT AUTO_INCREMENT PRIMARY KEY,timestamp TIMESTAMP, uuid VARCHAR(255), player VARCHAR(255), duration BIGINT, reason VARCHAR(255))");
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void banPlayer(OfflinePlayer player, long duration, String reason) {
        try {
            String sql = "INSERT INTO BannedPlayers (uuid, player, timestamp, duration, reason) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2 ,player.getName());
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setLong(4, duration);
            statement.setString(5, reason);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void banPlayer(OfflinePlayer player, String reason) {
        try {
            String sql = "INSERT INTO BannedPlayers (uuid, player, timestamp, duration, reason) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2, player.getName());
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setLong(4, -1);
            statement.setString(5, reason);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BanStatus getBanStatus(String uuid) {
        try {
            String sql = "SELECT * FROM BannedPlayers WHERE uuid = ?";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            boolean banned = resultSet.next();
            String reason = "";
            long duration = resultSet.getLong("duration");


            if (banned) {


                long muteTime = resultSet.getTimestamp("timestamp").getTime();
                long currentTime = new Date().getTime();
                long expirationTime = muteTime + duration;



                banned = currentTime < expirationTime;

                if(duration == -1){
                    banned = true;
                }

                if(!banned){
                    unBanPlayer(uuid);
                }else {
                    reason = resultSet.getString("reason");
                }

            }

            resultSet.close();
            statement.close();

            return new BanStatus(banned, reason , duration);
        } catch (SQLException ignored) {}
        return new BanStatus(false, "" , -1);
    }

    public void unBanPlayer(String uuid) {
        try {
            String sql = "DELETE FROM BannedPlayers WHERE uuid = ?";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, uuid);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

