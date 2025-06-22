package pl.peakplay.lifesteal.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigUtils {

    public static void createDefaultConfig(JavaPlugin plugin) {
        FileConfiguration config = plugin.getConfig();

        if (!config.contains("maxHearts")) {
            config.set("maxHearts", 20);
        }

        plugin.saveConfig();
    }

    public static String getKey(String key) {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(ConfigUtils.class);
        FileConfiguration config = plugin.getConfig();

        return config.getString(key);
    }

    public static boolean setKey(String key, String value) {
        JavaPlugin plugin = JavaPlugin.getProvidingPlugin(ConfigUtils.class);
        FileConfiguration config = plugin.getConfig();

        config.set(key, value);
        plugin.saveConfig();

        return config.getString(key) != null && config.getString(key).equals(value);
    }
}
