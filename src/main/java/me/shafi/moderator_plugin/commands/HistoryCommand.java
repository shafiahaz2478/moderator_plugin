package me.shafi.moderator_plugin.commands;

import me.shafi.moderator_plugin.Moderator_plugin;
import me.shafi.moderator_plugin.utils.ChatUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HistoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(!sender.hasPermission("moderator.history")){
            sender.sendMessage(ChatUtils.format("&6(!)&cYou are not allowed to run this command"));
            return false;
        }
        if (args.length < 1) {
            return true;
        }

        String playerName = args[0];
        List<String> historyLogs = Moderator_plugin.logManager.getPlayerHistory(playerName);

        if (historyLogs.isEmpty()) {
            sender.sendMessage( ChatUtils.format("&6(!)&cNo history found for player: " + playerName));
        } else {
            sender.sendMessage(ChatUtils.format("&aHistory for player: " + playerName));
            for (String log : historyLogs) {
                sender.sendMessage(log);
            }
        }

        return true;
    }
}
