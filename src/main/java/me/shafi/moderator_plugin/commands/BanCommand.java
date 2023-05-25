package me.shafi.moderator_plugin.commands;

import me.shafi.moderator_plugin.utils.ChatUtils;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BanCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg, String[] args) {
        if(!sender.hasPermission("moderator.ban")){
            sender.sendMessage(ChatUtils.format("&cYou are not allowed to run this command"));
            return false;
        }
        if(args.length >= 1){
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            String reason = ChatUtils.format("&cBan Hammer has spoken"); //default reason
            if(args.length >= 2){
                reason = ChatUtils.format("&c" + String.join(" ", Arrays.copyOfRange(args, 1, args.length)));
            }
            if(target.hasPlayedBefore()){
                BanList banList = Bukkit.getBanList(BanList.Type.NAME);

                banList.addBan(target.getName(), reason, null, sender.getName());
                target.getPlayer().kickPlayer("you been banned for: " + reason);
                sender.sendMessage(target.getName() + " has been banned for: " + reason);
            }else{
                sender.sendMessage("Player " + target.getName() + "does not exist");
            }

        }
        return false;
    }
}
