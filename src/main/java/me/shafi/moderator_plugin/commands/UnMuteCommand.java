package me.shafi.moderator_plugin.commands;

import me.shafi.moderator_plugin.Moderator_plugin;
import me.shafi.moderator_plugin.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnMuteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args){
        if(!sender.hasPermission("moderator.unmute")){
            sender.sendMessage(ChatUtils.format("&cYou are not allowed to run this command"));
            return false;
        }

        if(args.length >= 1){
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

            if(Moderator_plugin.muteManager.getMuteStatus(target.getName()).isMuted()){
                Moderator_plugin.muteManager.unMutePlayer(target.getName());

                sender.sendMessage(ChatUtils.format("&6(!)&a" +target.getName() + " has been unmuted"));
            }else{
                sender.sendMessage(ChatUtils.format("&6(!)&cPlayer " + target.getName() + "isnt muted"));
            }

        }
        return false;
    }
}
