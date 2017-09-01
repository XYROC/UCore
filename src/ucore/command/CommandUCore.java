package ucore.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import ucore.UCore;
import ucore.lib.Functions;

public class CommandUCore implements CommandExecutor {

	private UCore core;

	public CommandUCore(UCore core) {
		this.core = core;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("chest")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (player.isOp()) {
						player.getLocation().getBlock().setType(Material.CHEST);
						Functions.fillChestRandomly(player.getLocation(), UCore.ITEMGEN.getRandomItemList(4, 12), 4);
					}
				}
			}
			if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(ChatColor.GOLD + "/uc" + ChatColor.GRAY + ": UCore Hauptkommando");
				sender.sendMessage(ChatColor.GOLD + "/uc help" + ChatColor.GRAY + ": Zeigt diese Liste");
				sender.sendMessage(
						ChatColor.GOLD + "/uc chest" + ChatColor.GRAY + ": Generiert eine Kiste mit Loot (Spieler)");
				sender.sendMessage(ChatColor.GOLD + "/uc invsee <player> (<ec>)" + ChatColor.GRAY
						+ ": Oeffnet den (Enderkisten-)Inventar eines Spielers (Spieler)");
				sender.sendMessage(ChatColor.GOLD + "/uc generateEmptyWorld <name>" + ChatColor.GRAY
						+ ": Erstellt eine leere Welt");
				sender.sendMessage(ChatColor.GOLD + "/uc bounce <player>" + ChatColor.GRAY
						+ ": Wirft einen Spieler in die Luft (xD)");
				sender.sendMessage(ChatColor.GOLD + "/uc restart <server.sh/server.bat>" + ChatColor.GRAY
						+ ": Startet den Server neu");
				sender.sendMessage(ChatColor.GOLD + "/uc title <title> <subTitle>" + ChatColor.GRAY
						+ ": Sendet dir einen Titel (Spieler)");
				return true;
			}
		}
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("generateEmptyWorld")) {
				Functions.generateEmtptyWorld(args[1], sender);
			}
			if (args[0].equalsIgnoreCase("bounce")) {
				Player target = Bukkit.getPlayer(args[1]);
				if (!sender.isOp()) {
					sender.sendMessage(ChatColor.RED + "Du bist dazu nicht berechtigt.");
					return true;
				}
				if (target == null) {
					sender.sendMessage(ChatColor.RED + "Dieser Spieler ist nicht online.");
					return true;
				}
				target.setVelocity(new Vector(1, 5, 1));
			}
			if (args[0].equalsIgnoreCase("invsee")) {
				Player target = Bukkit.getPlayer(args[1]);
				if (!sender.isOp()) {
					sender.sendMessage(ChatColor.RED + "Du bist dazu nicht berechtigt.");
					return true;
				}
				if (target == null) {
					sender.sendMessage(ChatColor.RED + "Dieser Spieler ist nicht online.");
					return true;
				}
				if (sender instanceof Player) {
					Player p = (Player) sender;
					p.openInventory(target.getInventory());
				}
			}
			if (args[0].equalsIgnoreCase("restart")) {
				String file = args[1];
				if (file.endsWith(".bat")) {
					sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Restarting server... ");
					Functions.restartServerFromBatchFile(file);
					return true;
				}
				if (file.endsWith(".sh")) {
					sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Restarting server... ");
					Functions.startServerFromUnixShellScript(file);
					Bukkit.shutdown();
					return true;
				}
			}
		}
		if (args.length == 3) {
			if (args[0].equalsIgnoreCase("title")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					String title = args[1];
					String subTitle = args[2];
					Functions.sendTitle(player, title, subTitle, ChatColor.GOLD, ChatColor.GRAY, 20, 100, 20);
				}
			}
			if (args[0].equalsIgnoreCase("invsee")) {
				Player target = Bukkit.getPlayer(args[1]);
				if (!sender.isOp()) {
					sender.sendMessage(ChatColor.RED + "Du bist dazu nicht berechtigt.");
					return true;
				}
				if (target == null) {
					sender.sendMessage(ChatColor.RED + "Dieser Spieler ist nicht online.");
					return true;
				}
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (args[2].equalsIgnoreCase("ec")) {
						p.openInventory(target.getEnderChest());
						return true;
					}
					p.openInventory(target.getInventory());
				}
			}
		}
		return true;
	}

}
