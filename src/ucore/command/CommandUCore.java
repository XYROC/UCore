package ucore.command;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import ucore.UCore;
import ucore.lib.ULib;

public class CommandUCore implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("chest")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					if (player.isOp()) {
						player.getLocation().getBlock().setType(Material.CHEST);
						ULib.fillChestRandomly(player.getLocation(), UCore.ITEMGEN.getRandomItemList(4, 12), 4);
					}
				}
			}
			if (args[0].equalsIgnoreCase("help")) {
				sender.sendMessage(ChatColor.GOLD + "/uc" + ChatColor.GRAY + ": UCore Hauptkommando");
				sender.sendMessage(ChatColor.GOLD + "/uc help" + ChatColor.GRAY + ": Zeigt diese Liste");
				sender.sendMessage(
						ChatColor.GOLD + "/uc chest" + ChatColor.GRAY + ": Generiert eine Kiste mit zufälligen Items. (Spieler-Kommando) (Fun)");
				sender.sendMessage(ChatColor.GOLD + "/uc invsee <player> (<ec>)" + ChatColor.GRAY
						+ ": Oeffnet das (Enderkisten-)Inventar eines Spielers (Spieler-Kommando)");
				sender.sendMessage(ChatColor.GOLD + "/uc generateEmptyWorld <name>" + ChatColor.GRAY
						+ ": Erstellt eine leere Welt");
				sender.sendMessage(ChatColor.GOLD + "/uc bounce <player>" + ChatColor.GRAY
						+ ": Wirft einen Spieler in die Luft (xD)");
				sender.sendMessage(ChatColor.GOLD + "/uc restart <server.sh/server.bat>" + ChatColor.GRAY
						+ ": Startet den Server neu");
				sender.sendMessage(ChatColor.GOLD + "/uc title <title> <subTitle>" + ChatColor.GRAY
						+ ": Sendet dir einen Titel (Spieler)");
				sender.sendMessage(ChatColor.GOLD + "/uc path" + ChatColor.GRAY + ": Zeigt dir den Pfad des laufenden Minecraft-Servers");
				sender.sendMessage(ChatColor.GOLD + "/uc config" + ChatColor.GRAY + ": Config-Hauptkommando");
				sender.sendMessage(ChatColor.GOLD + "/uc config help" + ChatColor.GRAY + ": Config-Hilfe");
				return true;
			}
			if(args[0].equalsIgnoreCase("path")) {
				sender.sendMessage(ChatColor.RED+"Minecraft-Server-Pfad: "+ULib.getBukkitPath());
			}
			if (args[0].equalsIgnoreCase("restart")) {
				String file = (String) UCore.getData().get("config", "script.runServer");
				if(file.equalsIgnoreCase("")) {
					sender.sendMessage(ChatColor.RED+"Es wurde noch kein Skript festgelegt.");
					return true;
				}else if(!new File(file).exists()) {
					sender.sendMessage(ChatColor.RED+"Die festgelegte Skriptdatei existiert nicht.");
					return true;
				}
				if (file.endsWith(".bat")) {
					sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Restarting server... ");
					ULib.restartServerFromBatchFile(file);
					return true;
				}
				else if (file.endsWith(".sh")) {
					sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Restarting server... ");
					ULib.restartServerFromUnixShellScript(file);
					//Bukkit.shutdown();
					return true;
				} else {
					sender.sendMessage(ChatColor.RED+"Unbekanntes Dateiformat des Skripts. Nutze '.bat' oder '.sh'.");
				}
			}
		}
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("generateEmptyWorld")) {
				ULib.generateEmtptyWorld(args[1], sender);
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
					ULib.restartServerFromBatchFile(file);
					return true;
				}
				else if (file.endsWith(".sh")) {
					sender.sendMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Restarting server... ");
					ULib.restartServerFromUnixShellScript(file);
					//Bukkit.shutdown();
					return true;
				} else {
					sender.sendMessage(ChatColor.RED+"Unbekanntes Dateiformat.");
				}
			}
		}
		if (args.length == 3) {
			if (args[0].equalsIgnoreCase("title")) {
				if (sender instanceof Player) {
					Player player = (Player) sender;
					String title = args[1];
					String subTitle = args[2];
					ULib.sendTitle(player, title, subTitle, ChatColor.GOLD, ChatColor.GRAY, 20, 100, 20);
				}
			}
			if (args[0].equalsIgnoreCase("invsee")) {
				Player target = Bukkit.getPlayer(args[1]);
				if (!sender.isOp()) {
					sender.sendMessage(ChatColor.RED + "Du bist nicht dazu berechtigt.");
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
		if(args.length > 1) {
			if(args[0].equalsIgnoreCase("config")) {
				if(args.length == 2) {
					if(args[1].equalsIgnoreCase("help")) {
						sender.sendMessage(ChatColor.GOLD + "/uc config" + ChatColor.GRAY + ": Config-Hauptkommando");
						sender.sendMessage(ChatColor.GOLD + "/uc config setDefaultServerRunScript <file>" + ChatColor.GRAY + ": Setzt die Scriptdatei, die den Server startet.");
					}
				}
				if(args.length == 3) {
					if(args[1].equalsIgnoreCase("setDefaultServerRunScript")) {
						File file = new File("plugins//UCore//config.yml");
						FileConfiguration config = YamlConfiguration.loadConfiguration(file);
						config.set("script.runServer", args[2]);
						UCore.getData().getList("config").put("script.runServer", args[2]);
						try {
							config.save(file);
							sender.sendMessage(ChatColor.GRAY+"Der Server wird nun automatisch mit "+ChatColor.GREEN+"'"+args[2]+"'"+ChatColor.GRAY+" neu gestartet.");
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else if(args[1].equalsIgnoreCase("setEnableCommands")) {
						File file = new File("plugins//UCore//config.yml");
						FileConfiguration config = YamlConfiguration.loadConfiguration(file);
						config.set("UCore.enableCommands", Boolean.parseBoolean(args[2]));
						UCore.getData().getList("config").put("enableCommands", true);
						try {
							config.save(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return true;
	}

}
