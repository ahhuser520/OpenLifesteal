package pl.peakplay.lifesteal.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import pl.peakplay.lifesteal.main.Main;
import pl.peakplay.lifesteal.utils.LivesUtils;

import org.bukkit.BanList;

public class OnPlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        String uuid = player.getUniqueId().toString();

        int hearts = LivesUtils.getHearts(player.getUniqueId());
        hearts = Math.max(hearts - 1, 0);
        
        LivesUtils.setHearts(player.getUniqueId(), hearts);
    }
}
