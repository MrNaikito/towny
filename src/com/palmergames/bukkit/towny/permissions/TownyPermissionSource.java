package com.palmergames.bukkit.towny.permissions;

import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.entity.Player;

import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.object.TownyWorld;


/**
 * @author ElgarL
 * 
 * Manager for Permission provider plugins
 *
 */
public abstract class TownyPermissionSource {
	protected TownySettings settings;
	protected Towny plugin;
	
	protected GroupManager groupManager = null;
	protected de.bananaco.permissions.Permissions bPermissions = null;
	protected com.nijikokun.bukkit.Permissions.Permissions permissions = null;
	protected PermissionsEx pex = null;


	abstract public String getPrefixSuffix(Resident resident, String node);
	abstract public int getGroupPermissionIntNode(String playerName, String node);
	abstract public boolean hasPermission(Player player, String node);
	abstract public String getPlayerGroup(Player player);
	abstract public String getPlayerPermissionStringNode(String playerName, String node);
	
	public boolean hasWildOverride(TownyWorld world, Player player, int blockId, TownyPermission.ActionType action) {

		boolean bpermissions;
		
		//check for permissions
		if (bpermissions = plugin.isPermissions())
			if ((hasPermission(player, PermissionNodes.TOWNY_WILD_ALL.getNode(action.toString().toLowerCase())))
				|| (hasPermission(player, PermissionNodes.TOWNY_WILD_BLOCK_ALL.getNode(blockId + "." + action.toString().toLowerCase()))))
				return true;

		// No perms so check world settings.
		switch (action) {

		case BUILD:
			return world.getUnclaimedZoneBuild() || (!bpermissions && world.isUnclaimedZoneIgnoreId(blockId));
		case DESTROY:
			return world.getUnclaimedZoneDestroy() || (!bpermissions && world.isUnclaimedZoneIgnoreId(blockId));
		case SWITCH:
			return world.getUnclaimedZoneSwitch() || (!bpermissions && world.isUnclaimedZoneIgnoreId(blockId));
		case ITEM_USE:
			return world.getUnclaimedZoneItemUse() || (!bpermissions && world.isUnclaimedZoneIgnoreId(blockId));
		default:
			return false;
		}
	}
	
	public boolean isTownyAdmin(Player player) {
		if (player.isOp())
			return true;
		return hasPermission(player, PermissionNodes.TOWNY_ADMIN.getNode());
	}
	
}