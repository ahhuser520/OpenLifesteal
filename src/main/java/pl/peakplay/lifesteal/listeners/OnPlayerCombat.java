package pl.peakplay.lifesteal.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import pl.peakplay.lifesteal.main.Main;
import pl.peakplay.lifesteal.utils.ConfigUtils;
import pl.peakplay.lifesteal.utils.LangUtils;
import pl.peakplay.lifesteal.utils.LivesUtils;
import pl.peakplay.lifesteal.utils.*;

public class OnPlayerCombat implements Listener {


    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();
        
        if (killer == null) return;

        String message = LangUtils.getMessage("heart-stolen").replace("{player}", victim.getName());
        killer.sendMessage(message);

        int hearts = LivesUtils.getHearts(killer);
        String maxHearts = ConfigUtils.getKey("maxHearts");
        int maxHeartsInt = Integer.parseInt(maxHearts);

        if (hearts <= maxHeartsInt) {
            hearts += 1;
        }

        LivesUtils.setHearts(killer, hearts);
    }
}
