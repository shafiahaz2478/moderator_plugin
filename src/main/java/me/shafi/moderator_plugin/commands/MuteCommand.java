package me.shafi.moderator_plugin.commands;

import me.shafi.moderator_plugin.Moderator_plugin;
import me.shafi.moderator_plugin.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;



public class MuteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(!sender.hasPermission("moderator.mute")){
            sender.sendMessage(ChatUtils.format("&cYou are not allowed to run this command"));
            return false;
        }

        if(args.length >= 1) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            String reason = ChatUtils.format("Ban Hammer has spoken"); //default reason
            if(args.length >= 2){
                reason = ChatUtils.format(String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
            }
            if(target.hasPlayedBefore()) {
                if(Moderator_plugin.muteManager.getMuteStatus(target.getName()).isMuted()){
                    Moderator_plugin.muteManager.unMutePlayer(target.getName());
                }
                Moderator_plugin.muteManager.mutePlayer(target.getName(), reason);
                Moderator_plugin.logManager.logMute(target.getName(), reason);

                sender.sendMessage(ChatUtils.format("&6(!)&a" + target.getName() + " has been muted for: " + reason));
            }else{
                sender.sendMessage(ChatUtils.format("&6(!)&cPlayer " + target.getName() + "does not exist"));
            }
            return true;
        }
        return false;
    }
}
