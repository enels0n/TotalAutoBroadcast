package ru.enelson.total.autobroadcast;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ru.enelson.total.autobroadcast.manager.BroadcastManager;

public class TotalAutoBroadcast extends JavaPlugin {
	public static BroadcastManager bm;
	public static Plugin plugin;
	public static FileConfiguration config;
	public static File fileConfig;
	
	public void onEnable() {
		TotalAutoBroadcast.plugin = this;
		
		fileConfig = new File(getDataFolder(), "config.yml");
		if(!fileConfig.exists()) saveResource("config.yml", true);
		config = YamlConfiguration.loadConfiguration(fileConfig);
		
		TotalAutoBroadcast.bm = new BroadcastManager();
	}
	
	public void onDisable() {
		bm.deInit();
	}
}
