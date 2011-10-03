package com.palmergames.bukkit.towny.permissions;

import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.Resident;



public class BukkitPermSource extends TownyPermissionSource {
	
	public BukkitPermSource(Towny towny) {
		this.plugin = towny;
	}

	@Override
	public String getPrefixSuffix(Resident resident, String node) {
		// Bukkit doesn't support prefix/suffix
		return "";
	}
	
	/**
     * 
     * @param playerName
     * @param node
     * @return -1 = can't find
     */
    @Override
	public int getGroupPermissionIntNode(String playerName, String node) {
    	// Bukkit doesn't support non boolean nodes
    	return -1;
    }
	
	
    /** hasPermission
     * 
     * returns if a player has a certain permission node.
     * 
     * @param player
     * @param node
     * @return
     */
    @Override
	public boolean hasPermission(Player player, String node) {
    	return player.hasPermission(node);
    }
	
}