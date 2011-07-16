package ca.xshade.bukkit.towny.object;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.naming.InvalidNameException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.xshade.bukkit.towny.AlreadyRegisteredException;
import ca.xshade.bukkit.towny.DailyTimerTask;
import ca.xshade.bukkit.towny.EmptyNationException;
import ca.xshade.bukkit.towny.EmptyTownException;
import ca.xshade.bukkit.towny.HealthRegenTimerTask;
import ca.xshade.bukkit.towny.IConomyException;
import ca.xshade.bukkit.towny.MobRemovalTimerTask;
import ca.xshade.bukkit.towny.NotRegisteredException;
import ca.xshade.bukkit.towny.Towny;
import ca.xshade.bukkit.towny.TownyException;
import ca.xshade.bukkit.towny.TownyFormatter;
import ca.xshade.bukkit.towny.TownySettings;
import ca.xshade.bukkit.towny.db.TownyDataSource;
import ca.xshade.bukkit.towny.db.TownyFlatFileSource;
import ca.xshade.bukkit.towny.db.TownyHModFlatFileSource;
import ca.xshade.bukkit.towny.war.War;
import ca.xshade.bukkit.towny.war.WarSpoils;
import ca.xshade.bukkit.util.ChatTools;
import ca.xshade.bukkit.util.Colors;
import ca.xshade.bukkit.util.MinecraftTools;
import ca.xshade.util.FileMgmt;


public class TownyUniverse extends TownyObject {
	private Towny plugin;
	private Hashtable<String, Resident> residents = new Hashtable<String, Resident>();
	private Hashtable<String, Town> towns = new Hashtable<String, Town>();
	private Hashtable<String, Nation> nations = new Hashtable<String, Nation>();
	private Hashtable<String, TownyWorld> worlds = new Hashtable<String, TownyWorld>();
	// private List<Election> elections;
	private TownyFormatter formatter = new TownyFormatter(); //TODO : Make static
	private TownyDataSource dataSource;
	private int dailyTask = -1;
	private int mobRemoveTask = -1;
	private int healthRegenTask = -1;
	private War warEvent;
	private String rootFolder;
	
	public TownyUniverse() {
		setName("");
		rootFolder = "";
	}
	
	public TownyUniverse(String rootFolder) {
		setName("");
		this.rootFolder = rootFolder;
	}
	
	public TownyUniverse(Towny plugin) {
		setName("");
		this.plugin = plugin;
	}
	
	public void newDay() {
		if (!isDailyTimerRunning())
			toggleDailyTimer(true);
		//dailyTimer.schedule(new DailyTimerTask(this), 0);
		if (getPlugin().getServer().getScheduler().scheduleAsyncDelayedTask(getPlugin(), new DailyTimerTask(this)) == -1)
			plugin.sendErrorMsg("Could not schedule newDay.");
	}
	
	public void toggleMobRemoval(boolean on) {
		
		if (on && !isMobRemovalRunning()) {
			mobRemoveTask = getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(getPlugin(), new MobRemovalTimerTask(this, plugin.getServer()), 0, MinecraftTools.convertToTicks(TownySettings.getMobRemovalSpeed()));
			if (mobRemoveTask == -1)
				plugin.sendErrorMsg("Could not schedule mob removal loop.");
		} else if (!on && isMobRemovalRunning()) {
			getPlugin().getServer().getScheduler().cancelTask(mobRemoveTask);
			mobRemoveTask = -1;
		}
	}
	
	public void toggleDailyTimer(boolean on) {
		if (on && !isDailyTimerRunning()) {
			long timeTillNextDay = TownySettings.getDayInterval() - System.currentTimeMillis() % TownySettings.getDayInterval();
			dailyTask = getPlugin().getServer().getScheduler().scheduleAsyncRepeatingTask(getPlugin(), new DailyTimerTask(this), MinecraftTools.convertToTicks(timeTillNextDay), MinecraftTools.convertToTicks(TownySettings.getDayInterval()));
			if (dailyTask == -1)
				plugin.sendErrorMsg("Could not schedule new day loop.");
		} else if (!on && isDailyTimerRunning()) {
			getPlugin().getServer().getScheduler().cancelTask(dailyTask);
			dailyTask = -1;
		}

	}
	
