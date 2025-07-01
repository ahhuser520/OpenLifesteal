package pl.peakplay.lifesteal.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.peakplay.lifesteal.utils.LivesUtils;

public class LifestealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Ta komenda może być używana tylko przez graczy.");
            return true;
        }

        Player playerSender = (Player) commandSender;

        if(!playerSender.isOp() || !playerSender.hasPermission("openlifesteal.admin")){
            commandSender.sendMessage(ChatColor.RED + "Nie masz pozwolenia na uzycie tej komendy.");
            return true;
        }

        if (args.length < 1) {
            commandSender.sendMessage(ChatColor.RED + "Użyj: /lifesteal <give|set|revive> [argumenty]");
            return true;
        }
        String thisPlayerIsOffline = "Ten gracz jest offline.";
        String action = args[0];
        Player targetedPlayer = Bukkit.getPlayer(args[1]);

        try {
            switch (action.toLowerCase()) {
                case "give":
                    if (args.length != 3) {
                        playerSender.sendMessage(ChatColor.RED + "Użyj: /lifesteal give {username} {amount}");
                        return true;
                    }
                    if (targetedPlayer == null) {
                        playerSender.sendMessage(ChatColor.RED + thisPlayerIsOffline);
                        return true;
                    }
                    LivesUtils.setHearts(targetedPlayer, LivesUtils.getHearts(targetedPlayer) + Integer.parseInt(args[2]));
                    return true;

                case "set":
                    if (args.length != 3) {
                        playerSender.sendMessage(ChatColor.RED + "Użyj: /lifesteal set {username} {amount}");
                        return true;
                    }
                    if (targetedPlayer == null) {
                        playerSender.sendMessage(ChatColor.RED + thisPlayerIsOffline);
                        return true;
                    }
                    LivesUtils.setHearts(targetedPlayer, Integer.parseInt(args[2]));
                    return true;

                case "revive":
                    if (args.length != 2) {
                        playerSender.sendMessage(ChatColor.RED + "Użyj: /lifesteal revive {username}");
                        return true;
                    }
                    // TO DO: REVIVE PLAYER
                    return true;

                default:
                    playerSender.sendMessage("Nieznana akcja. Użyj: /lifesteal <give|set|revive>");
                    return true;
            }
        }catch(Exception e){
            playerSender.sendMessage(ChatColor.RED + "Error: "+e.getMessage());
        }

        return true;
    }
}
