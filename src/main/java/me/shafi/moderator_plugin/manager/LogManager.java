package me.shafi.moderator_plugin.manager;

import me.shafi.moderator_plugin.Moderator_plugin;
import me.shafi.moderator_plugin.utils.ChatUtils;
import me.shafi.moderator_plugin.utils.DurationUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class LogManager {
    private final Moderator_plugin plugin;

    public LogManager(Moderator_plugin plugin) {
        this.plugin = plugin;
        createTable();
    }

    private void createTable() {

        PreparedStatement statement;
        try{
            statement = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS PunishmentLog (id INT AUTO_INCREMENT PRIMARY KEY, type VARCHAR(10), timestamp TIMESTAMP, player VARCHAR(255), duration BIGINT, reason VARCHAR(255))");
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void logMute(String playerName, String reason) {
        logAction(ActionType.MUTE, -1 , playerName, reason);
    }

    public void logTempMute(String playerName, long duration, String reason) {
        logAction(ActionType.TEMPMUTE, duration, playerName,  reason);
    }

    public void logBan(String playerName, String reason) {
        logAction(ActionType.BAN,-1 , playerName,  reason);
    }

    public void logTempBan(String playerName, long duration, String reason) {
        logAction(ActionType.TEMPBAN, duration, playerName,  reason);
    }

    public void logKick(String playerName, String reason) {
        logAction(ActionType.KICK, -1, playerName,  reason);
    }

    private void logAction(ActionType type, long duration, String playerName, String reason) {
        try {
            String sql = "INSERT INTO PunishmentLog (type, timestamp, duration, player, reason) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, type.name());
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.setLong(3 , duration);
            statement.setString(4, playerName);
            statement.setString(5, reason);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getPlayerHistory(String playerName) {
        List<String> historyLogs = new ArrayList<>();

        try {
            String sql = "SELECT * FROM PunishmentLog WHERE player = ?";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, playerName);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                ActionType type = ActionType.valueOf(resultSet.getString("type"));
                Timestamp timestamp = resultSet.getTimestamp("timestamp");
                String reason = resultSet.getString("reason");
                long duration = resultSet.getLong("duration");
                String log;
                if(type == ActionType.TEMPBAN || type == ActionType.TEMPMUTE){
                    log = ChatUtils.format("&6[" + type.name() + "] &r" + playerName + " &2- " + timestamp.toString() + " &c- Duration: " + DurationUtils.formatDuration(duration) + " &b- Reason: " + reason);
                }else {
                    log = ChatUtils.format("&6[" + type.name() + "] &r"  + playerName + " &2- " + timestamp.toString() + " &b- Reason: " + reason);
                }
                historyLogs.add(log);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyLogs;
    }


}

enum ActionType {
    BAN,
    TEMPBAN,
    MUTE,
    TEMPMUTE,
    KICK
}


