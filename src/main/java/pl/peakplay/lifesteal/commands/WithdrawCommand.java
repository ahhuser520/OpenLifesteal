package pl.peakplay.lifesteal.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import net.md_5.bungee.api.ChatColor;
import pl.peakplay.lifesteal.main.Main;
import pl.peakplay.lifesteal.utils.LivesUtils;

public class WithdrawCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player player = (Player) sender;
		int hearts = LivesUtils.getHearts(player);
		LivesUtils.setHearts(player, hearts-1);
		
		PlayerInventory inventory = player.getInventory();
		
		inventory.addItem(Main.customHeart);
		
		player.sendMessage(ChatColor.GREEN + "Wypłaciłeś swoje serce jako item.");
		
		return false;
	}

}
