package ru.enelson.total.autobroadcast.utils;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class Utils {
	public static boolean checkRequire(Player player, String type, String input, String output) {
		input = PlaceholderAPI.setPlaceholders(player, input);
		output = PlaceholderAPI.setPlaceholders(player, output);

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
		default:
			return false;
		}
	}
}
