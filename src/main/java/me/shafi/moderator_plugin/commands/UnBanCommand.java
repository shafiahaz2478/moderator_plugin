package me.shafi.moderator_plugin.commands;

import me.shafi.moderator_plugin.Moderator_plugin;
import me.shafi.moderator_plugin.utils.ChatUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class UnBanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args){
        if(!sender.hasPermission("moderator.unban")){
            sender.sendMessage(ChatUtils.format("&cYou are not allowed to run this command"));
            return false;
        }

        if(args.length >= 1){
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            if(!Moderator_plugin.banManager.getBanStatus(target.getUniqueId().toString()).isBanned()){
                sender.sendMessage(ChatUtils.format("&6(!)&c Player " + target.getName() + " is not banned"));
                return true;
            }

            if(target.hasPlayedBefore()){
                Moderator_plugin.banManager.unBanPlayer(target.getUniqueId().toString());


                sender.sendMessage(ChatUtils.format("&6(!)&a " + target.getName() + " has been unbanned"));
            }else{
                sender.sendMessage(ChatUtils.format("&6(!)&c Player " + target.getName() + "does not exist"));
            }

        }
        return false;
    }
}
