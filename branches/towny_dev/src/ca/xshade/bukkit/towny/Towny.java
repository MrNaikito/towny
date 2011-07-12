package ca.xshade.bukkit.towny;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
//import javax.persistence.PersistenceException;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import ca.xshade.bukkit.questioner.Questioner;

import ca.xshade.bukkit.towny.command.TownyCommand;
import ca.xshade.bukkit.towny.command.TownCommand;
import ca.xshade.bukkit.towny.command.NationCommand;
import ca.xshade.bukkit.towny.command.NationChatCommand;
import ca.xshade.bukkit.towny.command.TownChatCommand;
import ca.xshade.bukkit.towny.command.PlotCommand;
import ca.xshade.bukkit.towny.command.ResidentCommand;
import ca.xshade.bukkit.towny.command.TownyAdminCommand;
import ca.xshade.bukkit.towny.command.TownyWorldCommand;
import ca.xshade.bukkit.towny.PlayerCache.TownBlockStatus;
import ca.xshade.bukkit.towny.event.TownyBlockListener;
import ca.xshade.bukkit.towny.event.TownyEntityListener;
import ca.xshade.bukkit.towny.event.TownyEntityMonitorListener;
import ca.xshade.bukkit.towny.event.TownyPlayerListener;
import ca.xshade.bukkit.towny.event.TownyPlayerLowListener;
import ca.xshade.bukkit.towny.event.TownyWorldListener;
import ca.xshade.bukkit.towny.object.Coord;
import ca.xshade.bukkit.towny.object.Resident;
import ca.xshade.bukkit.towny.object.Town;
import ca.xshade.bukkit.towny.object.TownBlock;
import ca.xshade.bukkit.towny.object.TownyIConomyObject;
import ca.xshade.bukkit.towny.object.TownyPermission;
import ca.xshade.bukkit.towny.object.TownyUniverse;
import ca.xshade.bukkit.towny.object.TownyWorld;
import ca.xshade.bukkit.towny.object.WorldCoord;
import ca.xshade.bukkit.towny.questioner.TownyQuestionTask;
import ca.xshade.bukkit.util.ChatTools;
import ca.xshade.bukkit.util.Colors;
import ca.xshade.questionmanager.Option;
import ca.xshade.questionmanager.Question;
import ca.xshade.util.FileMgmt;
import ca.xshade.util.JavaUtil;
import ca.xshade.util.StringMgmt;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.iConomy.*;
import com.nijikokun.bukkit.Permissions.Permissions;
import com.nijiko.permissions.PermissionHandler;


/**
 * Towny Plugin for Bukkit
 * 
 * Website: towny.xshade.ca
 * Source: https://github.com/Zren/Towny
 * 
 * @author Shade
 */

public class Towny extends JavaPlugin {
	private String version = "2.0.0";

	public static PermissionHandler permissionHandler;

	private final TownyPlayerListener playerListener = new TownyPlayerListener(this);
	private final TownyBlockListener blockListener = new TownyBlockListener(this);
	private final TownyEntityListener entityListener = new TownyEntityListener(this);
	private final TownyPlayerLowListener playerLowListener = new TownyPlayerLowListener(this);
	private final TownyEntityMonitorListener entityMonitorListener = new TownyEntityMonitorListener(this);
	private final TownyWorldListener worldListener = new TownyWorldListener(this);
	private TownyUniverse townyUniverse;
	private Map<String, PlayerCache> playerCache = Collections.synchronizedMap(new HashMap<String, PlayerCache>());
	private Map<String, List<String>> playerMode = Collections.synchronizedMap(new HashMap<String, List<String>>());
	private iConomy iconomy = null;
	private Permissions permissions = null;
	private boolean error = false;
	private Logger logger = Logger.getLogger("ca.xshade.bukkit.towny");
	//private GroupManager groupManager = null;
	
