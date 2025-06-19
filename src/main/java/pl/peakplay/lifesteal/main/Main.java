package pl.peakplay.lifesteal.main;

import pl.peakplay.lifesteal.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	private static Main instance;

    public static Main getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Lifesteal plugin enabled by ahhuser.");
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerCombat(), this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Lifesteal plugin disabled!");
    }

}
