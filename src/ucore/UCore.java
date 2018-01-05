package ucore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import ucore.command.CommandUCore;
import ucore.lib.Data;
import ucore.lib.ItemGen;
import ucore.lib.Listeners;
import ucore.lib.PlayerObject;
import ucore.world.ChunkGeneratorEmpty;

public class UCore extends JavaPlugin {
	
	public static final ItemGen ITEMGEN = new ItemGen();

	public static ArrayList<String> emtptyWorldsToGenerate;
	
	private static Data data;

	@Override
	public void onEnable() {
		loadData();
		loadConfiguration();
		loadCommands();
		new Listeners(this);
		emtptyWorldsToGenerate = new ArrayList<String>();
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		System.out.print(worldName+"|"+id);
		if (emtptyWorldsToGenerate.contains(worldName)) {
			emtptyWorldsToGenerate.remove(worldName);
			return new ChunkGeneratorEmpty();
		}
		return super.getDefaultWorldGenerator(worldName, id);
	}

	public static boolean hasPlayerPermission(PlayerObject playerObject, String permission) {
		return false;
	}

	/*private void loadPermissions() {
		System.out.println("[UCore] Loading permissions...");
		File dir = new File("plugins//UCore//Data//Permissions");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File("plugins//UCore//Data//Permissions//permissions.txt");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		}
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String s = reader.readLine();
			while (s != null) {
				String path = s;
				File lib = new File(path);
				if (file.exists()) {
					System.out.println("Loading " + path);
					try {
						BufferedReader r = new BufferedReader(new FileReader(lib));
						String line = reader.readLine();
						Permission p = new Permission("");
						while (line != null) {
							if (line.startsWith("name=")) {
								String name = line.split("=")[1];
								p = new Permission(name);
							} else {
								p.add(new PlayerObject(UUID.fromString(line)));
							}
						}
						r.close();
						permissionHandler.addPermission(p.getName(), p);
					} catch (Exception e) {
						System.err.println("Failed to read " + path);
						e.printStackTrace();
					}
				}
				s = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void savePermissions() {
		System.out.println("[UCore] Saving permissions...");
		try {
			File lib = new File("plugins//UCore//Data//Permissions//permissions.txt");
			for (Permission p : permissionHandler.getPermissions()) {
				File file = new File(p.getPath());
				if (!file.exists()) {
					BufferedWriter writer = new BufferedWriter(new FileWriter(file));
					file.createNewFile();
					writer.write("name=" + p.getName() + "\n");
					for (PlayerObject obj : p) {
						writer.write(obj.getUuid().toString() + "\n");
					}
					writer.close();
				} else {
					BufferedWriter writer = new BufferedWriter(new FileWriter(file));
					BufferedReader reader = new BufferedReader(new FileReader(file));
					while (reader.readLine() != null) {
						writer.flush();
					}
					for (PlayerObject obj : p) {
						writer.write(obj.getUuid().toString() + "\n");
					}
					reader.close();
					writer.close();
				}
				if (!lib.exists()) {
					lib.createNewFile();
				}
				BufferedReader libReader = new BufferedReader(new FileReader(lib));
				BufferedWriter libWriter = new BufferedWriter(new FileWriter(lib));
				while (libReader.readLine() != null) {
					libWriter.flush();
				}
				for (Permission permission : permissionHandler.getPermissions()) {
					libWriter.write(permission.getPath() + "\n");
				}
				libReader.close();
				libWriter.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	private void loadCommands() {
		if(YamlConfiguration.loadConfiguration(new File("plugins//UCore//config.yml")).getBoolean("UCore.enableCommands")) {
			CommandUCore commandUCore = new CommandUCore();
			getCommand("utilitycore").setExecutor(commandUCore);
		}
	}
	
	private void loadConfiguration() {
		File file = new File("plugins//UCore//config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		if(!file.exists()) {
			config.set("UCore.enableCommands", false);
			config.set("script.runServer", "");
			data.getList("config").put("enableCommands", config.getBoolean("enableCommands"));
			data.getList("config").put("script.runServer", config.getString("script.runServer"));
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		data.getList("config").put("enableCommands", config.getBoolean("enableCommands"));
		data.getList("config").put("script.runServer", config.getString("script.runServer"));
	}
	
	public void loadData() {
		data = new Data();
		data.appendList("config", new HashMap<String,Object>());
		// data.appendList("lang_de", new HashMap<String, Object>());
		// data.appendList("lang_en", new HashMap<String, Object>());
	}
	
	public static Data getData() {
		return data;
	}

}