	@Override
	public void onEnable() {
		version = this.getDescription().getVersion();
		//System.out.println("[Towny] Towny - create TownyUniverse Object...");
		townyUniverse = new TownyUniverse(this);
		//System.out.println("[Towny] Towny - Starting loadSettings...");
		//loadSettings();
		
		load();
		setupLogger();		
		
		// Setup bukkit command interfaces
		getCommand("townyadmin").setExecutor(new TownyAdminCommand(this));
		getCommand("townyworld").setExecutor(new TownyWorldCommand(this));
		getCommand("resident").setExecutor(new ResidentCommand(this));
		getCommand("towny").setExecutor(new TownyCommand(this));
		getCommand("town").setExecutor(new TownCommand(this));
		getCommand("nation").setExecutor(new NationCommand(this));
		getCommand("plot").setExecutor(new PlotCommand(this));
		getCommand("townchat").setExecutor(new TownChatCommand(this));
		getCommand("nationchat").setExecutor(new NationChatCommand(this));
		
		
		//checkPlugins();
		//load();
		
		/*
		if (TownySettings.isFirstRun()) {
			firstRun();
			setSetting("FIRST_RUN", false);
			loadSettings();
		}
		*/

		
		if (TownySettings.isTownyUpdating(getVersion()))
			update();
		
		registerEvents();
		System.out.println("[Towny] Version: " + version + " - Mod Enabled");
		
		// Re login anyone online. (In case of plugin reloading)
		for (Player player : getServer().getOnlinePlayers())
			try {
				getTownyUniverse().onLogin(player);
			} catch (TownyException x) {
				sendErrorMsg(player, x.getError());
			}
		//setupDatabase();
	}

	/*
	private void setupDatabase()
	{
		try
		{
		getDatabase().find(Towny.class).findRowCount();
		}
		catch(PersistenceException ex)
		{
			System.out.println("Installing database for " + getDescription().getName() + " due to first time usage");
			installDDL();
		}
	}
	*/
	
	@Override
	public List<Class<?>> getDatabaseClasses()
	{
		List<Class<?>> list = new ArrayList<Class<?>>();
		list.add(Towny.class);
		return list;
	}
	
	private void checkPlugins() {
		List<String> using = new ArrayList<String>();
		Plugin test;

		test = getServer().getPluginManager().getPlugin("Permissions");
		if (test == null)
			setSetting("USING_PERMISSIONS", false);
		else {
			permissions = (Permissions)test;
			if (TownySettings.isUsingPermissions())
				using.add("Permissions");
		}
		
		test = getServer().getPluginManager().getPlugin("iConomy");
		if (test == null)
			setSetting("USING_ICONOMY", false);
		else {
			iconomy = (iConomy)test;
			if (TownySettings.isUsingIConomy())
				using.add("iConomy");
		}
		
		test = getServer().getPluginManager().getPlugin("Essentials");
		if (test == null)
			setSetting("USING_ESSENTIALS", false);
		else if (TownySettings.isUsingEssentials())
			using.add("Essentials");
		
		test = getServer().getPluginManager().getPlugin("Questioner");
		if (test == null)
			setSetting("USING_QUESTIONER", false);
		else if (TownySettings.isUsingQuestioner())
			using.add("Questioner");
		
		if (using.size() > 0)
			System.out.println("[Towny] Using: " + StringMgmt.join(using, ", "));
	}

	@Override
	public void onDisable() {
		if (townyUniverse.getDataSource() != null && error == false)
			townyUniverse.getDataSource().saveAll();
		
		if (getTownyUniverse().isWarTime())
			getTownyUniverse().getWarEvent().toggleEnd();
		townyUniverse.toggleDailyTimer(false);
		townyUniverse.toggleMobRemoval(false);
		townyUniverse.toggleHealthRegen(false);
		
		playerCache.clear();
		playerMode.clear();
		
		townyUniverse = null;
		
		System.out.println("[Towny] Version: " + version + " - Mod Disabled");
	}
	
	public void loadSettings() {
		
		//System.out.println("[Towny] load");
		
		if (!townyUniverse.loadSettings())  {
			error = true;
			getServer().getPluginManager().disablePlugin(this);
		}
		
		Coord.setCellSize(TownySettings.getTownBlockSize());
		TownyIConomyObject.setPlugin(this);
		//TownyCommand.setUniverse(townyUniverse);
	}
	
