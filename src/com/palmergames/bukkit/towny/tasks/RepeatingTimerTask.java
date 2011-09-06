package com.palmergames.bukkit.towny.tasks;

import org.bukkit.Server;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.TownyException;
import com.palmergames.bukkit.towny.object.Coord;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.palmergames.bukkit.towny.object.TownyWorld;

public class RepeatingTimerTask extends TownyTimerTask {

private Server server;
	
	public RepeatingTimerTask(TownyUniverse universe, Server server) {
		super(universe);
		this.server = server;
	}
	
	@Override
	public void run() {
		if (universe.isWarTime())
			return;
		
		for (Player player : server.getOnlinePlayers()) {
			
		}
		
		//if (TownySettings.getDebug())
		//	System.out.println("[Towny] Debug: Health Regen");
	}
	
}
