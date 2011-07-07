package ca.xshade.bukkit.towny.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.xshade.bukkit.towny.AlreadyRegisteredException;
import ca.xshade.bukkit.towny.NotRegisteredException;
import ca.xshade.bukkit.towny.Towny;
import ca.xshade.bukkit.towny.TownyException;
import ca.xshade.bukkit.towny.TownySettings;
import ca.xshade.bukkit.towny.object.Resident;
import ca.xshade.bukkit.util.ChatTools;
import ca.xshade.bukkit.util.Colors;
import ca.xshade.util.StringMgmt;

/**
 * Send a list of all towny resident help commands to player
 * Command: /resident
 */

public class ResidentCommand implements CommandExecutor  {
	
	private static Towny plugin;
	private static final List<String> output = new ArrayList<String>();
	
	static {		
		output.add(ChatTools.formatTitle("/resident"));
		output.add(ChatTools.formatCommand("", "/resident", "", TownySettings.getLangString("res_1")));
		output.add(ChatTools.formatCommand("", "/resident", TownySettings.getLangString("res_2"), TownySettings.getLangString("res_3")));
		output.add(ChatTools.formatCommand("", "/resident", "list", TownySettings.getLangString("res_4")));
		output.add(ChatTools.formatCommand("", "/resident", "set [] .. []", "'/resident set' " + TownySettings.getLangString("res_5")));
		output.add(ChatTools.formatCommand("", "/resident", "friend [add/remove] " + TownySettings.getLangString("res_2"), TownySettings.getLangString("res_6")));
		output.add(ChatTools.formatCommand("", "/resident", "friend [add+/remove+] " + TownySettings.getLangString("res_2") + " ", TownySettings.getLangString("res_7")));
		output.add(ChatTools.formatCommand(TownySettings.getLangString("admin_sing"), "/resident", "delete " + TownySettings.getLangString("res_2"), ""));
	}
	
	public ResidentCommand(Towny instance) {
		plugin = instance;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (sender instanceof Player) {
			Player player = (Player)sender;
			
			if (args == null){
				for (String line : output)
					player.sendMessage(line);
				parseResidentCommand(player, args);
			} else{
				parseResidentCommand(player, args);
			}

		} else
			// Console
			for (String line : output)
				sender.sendMessage(Colors.strip(line));
		return true;
	}
	
	public void parseResidentCommand(Player player, String[] split) {
		if (split.length == 0)
			try {
				Resident resident = plugin.getTownyUniverse().getResident( player.getName());
				plugin.getTownyUniverse().sendMessage(player, plugin.getTownyUniverse().getStatus(resident));
			} catch (NotRegisteredException x) {
				plugin.sendErrorMsg(player, TownySettings.getLangString("msg_err_not_registered"));
			}
		else if (split[0].equalsIgnoreCase("?") || split[0].equalsIgnoreCase("help"))
			for (String line : output)
				player.sendMessage(line);
		else if (split[0].equalsIgnoreCase("list"))
			listResidents(player);
		else if (split[0].equalsIgnoreCase("set")) {
			String[] newSplit = StringMgmt.remFirstArg(split);
			residentSet(player, newSplit);
		} else if (split[0].equalsIgnoreCase("friend")) {
			String[] newSplit = StringMgmt.remFirstArg(split);
			residentFriend(player, newSplit);
		} else if (split[0].equalsIgnoreCase("delete")) {
			String[] newSplit = StringMgmt.remFirstArg(split);
			residentDelete(player, newSplit);
		} else
			try {
				Resident resident = plugin.getTownyUniverse().getResident(split[0]);
				plugin.getTownyUniverse().sendMessage(player, plugin.getTownyUniverse().getStatus(resident));
			} catch (NotRegisteredException x) {
				plugin.sendErrorMsg(player, String.format(TownySettings.getLangString("msg_err_not_registered_1"), split[0]));
			}
	}
	
