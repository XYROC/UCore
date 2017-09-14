package ucore.lib;

import java.io.IOException;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import ucore.UCore;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class ULib {
	

	public static void spawnFallingBlock(Location location, Material material, byte data) {
		location.getWorld().spawnFallingBlock(location, material, data);
	}

	public static void fillChest(Location location, ItemStack[] items) {
		BlockState state = location.getBlock().getState();
		if (state instanceof Chest) {
			Inventory inv = ((Chest)state).getInventory();
			inv.clear();
			for(ItemStack stack : items){
				inv.setItem(new Random().nextInt(inv.getSize()), stack);
			}
		}
	}
	
	public static void fillChestRandomly(Location location, ItemStack[] possibleItems, int probability) {
		BlockState state = location.getBlock().getState();
		if (state instanceof Chest) {
			Random random = new Random();
			Inventory inv = ((Chest)state).getInventory();
			inv.clear();
			for(int i = 0;i<inv.getSize();i++){
				if(random.nextInt(probability) == 0){
					inv.setItem(i, possibleItems[random.nextInt(possibleItems.length)]);
				}
			}
		}
	}
	
	public static void particleEffectExplosion(EnumParticle particle, Player player, int particles){
		Location location = player.getLocation();
		CraftPlayer craftPlayer = (CraftPlayer) player;
		/**
		 * PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particleType, true, x, y, z, offsetX, offsetY, offsetZ, speed, amount);
		 */
		PacketPlayOutWorldParticles packet0 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 1, 0, 0, 1, particles);
		PacketPlayOutWorldParticles packet1 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 1, 1, 0, 1, particles);
		PacketPlayOutWorldParticles packet2 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 1, 0, 1, 1, particles);
		PacketPlayOutWorldParticles packet3 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 1, 1, 1, 1, particles);
		PacketPlayOutWorldParticles packet4 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0, 1, 1, 1, particles);
		PacketPlayOutWorldParticles packet5 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0, 1, 0, 1, particles);
		PacketPlayOutWorldParticles packet6 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0, 0, 1, 1, particles);
		
		PacketPlayOutWorldParticles packet7 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), -1, 0, 0, 1, particles);
		PacketPlayOutWorldParticles packet8 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), -1, -1, 0, 1, particles);
		PacketPlayOutWorldParticles packet9 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), -1, 0, -1, 1, particles);
		PacketPlayOutWorldParticles packet10 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), -1, -1, -1, 1, particles);
		PacketPlayOutWorldParticles packet11 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0, -1, -1, 1, particles);
		PacketPlayOutWorldParticles packet12 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0, -1, 0, 1, particles);
		PacketPlayOutWorldParticles packet13 = new PacketPlayOutWorldParticles(particle, true, location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0, 0, -1, 1, particles);
		
		craftPlayer.getHandle().playerConnection.sendPacket(packet0);
		craftPlayer.getHandle().playerConnection.sendPacket(packet1);
		craftPlayer.getHandle().playerConnection.sendPacket(packet2);
		craftPlayer.getHandle().playerConnection.sendPacket(packet3);
		craftPlayer.getHandle().playerConnection.sendPacket(packet4);
		craftPlayer.getHandle().playerConnection.sendPacket(packet5);
		craftPlayer.getHandle().playerConnection.sendPacket(packet6);
		
		craftPlayer.getHandle().playerConnection.sendPacket(packet7);
		craftPlayer.getHandle().playerConnection.sendPacket(packet8);
		craftPlayer.getHandle().playerConnection.sendPacket(packet9);
		craftPlayer.getHandle().playerConnection.sendPacket(packet10);
		craftPlayer.getHandle().playerConnection.sendPacket(packet11);
		craftPlayer.getHandle().playerConnection.sendPacket(packet12);
		craftPlayer.getHandle().playerConnection.sendPacket(packet13);
	}
	
	public void bounceBlock(Block b)
    {
        if(b == null) return;
        
        FallingBlock fb = b.getWorld()
                .spawnFallingBlock(b.getLocation(), b.getType(), b.getData());
        
        b.setType(Material.AIR);
        
        float x = (float) -1 + (float) (Math.random() * ((1 - -1) + 1));
        float y = 2;//(float) -5 + (float)(Math.random() * ((5 - -5) + 1));
        float z = (float) -0.3 + (float)(Math.random() * ((0.3 - -0.3) + 1));
        
        fb.setVelocity(new Vector(x, y, z));
    }
	
	
	public static void generateEmtptyWorld(String name, CommandSender sender){
		sender.sendMessage(ChatColor.GREEN+"Generiere eine leere Welt ("+name+")...");
		UCore.emtptyWorldsToGenerate.add(name);
		WorldCreator worldCreator = new WorldCreator(name);
		Bukkit.createWorld(worldCreator);
		sender.sendMessage(ChatColor.GREEN+"Die Welt '"+name+"'wurde generiert!");
	}
	
	public static void sendTitle(Player player, String mainTitle, String subTitle, ChatColor color, ChatColor subColor, int fadeInTime, int showTime, int fadeOutTime){
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + mainTitle + "\",color:" + color.name().toLowerCase() + "}");
		IChatBaseComponent sTitle = ChatSerializer.a("{\"text\": \"" + subTitle + "\",color:" + subColor.name().toLowerCase() + "}");

		PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		PacketPlayOutTitle stitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, sTitle);
		PacketPlayOutTitle length = new PacketPlayOutTitle(fadeInTime, showTime, fadeOutTime);

		((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(stitle);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(length);
	}
	
	public static String getBukkitPath() {
		String path = Bukkit.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		return path;
	}
	
	public static String getBukkitFileName() {
		String path = getBukkitPath();
		String[] split = path.split("/");
		return split[split.length-1];
	}
	
	public static String getBukkitEnvironment() {
		String path = getBukkitPath();
		String[] split = path.split("/");
		String file = split[split.length-1];
		return path.substring(0, path.length()-file.length());
	}
	
	public static void restartServerFromUnixShellScript(String shellScript){
		/**
		 * shellScript example: 'run-server.sh'
		 */
		/*ProcessBuilder pb = new ProcessBuilder(shellScript, "", "");
		pb.directory(new File(getBukkitEnvironment()));
		try {
			pb.start();
		} catch (IOException e) {
			System.out.print("Failed to run "+shellScript+" : "+e.getCause());
			e.printStackTrace();
		}*/
		String file = getBukkitEnvironment()+shellScript;
		try {
			Runtime.getRuntime().exec(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bukkit.getServer().shutdown();
	}
	
	public static void restartServerFromBatchFile(String batchFile){
		/**
		 * batchFile example: 'run-server.bat'
		 */
		try {
			Runtime.getRuntime().exec("cmd /c start "+batchFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Bukkit.getServer().shutdown();
	}
	
	public static void startServerFromUnixShellScript(String shellScript){
		/**
		 * shellScript example: 'run-server.sh'
		 */
		/*ProcessBuilder pb = new ProcessBuilder(shellScript);
		pb.directory(new File(Bukkit.class.getProtectionDomain().getCodeSource().getLocation().getFile()));
		try {
			pb.start();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		try {
			Runtime.getRuntime().exec(shellScript);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void startServerFromBatchFile(String batchFile){
		/**
		 * batchFile example: 'run-server.bat'
		 */
		try {
			Runtime.getRuntime().exec("cmd /c start "+batchFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
