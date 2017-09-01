package ucore.lib;

import java.util.ArrayList;

public class Permission extends ArrayList<PlayerObject>{
	
	private static final long serialVersionUID = 1L;
	private String name;
	
	public Permission(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPath(){
		return "plugins//UCore//Data//Permissions//"+name+".txt";
	}

}
