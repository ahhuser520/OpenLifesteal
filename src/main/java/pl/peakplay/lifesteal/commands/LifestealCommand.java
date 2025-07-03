package pl.peakplay.lifesteal.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.peakplay.lifesteal.utils.LangUtils;
import pl.peakplay.lifesteal.utils.LivesUtils;

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

        try {
            switch (action.toLowerCase()) {
                case "give":
                    if (args.length != 3) {
                        playerSender.sendMessage(LangUtils.getMessage("give-usage"));
                        return true;
                    }

                    Player giveTarget = Bukkit.getPlayer(args[1]);
                    if (giveTarget == null) {
                        playerSender.sendMessage(LangUtils.getMessage("player-offline"));
                        return true;
                    }

                    int giveAmount = Integer.parseInt(args[2]);
                    int newHearts = LivesUtils.getHearts(giveTarget) + giveAmount;
                    LivesUtils.setHearts(giveTarget, newHearts);

                    playerSender.sendMessage("§aDodano " + giveAmount + " serc graczowi " + giveTarget.getName() + ".");
                    return true;

                case "set":
                    if (args.length != 3) {
                        playerSender.sendMessage(LangUtils.getMessage("set-usage"));
                        return true;
                    }

                    Player setTarget = Bukkit.getPlayer(args[1]);
                    if (setTarget == null) {
                        playerSender.sendMessage(LangUtils.getMessage("player-offline"));
                        return true;
                    }

                    int setAmount = Integer.parseInt(args[2]);
                    LivesUtils.setHearts(setTarget, setAmount);

                    playerSender.sendMessage("§aUstawiono " + setAmount + " serc graczowi " + setTarget.getName() + ".");
                    return true;

                case "revive":
                    if (args.length != 2) {
                        playerSender.sendMessage(LangUtils.getMessage("revive-usage"));
                        return true;
                    }

                    Player reviveTarget = Bukkit.getPlayer(args[1]);
                    if (reviveTarget == null) {
                        playerSender.sendMessage(LangUtils.getMessage("player-offline"));
                        return true;
                    }

                    // TODO: Implementacja revive (np. usunięcie bana + teleport)
                    playerSender.sendMessage("§aRevive jeszcze niezaimplementowane.");
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
