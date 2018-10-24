package me.cjCommands.main;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Keeps track of the players and the points on the map they set
 * 
 * @author CJ
 *
 */
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

	/**
	 * Sets the first point the player chooses
	 * 
	 * @param Players location
	 */
	public void setLocation1(Location location) {
		location1 = location.clone();
		location1Set = true;
	}

	/**
	 * Sets the second point the player chooses
	 * 
	 * @param Players location
	 */
	public void setLocation2(Location location) {
		location2 = location.clone();
		location2Set = true;
	}

	/**
	 * Checks if the player has set a first point
	 * 
	 * @return
	 */
	public boolean location1Set() {
		return location1Set;
	}

	/**
	 * Checks if the player has set a second point
	 * 
	 * @return
	 */
	public boolean location2Set() {
		return location2Set;
	}

	/**
	 * Returns the players name
	 * 
	 * @return
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Gets the first location
	 * 
	 * @return
	 */
	public Location getLocation1() {
		return location1;
	}

	/**
	 * Gets the second location
	 * 
	 * @return
	 */
	public Location getLocation2() {
		return location2;
	}

	/**
	 * Returns the distance between the 2 set points cast to an int
	 * 
	 * @return
	 */
	public int getDistanceInt() {
		return (int) Math.sqrt(Math.pow(location2.getX() - location1.getX(), 2)
				+ Math.pow(location2.getY() - location1.getY(), 2) + Math.pow(location2.getZ() - location1.getZ(), 2));
	}

	/**
	 * Returns the distance between the 2 set points
	 * 
	 * @return
	 */
	public double getDistanceDouble() {
		return Math.sqrt(Math.pow(location2.getX() - location1.getX(), 2)
				+ Math.pow(location2.getY() - location1.getY(), 2) + Math.pow(location2.getZ() - location1.getZ(), 2));
	}
}
