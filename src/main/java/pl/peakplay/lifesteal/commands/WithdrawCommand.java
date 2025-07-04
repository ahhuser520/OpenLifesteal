package pl.peakplay.lifesteal.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import pl.peakplay.lifesteal.main.Main;
import pl.peakplay.lifesteal.utils.LangUtils;
import pl.peakplay.lifesteal.utils.LivesUtils;

public class WithdrawCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(LangUtils.getMessage("only-players"));
            return true;
        }

        Player player = (Player) sender;
        int hearts = LivesUtils.getHearts(player.getUniqueId());

        if (hearts <= 0) {
            player.sendMessage(LangUtils.getMessage("no-hearts-to-withdraw"));
            return true;
        }

        LivesUtils.setHearts(player.getUniqueId(), hearts - 1);

        PlayerInventory inventory = player.getInventory();
        inventory.addItem(Main.customHeart);

        player.sendMessage(LangUtils.getMessage("heart-withdrawn"));

        return true;
    }
}
