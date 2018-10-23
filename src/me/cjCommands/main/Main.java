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
			case "getdist":
				getPointDistance(sender, args, true);
				break;
			case "getdistd":
				getPointDistance(sender, args, false);
				break;
			default:
				break;
			}
		}

		return true;
	}

	private void getPointDistance(CommandSender sender, String[] args, boolean sendAsInt) {
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

					DecimalFormat dp = new DecimalFormat(".###");

					if (point == 1) {
						ppl.setLocation1(player.getLocation());
						player.sendMessage("Set point 1 to XYZ: " + ChatColor.RED + dp.format(ppl.getLocation1().getX()) + " / " + ChatColor.GREEN + dp.format(ppl.getLocation1().getY()) + " / "
								+ ChatColor.BLUE + dp.format(ppl.getLocation1().getZ()));
					} else {
						ppl.setLocation2(player.getLocation());
						player.sendMessage("Set point 1 to XYZ: " + ChatColor.RED + dp.format(ppl.getLocation2().getX()) + " / " + ChatColor.GREEN + dp.format(ppl.getLocation2().getY()) + " / "
								+ ChatColor.BLUE + dp.format(ppl.getLocation2().getZ()));
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
			player = getServer().getPlayer(args[1]);
			if (player != null && player.isOnline()) {
				try {
					gamemode = Integer.parseInt(args[2]);
				} catch (NumberFormatException e) {
					sendMessage(sender, ChatColor.RED, "Invalid gamemode number");
					return;
				}
				messageBeginning = "Set gamemode to ";
			} else {
				sendMessage(sender, ChatColor.RED, args[1] + " is not in the server");
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
		if (args.length != 2) {
			sendMessage(sender, ChatColor.RED, "Invalid number of arguments");
		} else {
			Player player = getServer().getPlayer(args[1]);

			if (player != null && player.isOnline()) {
				sender.sendMessage(player.getLocation().toString());
			} else {
				sendMessage(sender, ChatColor.RED, args[1] + " is not in the server");
			}
		}
	}

	private void sendMessage(CommandSender sender, ChatColor color, String message) {
		if (sender instanceof Player) {
			sender.sendMessage(color + message);
		} else {
			getLogger().info(message);
		}
	}

	private void randomTeleportCommand(CommandSender sender) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			Location newLocation = new Location(player.getWorld(), MyFuncs.Random.randomIntBetween(-29999983, 29999983), 0, MyFuncs.Random.randomIntBetween(-29999983, 29999983));
			newLocation.setY(newLocation.getWorld().getHighestBlockAt(newLocation.getBlockX(), newLocation.getBlockZ()).getY());

			player.teleport(newLocation);
			player.sendMessage(ChatColor.GREEN + "Teleported to X:" + newLocation.getBlockX() + " Y:" + newLocation.getBlockY() + " Z:" + newLocation.getBlockZ());
		} else {
			getLogger().info("Must be a player to run this command");
		}
	}
}
