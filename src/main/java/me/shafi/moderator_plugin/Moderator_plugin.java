package me.shafi.moderator_plugin;

import me.shafi.moderator_plugin.commands.*;
import me.shafi.moderator_plugin.data.MySQL;
import me.shafi.moderator_plugin.listener.PlayerChatEvent;
import me.shafi.moderator_plugin.manager.LogManager;
import me.shafi.moderator_plugin.manager.MuteManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Moderator_plugin extends JavaPlugin {

    public MySQL sql;

    public static MuteManager muteManager;
    public static LogManager logManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerCommands();
        getServer().getPluginManager().registerEvents(new PlayerChatEvent() , this);


        this.sql = new MySQL(this.getConfig().getString("SQL.host"),this.getConfig().getString("SQL.port"),
                this.getConfig().getString("SQL.username"),this.getConfig().getString("SQL.password"),
                this.getConfig().getString("SQL.database"));

        try {
            sql.connect();
        } catch (SQLException e) {
            Bukkit.getLogger().warning("Database not connected");
            getPluginLoader().disablePlugin(this);
        }
        if(sql.isConnected()){
            Bukkit.getLogger().info("Database connected ");

            muteManager = new MuteManager(this);
            logManager = new LogManager(this);
        }

    }

    @Override
    public void onDisable(){
        try {
            if (sql.isConnected()) sql.disconnect();
        }catch (Exception ignored){}
    }


    public void registerCommands() {
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnBanCommand());
        getCommand("tempban").setExecutor(new TempBanCommand());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new UnMuteCommand());
        getCommand("tempmute").setExecutor(new TempMuteCommand());
        getCommand("history").setExecutor(new HistoryCommand());
        getCommand("kick").setExecutor(new KickCommand());
    }
}
