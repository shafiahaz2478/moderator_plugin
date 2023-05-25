package me.shafi.moderator_plugin;

import me.shafi.moderator_plugin.commands.BanCommand;
import me.shafi.moderator_plugin.commands.TempBanCommand;
import me.shafi.moderator_plugin.commands.UnBanCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Moderator_plugin extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommands();


    }


    public void registerCommands() {
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnBanCommand());
        getCommand("tempban").setExecutor(new TempBanCommand());
    }
}
