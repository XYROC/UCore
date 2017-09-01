package ucore.lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ucore.UCore;

public class PermissionHandler {

	private HashMap<String, Permission> hashPermissions;
	private ArrayList<Permission> permissions;

	private UCore core;

	public PermissionHandler(UCore core) {
		this.core = core;
		this.hashPermissions = new HashMap<>();
		this.permissions = new ArrayList<Permission>();
	}

	public void addPermission(String name, Permission permission) {
		hashPermissions.put(name, permission);
		permissions.add(permission);
	}
	
	public void addPermission(String name) {
		hashPermissions.put(name, new Permission(name));
		permissions.add(new Permission(name));
	}

	public void removePermission(String name) {
		hashPermissions.remove(name);
		for(Permission p : permissions){
			if(p.getName().equals(name)){
				permissions.remove(p);
			}
		}
	}

	public void permitPlayer(CommandSender sender, UUID uuid, String name) {
		Player player = Bukkit.getPlayer(uuid);
		if(player != null){
			PlayerObject playerObject = new PlayerObject(uuid);
			String[] var = name.split(".");
			if(hashPermissions.containsKey(name)){
				if(var.length > 1){
					String permission = var[var.length-1];
					if(permission.equals("*")){
						for(Permission p : permissions){
							if(p.getName().startsWith(name)){
								p.add(playerObject);
								hashPermissions.get(p.getName()).add(playerObject);
							}
						}
						sender.sendMessage(ChatColor.GREEN+"Dem Spieler "+player.getName()+" wurde erfolgreich die Berechtigung '"+name+"' hinzugefügt");
						return;
					}else{
						hashPermissions.get(name).add(playerObject);
						for(Permission p : permissions){
							if(p.getName().equals(name)){
								p.add(playerObject);
								break;
							}
						}
						sender.sendMessage(ChatColor.GREEN+"Dem Spieler "+player.getName()+" wurde erfolgreich die Berechtigung '"+name+"' hinzugefügt");
					}
					return;
				}
				sender.sendMessage(ChatColor.RED+"Ungültige Angabe.");
				return;
			}
			sender.sendMessage(ChatColor.RED+"Die Berechtigung '"+name+"' konnte nicht gefunden werden.");
		}else{
			sender.sendMessage(ChatColor.RED+"Der Spieler konnte nicht gefunden werden.");
		}
	}

	public void denyPlayer(String name, PlayerObject player) {

	}

	public UCore getUtilityCore() {
		return core;
	}

	public HashMap<String, Permission> getPermissionsHashmap() {
		return hashPermissions;
	}
	
	public ArrayList<Permission> getPermissions() {
		return permissions;
	}

}