	public void toggleHealthRegen(boolean on) {
		if (on && !isHealthRegenRunning()) {
			healthRegenTask = getPlugin().getServer().getScheduler().scheduleAsyncRepeatingTask(getPlugin(), new HealthRegenTimerTask(this, plugin.getServer()), 0, MinecraftTools.convertToTicks(TownySettings.getHealthRegenSpeed()));
			if (healthRegenTask == -1)
				plugin.sendErrorMsg("Could not schedule health regen loop.");
		} else if (!on && isHealthRegenRunning()) {
			getPlugin().getServer().getScheduler().cancelTask(healthRegenTask);
			healthRegenTask = -1;
		}
	}
	
	public boolean isMobRemovalRunning() {
		return mobRemoveTask != -1;
		//return mobRemoveTimer != null;
	}
	
	public boolean isDailyTimerRunning() {
		return dailyTask != -1;
		//return dailyTimer != null;
	}
	
	public boolean isHealthRegenRunning() {
		return healthRegenTask != -1;
		//return healthRegenTimer != null;
	}

	public void onLogin(Player player) throws AlreadyRegisteredException, NotRegisteredException {
		Resident resident;
		if (!hasResident(player.getName())) {
			newResident(player.getName());
			resident = getResident(player.getName());
			sendMessage(player, TownySettings.getRegistrationMsg());
			resident.setRegistered(System.currentTimeMillis());
			if (!TownySettings.getDefaultTownName().equals(""))
				try {
					getTown(TownySettings.getDefaultTownName()).addResident(resident);
				} catch (NotRegisteredException e) {
				} catch (AlreadyRegisteredException e) {
				}
			getDataSource().saveResidentList();
		} else
			resident = getResident(player.getName());

		resident.setLastOnline(System.currentTimeMillis());
		getDataSource().saveResident(resident);

		try {
			sendTownBoard(player, resident.getTown());
		} catch (NotRegisteredException e) {
		}

		if (isWarTime())
			getWarEvent().sendScores(player, 3);
	}

	public void onLogout(Player player) {
		try {
			Resident resident = getResident(player.getName());
			resident.setLastOnline(System.currentTimeMillis());
			getDataSource().saveResident(resident);
		} catch (NotRegisteredException e) {
		}
	}
	
	/**
	 * Teleports the player to his town's spawn location. If town doesn't have a
	 * spawn or player has no town, and teleport is forced, then player is sent
	 * to the world's spawn location.
	 * 
	 * @param player
	 * @param forceTeleport
	 */

	public void townSpawn(Player player, boolean forceTeleport) {
		try {
			Resident resident = plugin.getTownyUniverse().getResident(player.getName());
			Town town = resident.getTown();
			player.teleport(town.getSpawn());
			//show message if we are using iConomy and are charging for spawn travel.
			if (!plugin.isTownyAdmin(player) && TownySettings.isUsingIConomy() && TownySettings.getTownSpawnTravelPrice() != 0)
				plugin.sendMsg(player, String.format(TownySettings.getLangString("msg_cost_spawn"),
						TownySettings.getTownSpawnTravelPrice() + TownyIConomyObject.getIConomyCurrency()));
			//player.teleportTo(town.getSpawn());
		} catch (TownyException x) {
			if (forceTeleport) {
				player.teleport(player.getWorld().getSpawnLocation());
				//player.teleportTo(player.getWorld().getSpawnLocation());
				plugin.sendDebugMsg("onTownSpawn: [forced] "+player.getName());
			} else
				plugin.sendErrorMsg(player, x.getError());
		}
	}
	
	public Location getTownSpawnLocation(Player player) throws TownyException {
		try {
			Resident resident = plugin.getTownyUniverse().getResident(player.getName());
			Town town = resident.getTown();
			return town.getSpawn();
		} catch (TownyException x) {
			throw new TownyException("Unable to get spawn location");
		}
	}

	public void newResident(String name) throws AlreadyRegisteredException, NotRegisteredException {
		String filteredName;
		try {
			filteredName = checkAndFilterName(name);
		} catch (InvalidNameException e) {
			throw new NotRegisteredException(e.getMessage());
		}
		
		if (residents.containsKey(filteredName.toLowerCase()))
			throw new AlreadyRegisteredException("A resident with the name " + filteredName + " is already in use.");
		
		residents.put(filteredName.toLowerCase(), new Resident(filteredName));
	}

