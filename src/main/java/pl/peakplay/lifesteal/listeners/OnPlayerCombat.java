package pl.peakplay.lifesteal.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import pl.peakplay.lifesteal.main.Main;

public class OnPlayerCombat implements Listener {

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        Player victim = e.getEntity();
        Player killer = victim.getKiller();

        if (killer == null) return;

        String killerUUID = killer.getUniqueId().toString();
        FileConfiguration config = Main.getInstance().getConfig();

        int hearts = config.getInt("players." + killerUUID + ".hearts", 10);

        if (hearts < 20) {
            hearts += 1;
        }

        config.set("players." + killerUUID + ".hearts", hearts);
        Main.getInstance().saveConfig();

        double newMaxHealth = hearts * 2.0;
        killer.setMaxHealth(newMaxHealth);
    }
}
