package pl.peakplay.lifesteal.utils;

import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class LangUtils {
    private static JavaPlugin plugin;
    private static File langFile;
    private static Map<String, Object> messages = new HashMap<>();

    public static void init(JavaPlugin pluginInstance) {
        plugin = pluginInstance;
        File langFolder = new File(plugin.getDataFolder(), "lang");

        if (!langFolder.exists()) {
            langFolder.mkdirs();
        }

        langFile = new File(langFolder, "pl.yaml");

        if (!langFile.exists()) {
            createDefaultLangFile();
        }

        loadLangFile();
    }

    private static void createDefaultLangFile() {
        Map<String, Object> defaultMessages = new HashMap<>();
        defaultMessages.put("heart-stolen", "§cUkradłeś serce od: {player}");
        defaultMessages.put("no-permission", "§cNie masz uprawnień do tej komendy.");
        defaultMessages.put("player-not-found", "§cNie znaleziono gracza.");
        defaultMessages.put("ban-reason", "§cUmarłeś. Nie masz więcej żyć.");
        defaultMessages.put("ban-kick-message", "§cZostałeś zbanowany: {reason}");
        defaultMessages.put("max-hearts-reached", "§cMasz już maksymalną liczbę serc!");
        defaultMessages.put("heart-gained", "§aZyskałeś dodatkowe serce!");
        defaultMessages.put("only-players", "§cTa komenda może być używana tylko przez graczy.");
        defaultMessages.put("invalid-usage-lifesteal", "§cUżyj: /lifesteal <give|set|revive> [argumenty]");
        defaultMessages.put("player-offline", "§cTen gracz jest offline.");
        defaultMessages.put("unknown-action-lifesteal", "§cNieznana akcja. Użyj: /lifesteal <give|set|revive>");
        defaultMessages.put("command-error", "§cWystąpił błąd: {error}");
        defaultMessages.put("give-usage", "§cUżyj: /lifesteal give {username} {amount}");
        defaultMessages.put("set-usage", "§cUżyj: /lifesteal set {username} {amount}");
        defaultMessages.put("revive-usage", "§cUżyj: /lifesteal revive {username}");
        defaultMessages.put("player-not-found", "§cGracz nie zostal znaleziony.");

        Yaml yaml = new Yaml();

        try (Writer writer = new FileWriter(langFile)) {
            yaml.dump(defaultMessages, writer);
        } catch (IOException e) {
            System.err.println("Nie udało się zapisać pliku językowego: " + e.getMessage());
        }
    }

    private static void loadLangFile() {
        Yaml yaml = new Yaml();
        try (InputStream input = new FileInputStream(langFile)) {
            messages = yaml.load(input);
            if (messages == null) messages = new HashMap<>();
        } catch (IOException e) {
            System.err.println("Nie udało się załadować pliku językowego: " + e.getMessage());
            messages = new HashMap<>();
        }
    }

    public static String getMessage(String key) {
        Object value = messages.get(key);
        return value != null ? value.toString() : "§cBrak tłumaczenia dla: " + key;
    }
}
