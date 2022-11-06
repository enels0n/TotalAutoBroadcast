package ru.enelson.total.autobroadcast.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import org.bukkit.Sound;

public class BMessage {
	private int taskNumber;
	private int interval;
	private List<BMessageLine> lines;
	private String permission;
	private int currentMessage;
	private List<String> worlds;
	private Sound sound;
	private float soundVolume;
	private float soundPitch;
	
	BMessage(String group, ConfigurationSection config) {
		this.currentMessage = 0;
		this.taskNumber = 0;
		this.interval = config.getInt("interval");
		this.permission = "totalautobroadcast.group."+group;
		this.worlds = config.getStringList("enabled-worlds");
		this.lines = new ArrayList<BMessageLine>();
		for(String line : config.getConfigurationSection("messages").getKeys(false)) {
			BMessageLine bmline = new BMessageLine(
					config.getString("messages."+line+".type"),
					config.getString("messages."+line+".text"),
					config.getConfigurationSection("messages."+line+".requirements"));
			this.lines.add(bmline);
		}
		
		if(config.get("sound")!=null) {
			this.sound = Sound.valueOf(config.getString("sound.name"));
			this.soundVolume = (float)config.getDouble("sound.volume");
			this.soundPitch = (float)config.getDouble("sound.pitch");
		}
	}
	
	public void setTaskNumber(int taskNumber) {
		this.taskNumber = taskNumber;
	}
	
	public BMessageLine getCurrentMessage() {
		return this.lines.get(this.currentMessage);
	}
	
	public void nextMessage() {
		//test
		this.currentMessage = (this.lines.size()==this.currentMessage+1) ? 0 : this.currentMessage+1;
	}
	
	public List<String> getEnabledWorlds() {
		return this.worlds;
	}
	
	public String getPermission() {
		return this.permission;
	}
	
	public int getTasknumber() {
		return this.taskNumber;
	}
	
	public List<Player> getReceivers() {
		List<Player> receivers  = new ArrayList<Player>();
		BMessageLine bmline = this.lines.get(this.currentMessage);
		for(Player player : Bukkit.getOnlinePlayers()) {
			
			if(!this.worlds.contains(player.getWorld().getName()))
				continue;
			
			if(!player.isPermissionSet(this.permission) || 
					!player.getEffectivePermissions().stream()
					.filter(pai -> pai.getPermission().equals(this.permission)).findFirst().get().getValue())
				continue;
			
			if(!bmline.checkRequires(player))
				continue;
			
			receivers.add(player);
		}
		return receivers;
	}
	
	public int getInterval() {
		return this.interval;
	}
	
	public Sound getSound() {
		return this.sound;
	}
	
	public float getSoundVolume() {
		return this.soundVolume;
	}
	
	public float getSoundPitch() {
		return this.soundPitch;
	}
}
