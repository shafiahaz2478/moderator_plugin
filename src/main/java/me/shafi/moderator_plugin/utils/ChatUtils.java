package me.shafi.moderator_plugin.utils;

import org.bukkit.ChatColor;

public class ChatUtils {

    public static String format(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
