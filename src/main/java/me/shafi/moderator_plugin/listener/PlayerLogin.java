package me.shafi.moderator_plugin.listener;

import me.shafi.moderator_plugin.BanStatus;
import me.shafi.moderator_plugin.Moderator_plugin;
import me.shafi.moderator_plugin.utils.ChatUtils;
import me.shafi.moderator_plugin.utils.DurationUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerLogin implements Listener {

    @EventHandler
    public void onlogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        BanStatus banStatus = Moderator_plugin.banManager.getBanStatus(player.getUniqueId().toString());
        if(banStatus.isBanned()){
            if(banStatus.getDuration() > 0){
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED , ChatUtils.format("&6 You have been banned for " + DurationUtils.formatDuration(banStatus.getDuration()) +"\nReason: &c" + banStatus.getReason()));
            }else {
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED , ChatUtils.format("&6 You have been banned \nReason: &c" + banStatus.getReason() ));
            }
        }
    }
}
