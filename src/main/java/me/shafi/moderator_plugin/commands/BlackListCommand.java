package me.shafi.moderator_plugin.commands;

import me.shafi.moderator_plugin.Moderator_plugin;
import me.shafi.moderator_plugin.utils.ChatUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BlackListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(!sender.hasPermission("moderator.blacklist")){
            sender.sendMessage(ChatUtils.format("&6(!)&cYou are not allowed to run this command"));
            return false;
        }
        if(args.length >= 1){
            Player target = Bukkit.getPlayer(args[0]);
            String reason = "Ban Hammer has spoken"; //default reason
            if(args.length >= 2){
                reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            }
            if(target.isOnline()){
                Moderator_plugin.blackListManager.blackList(target , reason);
                Moderator_plugin.banManager.banPlayer(target , reason);
                Moderator_plugin.logManager.logblackList(target.getName() , reason);
                target.getPlayer().kickPlayer(ChatUtils.format("&6 You have been banned \nReason: &c" + reason));
                sender.sendMessage( ChatUtils.format("&6(!)&a"+target.getName() + " has been banned for: " + reason));
            }else{
                sender.sendMessage(ChatUtils.format("&6(!)&c Player " + target.getName() + "is not online"));
            }

        }
        return false;
    }
}
