package me.cjCommands.main;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PlayerPointLocations {
	Player player;
	String playerName;
	private Location location1;
	private Location location2;
	private boolean location1Set;
	private boolean location2Set;

	public PlayerPointLocations(Player player) {
		this.player = player;
		playerName = player.getName();
		location1Set = false;
		location2Set = false;
	}

	public void setLocation1(Location location) {
		location1 = location.clone();
		location1Set = true;
	}

	public void setLocation2(Location location) {
		location2 = location.clone();
		location2Set = true;
	}

	public boolean location1Set() {
		return location1Set;
	}

	public boolean location2Set() {
		return location2Set;
	}

	public String getPlayerName() {
		return playerName;
	}

	public Location getLocation1() {
		return location1;
	}

	public Location getLocation2() {
		return location2;
	}

	public int getDistanceInt() {
		return (int) Math.sqrt(Math.pow(location2.getX() - location1.getX(), 2) + Math.pow(location2.getY() - location1.getY(), 2) + Math.pow(location2.getZ() - location1.getZ(), 2));
	}

	public double getDistanceDouble() {
		return Math.sqrt(Math.pow(location2.getX() - location1.getX(), 2) + Math.pow(location2.getY() - location1.getY(), 2) + Math.pow(location2.getZ() - location1.getZ(), 2));
	}
}
