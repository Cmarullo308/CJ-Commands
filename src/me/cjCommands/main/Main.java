package me.cjCommands.main;

import java.text.DecimalFormat;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Tools.MyFuncs;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {
	PlayerPoints playerPoints;

	public Main() {
		playerPoints = new PlayerPoints();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length == 0) {
			if (sender instanceof Player) {
				sendMessage(sender, ChatColor.RED, "No arguements");
			}
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
				distanceToPlayer(sender, args, true);
				break;
			case "disttod":
				distanceToPlayer(sender, args, false);
				break;
			case "help":
				helpMenu(sender, args);
				break;
			default:
				break;
			}
		}

		return true;
	}

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
		}

		sender.sendMessage(helpMenu);

	}

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

	private void distanceToPlayer(CommandSender sender, String[] args, boolean sendAsInt) {
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
			sendMessage(sender, ChatColor.RED, "Invalid number of arguements");
			return;
		} else if (args.length == 2) {
			player1 = (Player) sender;
			player2 = getServer().getPlayer(args[1]);

			if (player2 == null) {
				sendMessage(sender, ChatColor.RED, "Player is not in this server");
				return;
			}
		} else if (args.length == 3) {
			player1 = getServer().getPlayer(args[1]);
			player2 = getServer().getPlayer(args[2]);

			if (player1 == null && player2 == null) {
				sendMessage(sender, ChatColor.RED, "Neither player is in this server");
				return;
			} else if (player1 == null) {
				sendMessage(sender, ChatColor.RED, args[1] + " is not in this server");
				return;
			} else if (player2 == null) {
				sendMessage(sender, ChatColor.RED, args[2] + " is not in this server");
				return;
			}
		}

		distance = getDistance(player1.getLocation(), player2.getLocation());

		if (args.length == 2) {
			if (sendAsInt) {
				sender.sendMessage(player2.getName() + " is " + (int) distance + " blocks away");
			} else {
				DecimalFormat dp = new DecimalFormat(".###");
				sender.sendMessage(player2.getName() + " is " + dp.format(distance) + " blocks away");
			}
		} else {
			if (sendAsInt) {
				sender.sendMessage(
						player1.getName() + " is " + (int) distance + " blocks away from " + player2.getName());
			} else {
				DecimalFormat dp = new DecimalFormat(".###");
				sender.sendMessage(
						player1.getName() + " is " + dp.format(distance) + " blocks away from " + player2.getName());
			}
		}

	}

	private double getDistance(Location location1, Location location2) {
		return Math.sqrt(Math.pow(location2.getX() - location1.getX(), 2)
				+ Math.pow(location2.getY() - location1.getY(), 2) + Math.pow(location2.getZ() - location1.getZ(), 2));
	}

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
					sendMessage(sender, ChatColor.RED, "No points set");
					return;
				} else if (!ppl.location1Set()) {
					sendMessage(sender, ChatColor.RED, "Point 1 not set");
					return;
				} else if (!ppl.location2Set()) {
					sendMessage(sender, ChatColor.RED, "Point 2 not set");
					return;
				}

				DecimalFormat dp = new DecimalFormat(".###");

				if (sendAsInt) {
					sendMessage(sender, ChatColor.WHITE, "Distance: " + ppl.getDistanceInt());
				} else {
					sendMessage(sender, ChatColor.WHITE, "Distance " + dp.format(ppl.getDistanceDouble()));
				}
			} else {
				sendMessage(sender, ChatColor.RED, "Invalid number of arguements");
				return;
			}
		} else {
			sender.sendMessage("Must be a player to run this command");
			return;
		}
	}

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
					sendMessage(sender, ChatColor.RED, "Invalid number. Must be 1 or 2");
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
						player.sendMessage("Set point 1 to XYZ: " + formatLocation(ppl.getLocation2(), 3));
					}
				} else {
					sendMessage(sender, ChatColor.RED, "Invalid number. Must be 1 or 2");
					return;
				}
			} else {
				sendMessage(sender, ChatColor.RED, "Invalid number of arguements");
				return;
			}
		} else {
			sender.sendMessage("Must be a player");
		}
	}

	private void setGamemode(CommandSender sender, String[] args) {

		Player player;
		int gamemode = -1;
		String messageBeginning;

		if (args.length == 2) { // Setting own gamemode
			if (!sender.hasPermission("CJCommands.setgamemode")) {
				noPermission(sender);
				return;
			}
			if (sender instanceof Player) {
				player = (Player) sender;
				try {
					gamemode = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					sendMessage(sender, ChatColor.RED, "Invalid gamemode number");
					return;
				}
				messageBeginning = "Set own gamemode to ";
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
				try {
					gamemode = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					sendMessage(sender, ChatColor.RED, "Invalid gamemode number");
					return;
				}

				// Grammar
				if (args[2].charAt(args[2].length() - 1) == 's') {
					messageBeginning = "Set" + args[2] + "' gamemode to ";
				} else {
					messageBeginning = "Set" + args[2] + "'s gamemode to ";
				}
			} else {
				sendMessage(sender, ChatColor.RED, args[2] + " is not in the server");
				return;
			}
		} else {
			sendMessage(sender, ChatColor.RED, "Invalid number of arguements");
			return;
		}

		switch (gamemode) {
		case 0:
			if (player.getGameMode() != GameMode.SURVIVAL) {
				player.setGameMode(GameMode.SURVIVAL);
				sendMessage(sender, ChatColor.WHITE, messageBeginning + GameMode.SURVIVAL);
			}
			break;
		case 1:
			if (player.getGameMode() != GameMode.CREATIVE) {
				player.setGameMode(GameMode.CREATIVE);
				sendMessage(sender, ChatColor.WHITE, messageBeginning + GameMode.CREATIVE);
			}
			break;
		case 2:
			if (player.getGameMode() != GameMode.ADVENTURE) {
				player.setGameMode(GameMode.ADVENTURE);
				sendMessage(sender, ChatColor.WHITE, messageBeginning + GameMode.ADVENTURE);
			}
			break;
		case 3:
			if (player.getGameMode() != GameMode.SPECTATOR) {
				player.setGameMode(GameMode.SPECTATOR);
				sendMessage(sender, ChatColor.WHITE, messageBeginning + GameMode.SPECTATOR);
			}
			break;
		default:
			sendMessage(sender, ChatColor.RED, "Invalid gamemode number");
			break;
		}
	}

	private void getPlayerLocation(CommandSender sender, String[] args) {
		if (!sender.hasPermission("CJCommands.getplayerlocation")) {
			noPermission(sender);
			return;
		}

		if (args.length != 2) {
			sendMessage(sender, ChatColor.RED, "Invalid number of arguments");
		} else {
			Player player = getServer().getPlayer(args[1]);

			if (player != null && player.isOnline()) {
				sender.sendMessage(formatLocation(player.getLocation(), 3));
			} else {
				sendMessage(sender, ChatColor.RED, args[1] + " is not in the server");
			}
		}
	}

	private String formatLocation(Location location, int decimalPoints) {
		String decimalFormat = ".";

		for (int i = 0; i < decimalPoints; i++) {
			decimalFormat += "#";
		}

		DecimalFormat dp = new DecimalFormat(decimalFormat);

		return ChatColor.RED + dp.format(location.getX()) + ChatColor.WHITE + " / " + ChatColor.GREEN
				+ dp.format(location.getY()) + ChatColor.WHITE + " / " + ChatColor.BLUE + dp.format(location.getZ());
	}

	private void sendMessage(CommandSender sender, ChatColor color, String message) {
		if (sender instanceof Player) {
			sender.sendMessage(color + message);
		} else {
			getLogger().info(message);
		}
	}

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

	private void noPermission(CommandSender sender) {
		sender.sendMessage(ChatColor.RED + "You do not have permission to use this command");
	}
}
