package pl.peakplay.lifesteal.commands;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.peakplay.lifesteal.utils.LangUtils;
import pl.peakplay.lifesteal.utils.LivesUtils;

import java.util.UUID;

public class LifestealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(LangUtils.getMessage("only-players"));
            return true;
        }

        Player playerSender = (Player) commandSender;

        if (!playerSender.isOp() || !playerSender.hasPermission("openlifesteal.admin")) {
            playerSender.sendMessage(LangUtils.getMessage("no-permission"));
            return true;
        }

        if (args.length < 1) {
            playerSender.sendMessage(LangUtils.getMessage("invalid-usage"));
            return true;
        }

        String action = args[0];
        
        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[1]);
        UUID targetedUuid = targetPlayer.getUniqueId();

        if (!targetPlayer.hasPlayedBefore() && !targetPlayer.isOnline()) {
            playerSender.sendMessage(LangUtils.getMessage("player-not-found"));
            return true;
        } 

        try {
            switch (action.toLowerCase()) {
                case "give":
                    if (args.length != 3) {
                        playerSender.sendMessage(LangUtils.getMessage("give-usage"));
                        return true;
                    }

                    int giveAmount = Integer.parseInt(args[2]);
                    int newHearts = LivesUtils.getHearts(targetedUuid) + giveAmount;
                    LivesUtils.setHearts(targetedUuid, newHearts);

                    playerSender.sendMessage("§aDodano " + giveAmount + " serc graczowi " + targetedUuid + ".");
                    return true;

                case "set":
                    if (args.length != 3) {
                        playerSender.sendMessage(LangUtils.getMessage("set-usage"));
                        return true;
                    }

                    int setAmount = Integer.parseInt(args[2]);
                    LivesUtils.setHearts(targetedUuid, setAmount);

                    playerSender.sendMessage("§aUstawiono " + setAmount + " serc graczowi " + targetedUuid + ".");
                    return true;

                case "revive":
                    if (args.length != 2) {
                        playerSender.sendMessage(LangUtils.getMessage("revive-usage"));
                        return true;
                    }

                    BanList banList = Bukkit.getBanList(BanList.Type.NAME);
                    banList.pardon(targetPlayer.getName());
                    LivesUtils.setHearts(targetedUuid, 10);
                    return true;

                default:
                    playerSender.sendMessage(LangUtils.getMessage("unknown-action"));
                    return true;
            }
        } catch (Exception e) {
            String msg = LangUtils.getMessage("command-error").replace("{error}", e.getMessage());
            playerSender.sendMessage(msg);
        }

        return true;
    }
}
