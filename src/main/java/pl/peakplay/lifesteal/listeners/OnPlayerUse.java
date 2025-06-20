package pl.peakplay.lifesteal.listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import pl.peakplay.lifesteal.main.Main;

public class OnPlayerUse implements Listener{
	
	@EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || !item.hasItemMeta()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        Integer identifier = meta.getPersistentDataContainer().get(new NamespacedKey(Main.getInstance(), "custom_heart"), PersistentDataType.INTEGER);
        if (identifier == null || identifier != 1) return;

        FileConfiguration config = Main.getInstance().getConfig();
        String uuid = player.getUniqueId().toString();

        int currentHearts = config.getInt("players." + uuid + ".hearts", 10);

        if (currentHearts >= 20) {
            player.sendMessage("§cMasz już maksymalną liczbę serc!");
            return;
        }

        currentHearts++;
        config.set("players." + uuid + ".hearts", currentHearts);
        Main.getInstance().saveConfig();

        double newMaxHealth = currentHearts * 2.0;
        player.setMaxHealth(newMaxHealth);
        player.sendMessage("§aZyskałeś dodatkowe serce!");

        item.setAmount(item.getAmount() - 1);
    }
}
