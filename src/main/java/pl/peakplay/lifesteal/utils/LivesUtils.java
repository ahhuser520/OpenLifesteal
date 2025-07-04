package pl.peakplay.lifesteal.utils;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import pl.peakplay.lifesteal.main.Main;

import java.util.UUID;

public class LivesUtils {
    static FileConfiguration config = Main.getInstance().getConfig();

    public static int getHearts(UUID uuid) {
        int hearts = config.getInt("players." + uuid + ".hearts", 10);
        return hearts;
    }

    public static void setHearts(UUID uuid, int hearts) {
        config.set("players." + uuid + ".hearts", hearts);
        Main.getInstance().saveConfig();

        double newMaxHealth = hearts * 2.0;

        String reason = LangUtils.getMessage("ban-reason");
        String kickMessage = LangUtils.getMessage("ban-kick-message").replace("{reason}", reason);

        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(uuid);
        boolean isOnline = targetPlayer.isOnline();
        Player onlinePlayer = Bukkit.getPlayer(uuid);
        if (newMaxHealth == 0) {
            Bukkit.getBanList(BanList.Type.NAME).addBan(
                    targetPlayer.getName(),
                    reason,
                    null,
                    "LifestealPlugin"
            );

            if (isOnline){
                assert onlinePlayer != null;
                onlinePlayer.kickPlayer(kickMessage);
            }
        } else {
            if(isOnline) {
                Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                    onlinePlayer.setMaxHealth(newMaxHealth);
                }, 1L);
            }
        }
    }
}
