package ru.enelson.total.autobroadcast.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import ru.enelson.total.autobroadcast.TotalAutoBroadcast;

public class BroadcastManager {
	List<BMessage> messages;
	
	public BroadcastManager() {
		this.messages = new ArrayList<BMessage>();
		
		for(String group : TotalAutoBroadcast.config.getConfigurationSection("groups").getKeys(false)) {
			BMessage bmessage = new BMessage(group, TotalAutoBroadcast.config.getConfigurationSection("groups."+group));
			this.messages.add(bmessage);
			int taskNumber = Bukkit.getScheduler().scheduleSyncRepeatingTask(TotalAutoBroadcast.plugin,() -> {
				bmessage.getReceivers().forEach(p -> {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',PlaceholderAPI.setPlaceholders(p, bmessage.getCurrentMessage().getText())));
					if(bmessage.getSound()!=null) {
						p.playSound(p, bmessage.getSound(), bmessage.getSoundVolume(), bmessage.getSoundPitch());
					}
				});
			}, bmessage.getInterval()*20, bmessage.getInterval()*20);
			bmessage.setTaskNumber(taskNumber);
		}
	}
	
	public void deInit() {
		this.messages.forEach(bmessage -> Bukkit.getScheduler().cancelTask(bmessage.getTasknumber()));
		this.messages.clear();
	}
}
