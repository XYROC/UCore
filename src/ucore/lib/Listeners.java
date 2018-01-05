package ucore.lib;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import ucore.UCore;

public class Listeners implements Listener{
	
	
	public Listeners(UCore ucore) {
		ucore.getServer().getPluginManager().registerEvents(this, ucore);
	}
	
	@EventHandler
	public void onCommand(final PlayerCommandPreprocessEvent e){
		if(e.getMessage().startsWith("//calc") || e.getMessage().startsWith("/worldedit:/calc") || e.getMessage().startsWith("//calculate") || e.getMessage().startsWith("/worldedit:/calculate")){
			e.setMessage(e.getMessage().replace("{", "").replace("=", ""));
		}
		if(e.getMessage().startsWith("//solve") || e.getMessage().startsWith("/worldedit:/solve")){
			e.setMessage(e.getMessage().replace("{", "").replace("=", ""));
		}
		if(e.getMessage().startsWith("//eval") || e.getMessage().startsWith("/worldedit:/eval") || e.getMessage().startsWith("//evaluate") || e.getMessage().startsWith("/worldedit:/evaluate")){
			e.setMessage(e.getMessage().replace("{", "").replace("=", ""));
		}
	}
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e){
		System.out.println(e.getEntity().getName()+" died at X: "+e.getEntity().getLocation().getBlockX()+" Y:"+e.getEntity().getLocation().getBlockY()+" Z:"+e.getEntity().getLocation().getBlockZ());
		e.getEntity().sendMessage("You died at X:"+e.getEntity().getLocation().getBlockX()+" Y:"+e.getEntity().getLocation().getBlockY()+" Z:"+e.getEntity().getLocation().getBlockZ());
	}
	

}