	public void listResidents(Player player) {
		player.sendMessage(ChatTools.formatTitle(TownySettings.getLangString("res_list")));
		String colour;
		ArrayList<String> formatedList = new ArrayList<String>();
		for (Resident resident : plugin.getTownyUniverse().getActiveResidents()) {
			if (resident.isKing())
				colour = Colors.Gold;
			else if (resident.isMayor())
				colour = Colors.LightBlue;
			else
				colour = Colors.White;
			formatedList.add(colour + resident.getName() + Colors.White);
		}
		for (String line : ChatTools.list(formatedList))
			player.sendMessage(line);
	}
	
	/**
	 * 
	 * Command: /resident set [] ... []
	 * 
	 * @param player
	 * @param split
	 */

	/*
	 * perm [resident/outsider] [build/destroy] [on/off]
	 */

	public void residentSet(Player player, String[] split) {
		
		if (split.length == 0) {
			player.sendMessage(ChatTools.formatCommand("", "/resident set", "perm ...", "'/resident set perm' " + TownySettings.getLangString("res_5")));
			player.sendMessage(ChatTools.formatCommand("", "/resident set", "mode ...", "'/resident set mode' " + TownySettings.getLangString("res_5")));
		} else {
			Resident resident;
			try {
				resident = plugin.getTownyUniverse().getResident(player.getName());
			} catch (TownyException x) {
				plugin.sendErrorMsg(player, x.getError());
				return;
			}

			// TODO: Let admin's call a subfunction of this.
			if (split[0].equalsIgnoreCase("perm")) {
				String[] newSplit = StringMgmt.remFirstArg(split);
				TownCommand.setTownBlockOwnerPermissions(player, resident, newSplit);
			} else if (split[0].equalsIgnoreCase("mode")) {
				String[] newSplit = StringMgmt.remFirstArg(split);
				setMode(player, newSplit);
			} else {
				plugin.sendErrorMsg(player, String.format(TownySettings.getLangString("msg_err_invalid_property"), "town"));
				return;
			}

			plugin.getTownyUniverse().getDataSource().saveResident(resident);
		}
	}
	
	private void setMode(Player player, String[] split) {
		if (split.length == 0) {
			player.sendMessage(ChatTools.formatCommand("", "/resident set mode", "clear", ""));
			player.sendMessage(ChatTools.formatCommand("", "/resident set mode", "[mode] ...[mode]", ""));
			player.sendMessage(ChatTools.formatCommand("Mode", "map", "", TownySettings.getLangString("mode_1")));
			player.sendMessage(ChatTools.formatCommand("Mode", "townclaim", "", TownySettings.getLangString("mode_2")));
			player.sendMessage(ChatTools.formatCommand("Mode", "townunclaim", "", TownySettings.getLangString("mode_3")));
			player.sendMessage(ChatTools.formatCommand("Eg", "/resident set mode", "map townclaim", ""));
		} else if (split[0].equalsIgnoreCase("reset") || split[0].equalsIgnoreCase("clear"))
			plugin.removePlayerMode(player);
		else
			plugin.setPlayerMode(player, split);
	}

	public void residentFriend(Player player, String[] split) {
		if (split.length == 0) {
			player.sendMessage(ChatTools.formatCommand("", "/resident friend", "add " + TownySettings.getLangString("res_2"), ""));
			player.sendMessage(ChatTools.formatCommand("", "/resident friend", "remove " + TownySettings.getLangString("res_2"), ""));
			player.sendMessage(ChatTools.formatCommand("", "/resident friend", "clear", ""));
		} else {
			Resident resident;
			try {
				resident = plugin.getTownyUniverse().getResident(player.getName());
			} catch (TownyException x) {
				plugin.sendErrorMsg(player, x.getError());
				return;
			}

			// TODO: Let admin's call a subfunction of this.
			if (split[0].equalsIgnoreCase("add")) {
				String[] names = StringMgmt.remFirstArg(split);
				residentFriendAdd(player, resident, plugin.getTownyUniverse().getOnlineResidents(player, names));
			} else if (split[0].equalsIgnoreCase("remove")) {
				String[] names = StringMgmt.remFirstArg(split);
				residentFriendRemove(player, resident, plugin.getTownyUniverse().getOnlineResidents(player, names));
			} else if (split[0].equalsIgnoreCase("add+")) {
				String[] names = StringMgmt.remFirstArg(split);
				residentFriendAdd(player, resident, getResidents(player, names));
			} else if (split[0].equalsIgnoreCase("remove+")) {
				String[] names = StringMgmt.remFirstArg(split);
				residentFriendRemove(player, resident, getResidents(player, names));
			} else if (split[0].equalsIgnoreCase("clearlist") || split[0].equalsIgnoreCase("clear")) {
				residentFriendRemove(player, resident, resident.getFriends());
			}

		}
	}
	
