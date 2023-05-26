package me.shafi.moderator_plugin.manager;

import me.shafi.moderator_plugin.Moderator_plugin;
import me.shafi.moderator_plugin.MuteStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class MuteManager {
    private final Moderator_plugin plugin;

    public MuteManager(Moderator_plugin plugin) {
        this.plugin = plugin;
        createTable();
    }

    public void createTable() {
        PreparedStatement statement;
        try{
            statement = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS MutedPlayers (id INT AUTO_INCREMENT PRIMARY KEY,timestamp TIMESTAMP, player VARCHAR(255), duration BIGINT, reason VARCHAR(255))");
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void mutePlayer(String playerName, long duration, String reason) {
        try {
            String sql = "INSERT INTO MutedPlayers (player,timestamp, duration, reason) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, playerName);
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.setLong(3, duration);
            statement.setString(4, reason);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void mutePlayer(String playerName, String reason) {
        try {
            String sql = "INSERT INTO MutedPlayers (player,timestamp, duration, reason) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, playerName);
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.setLong(3, -1);
            statement.setString(4, reason);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MuteStatus getMuteStatus(String playerName) {
        try {
            String sql = "SELECT * FROM MutedPlayers WHERE player = ?";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();
            boolean muted = resultSet.next();
            String reason = "";


            if (muted) {
                long duration = resultSet.getLong("duration");
                long muteTime = resultSet.getTimestamp("timestamp").getTime();
                long currentTime = new Date().getTime();
                long expirationTime = muteTime + duration;



                muted = currentTime < expirationTime;

                if(duration == -1){
                    muted = true;
                }

                if(!muted){
                    unMutePlayer(playerName);
                }else {
                    reason = resultSet.getString("reason");
                }
            }

            resultSet.close();
            statement.close();

            return new MuteStatus(muted, reason);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new MuteStatus(false, "");
    }

    public void unMutePlayer(String playerName) {
        try {
            String sql = "DELETE FROM MutedPlayers WHERE player = ?";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, playerName);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
