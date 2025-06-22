package pl.peakplay.lifesteal.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import pl.peakplay.lifesteal.main.Main;

public class OnPlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String uuid = player.getUniqueId().toString();

        FileConfiguration config = Main.getInstance().getConfig();

        if (!config.contains("players." + uuid + ".hearts")) {
            config.set("players." + uuid + ".hearts", 10);
            Main.getInstance().saveConfig();
        }

        int hearts = config.getInt("players." + uuid + ".hearts", 10);

        double newMaxHealth = hearts * 2.0;
        if(newMaxHealth != 0) {
        	player.setMaxHealth(newMaxHealth);
        }
    }
}