	public void newTown(String name) throws AlreadyRegisteredException, NotRegisteredException {
		String filteredName;
		try {
			filteredName = checkAndFilterName(name);
		} catch (InvalidNameException e) {
			throw new NotRegisteredException(e.getMessage());
		}
		
		if (towns.containsKey(filteredName.toLowerCase()))
			throw new AlreadyRegisteredException("The town " + filteredName + " is already in use.");
		
		towns.put(filteredName.toLowerCase(), new Town(filteredName));
	}

	public void newNation(String name) throws AlreadyRegisteredException, NotRegisteredException {
		String filteredName;
		try {
			filteredName = checkAndFilterName(name);
		} catch (InvalidNameException e) {
			throw new NotRegisteredException(e.getMessage());
		}
		
		if (nations.containsKey(filteredName.toLowerCase()))
			throw new AlreadyRegisteredException("The nation " + filteredName + " is already in use.");
		
		nations.put(filteredName.toLowerCase(), new Nation(filteredName));
	}

	public void newWorld(String name) throws AlreadyRegisteredException, NotRegisteredException {
		String filteredName;
		try {
			filteredName = checkAndFilterName(name);
		} catch (InvalidNameException e) {
			throw new NotRegisteredException(e.getMessage());
		}
		
		if (worlds.containsKey(filteredName.toLowerCase()))
			throw new AlreadyRegisteredException("The world " + filteredName + " is already in use.");
		
		worlds.put(filteredName.toLowerCase(), new TownyWorld(filteredName));
	}
	
	public String checkAndFilterName(String name) throws InvalidNameException {
		String out = TownySettings.filterName(name);
		
		if (!TownySettings.isValidName(out))
			throw new InvalidNameException(out + " is an invalid name.");
		
		return out;
	}

	public boolean hasResident(String name) {
		return residents.containsKey(name.toLowerCase());
	}

	public boolean hasTown(String name) {
		return towns.containsKey(name.toLowerCase());
	}
	
	public boolean hasNation(String name) {
		return nations.containsKey(name.toLowerCase());
	}

	public void renameTown(Town town, String newName) throws AlreadyRegisteredException {
		if (hasTown(newName))
			throw new AlreadyRegisteredException("The town " + newName + " is already in use.");

		// TODO: Delete/rename any invites.

		String oldName = town.getName();
		towns.put(newName.toLowerCase(), town);
		//Tidy up old files
		// Has to be done here else the town no longer exists and the move command may fail.
		getDataSource().deleteTown(town);
		
		towns.remove(oldName.toLowerCase());
		town.setName(newName);
		Town oldTown = new Town(oldName);
		
		try {
			oldTown.pay(oldTown.getHoldingBalance(), town);
		} catch (IConomyException e) {
		}
		getDataSource().saveTown(town);
		getDataSource().saveTownList();
		
	}
	
	public void renameNation(Nation nation, String newName) throws AlreadyRegisteredException {
		if (hasNation(newName))
			throw new AlreadyRegisteredException("The nation " + newName + " is already in use.");

		// TODO: Delete/rename any invites.

		String oldName = nation.getName();
		nations.put(newName.toLowerCase(), nation);
		//Tidy up old files
		getDataSource().deleteNation(nation);
				
		nations.remove(oldName.toLowerCase());
		nation.setName(newName);
		Nation oldNation = new Nation(oldName);
		
		try {
			oldNation.pay(oldNation.getHoldingBalance(), nation);
		} catch (IConomyException e) {
		}
		getDataSource().saveNation(nation);
		getDataSource().saveNationList();
		
	}

	public Resident getResident(String name) throws NotRegisteredException {
		Resident resident = residents.get(name.toLowerCase());
		if (resident == null)
			throw new NotRegisteredException(name + " is not registered.");
		return resident;
	}

	public void sendMessage(Player player, List<String> lines) {
		sendMessage(player, lines.toArray(new String[0]));
	}

	public void sendTownMessage(Town town, List<String> lines) {
		sendTownMessage(town, lines.toArray(new String[0]));
	}

	public void sendNationMessage(Nation nation, List<String> lines) {
		sendNationMessage(nation, lines.toArray(new String[0]));
	}

	public void sendGlobalMessage(List<String> lines) {
		sendGlobalMessage(lines.toArray(new String[0]));
	}

	public void sendGlobalMessage(String line) {
		for (Player player : getOnlinePlayers())
			player.sendMessage(line);
	}
	
	public void sendMessage(Player player, String[] lines) {
		for (String line : lines)
			player.sendMessage(line);
	}

