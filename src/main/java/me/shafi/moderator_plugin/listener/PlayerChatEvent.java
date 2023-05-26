package me.shafi.moderator_plugin.listener;

import me.shafi.moderator_plugin.Moderator_plugin;
import me.shafi.moderator_plugin.MuteStatus;
import me.shafi.moderator_plugin.utils.ChatUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        MuteStatus muteStatus = Moderator_plugin.muteManager.getMuteStatus(player.getName());
        if(muteStatus.isMuted()){
            player.sendMessage(ChatUtils.format("&6(!)&cYou have been muted for: " + muteStatus.getReason()));
            e.setCancelled(true);
        }
    }
}
