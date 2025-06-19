package pl.peakplay.lifesteal.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.peakplay.lifesteal.main.Main;
import org.bukkit.BanList;

public class OnPlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        String uuid = player.getUniqueId().toString();

        FileConfiguration config = Main.getInstance().getConfig();

        int hearts = config.getInt("players." + uuid + ".hearts", 10);

        hearts = Math.max(hearts - 1, 0);

        config.set("players." + uuid + ".hearts", hearts);
        Main.getInstance().saveConfig();
        String reason = "Umarłeś. Nie masz więcej żyć";
        double newMaxHealth = hearts * 2.0;
     
        if(newMaxHealth == 0) {
        	Bukkit.getBanList(BanList.Type.NAME).addBan(
                player.getName(),
                reason,
                null,
                "LifeStealPlugin"
            );

            if (player.isOnline() && player.getPlayer() != null) {
            	player.getPlayer().kickPlayer("You have been banned: " + reason);
            }
        }else {
        	Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
        		player.setMaxHealth(newMaxHealth);
        	}, 1L);
        }
    }
}
