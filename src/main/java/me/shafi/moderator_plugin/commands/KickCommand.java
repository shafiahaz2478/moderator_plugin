package me.shafi.moderator_plugin.commands;

import me.shafi.moderator_plugin.Moderator_plugin;
import me.shafi.moderator_plugin.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class KickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(!sender.hasPermission("moderator.mute")){
            sender.sendMessage(ChatUtils.format("&cYou are not allowed to run this command"));
            return false;
        }

        if(args.length >= 1) {
            Player target = Bukkit.getPlayer(args[0]);
            String reason = ChatUtils.format("Ban Hammer has spoken"); //default reason
            if(args.length >= 2){
                reason = ChatUtils.format( String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
            }
            if(target.isOnline()) {

                Moderator_plugin.logManager.logKick(target.getName(), reason);
                target.kickPlayer(ChatUtils.format("you been banned for: " + reason));

                sender.sendMessage(ChatUtils.format( "&6(!)&a" +target.getName() + " has been muted for: " + reason));
            }else{
                sender.sendMessage(ChatUtils.format( "&6(!)&cPlayer " + target.getName() + " is offline"));
            }
            return true;
        }
        return false;
    }
}
