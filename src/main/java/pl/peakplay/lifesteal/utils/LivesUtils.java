package pl.peakplay.lifesteal.utils;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import pl.peakplay.lifesteal.main.Main;

public class LivesUtils {
	static FileConfiguration config = Main.getInstance().getConfig();

	public static int getHearts(Player p) {
		
        int hearts = config.getInt("players." + p.getUniqueId() + ".hearts", 10);

        return hearts;
	}
	
	public static void setHearts(Player player, int hearts) {
		config.set("players." + player.getUniqueId() + ".hearts", hearts);
		Main.getInstance().saveConfig();
        double newMaxHealth = hearts * 2.0;

        String reason = "Umarłeś. Nie masz więcej żyć";
             
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
