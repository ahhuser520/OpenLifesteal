package pl.peakplay.lifesteal.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.md_5.bungee.api.ChatColor;
import pl.peakplay.lifesteal.main.Main;
import pl.peakplay.lifesteal.utils.ConfigUtils;
import pl.peakplay.lifesteal.utils.LivesUtils;

public class OnPlayerCombat implements Listener {

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();
        
        if (killer == null) return;
        
        String killerUUID = killer.getUniqueId().toString();
        FileConfiguration config = Main.getInstance().getConfig();
        killer.sendMessage(ChatColor.RED + "Ukradłeś serce od: "+victim.getName());
        int hearts = LivesUtils.getHearts(killer);
        
        String maxHearts = ConfigUtils.getKey("maxHearts");
        int maxHeartsInt = Integer.parseInt(maxHearts);
        if (hearts <= maxHeartsInt) {
            hearts += 1;
        }

        LivesUtils.setHearts(killer, hearts);
    }
}