	public void load() {
		
		loadSettings();
		
		checkPlugins();
		
		if (TownySettings.isForcingPvP())
			for (Town town : townyUniverse.getTowns())
				town.setPVP(true);
		
		townyUniverse.toggleDailyTimer(true);
		townyUniverse.toggleMobRemoval(TownySettings.isRemovingWorldMobs() || TownySettings.isRemovingTownMobs() );
		townyUniverse.toggleHealthRegen(TownySettings.hasHealthRegen());
		updateCache();
	}

	private void registerEvents() {
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
		//getServer().getPluginManager().registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_TELEPORT, playerListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_CHAT, playerLowListener, Priority.Low, this);
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);

		getServer().getPluginManager().registerEvent(Event.Type.BLOCK_PLACE, blockListener, Priority.Lowest, this);
		getServer().getPluginManager().registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Lowest, this);
		getServer().getPluginManager().registerEvent(Event.Type.BLOCK_IGNITE, blockListener, Priority.Lowest, this);
		getServer().getPluginManager().registerEvent(Event.Type.BLOCK_BURN, blockListener, Priority.Lowest, this);
		//getServer().getPluginManager().registerEvent(Event.Type.BLOCK_INTERACT, blockListener, Priority.Normal, this);

		getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Lowest, this);
		getServer().getPluginManager().registerEvent(Event.Type.ENTITY_EXPLODE, entityListener, Priority.Lowest, this);
		getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DAMAGE, entityMonitorListener, Priority.Lowest, this);
		getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Lowest, this);
		getServer().getPluginManager().registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Priority.Lowest, this);
		
		getServer().getPluginManager().registerEvent(Event.Type.WORLD_LOAD, worldListener, Priority.Normal, this);
	}
	
	/*
	private void firstRun() {
		System.out.println("------------------------------------");
		System.out.println("[Towny] Detected first run");
		
		try {
			String newLine = System.getProperty("line.separator");
			BufferedWriter fout = new BufferedWriter(new FileWriter(getDataFolder().getPath() + FileMgmt.fileSeparator() + "settings" + FileMgmt.fileSeparator() + "town-levels.csv"));
			fout.write("0,, Ruin,Spirit ,,1" + newLine);
			fout.write("1,, Hamlet,,,16" + newLine);
			fout.write("2,, Village,Mayor ,,64" + newLine);
			fout.write("6,, Town,Lord ,,128" + newLine);
			fout.write("12,, City,Lord ,,256");
			fout.close();
			System.out.println("[Towny] Registered default town levels.");
		} catch (Exception e) {
			System.out.println("[Towny] Error: Could not write default town levels file.");
		}
		try {
			String newLine = System.getProperty("line.separator");
			BufferedWriter fout = new BufferedWriter(new FileWriter(getDataFolder().getPath() + FileMgmt.fileSeparator() + "settings" + FileMgmt.fileSeparator() + "nation-levels.csv"));
			fout.write("0,, Wilderness,, Lands,Leader ," + newLine);
			fout.write("1,Dominion of ,,, Center,Leader ," + newLine);
			fout.write("2,Lands of ,,, Center,Leader ," + newLine);
			fout.write("3,, Country,, Lands,King ," + newLine);
			fout.write("6,, Kingdom,, Lands,King ," + newLine);
			fout.write("12,, Empire,, Lands,Emperor ,");
			fout.close();
			System.out.println("[Towny] Registered default nation levels.");
		} catch (Exception e) {
			System.out.println("[Towny] Error: Could not write default nation levels file.");
		}
		System.out.println("------------------------------------");
	}
	*/
	
	public void update() {
		try {
			List<String> changeLog = JavaUtil.readTextFromJar("/ChangeLog.txt");
			boolean display = false;
			System.out.println("------------------------------------");
			System.out.println("[Towny] ChangeLog up until v" + getVersion());
			String lastVersion = TownySettings.getLastRunVersion();
			for (String line : changeLog) { //TODO: crawl from the bottom, then past from that index.
				if (line.startsWith("v" + lastVersion))
					display = true;
				if (display && line.replaceAll(" ", "").replaceAll("\t", "").length() > 0)
					System.out.println(line);
			}
			System.out.println("------------------------------------");
		} catch (IOException e) {
			sendDebugMsg("Could not read ChangeLog.txt");
		}
		setSetting("LAST_RUN_VERSION", getVersion());
	}
	

	public TownyUniverse getTownyUniverse() {
		return townyUniverse;
	}

	public void sendErrorMsg(Player player, String msg) {
		for (String line : ChatTools.color(TownySettings.getLangString("default_towny_prefix") + Colors.Rose + msg))
			player.sendMessage(line);
		sendDevMsg(msg);
	}
	
	public void sendErrorMsg(String msg) {
		System.out.println("[Towny] Error: " + msg);
	}
	
	public void sendDevMsg(String msg) {
		if (TownySettings.isDevMode()) {
			Player townyDev = getServer().getPlayer(TownySettings.getString("dev_name"));
			if (townyDev == null)
				return;
			for (String line : ChatTools.color(TownySettings.getLangString("default_towny_prefix") + " DevMode: " + Colors.Rose + msg))
				townyDev.sendMessage(line);
		}
	}
	
	public void sendDebugMsg(String msg) {
		if (TownySettings.getDebug())
			System.out.println("[Towny] Debug: " + msg);
		sendDevMsg(msg);
	}

	public void sendErrorMsg(Player player, String[] msg) {
		for (String line : msg)
			sendErrorMsg(player, line);
	}

	public void sendMsg(Player player, String msg) {
		for (String line : ChatTools.color(TownySettings.getLangString("default_towny_prefix") + Colors.Green + msg))
			player.sendMessage(line);
	}

	public String getVersion() {
		return version;
	}

	public World getServerWorld(String name) throws NotRegisteredException {
		for (World world : getServer().getWorlds())
			if (world.getName().equals(name))
				return world;

		throw new NotRegisteredException();
	}
	
	public boolean hasCache(Player player) {
		return playerCache.containsKey(player.getName().toLowerCase());
	}
	
	public void newCache(Player player) {
		try {
			playerCache.put(player.getName().toLowerCase(), new PlayerCache(getTownyUniverse().getWorld(player.getWorld().getName()), player));
		} catch (NotRegisteredException e) {
			sendErrorMsg(player, "Could not create permission cache for this world ("+player.getWorld().getName()+".");
		}
		
	}
	
	public void deleteCache(Player player) {
		deleteCache(player.getName());
	}
	
	public void deleteCache(String name) {
		playerCache.remove(name.toLowerCase());
	}
	
	public PlayerCache getCache(Player player) {
		if (!hasCache(player))
			newCache(player);
		
		return playerCache.get(player.getName().toLowerCase());
	}
	
	public void updateCache(WorldCoord worldCoord) {
		for (Player player : getServer().getOnlinePlayers())
			if (Coord.parseCoord(player).equals(worldCoord))
				getCache(player).setLastTownBlock(worldCoord); //Automatically resets permissions.
	}
	
	public void updateCache() {
		for (Player player : getServer().getOnlinePlayers())
			try {
				getCache(player).setLastTownBlock(new WorldCoord(getTownyUniverse().getWorld(player.getWorld().getName()), Coord.parseCoord(player)));
			} catch (NotRegisteredException e) {
				deleteCache(player);
			}
	}
	
	public boolean isTownyAdmin(Player player) {
		if (player.isOp())
			return true;
		return hasPermission(player, "towny.admin");
	}
	
	public void setPlayerMode(Player player, String[] modes) {
		playerMode.put(player.getName(), Arrays.asList(modes));
		sendMsg(player, ("Modes set: " + StringMgmt.join(modes, ",")));
	}
	
	public void removePlayerMode(Player player) {
		playerMode.remove(player.getName());
		sendMsg(player, ("Mode removed."));
	}
	
	public List<String> getPlayerMode(Player player) {
		return playerMode.get(player.getName());
	}
	
	public boolean hasPlayerMode(Player player, String mode) {
		List<String> modes = getPlayerMode(player);
		if (modes == null)
			return false;
		else
			return modes.contains(mode); 
	}
	
	public List<String> getPlayerMode(String name) {
		return playerMode.get(name);
	}
	
	public boolean hasPlayerMode(String name, String mode) {
		List<String> modes = getPlayerMode(name);
		if (modes == null)
			return false;
		else
			return modes.contains(mode); 
	}

	public boolean checkEssentialsTeleport(Player player) {
		if (!TownySettings.isUsingEssentials() || !TownySettings.isAllowingTownSpawn())
			return true;
		
		Plugin test = getServer().getPluginManager().getPlugin("Essentials");
		if (test == null)
			return true;
		Essentials essentials = (Essentials)test;
		//essentials.loadClasses();
		sendDebugMsg("Using Essentials");
		
		try {
			@SuppressWarnings("unused")
			User user = essentials.getUser(player);
			return true;
		} catch (Exception e) {
			sendErrorMsg(player, "Error: " + e.getMessage());
			return false;
		}
		
	}
	
	// is permissions active
	public boolean isPermissions() {
		return (TownySettings.isUsingPermissions() && permissions != null);
	}

	public boolean hasPermission(Player player, String node) {
		sendDebugMsg("Perm Check: Does " + player.getName() + " have the node '" + node + "'?");
		if (isPermissions()) {
			sendDebugMsg("    Permissions installed.");
			PermissionHandler handler = permissions.getHandler();
			boolean perm = handler.permission(player, node);
			sendDebugMsg("    Permissions says "+perm+".");
			return perm;
		// } else if (groupManager != null)
		//	return groupManager.getHandler().permission(player, node);
		} else {
			sendDebugMsg("    Does not have permission.");
			return false;
		}
	}

	public void sendMsg(String msg) {
		System.out.println("[Towny] " + msg);
	}
	
	public String getConfigPath() {
		return getDataFolder().getPath() + FileMgmt.fileSeparator() + "settings" + FileMgmt.fileSeparator() + "config.yml";
	}
	
	public void setSetting(String root, Object value) {
			TownySettings.setProperty(root, value);
	}
	
	public Object getSetting(String root) {
		return TownySettings.getProperty(root);
}
	

	public TownBlockStatus getStatusCache(Player player, WorldCoord worldCoord) {
		if (isTownyAdmin(player))
			return TownBlockStatus.ADMIN;
		
		if (!worldCoord.getWorld().isUsingTowny())
			return TownBlockStatus.OFF_WORLD;
		
		TownyUniverse universe = getTownyUniverse();
		TownBlock townBlock;
		Town town;
		try {
			townBlock = worldCoord.getTownBlock();
			town = townBlock.getTown();
		} catch (NotRegisteredException e) {
			// Unclaimed Zone switch rights
			return TownBlockStatus.UNCLAIMED_ZONE;
		}

		Resident resident;
		try {
			resident = universe.getResident(player.getName());
		} catch (TownyException e) {
			return TownBlockStatus.NOT_REGISTERED;
		}
		
		try {
			// War Time switch rights
			if (universe.isWarTime())
				try {
					if (!resident.getTown().getNation().isNeutral() && !town.getNation().isNeutral())
						return TownBlockStatus.WARZONE;	
				} catch (NotRegisteredException e) {
				}
			
			// Town Owner Override
			try {
				if (townBlock.getTown().isMayor(resident) || townBlock.getTown().hasAssistant(resident))
					return TownBlockStatus.TOWN_OWNER;
			} catch (NotRegisteredException e) {
			}
				
			// Resident Plot switch rights
			try {
				Resident owner = townBlock.getResident();
				if (resident == owner)
					return TownBlockStatus.PLOT_OWNER;
				else if (owner.hasFriend(resident))
					return TownBlockStatus.PLOT_FRIEND;
				else if (resident.hasTown() && townyUniverse.isAlly(owner.getTown(), resident.getTown()))
					return TownBlockStatus.PLOT_ALLY;
				else
					// Exit out and use town permissions
					throw new TownyException();
			} catch (NotRegisteredException x) {
			} catch (TownyException x) {
			}

			// Town resident destroy rights
			if (!resident.hasTown())
				throw new TownyException();

			if (resident.getTown() != town) {
				// Allied destroy rights
				if (universe.isAlly(town, resident.getTown()))
					return TownBlockStatus.TOWN_ALLY;
				else
					return TownBlockStatus.OUTSIDER;
			} else if (resident.isMayor() || resident.getTown().hasAssistant(resident))
				return TownBlockStatus.TOWN_OWNER;
			else
				return TownBlockStatus.TOWN_RESIDENT;
		} catch (TownyException e) {
			// Outsider destroy rights
			return TownBlockStatus.OUTSIDER;
		}
	}
	
	public TownBlockStatus cacheStatus(Player player, WorldCoord worldCoord, TownBlockStatus townBlockStatus) {
		PlayerCache cache = getCache(player);
		cache.updateCoord(worldCoord);
		cache.setStatus(townBlockStatus);

		sendDebugMsg(player.getName() + " (" + worldCoord.toString() + ") Cached Status: " + townBlockStatus);
		return townBlockStatus;
	}


	public void cacheBuild(Player player, WorldCoord worldCoord, boolean buildRight) {
		PlayerCache cache = getCache(player);
		cache.updateCoord(worldCoord);
		cache.setBuildPermission(buildRight);

		sendDebugMsg(player.getName() + " (" + worldCoord.toString() + ") Cached Build: " + buildRight);
	}

	public void cacheDestroy(Player player, WorldCoord worldCoord, boolean destroyRight) {
		PlayerCache cache = getCache(player);
		cache.updateCoord(worldCoord);
		cache.setDestroyPermission(destroyRight);

		sendDebugMsg(player.getName() + " (" + worldCoord.toString() + ") Cached Destroy: " + destroyRight);
	}
	
	public void cacheSwitch(Player player, WorldCoord worldCoord, boolean switchRight) {
		PlayerCache cache = getCache(player);
		cache.updateCoord(worldCoord);
		cache.setSwitchPermission(switchRight);

		sendDebugMsg(player.getName() + " (" + worldCoord.toString() + ") Cached Switch: " + switchRight);
	}
	
	public void cacheItemUse(Player player, WorldCoord worldCoord, boolean itemUseRight) {
		PlayerCache cache = getCache(player);
		cache.updateCoord(worldCoord);
		cache.setItemUsePermission(itemUseRight);

		sendDebugMsg(player.getName() + " (" + worldCoord.toString() + ") Cached Item Use: " + itemUseRight);
	}
	
	public void cacheBlockErrMsg(Player player, String msg) {
		PlayerCache cache = getCache(player);
		cache.setBlockErrMsg(msg);
	}
	
	public boolean getPermission(Player player, TownBlockStatus status, WorldCoord pos, TownyPermission.ActionType actionType) {
		if (status == TownBlockStatus.OFF_WORLD ||
			status == TownBlockStatus.ADMIN ||
			status == TownBlockStatus.WARZONE ||
			status == TownBlockStatus.PLOT_OWNER ||
			status == TownBlockStatus.TOWN_OWNER)
				return true;
		
		if (status == TownBlockStatus.NOT_REGISTERED) {
			cacheBlockErrMsg(player, TownySettings.getLangString("msg_cache_block_error"));
			return false;
		}
		
		TownBlock townBlock;
		Town town;
		try {
			townBlock = pos.getTownBlock();
			town = townBlock.getTown();
		} catch (NotRegisteredException e) {
			
			// Wilderness Permissions
			if (status == TownBlockStatus.UNCLAIMED_ZONE)
				if (hasPermission(player, "towny.wild." + actionType.toString()))
					return true;
			
				else if (!TownyPermission.getUnclaimedZone(actionType, pos.getWorld())) {
					// TODO: Have permission to destroy here
					cacheBlockErrMsg(player, String.format(TownySettings.getLangString("msg_cache_block_error_wild"), actionType.toString()));
					return false;
				} else
					return true;
			else {
				sendErrorMsg(player, "Error updating destroy permission.");
				return false;
			}
		}
		
		// Plot Permissions
		try {
			Resident owner = townBlock.getResident();
			
			if (status == TownBlockStatus.PLOT_FRIEND) {
				if (owner.getPermissions().getResident(actionType))
					return true;
				else {
					cacheBlockErrMsg(player, String.format(TownySettings.getLangString("msg_cache_block_error_plot"), "friends", actionType.toString()));
					return false;
				}
			} else if (status == TownBlockStatus.PLOT_ALLY)
				if (owner.getPermissions().getAlly(actionType))
					return true;
				else {
					cacheBlockErrMsg(player, String.format(TownySettings.getLangString("msg_cache_block_error_plot"), "allies", actionType.toString()));
					return false;
				}
			else //TODO: (Remove) if (status == TownBlockStatus.OUTSIDER)
				if (owner.getPermissions().getOutsider(actionType))
					return true;
				else {
					cacheBlockErrMsg(player, String.format(TownySettings.getLangString("msg_cache_block_error_plot"), "outsiders", actionType.toString()));
					return false;
				}
		} catch (NotRegisteredException x) {
		}
	
		// Town Permissions
		if (status == TownBlockStatus.TOWN_RESIDENT) {
			if (town.getPermissions().getResident(actionType))
				return true;
			else {
				cacheBlockErrMsg(player, String.format(TownySettings.getLangString("msg_cache_block_error_town_resident"), actionType.toString()));
				return false;
			}
		} else if (status == TownBlockStatus.TOWN_ALLY)
			if (town.getPermissions().getAlly(actionType))
				return true;
			else {
				cacheBlockErrMsg(player, String.format(TownySettings.getLangString("msg_cache_block_error_town_allies"), actionType.toString()));
				return false;
			}
		else if (status == TownBlockStatus.OUTSIDER)
			if (town.getPermissions().getOutsider(actionType))
				return true;
			else {
				cacheBlockErrMsg(player, String.format(TownySettings.getLangString("msg_cache_block_error_town_outsider"), actionType.toString()));
				return false;
			}
		
		sendErrorMsg(player, "Error updating " + actionType.toString() + " permission.");
		return false;
	}	

	public iConomy getIConomy() throws IConomyException {
		if (iconomy == null)
			throw new IConomyException("iConomy is not installed");
		else
			return iconomy;
		
	}

	public Logger getLogger() {
		return logger;
	}
	
	public void log(String msg) {
		if (TownySettings.isLogging())
			getLogger().info(ChatColor.stripColor(msg));
	}
	
	public void setupLogger() {
		FileMgmt.checkFolders(new String[]{getTownyUniverse().getRootFolder() + FileMgmt.fileSeparator() + "logs"});
        try {
            FileHandler fh = new FileHandler(getDataFolder() + FileMgmt.fileSeparator() + "logs" + FileMgmt.fileSeparator() + "towny.log", TownySettings.isAppendingToLog());
            fh.setFormatter(new TownyLogFormatter());
            getLogger().addHandler(fh);
        } catch (IOException e) {
        	e.printStackTrace();
        }
	}
	
	public boolean hasWildOverride(TownyWorld world, Player player, int blockId, TownyPermission.ActionType action) {
		return world.isUnclaimedZoneIgnoreId(blockId) || hasPermission(player, "towny.wild.block." + blockId + "." + action.toString());
	}
	
	public void appendQuestion(Questioner questioner, Question question) throws Exception {
		for (Option option : question.getOptions())
			if (option.getReaction() instanceof TownyQuestionTask)
				((TownyQuestionTask)option.getReaction()).setTowny(this);
		questioner.appendQuestion(question);
	}
	
	public boolean parseOnOff(String s) throws Exception {
		if (s.equalsIgnoreCase("on"))
			return true;
		else if (s.equalsIgnoreCase("off"))
			return false;
		else
			throw new Exception(String.format(TownySettings.getLangString("msg_err_invalid_input"), " on/off."));
	}
}