	public Player getPlayer(Resident resident) throws TownyException {
		for (Player player : getOnlinePlayers())
			if (player.getName().equals(resident.getName()))
				return player;
		throw new TownyException("Resident is not online");
	}

	public void sendResidentMessage(Resident resident, String[] lines) throws TownyException {
		for (String line : lines)
			plugin.log("[Resident Msg] " + resident.getName() + ": " + line);
		Player player = getPlayer(resident);
		for (String line : lines)
			player.sendMessage(line);
		
	}

	public void sendTownMessage(Town town, String[] lines) {
		for (String line : lines)
			plugin.log("[Town Msg] " + town.getName() + ": " + line);
		for (Player player : getOnlinePlayers(town)){
			for (String line : lines)
				player.sendMessage(line);
		}
	}

	public void sendNationMessage(Nation nation, String[] lines) {
		for (String line : lines)
			plugin.log("[Nation Msg] " + nation.getName() + ": " + line);
		for (Player player : getOnlinePlayers(nation))
			for (String line : lines)
				player.sendMessage(line);
	}

	public void sendGlobalMessage(String[] lines) {
		for (String line : lines)
			plugin.log("[Global Msg] " + line);
		for (Player player : getOnlinePlayers())
			for (String line : lines)
				player.sendMessage(line);
	}

	public void sendResidentMessage(Resident resident, String line) throws TownyException {
		plugin.log("[Resident Msg] " + resident.getName() + ": " + line);
		Player player = getPlayer(resident);
		player.sendMessage(TownySettings.getLangString("default_towny_prefix") + line);
	}

	public void sendTownMessage(Town town, String line) {
		plugin.log("[Town Msg] " + town.getName() + ": " + line);
		for (Player player : getOnlinePlayers(town))
			player.sendMessage(TownySettings.getLangString("default_towny_prefix") + line);
	}
	
	public void sendNationMessage(Nation nation, String line) {
		plugin.log("[Nation Msg] " + nation.getName() + ": " + line);
		for (Player player : getOnlinePlayers(nation))
			player.sendMessage(line);
	}

	public void sendTownBoard(Player player, Town town) {
		for (String line : ChatTools.color(Colors.Gold + "[" + town.getName() + "] " + Colors.Yellow + town.getTownBoard()))
			player.sendMessage(line);
	}

	public Player[] getOnlinePlayers() {
		return plugin.getServer().getOnlinePlayers();
	}

