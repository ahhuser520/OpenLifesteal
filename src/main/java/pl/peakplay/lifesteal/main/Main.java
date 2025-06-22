package pl.peakplay.lifesteal.main;

import pl.peakplay.lifesteal.listeners.*;
import pl.peakplay.lifesteal.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    private ItemStack customHeart;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("OpenLifesteal plugin by ahhuser enabled.");
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerCombat(), this);
        ConfigUtils.createDefaultConfig(this);
        createCustomItem();
        createCustomRecipe();
    }
    
    private void createCustomItem() {
        customHeart = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = customHeart.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§c§lSerce");
            meta.getPersistentDataContainer().set(new NamespacedKey(this, "custom_heart"), PersistentDataType.INTEGER, 1);
            customHeart.setItemMeta(meta);
        }
    }

    private void createCustomRecipe() {
        NamespacedKey key = new NamespacedKey(this, "custom_heart_recipe");

        ShapedRecipe recipe = new ShapedRecipe(key, customHeart);
        recipe.shape("GGG", "GEG", "ONO");

        recipe.setIngredient('O', Material.OBSIDIAN);
        recipe.setIngredient('G', Material.GOLD_INGOT);
        recipe.setIngredient('N', Material.NETHERITE_INGOT);
        recipe.setIngredient('E', Material.NETHER_STAR);

        Bukkit.addRecipe(recipe);
    }

    @Override
    public void onDisable() {
        getLogger().info("OpenLifesteal plugin disabled!");
    }

}
