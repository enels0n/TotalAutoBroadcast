package ru.enelson.total.autobroadcast.manager;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import ru.enelson.total.autobroadcast.utils.Utils;

public class BMessageLine {
	private String type;
	private String text;
	private ConfigurationSection requirements;
	
	BMessageLine(String type, String text, ConfigurationSection requirements) {
		this.type = type;
		this.text = text;
		this.requirements = requirements;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getText() {
		return this.text;
	}
	
	public ConfigurationSection getRequires() {
		return this.requirements;
	}
	
	public boolean checkRequires(Player player) {
		if(this.requirements==null) return true;
		for(String st : this.requirements.getConfigurationSection("").getKeys(false)) {
			if(!Utils.checkRequire(player, this.requirements.getString(st+".type"), this.requirements.getString(st+".input"), this.requirements.getString(st+".output")))
				return false;
		}
		
		return true;
	}
}