	public List<Player> getOnlinePlayers(ResidentList residents) {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Player player : getOnlinePlayers())
			if (residents.hasResident(player.getName()))
				players.add(player);
		return players;
	}
	
	public List<Player> getOnlinePlayers(Town town) {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Player player : getOnlinePlayers())
			if (town.hasResident(player.getName()))
				players.add(player);
		return players;
	}

	public List<Player> getOnlinePlayers(Nation nation) {
		ArrayList<Player> players = new ArrayList<Player>();
		for (Town town : nation.getTowns())
			players.addAll(getOnlinePlayers(town));
		return players;
	}

	public List<Resident> getResidents() {
		return new ArrayList<Resident>(residents.values());
	}

	public Set<String> getResidentKeys() {
		return residents.keySet();
	}

	public List<Town> getTowns() {
		return new ArrayList<Town>(towns.values());
	}

	public List<Nation> getNations() {
		return new ArrayList<Nation>(nations.values());
	}

	public List<TownyWorld> getWorlds() {
		return new ArrayList<TownyWorld>(worlds.values());
	}
	
	public List<Town> getTownsWithoutNation() {
		List<Town> townFilter = new ArrayList<Town>();
		for (Town town : getTowns())
			if (!town.hasNation())
				townFilter.add(town);
		return townFilter;
	}
	
	public List<Resident> getResidentsWithoutTown() {
		List<Resident> residentFilter = new ArrayList<Resident>();
		for (Resident resident : getResidents())
			if (!resident.hasTown())
				residentFilter.add(resident);
		return residentFilter;
	}

	public List<Resident> getActiveResidents() {
		List<Resident> activeResidents = new ArrayList<Resident>();
		for (Resident resident : getResidents())
			if (isActiveResident(resident))
				activeResidents.add(resident);
		return activeResidents;
	}

	public boolean isActiveResident(Resident resident) {
		return System.currentTimeMillis() - resident.getLastOnline() < TownySettings.getInactiveAfter();
	}
	
	public List<Resident> getResidents(String[] names) {
		List<Resident> matches = new ArrayList<Resident>();
		for (String name : names)
			try {
				matches.add(getResident(name));
			} catch (NotRegisteredException e) {
			}
		return matches;
	}
	
	public List<Town> getTowns(String[] names) {
		List<Town> matches = new ArrayList<Town>();
		for (String name : names)
			try {
				matches.add(getTown(name));
			} catch (NotRegisteredException e) {
			}
		return matches;
	}
	
	public List<Nation> getNations(String[] names) {
		List<Nation> matches = new ArrayList<Nation>();
		for (String name : names)
			try {
				matches.add(getNation(name));
			} catch (NotRegisteredException e) {
			}
		return matches;
	}
	
	public List<String> getStatus(Resident resident) {
		return getFormatter().getStatus(resident);
	}

	public List<String> getStatus(Town town) {
		return getFormatter().getStatus(town);
	}

	public List<String> getStatus(Nation nation) {
		return getFormatter().getStatus(nation);
	}
	
	public List<String> getStatus(TownyWorld world) {
		return getFormatter().getStatus(world);
	}

	public Town getTown(String name) throws NotRegisteredException {
		Town town = towns.get(name.toLowerCase());
		if (town == null)
			throw new NotRegisteredException(name + " is not registered.");
		return town;
	}

	public Nation getNation(String name) throws NotRegisteredException {
		Nation nation = nations.get(name.toLowerCase());
		if (nation == null)
			throw new NotRegisteredException(name + " is not registered.");
		return nation;
	}
	
	public String getRootFolder() {
		if (plugin != null)
			return plugin.getDataFolder().getPath();
		else
			return rootFolder;
	}

	public boolean loadSettings() {
		try {
			FileMgmt.checkFolders(new String[]{
					getRootFolder(),
					getRootFolder() + FileMgmt.fileSeparator() + "settings"});
			/*
			FileMgmt.checkFiles(new String[]{
					getRootFolder() + FileMgmt.fileSeparator() + "settings" + FileMgmt.fileSeparator() + "town-levels.csv",
					getRootFolder() + FileMgmt.fileSeparator() + "settings" + FileMgmt.fileSeparator() + "nation-levels.csv"});
					*/
			TownySettings.loadConfig(getRootFolder() + FileMgmt.fileSeparator() + "settings" + FileMgmt.fileSeparator() + "config.yml", "/plugin.yml");
			TownySettings.loadLanguage(getRootFolder() + FileMgmt.fileSeparator() + "settings", "/english.yml");
			//TownySettings.loadTownLevelConfig(getRootFolder() + FileMgmt.fileSeparator() + "settings" + FileMgmt.fileSeparator() + "town-levels.csv");
			//TownySettings.loadNationLevelConfig(getRootFolder() + FileMgmt.fileSeparator() + "settings" + FileMgmt.fileSeparator() + "nation-levels.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		System.out.println("[Towny] Database: [Load] " + TownySettings.getLoadDatabase() + " [Save] " + TownySettings.getSaveDatabase());
		if (!loadDatabase(TownySettings.getLoadDatabase())) {
			System.out.println("[Towny] Error: Failed to load!");
			return false;
		}
		
		try {
			setDataSource(TownySettings.getSaveDatabase());
			getDataSource().initialize(plugin, this);
			try {
				getDataSource().backup();
			} catch (IOException e) {
				System.out.println("[Towny] Error: Could not create backup.");
				e.printStackTrace();
				return false;
			}
			
			//if (TownySettings.isSavingOnLoad())
			//	townyUniverse.getDataSource().saveAll();
		} catch (UnsupportedOperationException e) {
			System.out.println("[Towny] Error: Unsupported save format!");
			return false;
		}
		
		
		return true;
	}

	public boolean loadDatabase(String databaseType) {
		try {
			setDataSource(databaseType);
		} catch (UnsupportedOperationException e) {
			return false;
		}

		getDataSource().initialize(plugin, this);
		
		// make sure all tables are clear before loading
		worlds.clear();
		nations.clear();
		towns.clear();
		residents.clear();
		
		return getDataSource().loadAll();
	}

	public TownyWorld getWorld(String name) throws NotRegisteredException {
		TownyWorld world = worlds.get(name.toLowerCase());
		if (world == null) {
			try {
				newWorld(name);
			} catch (AlreadyRegisteredException e) {
				throw new NotRegisteredException("Not registered, but already registered when trying to register.");
			} catch (NotRegisteredException e) {
				e.printStackTrace();
			}
			world = worlds.get(name.toLowerCase());
			if (world == null)
				throw new NotRegisteredException("Could not create world " + name.toLowerCase());
		}
		return world;
	}
	
	public boolean isAlly(String a, String b) {
		try {
			Resident residentA = getResident(a);
			Resident residentB = getResident(b);
			if (residentA.getTown() == residentB.getTown())
				return true;
			if (residentA.getTown().getNation() == residentB.getTown().getNation())
				return true;
			if (residentA.getTown().getNation().hasAlly(residentB.getTown().getNation()))
				return true;
		} catch (NotRegisteredException e) {
			return false;
		}
		return false;
	}

	public boolean isAlly(Town a, Town b) {
		try {
			if (a == b)
				return true;
			if (a.getNation() == b.getNation())
				return true;
			if (a.getNation().hasAlly(b.getNation()))
				return true;
		} catch (NotRegisteredException e) {
			return false;
		}
		return false;
	}

	public void setDataSource(String databaseType) throws UnsupportedOperationException {
		if (databaseType.equalsIgnoreCase("flatfile"))
			setDataSource(new TownyFlatFileSource());
		else if (databaseType.equalsIgnoreCase("flatfile-hmod"))
			setDataSource(new TownyHModFlatFileSource());
		else
			throw new UnsupportedOperationException();
	}
	
	public void setDataSource(TownyDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public TownyDataSource getDataSource() {
		return dataSource;
	}

	public void setFormatter(TownyFormatter formatter) {
		this.formatter = formatter;
	}

	public TownyFormatter getFormatter() {
		return formatter;
	}

	public boolean isWarTime() {
		return warEvent != null ? warEvent.isWarTime() : false;
	}

	public void collectNationTaxes() throws IConomyException {
		for (Nation nation : new ArrayList<Nation>(nations.values()))
			collectNationTaxes(nation);
	}

	public void collectNationTaxes(Nation nation) throws IConomyException {
		if (nation.getTaxes() > 0)
			for (Town town : new ArrayList<Town>(nation.getTowns())) {
				if (town.isCapital())
					continue;
				if (!town.pay(nation.getTaxes(), nation)) {
					try {
						sendNationMessage(nation, TownySettings.getCouldntPayTaxesMsg(town, "nation"));
						nation.removeTown(town);
					} catch (EmptyNationException e) {
						// Always has 1 town (capital) so ignore
					} catch (NotRegisteredException e) {
					}
					getDataSource().saveTown(town);
					getDataSource().saveNation(nation);
				} else
					sendTownMessage(town, TownySettings.getPayedTownTaxMsg() + nation.getTaxes());
			}
	}

	public void collectTownTaxes() throws IConomyException {
		for (Town town : new ArrayList<Town>(towns.values()))
			collectTownTaxe(town);
	}

	public void collectTownTaxe(Town town) throws IConomyException {
		//Resident Tax
		if (town.getTaxes() > 0)
			for (Resident resident : new ArrayList<Resident>(town.getResidents()))
				if (town.isMayor(resident) || town.hasAssistant(resident)) {
					try {
						sendResidentMessage(resident, TownySettings.getTaxExemptMsg());
					} catch (TownyException e) {
					}
					continue;
				} else if (!resident.pay(town.getTaxes(), town)) {
					sendTownMessage(town, TownySettings.getCouldntPayTaxesMsg(resident, "town"));
					try {
						town.removeResident(resident);
					} catch (NotRegisteredException e) {
					} catch (EmptyTownException e) {
					}
					getDataSource().saveResident(resident);
					getDataSource().saveTown(town);
				} else
					try {
						sendResidentMessage(resident, TownySettings.getPayedResidentTaxMsg() + town.getTaxes());
					} catch (TownyException e1) {
					}
				
		
		//Plot Tax
		if (town.getPlotTax() > 0) {
			Hashtable<Resident,Integer> townPlots = new Hashtable<Resident,Integer>();
			for (TownBlock townBlock : new ArrayList<TownBlock>(town.getTownBlocks())) {
				if (!townBlock.hasResident())
					continue;
				try {
					Resident resident = townBlock.getResident();
					if (town.isMayor(resident) || town.hasAssistant(resident))
						continue;
					if (!resident.pay(town.getPlotTax(), town)) {
						sendTownMessage(town,  String.format(TownySettings.getLangString("msg_couldnt_pay_plot_taxes"), resident));
						townBlock.setResident(null);
						getDataSource().saveResident(resident);
						getDataSource().saveWorld(townBlock.getWorld());
					} else
						townPlots.put(resident, (townPlots.containsKey(resident) ? townPlots.get(resident) : 0) + 1);
				} catch (NotRegisteredException e) {
				}
			}
			for (Resident resident : townPlots.keySet())
				try {
					int numPlots = townPlots.get(resident);
					int totalCost = town.getPlotTax() * numPlots;
					sendResidentMessage(resident, String.format(TownySettings.getLangString("msg_payed_plot_cost"), totalCost, numPlots, town.getName()));
				} catch (TownyException e) {
				}
		}
	}

	public void startWarEvent() {
		this.warEvent = new War(plugin, TownySettings.getWarTimeWarningDelay());
	}
	
	public void endWarEvent() {
		if (isWarTime())
			warEvent.toggleEnd();
		// Automatically makes warEvent null
	}
	
	public void clearWarEvent() {
		getWarEvent().cancleTasks(getPlugin().getServer().getScheduler());
		setWarEvent(null);
	}
	
	//TODO: throw error if null
	public War getWarEvent() {
		return warEvent;
	}

	public void setWarEvent(War warEvent) {
		this.warEvent = warEvent;
	}
	
	public Towny getPlugin() {
		return plugin;
	}

	public void setPlugin(Towny plugin) {
		this.plugin = plugin;
	}

	public void removeWorld(TownyWorld world) throws UnsupportedOperationException {
		getDataSource().deleteWorld(world);
		throw new UnsupportedOperationException();
	}
	
	public void removeNation(Nation nation) {
		getDataSource().deleteNation(nation);
		List<Town> toSave = new ArrayList<Town>(nation.getTowns());
		nation.clear();
		try {
			nation.pay(nation.getHoldingBalance(), new WarSpoils());
		} catch (IConomyException e) {
		}
		nations.remove(nation.getName().toLowerCase());
		plugin.updateCache();
		for (Town town : toSave)
			getDataSource().saveTown(town);
		getDataSource().saveNationList();
	}

	////////////////////////////////////////////
	
	
	public void removeTown(Town town) {
		getDataSource().deleteTown(town);
		List<Resident> toSave = new ArrayList<Resident>(town.getResidents());
		try {
			
			if (town.hasNation()) {
				Nation nation = town.getNation();
				nation.removeTown(town);
				getDataSource().saveNation(nation);
			}
			town.clear();
		} catch (EmptyNationException e) {
			removeNation(e.getNation());
		} catch (NotRegisteredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			town.pay(town.getHoldingBalance(), new WarSpoils());
		} catch (IConomyException e) {
		}
		
		
		for (Resident resident : toSave) {
			removeResident(resident);
			//getDataSource().saveResident(resident);
		}
		
		towns.remove(town.getName().toLowerCase());
		plugin.updateCache();

		getDataSource().saveTownList();
	}

	public void removeResident(Resident resident) {

		getDataSource().deleteResident(resident);
		try {
			if (resident.hasTown()) {
				Town town = resident.getTown();
				town.removeResident(resident);
				getDataSource().saveTown(town);
			}
			resident.clear();
		} catch (EmptyTownException e) {
			removeTown(e.getTown());
		} catch (NotRegisteredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String name = resident.getName();
		//residents.remove(name.toLowerCase());
		//plugin.deleteCache(name);
		//getDataSource().saveResidentList();
	}
	
	/////////////////////////////////////////////
	
	
	public void sendUniverseTree(CommandSender sender) {
		for (String line : getTreeString(0))
			sender.sendMessage(line);
	}

	public void removeTownBlock(TownBlock townBlock) {
		Resident resident = null;
		Town town = null;
		try {
			resident = townBlock.getResident();
		} catch (NotRegisteredException e) {
		}
		try {
			town = townBlock.getTown();
		} catch (NotRegisteredException e) {
		}
		TownyWorld world = townBlock.getWorld();
		world.removeTownBlock(townBlock);
		getDataSource().saveWorld(world);
		if (resident != null)
			getDataSource().saveResident(resident);
		if (town != null)
			getDataSource().saveTown(town);
	}
	
	public void removeTownBlocks(Town town) {
		for (TownBlock townBlock : new ArrayList<TownBlock>(town.getTownBlocks()))
			removeTownBlock(townBlock);
	}

	public void collectTownCosts() throws IConomyException {
		for (Town town : new ArrayList<Town>(towns.values()))
			if (!town.pay(TownySettings.getTownUpkeepCost(town))) {
				removeTown(town);
				sendGlobalMessage(town.getName() + TownySettings.getLangString("msg_bankrupt_town"));
			}
	}
	
	public void collectNationCosts() throws IConomyException {
		for (Nation nation : new ArrayList<Nation>(nations.values())) {
			if (!nation.pay(TownySettings.getNationUpkeepCost(nation))) {
				removeNation(nation);
				sendGlobalMessage(nation.getName() + TownySettings.getLangString("msg_bankrupt_nation"));
			}
			if (nation.isNeutral())
				if (!nation.pay(TownySettings.getNationNeutralityCost())) {
					try {
						nation.setNeutral(false);
					} catch (TownyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getDataSource().saveNation(nation);
					sendNationMessage(nation, TownySettings.getLangString("msg_nation_not_neutral"));
				}
		}
		
	}
	
	public List<TownBlock> getAllTownBlocks() {
		List<TownBlock> townBlocks = new ArrayList<TownBlock>();
		for (TownyWorld world : getWorlds())
			townBlocks.addAll(world.getTownBlocks());
		return townBlocks;
	}
	
	@Override
	public List<String> getTreeString(int depth) {
		List<String> out = new ArrayList<String>();
		out.add(getTreeDepth(depth) + "Universe ("+getName()+")");
		if (plugin != null) {
			out.add(getTreeDepth(depth+1) + "Server ("+plugin.getServer().getName()+")");
			out.add(getTreeDepth(depth+2) + "Version: " + plugin.getServer().getVersion());
			out.add(getTreeDepth(depth+2) + "Players: " + plugin.getServer().getOnlinePlayers().length + "/" + plugin.getServer().getMaxPlayers());
			out.add(getTreeDepth(depth+2) + "Worlds (" + plugin.getServer().getWorlds().size() + "): " + Arrays.toString(plugin.getServer().getWorlds().toArray(new World[0])));
		}
		out.add(getTreeDepth(depth+1) + "Worlds (" + getWorlds().size() + "):");
		for (TownyWorld world : getWorlds())
			out.addAll(world.getTreeString(depth+2));
		
		out.add(getTreeDepth(depth+1) + "Nations (" + getNations().size() + "):");
		for (Nation nation : getNations())
			out.addAll(nation.getTreeString(depth+2));
		
		Collection<Town> townsWithoutNation = getTownsWithoutNation();
		out.add(getTreeDepth(depth+1) + "Towns (" + townsWithoutNation.size() + "):");
		for (Town town : townsWithoutNation)
			out.addAll(town.getTreeString(depth+2));
		
		Collection<Resident> residentsWithoutTown = getResidentsWithoutTown();
		out.add(getTreeDepth(depth+1) + "Residents (" + residentsWithoutTown.size() + "):");
		for (Resident resident : residentsWithoutTown)
			out.addAll(resident.getTreeString(depth+2));
		return out;
	}

	public boolean areAllAllies(List<Nation> possibleAllies) {
		if (possibleAllies.size() <= 1)
			return true;
		else {
			for (int i = 0; i < possibleAllies.size() - 1; i++)
				if (!possibleAllies.get(i).hasAlly(possibleAllies.get(i+1)))
					return false;
			return true;
		}
	}

	public void sendMessageTo(ResidentList residents, String msg, String modeRequired) {
		for (Player player : getOnlinePlayers(residents))
			if (plugin.hasPlayerMode(player, modeRequired))
				player.sendMessage(msg);
	}
	
	public List<Resident> getOnlineResidents(Player player, String[] names) {
		List<Resident> invited = new ArrayList<Resident>();
		for (String name : names) {
			List<Player> matches = plugin.getServer().matchPlayer(name);
			if (matches.size() > 1) {
				String line = "Multiple players selected";
				for (Player p : matches)
					line += ", " + p.getName();
				plugin.sendErrorMsg(player, line);
			} else if (matches.size() == 1)
				try {
					Resident target = plugin.getTownyUniverse().getResident(matches.get(0).getName());
					invited.add(target);
				} catch (TownyException x) {
					plugin.sendErrorMsg(player, x.getError());
				}
		}
		return invited;
	}	
}
