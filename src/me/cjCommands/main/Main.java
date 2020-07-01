package me.cjCommands.main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import Tools.MyFuncs;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	PlayerPoints playerPoints;

	public Main() {
		playerPoints = new PlayerPoints();
	}

//	@Override
//	public void onEnable() {
//		getConfig().options().copyDefaults(true);
//		saveConfig();
//	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length == 0) {
			sender.sendMessage(ChatColor.RED + "No arguements");
		} else {
			switch (args[0].toLowerCase()) {
			case "randomtp":
				randomTeleportCommand(sender);
				break;
			case "getloc":
				getPlayerLocation(sender, args);
				break;
			case "getlocation":
				getPlayerLocation(sender, args);
				break;
			case "gamemode":
				setGamemode(sender, args);
				break;
			case "gm":
				setGamemode(sender, args);
				break;
			case "setpoint":
				setPoint(sender, args);
				break;
			case "getpoints":
				getPoints(sender, args);
				break;
			case "getdist":
				getPointDistance(sender, args, true);
				break;
			case "getdistd":
				getPointDistance(sender, args, false);
				break;
			case "distto":
				distanceToPlayer(sender, args);
				break;
			case "help":
				helpMenu(sender, args);
				break;
			case "slay":
				slay(sender, args);
				break;
			case "teleportbehind":
				teleportBehind(sender, args);
				break;
			case "tp":
				teleport(sender, args);
				break;
			case "removeitemmeta":
				removeItemMeta(sender, args);
				break;
			default:
				break;
			}
		}

		return true;
	}

	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("c")) {
			ArrayList<String> choices = new ArrayList<String>();
			if (args.length == 1) {
				String arg = args[0].toLowerCase();
				if ("randomTP".startsWith(arg)) {
					choices.add("randomTP");
				}

				if ("getlocation".startsWith(arg)) {
					choices.add("getlocation");
				}

				if ("gamemode".startsWith(arg)) {
					choices.add("gamemode");
				}

				if ("setpoint".startsWith(arg)) {
					choices.add("setpoint");
				}

				if ("getpoints".startsWith(arg)) {
					choices.add("getpoints");
				}

				if ("getdist".startsWith(arg)) {
					choices.add("getdist");
				}

				if ("distto".startsWith(arg)) {
					choices.add("distto");
				}

				if ("help".startsWith(arg)) {
					choices.add("help");
				}

				if ("slay".startsWith(arg)) {
					choices.add("slay");
				}

				if ("teleportbehind".startsWith(arg)) {
					choices.add("teleportBehind");
				}

				if ("tp".startsWith(arg)) {
					choices.add("tp");
				}

				if ("removeitemmeta".startsWith(arg)) {
					choices.add("removeItemMeta");
				}
			} else if (args.length == 2) {
				String arg = args[0].toLowerCase();
				consoleMessage(arg);
				if (arg.equals("gamemode") || arg.equals("gm")) {
					arg = args[1].toLowerCase();
					if ("adventure".startsWith(arg)) {
						choices.add("adventure");
					}

					if ("creative".startsWith(arg)) {
						choices.add("creative");
					}

					if ("spectator".startsWith(arg)) {
						choices.add("spectator");
					}

					if ("survival".startsWith(arg)) {
						choices.add("survival");
					}
				} else if (arg.equals("setpoint")) {
					arg = args[1].toLowerCase();
					if ("1".startsWith(arg)) {
						choices.add("1");
					}

					if ("2".startsWith(arg)) {
						choices.add("2");
					}
				}
			}
			Collections.sort(choices);
			if (choices.size() > 0) {
				return choices;
			}
		}

		return null;
	}

	private void removeItemMeta(CommandSender sender, String[] args) {
		// Checks if the sender is a player
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Must be a player to teleport");
			return;
		}

		// Checks if the sender had permission to run this command
		if (!sender.hasPermission("CJCommands.removeItemMeta")) {
			noPermission(sender);
			return;
		}

		Player player = (Player) sender;

		Material material = player.getInventory().getItemInMainHand().getType();
		int amount = player.getInventory().getItemInMainHand().getAmount();
		ItemStack newItemStack = new ItemStack(material, amount);

		int damage = 0;
		// If item is damaged
		if (((Damageable) player.getInventory().getItemInMainHand().getItemMeta()).getDamage() != 0) {
			damage = ((Damageable) player.getInventory().getItemInMainHand().getItemMeta()).getDamage();
		}

		if (damage != 0) {
			ItemMeta meta = newItemStack.getItemMeta();
			((Damageable) meta).setDamage(damage);
			newItemStack.setItemMeta(meta);
		}

		player.getInventory().setItemInMainHand(newItemStack);
	}

	public void consoleMessage(String message) {
		getLogger().info(message);
	}

	private void teleport(CommandSender sender, String[] args) {
		if (args.length == 2) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Must be a player to run this command");
				return;
			} else if (!sender.hasPermission("CJCommands.teleport")) {
				noPermission(sender);
				return;
			}

			Player player = (Player) sender;
			Player toPlayer = getServer().getPlayer(args[1]);

			if (toPlayer != null) {
				player.teleport(toPlayer);
			}
		} else if (args.length == 3) {
			if (!sender.hasPermission("CJCommands.teleportothers")) {
				noPermission(sender);
				return;
			}

			Player player1 = getServer().getPlayer(args[1]);
			Player player2 = getServer().getPlayer(args[2]);

			if (player1 == null) {
				sender.sendMessage(args[1] + " does not exist in this server");
				return;
			} else if (player2 == null) {
				sender.sendMessage(args[2] + " does not exist in this server");
				return;
			}

			player1.teleport(player2);
		} else {
			sender.sendMessage(ChatColor.RED + "Invalid number of arguements");
		}
	}

	/**
	 * Teleports a player to the block behind another player
	 * 
	 * @param sender
	 * @param args
	 */
	private void teleportBehind(CommandSender sender, String[] args) {
		if (args.length == 2) {
			// Checks if the sender is a player
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED + "Must be a player to teleport");
				return;
			}

			// Checks if the sender had permission to run this command
			if (!sender.hasPermission("CJCommands.teleportbehind")) {
				noPermission(sender);
				return;
			}

			Player senderPlayer = (Player) sender;
			Player toPlayer = getServer().getPlayer(args[1]);

			if (toPlayer == null) {
				sender.sendMessage(ChatColor.RED + "\"" + args[1] + "\" does not exist in this server");
				return;
			}

			senderPlayer.teleport(getBehindLocation(toPlayer));
			senderPlayer.sendMessage("Teleported behind " + toPlayer.getName());
		} else if (args.length == 3) {
			// Checks if the sender had permission to run this command
			if (!sender.hasPermission("CJCommands.teleportbehindothers")) {
				noPermission(sender);
				return;
			}

			Player fromPlayer = getServer().getPlayer(args[1]);
			Player toPlayer = getServer().getPlayer(args[2]);

			if (fromPlayer == null && toPlayer == null) {
				sender.sendMessage(ChatColor.RED + "Neither player exists in this server");
				return;
			} else if (fromPlayer == null) {
				sender.sendMessage(ChatColor.RED + "\"" + args[1] + "\" does not exist in this server");
				return;
			} else if (toPlayer == null) {
				sender.sendMessage(ChatColor.RED + "\"" + args[2] + "\" does not exist in this server");
				return;
			}

			fromPlayer.teleport(getBehindLocation(toPlayer));
			sender.sendMessage("Teleported " + fromPlayer.getName() + " behind " + toPlayer.getName());
		}
	}

	/**
	 * Gets the location 1 block behind a player
	 * 
	 * @param player
	 * @return Location behind the player
	 */
	private Location getBehindLocation(Player player) {
		Location behindLocation = player.getLocation();

		switch (player.getFacing()) {
		case NORTH:
			behindLocation.setZ(behindLocation.getZ() + 1);
			break;
		case SOUTH:
			behindLocation.setZ(behindLocation.getZ() - 1);
			break;
		case EAST:
			behindLocation.setX(behindLocation.getX() - 1);
			break;
		case WEST:
			behindLocation.setX(behindLocation.getX() + 1);
			break;
		default:
			break;
		}

		behindLocation.setPitch(0);

		return behindLocation;
	}

	/**
	 * Kills player
	 * 
	 * @param sender
	 * @param args
	 */
	private void slay(CommandSender sender, String[] args) {
		if (!sender.hasPermission("CJCommands.slay")) {
			noPermission(sender);
			return;
		}

		if (args.length != 2) {
			sender.sendMessage(ChatColor.RED + "Invalid number of arguements");
			return;
		}

		Player victim = getServer().getPlayer(args[1]);

		if (victim == null || !victim.getName().equalsIgnoreCase(args[1])) {
			sender.sendMessage(ChatColor.RED + "Player is not in this server");
			return;
		}

		victim.setHealth(0);
	}

	/**
	 * Displays a help menu to the command sender
	 * 
	 * @param sender
	 * @param args
	 */
	private void helpMenu(CommandSender sender, String args[]) {
		String helpMenu = "";

		if (args.length == 1 || args[1].equals("1")) {
			helpMenu += ChatColor.GREEN + "Commands\n";
			helpMenu += ChatColor.GREEN + "RandomTP: " + ChatColor.WHITE + "Teleports you to a random location\n";
			helpMenu += ChatColor.GREEN + "GetPoints: " + ChatColor.WHITE + "Shows you your set points\n";
			helpMenu += ChatColor.GREEN + "GetLocation <Player name>: " + ChatColor.WHITE + "Gets a players location\n"
					+ ChatColor.GREEN + "  Can also use\n  GetLoc <Player name>\n";
			helpMenu += ChatColor.GREEN + "GameMode: <Gamemode number> [Player name]: " + ChatColor.WHITE
					+ "Changes players gamemode\n" + ChatColor.GREEN
					+ "  Can also use\n  GM <Gamemode number> [Player name]" + "\n";
			helpMenu += ChatColor.GREEN + "SetPoint <Point number>: " + ChatColor.WHITE + "Sets the point location\n";
			helpMenu += ChatColor.GREEN + "GetDist: " + ChatColor.WHITE
					+ "Shows the distance between the 2 set points\n";
			helpMenu += ChatColor.GREEN + "GetDistD: " + ChatColor.WHITE
					+ "Shows the distance between the 2 set points with decimal points\n";
		} else if (args[1].equals("2")) {
			helpMenu += ChatColor.GREEN + "Commands Page 2\n";
			helpMenu += ChatColor.GREEN + "SetPoint <Point number>: " + ChatColor.WHITE + "Sets the point location\n";
			helpMenu += ChatColor.GREEN + "GetDist: " + ChatColor.WHITE
					+ "Shows the distance between the 2 set points\n";
			helpMenu += ChatColor.GREEN + "GetDistD: " + ChatColor.WHITE
					+ "Shows the distance between the 2 set points with decimal points\n";
			helpMenu += ChatColor.GREEN + "DistTo <Player name> [Player name]: " + ChatColor.WHITE
					+ "Shows distance between 2 players\n";
			helpMenu += ChatColor.GREEN + "DistToD <Player name> [Player name]: " + ChatColor.WHITE
					+ "Shows distance between 2 players with decimal points\n";
			helpMenu += ChatColor.GREEN + "TP <player1> [player2]: " + ChatColor.WHITE
					+ "Teleport to a player, or a player to a player";
		}

		sender.sendMessage(helpMenu);

	}

	/**
	 * Returns the 2 points the player set to the command sender
	 * 
	 * @param sender
	 * @param args
	 */
	private void getPoints(CommandSender sender, String[] args) {
		if (!sender.hasPermission("CJCommands.getpoints")) {
			noPermission(sender);
			return;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage("Must be a player to run this command");
		}

		Player player = (Player) sender;
		PlayerPointLocations ppl = playerPoints.getPlayer(player.getName());

		if (ppl == null) {
			player.sendMessage(ChatColor.RED + "You haven't set any points");
			return;
		} else if (ppl.location1Set() && ppl.location2Set()) {
			player.sendMessage("Point 1 - XYZ: " + formatLocation(ppl.getLocation1(), 3) + ChatColor.WHITE
					+ "\nPoint 2 - XYZ: " + formatLocation(ppl.getLocation2(), 3));
		} else if (ppl.location1Set() == false && ppl.location2Set()) {
			player.sendMessage("Point 1 not set\n" + "Point 2 - XYZ: " + formatLocation(ppl.getLocation2(), 3));
		} else if (ppl.location1Set() && ppl.location2Set() == false) {
			player.sendMessage(
					"Point 1 - XYZ: " + formatLocation(ppl.getLocation1(), 3) + ChatColor.WHITE + "\nPoint 2 not set");
		}

	}

	/**
	 * Gets the distance between 2 players and sends it to the command sender
	 * 
	 * @param sender
	 * @param args
	 * @param sendAsInt
	 */
	private void distanceToPlayer(CommandSender sender, String[] args) {
		if (!sender.hasPermission("CJCommands.distto")) {
			noPermission(sender);
			return;
		}

		Player player1 = null;
		Player player2 = null;
		double distance;

		if (!(sender instanceof Player)) {
			sender.sendMessage("Must be a player to run this command");
			return;
		} else if (args.length != 2 && args.length != 3) {
			sender.sendMessage(ChatColor.RED + "Invalid number of arguements");
			return;
		} else if (args.length == 2) {
			player1 = (Player) sender;
			player2 = getServer().getPlayer(args[1]);

			if (player2 == null) {
				sender.sendMessage(ChatColor.RED + "Player is not in this server");
				return;
			}

			if (!player1.getLocation().getWorld().equals(player2.getLocation().getWorld())) {
				sender.sendMessage(ChatColor.RED + "Player is in a different world, cannot calculate distance");
				return;
			}
		} else if (args.length == 3) {
			player1 = getServer().getPlayer(args[1]);
			player2 = getServer().getPlayer(args[2]);

			if (player1 == null && player2 == null) {
				sender.sendMessage(ChatColor.RED + "Neither player is in this server");
				return;
			} else if (player1 == null) {
				sender.sendMessage(ChatColor.RED + args[1] + " is not in this server");
				return;
			} else if (player2 == null) {
				sender.sendMessage(ChatColor.RED + args[2] + " is not in this server");
				return;
			}

			if (!player1.getLocation().getWorld().equals(player2.getLocation().getWorld())) {
				sender.sendMessage(ChatColor.RED + "Players are in different worlds, cannot calculate distance");
				return;
			}
		}

		distance = getDistance(player1.getLocation(), player2.getLocation());

		if (args.length == 2) {
			DecimalFormat dp = new DecimalFormat("#.##");
			sender.sendMessage(player2.getName() + " is " + dp.format(distance) + " blocks away");
		} else {
			DecimalFormat dp = new DecimalFormat("#.##");
			sender.sendMessage(
					player1.getName() + " is " + dp.format(distance) + " blocks away from " + player2.getName());
		}

	}

	/**
	 * Gets the distance between 2 Locations
	 * 
	 * @param location1
	 * @param location2
	 * @return
	 */
	private double getDistance(Location location1, Location location2) {
		return Math.sqrt(Math.pow(location2.getX() - location1.getX(), 2)
				+ Math.pow(location2.getY() - location1.getY(), 2) + Math.pow(location2.getZ() - location1.getZ(), 2));
	}

	/**
	 * Gets the distance between the 2 points the player set
	 * 
	 * @param sender
	 * @param args
	 * @param sendAsInt
	 */
	private void getPointDistance(CommandSender sender, String[] args, boolean sendAsInt) {
		if (!sender.hasPermission("CJCommands.getpointdistance")) {
			noPermission(sender);
			return;
		}

		if (sender instanceof Player) {
			if (args.length == 1) {
				Player player = (Player) sender;
				PlayerPointLocations ppl = playerPoints.getPlayer(player.getName());

				if (ppl == null) {
					sender.sendMessage(ChatColor.RED + "No points set");
					return;
				} else if (!ppl.location1Set()) {
					sender.sendMessage(ChatColor.RED + "Point 1 not set");
					return;
				} else if (!ppl.location2Set()) {
					sender.sendMessage(ChatColor.RED + "Point 2 not set");
					return;
				}

				if (!ppl.getLocation1().getWorld().equals(ppl.getLocation2().getWorld())) {
					sender.sendMessage(
							ChatColor.RED + "The 2 points are in different worlds, cannot calculate distance");
					return;
				}

				DecimalFormat dp = new DecimalFormat(".###");

				if (sendAsInt) {
					sender.sendMessage(ChatColor.WHITE + "Distance: " + ppl.getDistanceInt());
				} else {
					sender.sendMessage("Distance " + dp.format(ppl.getDistanceDouble()));
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Invalid number of arguements");
				return;
			}
		} else {
			sender.sendMessage("Must be a player to run this command");
			return;
		}
	}

	/**
	 * Sets one of the players saved points
	 * 
	 * @param sender
	 * @param args
	 */
	private void setPoint(CommandSender sender, String[] args) {
		if (!sender.hasPermission("CJCommands.setpoint")) {
			noPermission(sender);
			return;
		}

		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 2) {
				double point = -1;
				try {
					point = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					sender.sendMessage(ChatColor.RED + "Invalid number. Must be 1 or 2");
					return;
				}

				if (point == 1 || point == 2) {
					PlayerPointLocations ppl = playerPoints.getPlayer(player.getName());
					if (ppl == null) {
						playerPoints.add(new PlayerPointLocations(player));
						ppl = playerPoints.getPlayer(player.getName());
					}

					if (point == 1) {
						ppl.setLocation1(player.getLocation());
						player.sendMessage("Set point 1 to XYZ: " + formatLocation(ppl.getLocation1(), 3));
					} else {
						ppl.setLocation2(player.getLocation());
						player.sendMessage("Set point 2 to XYZ: " + formatLocation(ppl.getLocation2(), 3));
					}
				} else {
					sender.sendMessage(ChatColor.RED + "Invalid number. Must be 1 or 2");
					return;
				}
			} else {
				sender.sendMessage(ChatColor.RED + "Invalid number of arguements");
				return;
			}
		} else {
			sender.sendMessage("Must be a player");
		}
	}

	/**
	 * Sets the gamemode of a player
	 * 
	 * @param sender
	 * @param args
	 */
	private void setGamemode(CommandSender sender, String[] args) {

		Player player;
		String messageBeginning;

		if (args.length == 2) { // Setting own gamemode
			if (!sender.hasPermission("CJCommands.setgamemode")) {
				noPermission(sender);
				return;
			}
			if (sender instanceof Player) {
				player = (Player) sender;
				String mode = args[1].toLowerCase();
				if (mode.equals("creative")) {
					player.setGameMode(GameMode.CREATIVE);
					player.sendMessage(ChatColor.WHITE + "Set gamemode to " + ChatColor.YELLOW + GameMode.CREATIVE);
				} else if (mode.equals("survival")) {
					player.setGameMode(GameMode.SURVIVAL);
					player.sendMessage(ChatColor.WHITE + "Set gamemode to " + ChatColor.YELLOW + GameMode.SURVIVAL);
				} else if (mode.equals("spectator")) {
					player.setGameMode(GameMode.SPECTATOR);
					player.sendMessage(ChatColor.WHITE + "Set gamemode to " + ChatColor.YELLOW + GameMode.SPECTATOR);
				} else if (mode.equals("adventure")) {
					player.setGameMode(GameMode.ADVENTURE);
					player.sendMessage(ChatColor.WHITE + "Set gamemode to " + ChatColor.YELLOW + GameMode.ADVENTURE);
				}
			} else {
				sender.sendMessage("Must be a player to change gamemodes");
				return;
			}
		} else if (args.length == 3) { // Setting another players gamemode
			if (!sender.hasPermission("CJCommands.setgamemodeotherplayer")) {
				noPermission(sender);
				return;
			}
			player = getServer().getPlayer(args[2]);
			if (player != null && player.isOnline()) {

				// Grammar
				if (args[2].charAt(args[2].length() - 1) == 's') {
					messageBeginning = "Set " + args[2] + "' gamemode to ";
				} else {
					messageBeginning = "Set " + args[2] + "'s gamemode to ";
				}

				String mode = args[1].toLowerCase();
				if (mode.equals("creative")) {
					player.setGameMode(GameMode.CREATIVE);
					player.sendMessage(ChatColor.WHITE + messageBeginning + ChatColor.YELLOW + GameMode.CREATIVE);
				} else if (mode.equals("survival")) {
					player.setGameMode(GameMode.SURVIVAL);
					player.sendMessage(ChatColor.WHITE + messageBeginning + ChatColor.YELLOW + GameMode.SURVIVAL);
				} else if (mode.equals("spectator")) {
					player.setGameMode(GameMode.SPECTATOR);
					player.sendMessage(ChatColor.WHITE + messageBeginning + ChatColor.YELLOW + GameMode.SPECTATOR);
				} else if (mode.equals("adventure")) {
					player.setGameMode(GameMode.ADVENTURE);
					player.sendMessage(ChatColor.WHITE + messageBeginning + ChatColor.YELLOW + GameMode.ADVENTURE);
				}
			} else {
				sender.sendMessage(ChatColor.RED + args[2] + " is not in the server");
				return;
			}
		} else {
			sender.sendMessage(ChatColor.RED + "Invalid number of arguements");
			return;
		}
	}

	/**
	 * Returns the location of a player to the command sender
	 * 
	 * @param sender
	 * @param args
	 */
	private void getPlayerLocation(CommandSender sender, String[] args) {
		if (!sender.hasPermission("CJCommands.getplayerlocation")) {
			noPermission(sender);
			return;
		}

		if (args.length != 2) {
			sender.sendMessage(ChatColor.RED + "Invalid number of arguments");
		} else {
			Player player = getServer().getPlayer(args[1]);

			if (player != null && player.isOnline()) {
				sender.sendMessage(formatLocation(player.getLocation(), 3));
			} else {
				sender.sendMessage(ChatColor.RED + args[1] + " is not in the server");
			}
		}
	}

	/**
	 * Takes a Location and formats it to a string X / Y / Z
	 * 
	 * @param location
	 * @param decimalPoints Number of decimal points each number will be
	 * @return
	 */
	private String formatLocation(Location location, int decimalPoints) {
		String decimalFormat = ".";

		for (int i = 0; i < decimalPoints; i++) {
			decimalFormat += "#";
		}

		DecimalFormat dp = new DecimalFormat(decimalFormat);

		return ChatColor.YELLOW + location.getWorld().getName() + ChatColor.WHITE + " / " + ChatColor.RED
				+ dp.format(location.getX()) + ChatColor.WHITE + " / " + ChatColor.GREEN + dp.format(location.getY())
				+ ChatColor.WHITE + " / " + ChatColor.BLUE + dp.format(location.getZ());
	}

	/**
	 * Teleports the command sender to a random location in the world they're in
	 * 
	 * @param sender
	 */
	private void randomTeleportCommand(CommandSender sender) {
		if (!sender.hasPermission("CJCommands.randomtp")) {
			noPermission(sender);
			return;
		}

		if (sender instanceof Player) {
			Player player = (Player) sender;
			Location newLocation = new Location(player.getWorld(), MyFuncs.Random.randomIntBetween(-29999983, 29999983),
					0, MyFuncs.Random.randomIntBetween(-29999983, 29999983));
			newLocation.setY(
					newLocation.getWorld().getHighestBlockAt(newLocation.getBlockX(), newLocation.getBlockZ()).getY());

			player.teleport(newLocation);
			player.sendMessage(ChatColor.GREEN + "Teleported to X:" + newLocation.getBlockX() + " Y:"
					+ newLocation.getBlockY() + " Z:" + newLocation.getBlockZ());
		} else {
			getLogger().info("Must be a player to run this command");
		}
	}

	/**
	 * Sends the player a message saying they don't have permission to run a command
	 * 
	 * @param sender
	 */
	private void noPermission(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
	}
}
