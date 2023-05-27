package me.shafi.moderator_plugin.commands;

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
            if(!target.isBanned()){
                return true;
            }

            if(target.hasPlayedBefore()){
                BanList banList = Bukkit.getBanList(BanList.Type.IP);

                banList.pardon(target.getName());


                sender.sendMessage(ChatUtils.format("&6(!)&a" + target.getName() + " has been unbanned"));
            }else{
                sender.sendMessage(ChatUtils.format("&6(!)&cPlayer " + target.getName() + "does not exist"));
            }

        }
        return false;
    }
}
