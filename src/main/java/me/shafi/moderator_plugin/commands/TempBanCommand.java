package me.shafi.moderator_plugin.commands;

import me.shafi.moderator_plugin.utils.ChatUtils;
import me.shafi.moderator_plugin.utils.DurationUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Date;

import static me.shafi.moderator_plugin.utils.DurationUtils.parseDuration;

public class TempBanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args){
        if(!sender.hasPermission("moderator.tempban")){
            sender.sendMessage(ChatUtils.format("&cYou are not allowed to run this command"));
            return false;
        }
        if(args.length >= 1){
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            long duration = parseDuration(args[1]);
            String reason = ChatUtils.format("&cBan Hammer has spoken"); //default reason

            if(args.length >= 3){
                reason = ChatUtils.format("&c" + String.join(" ", Arrays.copyOfRange(args, 2, args.length)));
            }
            if(target.hasPlayedBefore()){
                BanList banList = Bukkit.getBanList(BanList.Type.NAME);
                long expiration = System.currentTimeMillis() + duration;

                banList.addBan(target.getName(), reason, new Date(expiration), sender.getName());
                target.getPlayer().kickPlayer("you been banned for " + DurationUtils.formatDuration(duration) +" for: " + reason);
                sender.sendMessage(target.getName() + " has been banned for " + DurationUtils.formatDuration(duration) +" for: " + ChatColor.stripColor(reason));
            }else{
                sender.sendMessage("Player " + target.getName() + "does not exist");
            }

        }
        return false;
    }
}