package me.shafi.moderator_plugin.manager;

import me.shafi.moderator_plugin.Moderator_plugin;
import me.shafi.moderator_plugin.status.BanStatus;
import me.shafi.moderator_plugin.status.BlackListStatus;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class BlackListManager {

    private final Moderator_plugin plugin;

    public BlackListManager(Moderator_plugin plugin) {
        this.plugin = plugin;
        createTable();
    }

    public void createTable() {
        PreparedStatement statement;
        try{
            statement = plugin.sql.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS BlackList (id INT AUTO_INCREMENT PRIMARY KEY,timestamp TIMESTAMP, ip VARCHAR(255), player VARCHAR(255), reason VARCHAR(255))");
            statement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }


    public void blackList(Player player, String reason) {
        try {
            String sql = "INSERT INTO BlackList (ip, player, timestamp, reason) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, player.getAddress().getHostString());
            statement.setString(2, player.getName());
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.setString(4, reason);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public BlackListStatus getBlackListStatus(String ip) {
        try {
            String sql = "SELECT * FROM BlackList WHERE ip = ?";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, ip);
            ResultSet resultSet = statement.executeQuery();
            boolean banned = resultSet.next();
            String reason = resultSet.getString("reason");

            resultSet.close();
            statement.close();

            return new BlackListStatus(banned, reason );
        } catch (SQLException ignored) {}
        return new BlackListStatus(false, "");
    }
    public BlackListStatus getBlackListStatus(OfflinePlayer player) {
        try {
            String sql = "SELECT * FROM BlackList WHERE player = ?";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, player.getName());
            ResultSet resultSet = statement.executeQuery();
            boolean banned = resultSet.next();
            String reason = resultSet.getString("reason");

            resultSet.close();
            statement.close();

            return new BlackListStatus(banned, reason );
        } catch (SQLException ignored) {}
        return new BlackListStatus(false, "");
    }

    public void removeBlackList(String player) {
        try {
            String sql = "DELETE FROM BlackList WHERE player = ?";
            PreparedStatement statement = plugin.sql.getConnection().prepareStatement(sql);
            statement.setString(1, player);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
