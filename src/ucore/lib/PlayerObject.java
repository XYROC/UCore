package ucore.lib;

import java.util.UUID;

public class PlayerObject {
	
	private UUID uuid;
	
	public PlayerObject(UUID uuid) {
		this.uuid = uuid;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	

}
