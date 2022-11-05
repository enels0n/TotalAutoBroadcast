package ru.enelson.total.autobroadcast.utils;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class Utils {
	public static boolean checkRequire(Player player, String type, String input, String output) {
		if(input!=null)
			input = PlaceholderAPI.setPlaceholders(player, input);
		if(output!=null)
			output = PlaceholderAPI.setPlaceholders(player, output);
		final String input1 = input;
		
		switch(type) {
		case("=="):
			return input.equals(output);
		case("!="):
			return !input.equals(output);
		case(">"):
			return Double.parseDouble(input) > Double.parseDouble(output);
		case(">="):
			return Double.parseDouble(input) >= Double.parseDouble(output);
		case("<"):
			return Double.parseDouble(input) < Double.parseDouble(output);
		case("<="):
			return Double.parseDouble(input) <= Double.parseDouble(output);
		case("has perm"):
			return (!player.isPermissionSet(input) || 
					!player.getEffectivePermissions().stream()
					.filter(pai -> pai.getPermission().equals(input1)).findFirst().get().getValue());
		case("!has perm"):
			return (!(player.isPermissionSet(input) && 
					player.getEffectivePermissions().stream()
					.filter(pai -> pai.getPermission().equals(input1)).findFirst().get().getValue()));
		default:
			return false;
		}
	}
}
