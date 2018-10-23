package me.cjCommands.main;

import java.util.LinkedList;

public class PlayerPoints {
	LinkedList<PlayerPointLocations> list;

	public PlayerPoints() {
		list = new LinkedList<PlayerPointLocations>();
	}

	public void add(PlayerPointLocations ppl) {
		list.add(ppl);
	}

	public PlayerPointLocations getPlayer(String userName) {
		int index = getIndexNumberFromName(userName);
		if (index != -1) {
			return list.get(index);
		} else {
			return null;
		}
	}

	private int getIndexNumberFromName(String userName) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getPlayerName().equalsIgnoreCase(userName)) {
				return i;
			}
		}

		return -1;
	}
}