	private static List<Resident> getResidents(Player player, String[] names) {
		List<Resident> invited = new ArrayList<Resident>();
		for (String name : names)
			try {
				Resident target = plugin.getTownyUniverse().getResident(name);
				invited.add(target);
			} catch (TownyException x) {
				plugin.sendErrorMsg(player, x.getError());
			}
		return invited;
	}

	public void residentFriendAdd(Player player, Resident resident, List<Resident> invited) {
		ArrayList<Resident> remove = new ArrayList<Resident>();
		for (Resident newFriend : invited)
			try {
				resident.addFriend(newFriend);
				plugin.deleteCache(newFriend.getName());
			} catch (AlreadyRegisteredException e) {
				remove.add(newFriend);
			}
		for (Resident newFriend : remove)
			invited.remove(newFriend);

		if (invited.size() > 0) {
			String msg = "Added ";
			for (Resident newFriend : invited) {
				msg += newFriend.getName() + ", ";
				Player p = plugin.getServer().getPlayer(newFriend.getName());
				if (p != null)
					plugin.sendMsg(p, String.format(TownySettings.getLangString("msg_friend_add"), player.getName()));
			}
			msg += "to your friend list.";
			plugin.sendMsg(player, msg);
			plugin.getTownyUniverse().getDataSource().saveResident(resident);
		} else
			plugin.sendErrorMsg(player, TownySettings.getLangString("msg_invalid_name"));
	}

	public void residentFriendRemove(Player player, Resident resident, List<Resident> kicking) {
		List<Resident> remove = new ArrayList<Resident>();
		List<Resident> toKick = new ArrayList<Resident>(kicking);
		
		for (Resident friend : toKick) {
			try {
				resident.removeFriend(friend);
				plugin.deleteCache(friend.getName());
			} catch (NotRegisteredException e) {
				remove.add(friend);
			}
		}
		// remove invalid names so we don't try to send them messages			
		if (remove.size() > 0)
			for (Resident friend : remove)
				toKick.remove(friend);

		if (toKick.size() > 0) {
			String msg = TownySettings.getLangString("msg_removed");
			Player p;
			for (Resident member : toKick) {
				msg += member.getName() + ", ";
				p = plugin.getServer().getPlayer(member.getName());
				if (p != null)
					plugin.sendMsg(p, String.format(TownySettings.getLangString("msg_friend_remove"), player.getName()));
			}
			msg += TownySettings.getLangString("msg_from_list");;
			plugin.sendMsg(player, msg);
			plugin.getTownyUniverse().getDataSource().saveResident(resident);
		} else
			plugin.sendErrorMsg(player, TownySettings.getLangString("msg_invalid_name"));
			
	}
	
	public void residentDelete(Player player, String[] split) {
		if (split.length == 0)
			try {
				Resident resident = plugin.getTownyUniverse().getResident(player.getName());
				plugin.getTownyUniverse().removeResident(resident);
				plugin.getTownyUniverse().sendGlobalMessage(TownySettings.getDelResidentMsg(resident));
			} catch (TownyException x) {
				plugin.sendErrorMsg(player, x.getError());
				return;
			}
		else
			try {
				if (!plugin.isTownyAdmin(player))
					throw new TownyException(TownySettings.getLangString("msg_err_admin_only_delete"));
				Resident resident = plugin.getTownyUniverse().getResident(split[0]);
				plugin.getTownyUniverse().removeResident(resident);
				plugin.getTownyUniverse().sendGlobalMessage(TownySettings.getDelResidentMsg(resident));
			} catch (TownyException x) {
				plugin.sendErrorMsg(player, x.getError());
				return;
			}
	}
	
}

