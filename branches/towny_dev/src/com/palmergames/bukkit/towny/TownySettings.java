package com.palmergames.bukkit.towny;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.palmergames.bukkit.config.CommentedConfiguration;
import com.palmergames.bukkit.config.ConfigNodes;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlockOwner;
import com.palmergames.bukkit.towny.object.TownyObject;
import com.palmergames.bukkit.towny.object.TownyPermission.ActionType;
import com.palmergames.bukkit.towny.object.TownyPermission.PermLevel;
import com.palmergames.bukkit.towny.object.WorldCoord;
import com.palmergames.util.FileMgmt;

import org.bukkit.util.config.Configuration;
import org.bukkit.util.config.ConfigurationNode;


public class TownySettings {
	
	// Nation Level
	public enum NationLevel {
		NAME_PREFIX,
		NAME_POSTFIX,
		CAPITAL_PREFIX,
		CAPITAL_POSTFIX,
		KING_PREFIX,
		KING_POSTFIX,
		UPKEEP_MULTIPLIER
	};
	// Town Level
	public enum TownLevel {
		NAME_PREFIX,
		NAME_POSTFIX,
		MAYOR_PREFIX,
		MAYOR_POSTFIX,
		TOWN_BLOCK_LIMIT,
		UPKEEP_MULTIPLIER
	};
	
	
	private static Pattern namePattern = null;	
	private static CommentedConfiguration config, newConfig;
	private static Configuration language;
    //private static Configuration permissions;
	
	private static final SortedMap<Integer,Map<TownySettings.TownLevel,Object>> configTownLevel = 
		Collections.synchronizedSortedMap(new TreeMap<Integer,Map<TownySettings.TownLevel,Object>>(Collections.reverseOrder()));
	private static final SortedMap<Integer,Map<TownySettings.NationLevel,Object>> configNationLevel = 
		Collections.synchronizedSortedMap(new TreeMap<Integer,Map<TownySettings.NationLevel,Object>>(Collections.reverseOrder()));
	
	/*
	static {		
		newTownLevel(0, "", " Town", "Mayor ", "", 16);
		newNationLevel(0, "", " Nation", "Capital: ", " City", "King ", "");
	}
	*/
	
	public static void newTownLevel(int numResidents,
			String namePrefix, String namePostfix,
			String mayorPrefix, String mayorPostfix, 
			int townBlockLimit, double townUpkeepMultiplier) {
		ConcurrentHashMap<TownySettings.TownLevel,Object> m = new ConcurrentHashMap<TownySettings.TownLevel,Object>();
		m.put(TownySettings.TownLevel.NAME_PREFIX, namePrefix);
		m.put(TownySettings.TownLevel.NAME_POSTFIX, namePostfix);
		m.put(TownySettings.TownLevel.MAYOR_PREFIX, mayorPrefix);
		m.put(TownySettings.TownLevel.MAYOR_POSTFIX, mayorPostfix);
		m.put(TownySettings.TownLevel.TOWN_BLOCK_LIMIT, townBlockLimit);
		m.put(TownySettings.TownLevel.UPKEEP_MULTIPLIER, townUpkeepMultiplier);
		configTownLevel.put(numResidents, m);
	}
	
	public static void newNationLevel(int numResidents, 
			String namePrefix, String namePostfix, 
			String capitalPrefix, String capitalPostfix,
			String kingPrefix, String kingPostfix, double nationUpkeepMultiplier) {
		ConcurrentHashMap<TownySettings.NationLevel,Object> m = new ConcurrentHashMap<TownySettings.NationLevel,Object>();
		m.put(TownySettings.NationLevel.NAME_PREFIX, namePrefix);
		m.put(TownySettings.NationLevel.NAME_POSTFIX, namePostfix);
		m.put(TownySettings.NationLevel.CAPITAL_PREFIX, capitalPrefix);
		m.put(TownySettings.NationLevel.CAPITAL_POSTFIX, capitalPostfix);
		m.put(TownySettings.NationLevel.KING_PREFIX, kingPrefix);
		m.put(TownySettings.NationLevel.KING_POSTFIX, kingPostfix);
		m.put(TownySettings.NationLevel.UPKEEP_MULTIPLIER, nationUpkeepMultiplier);
		configNationLevel.put(numResidents, m);
	}
	
	/**
	 * Loads town levels. Level format ignores lines starting with #.
	 * Each line is considered a level. Each level is loaded as such:
	 * 
	 * numResidents:namePrefix:namePostfix:mayorPrefix:mayorPostfix:townBlockLimit
	 * 
	 * townBlockLimit is a required field even if using a calculated ratio.
	 * 
	 * @throws IOException 
	 */
	
	public static void loadTownLevelConfig() throws IOException {
        ArrayList<ConfigurationNode> levels = new ArrayList<ConfigurationNode>(config.getNodeList("levels.town_level", null));
        for (ConfigurationNode level : levels) {
            newTownLevel(level.getInt("numResidents", 0), level.getString("namePrefix", ""),
                    level.getString("namePostfix", ""), level.getString("mayorPrefix", ""),
                    level.getString("mayorPostfix", ""), level.getInt("townBlockLimit", 1),
                    level.getDouble("upkeepModifier", 1.0));
        }

		/*
		//get an Iterator object for list using iterator() method.
		Iterator<String> itr = lines.iterator();

		//use hasNext() and next() methods of Iterator to iterate through the elements
		while(itr.hasNext()) {
                tokens = itr.next().split(",", 7);
                if (tokens.length >= 7)
					try {
                        int numResidents = Integer.parseInt(tokens[0]);
                        int townBlockLimit = Integer.parseInt(tokens[5]);
                        double townUpkeepMult = Double.valueOf(tokens[6]);
                        newTownLevel(numResidents, tokens[1], tokens[2], tokens[3], tokens[4], townBlockLimit, townUpkeepMult);
						if (getDebug())
							// Used to know the actual values registered
							 System.out.println("[Towny] Debug: Added town level: "+numResidents+" "+Arrays.toString(getTownLevel(numResidents).values().toArray()));
							//System.out.println("[Towny] Debug: Added town level: "+numResidents+" "+Arrays.toString(tokens));
                    } catch (Exception e) {
                    	System.out.println("[Towny] Input Error: Town level ignored: " + itr.toString());
                    }
                else
                	System.out.println("[Towny] loadTownLevelConfig bad length");
            //}

		}

		/*
		//BufferedReader fin = new BufferedReader(new FileReader(filepath));
		 
        while ((line = fin.readLine()) != null)
			if (!line.startsWith("#")) { //Ignore comment lines
                tokens = line.split(",", 6);
                if (tokens.length >= 6)
					try {
                        int numResidents = Integer.parseInt(tokens[0]);
                        int townBlockLimit = Integer.parseInt(tokens[5]);
                        newTownLevel(numResidents, tokens[1], tokens[2], tokens[3], tokens[4], townBlockLimit);
						if (getDebug())
							// Used to know the actual values registered
							 System.out.println("[Towny] Debug: Added town level: "+numResidents+" "+Arrays.toString(getTownLevel(numResidents).values().toArray()));
							//System.out.println("[Towny] Debug: Added town level: "+numResidents+" "+Arrays.toString(tokens));
                    } catch (Exception e) {
                    	System.out.println("[Towny] Input Error: Town level ignored: " + line);
                    }
            }
        fin.close();
        */
	}
	
	/**
	 * Loads nation levels. Level format ignores lines starting with #.
	 * Each line is considered a level. Each level is loaded as such:
	 * 
	 * numResidents:namePrefix:namePostfix:capitalPrefix:capitalPostfix:kingPrefix:kingPostfix
	 * 
	 * @throws IOException 
	 */

	public static void loadNationLevelConfig() throws IOException {
		ArrayList<ConfigurationNode> levels = new ArrayList<ConfigurationNode>(config.getNodeList("levels.nation_level", null));
        for (ConfigurationNode level : levels) {
            newNationLevel(level.getInt("numResidents", 0), level.getString("namePrefix", ""),
                    level.getString("namePostfix", ""), level.getString("capitalPrefix", ""),
                    level.getString("capitalPostfix", ""), level.getString("kingPrefix", ""),
                    level.getString("kingPostfix", ""), level.getDouble("upkeepModifier", 1.0));
        }

		/*
		//get an Iterator object for list using iterator() method.
		Iterator<String> itr = lines.iterator();
		
		//use hasNext() and next() methods of Iterator to iterate through the elements
		while(itr.hasNext()) {
			tokens = itr.next().split(",", 8);
            if (tokens.length >= 8)
				try {
                    int numResidents = Integer.parseInt(tokens[0]);
                    double upkeep = Double.valueOf(tokens[7]);
                    newNationLevel(numResidents, tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], upkeep);
					if (getDebug())
						// Used to know the actual values registered
						// System.out.println("[Towny] Debug: Added nation level: "+numResidents+" "+Arrays.toString(getNationLevel(numResidents).values().toArray()));
						System.out.println("[Towny] Debug: Added nation level: "+numResidents+" "+Arrays.toString(tokens));
                } catch (Exception e) {
                	System.out.println("[Towny] Input Error: Nation level ignored: " + itr.toString());
                }

			
		}
		
		/*
		String line;
		String[] tokens;
		BufferedReader fin = new BufferedReader(new FileReader(filepath));
        while ((line = fin.readLine()) != null)
			if (!line.startsWith("#")) { //Ignore comment lines
                tokens = line.split(",", 7);
                if (tokens.length >= 7)
					try {
                        int numResidents = Integer.parseInt(tokens[0]);
                        newNationLevel(numResidents, tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6]);
						if (getDebug())
							// Used to know the actual values registered
							// System.out.println("[Towny] Debug: Added nation level: "+numResidents+" "+Arrays.toString(getNationLevel(numResidents).values().toArray()));
							System.out.println("[Towny] Debug: Added nation level: "+numResidents+" "+Arrays.toString(tokens));
                    } catch (Exception e) {
                    	System.out.println("[Towny] Input Error: Nation level ignored: " + line);
                    }
            }
        fin.close();
        */
	}
	
	public static Map<TownySettings.TownLevel,Object> getTownLevel(int numResidents) {
		return configTownLevel.get(numResidents);
	}
	
	public static Map<TownySettings.NationLevel,Object> getNationLevel(int numResidents) {
		return configNationLevel.get(numResidents);
	}
	
	public static Map<TownySettings.TownLevel,Object> getTownLevel(Town town) {
		return getTownLevel(calcTownLevel(town));
	}
	
	public static Map<TownySettings.NationLevel,Object> getNationLevel(Nation nation) {
		return getNationLevel(calcNationLevel(nation));
	}
	
	//TODO: more efficient way
	public static int calcTownLevel(Town town) {
		int n = town.getNumResidents();
		for (Integer level : configTownLevel.keySet())
			if (n >= level)
				return level;
        return 0;
    }
	
	//TODO: more efficient way
	public static int calcNationLevel(Nation nation) {
		int n = nation.getNumResidents();		
		for (Integer level : configNationLevel.keySet())
			if (n >= level)
				return level;
        return 0;
    }
	
	//////////////////////////////
	
	public static void loadConfig(String filepath, String version) throws IOException {
		
        File file = FileMgmt.CheckYMLExists(new File(filepath));
		if (file != null) {
				
			// read the config.yml into memory
			config = new CommentedConfiguration(file);
			config.load();

            setDefaults(version, file);

            config.save();

			// Load Nation & Town level data into maps.
			loadTownLevelConfig();
			loadNationLevelConfig();
		}
		
	}
	
	// This will read the language entry in the config.yml to attempt to load custom languages
	// if the file is not found it will load the default from resource
	public static void loadLanguage (String filepath, String defaultRes) throws IOException {		
		
		String defaultName = filepath + FileMgmt.fileSeparator() + getString("language", defaultRes);
		
		File file = FileMgmt.unpackLanguageFile(defaultName, defaultRes);
		if (file != null) {
				
			// read the (language).yml into memory
			language = new Configuration(file);
			language.load();
				
		}
	}

	
	private static void sendError(String msg) {
		
		System.out.println("[Towny] Error could not read " + msg);
		
	}
	
	///////////////////////////////////
	
	// Functions to pull data from the config and language files

	private static String[] parseString(String str) {
		return parseSingleLineString(str).split("@");
	}
	
	public static String parseSingleLineString(String str) {
		return str.replaceAll("&", "\u00A7");
	}

    public static Boolean getBoolean(String root, Boolean def) {
        return config.getBoolean(root.toLowerCase(), def);
    }
    /*
	public static Boolean getBoolean(String root){
        return config.getBoolean(root.toLowerCase(), true);
    }
    */

    private static Double getDouble(String root, Double def) {
        return config.getDouble(root.toLowerCase(), def);
    }
    /*
    private static Double getDouble(String root){
        return config.getDouble(root.toLowerCase(), 0);
    }
    */

    private static Integer getInt(String root, Integer def) {
        return config.getInt(root.toLowerCase(), def);
    }
    /*
    private static Integer getInt(String root){
        return config.getInt(root.toLowerCase(), 0);
    }
    */

    private static Long getLong(String root, Long def) {
        return Long.parseLong(getString(root, Long.toString(def)));
    }
    
    /*
    private static Long getLong(String root){
        return Long.parseLong(getString(root).trim());
    }
    */
    
    /*
     * Public Functions to read data from the Configuration
     * and Language data
     * 
     * 
     */

    public static String getString(String root, String def) {
        return config.getString(root.toLowerCase(), def);
    }
    /*
    public static String getString(String root){
    	
    	String data = config.getString(root.toLowerCase());
    	if (data == null) {
    		sendError(root.toLowerCase() + " from config.yml");
    		return "";
    	}
        return data;
    }
    */
    public static String getLangString(String root){
    	
    	String data = language.getString(root.toLowerCase());
    	if (data == null) {
    		sendError(root.toLowerCase() + " from " + config.getString("language"));
    		return "";
    	}
        return parseSingleLineString(data);
    }

    public static List<Integer> getIntArr(String root, String def) {
        String[] strArray = getString(root.toLowerCase(), def).split(",");
		List<Integer> list = new ArrayList<Integer>();
		if (strArray != null) {
		for (int ctr=0; ctr < strArray.length; ctr++)
			if (strArray[ctr] != null)
				list.add(Integer.parseInt(strArray[ctr]));
		}
		return list;
    }
 // read a comma delimited string into an Integer list
    /*
	public static List<Integer> getIntArr(String root) {
		
		String[] strArray = getString(root.toLowerCase()).split(",");
		List<Integer> list = new ArrayList<Integer>();
		if (strArray != null) {
		for (int ctr=0; ctr < strArray.length; ctr++)
			if (strArray[ctr] != null)
				list.add(Integer.parseInt(strArray[ctr]));
		}	
		return list;
	}
	*/

    public static List<String> getStrArr(String root, String def) {
        String[] strArray = getString(root.toLowerCase(), def).split(",");
		List<String> list = new ArrayList<String>();
		if (strArray != null) {
		for (int ctr=0; ctr < strArray.length; ctr++)
			if (strArray[ctr] != null)
				list.add(strArray[ctr].trim());
		}
		return list;
    }
	// read a comma delimited string into a trimmed list.
    /*
	public static List<String> getStrArr(String root) {
		
		String[] strArray = getString(root.toLowerCase()).split(",");
		List<String> list = new ArrayList<String>();
		if (strArray != null) {
		for (int ctr=0; ctr < strArray.length; ctr++)
			if (strArray[ctr] != null)
				list.add(strArray[ctr].trim());
		}
		return list;
	}
	*/

    public static void addComment(String root, String...comments) {
        newConfig.addComment(root.toLowerCase(), comments);
    }

    private static void setDefaults(String version, File file) {
    	
    	newConfig = new CommentedConfiguration(file);
    	
        // Version
        addComment("version", "", "# This is for showing the changelog on updates.  Please do not edit.");
        setNewProperty(ConfigNodes.VERSION.getRoot(), version);
        setNewProperty(ConfigNodes.LAST_RUN_VERSION.getRoot(), getLastRunVersion(version));

        addComment(ConfigNodes.LANGUAGE.getRoot(), "", "# The language file you wish to use");
        getString(ConfigNodes.LANGUAGE.getRoot(), ConfigNodes.LANGUAGE.getDefault());
        
        

        addComment(ConfigNodes.PERMS.getRoot(), "", "#  Possible permission nodes",
        							  "#",
        							  "#    towny.admin: User is able to use /townyadmin, as well as the ability to build/destroy anywhere. User is also able to make towns or nations when set to admin only.",
        							  "#    towny.town.new :User is able to create a town",
        							  "#    towny.town.claim : User is able to expand his town with /town claim",
        							  "#    towny.town.resident : User is able to join towns upon invite.",
        							  "#    towny.nation.new :User is able to create a nation",
        							  "#    towny.wild.*: User is able to build/destroy in wild regardless.",
        							  "#        towny.wild.build",
        							  "#        towny.wild.destroy",
        							  "#        towny.wild.switch",
        							  "#        towny.wild.item_use",
        							  "#    towny.wild.block.[block id].* : User is able to edit [block id] in the wild.",
        							  "#    towny.spawntp :Use /town spawn",
        							  "#    towny.publicspawntp : Use ''/town spawn [town]'' (teleport to other towns)",
        							  "#",
        							  "# these will be moved to permissions nodes at a later date");
        setNewProperty(ConfigNodes.PERMS_TOWN_CREATION_ADMIN_ONLY.getRoot(),isTownCreationAdminOnly());
        setNewProperty(ConfigNodes.PERMS_NATION_CREATION_ADMIN_ONLY.getRoot(),isNationCreationAdminOnly());

        addComment(ConfigNodes.LEVELS.getRoot(), "", "");
        setDefaultLevels();

        addComment(ConfigNodes.TOWN.getRoot(), "", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |               Town Claim/new defaults                | #",
                "# +------------------------------------------------------+ #",
                "############################################################", "");
        addComment(ConfigNodes.TOWN_MAX_PLOTS_PER_RESIDENT.getRoot(), "# maximum number of plots any single resident can own");
        setNewProperty(ConfigNodes.TOWN_MAX_PLOTS_PER_RESIDENT.getRoot(), getMaxPlotsPerResident());
        addComment(ConfigNodes.TOWN_LIMIT.getRoot(), "# Maximum number of towns allowed on the server.");
        getTownLimit();
        addComment(ConfigNodes.TOWN_MIN_DISTANCE_FROM_TOWN_HOMEBLOCK.getRoot(), "",
                "# Minimum number of plots any towns home plot must be from the next town.",
                "# This will prevent someone founding a town right on your doorstep");
        setNewProperty(ConfigNodes.TOWN_MIN_DISTANCE_FROM_TOWN_HOMEBLOCK.getRoot(), getMinDistanceFromTownHomeblocks());
        addComment(ConfigNodes.TOWN_MAX_DISTANCE_BETWEEN_HOMEBLOCKS.getRoot(), "",
                "# Maximum distance between homblocks.",
                "# This will force players to build close together.");
        setNewProperty(ConfigNodes.TOWN_MAX_DISTANCE_BETWEEN_HOMEBLOCKS.getRoot(), getMaxDistanceBetweenHomeblocks());
        addComment(ConfigNodes.TOWN_TOWN_BLOCK_RATIO.getRoot(), "",
                "# The maximum townblocks available to a town is (numResidents * ratio).",
                "# Setting this value to 0 will instead use the level based jump values determined in the town level config.");
        setNewProperty(ConfigNodes.TOWN_TOWN_BLOCK_RATIO.getRoot(), getTownBlockRatio());
        addComment(ConfigNodes.TOWN_TOWN_BLOCK_SIZE.getRoot(),
                "# The size of the square grid cell. Changing this value is suggested only when you first install Towny.",
                "# Doing so after entering data will shift things unwantedly. Using smaller value will allow higher precision,",
                "# at the cost of more work setting up. Also, extremely small values will render the caching done useless.",
                "# Each cell is (town_block_size * town_block_size * 128) in size, with 128 being from bedrock to clouds.");
        setNewProperty(ConfigNodes.TOWN_TOWN_BLOCK_SIZE.getRoot(), getTownBlockSize());

        addComment(ConfigNodes.NWS.getRoot(), "", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |             Default new world settings               | #",
                "# +------------------------------------------------------+ #",
                "############################################################",
                "",
                "# These flags are only used at the initial setp of a new world.",
                "",
                "# Once Towny is running each world can be altered from within game",
                "# using '/townyworld toggle'", "");
        addComment("new_world_settings.FORCE_PVP_ON",
                "# force_pvp_on is a global flag and overrides any towns flag setting");
        isForcingPvP();
        addComment("new_world_settings.DISABLE_PLAYER_CROP_TRAMPLING",
                "# Disable players trampling crops");
        isPlayerTramplingCropsDisabled();
        addComment("new_world_settings.DISABLE_CREATURE_CROP_TRAMPLING",
                "# Disable creatures trampling crops");
        isCreatureTramplingCropsDisabled();
        addComment("new_world_settings.WORLD_MONSTERS_ON",
                "# world_monsters_on is a global flag setting per world.");
        isWorldMonstersOn();
        addComment("new_world_settings.FORCE_EXPLOSIONS_ON",
                "# force_explosions_on is a global flag and overrides any towns flag setting");
        isForcingExplosions();
        addComment("new_world_settings.FORCE_TOWN_MONSTERS_ON",
                "# force_monsters_on is a global flag and overrides any towns flag setting");
        isForcingMonsters();
        addComment("new_world_settings.FORCE_FIRE_ON",
                "# force_fire_on is a global flag and overrides any towns flag setting");
        isForcingFire();

        addComment("global_town_settings", "", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |                Global town settings                  | #",
                "# +------------------------------------------------------+ #",
                "############################################################", "");
        addComment("GLOBAL_TOWN_SETTINGS.FRIENDLY_FIRE",
                "# can residents/Allies harm other residents when in a town with pvp enabled?");
        getFriendlyFire();
        addComment("GLOBAL_TOWN_SETTINGS.HEALTH_REGEN", "",
                "# Players within their town or allied towns will regenerate half a heart after every health_regen_speed milliseconds.");
        hasHealthRegen();
        getHealthRegenSpeed();
        addComment("GLOBAL_TOWN_SETTINGS.DAILY_TAXES", "",
                "# Enables taxes to be collected daily by town/nation",
                "# If a town can't pay it's tax then it is kicked from the nation.",
                "# if a resident can't pay his plot tax he loses his plot.",
                "# if a resident can't pay his town tax then he is kicked from the town.",
                "# if a town or nation fails to pay it's upkeep it is deleted.");
        isTaxingDaily();
        addComment("GLOBAL_TOWN_SETTINGS.ALLOW_OUTPOSTS", "",
                "# Allow towns to claim outposts (a townblock not connected to town).");
        isAllowingOutposts();
        addComment("GLOBAL_TOWN_SETTINGS.ALLOW_TOWN_SPAWN", "# Allow the use of /town spawn");
        isAllowingTownSpawn();
        addComment("GLOBAL_TOWN_SETTINGS.ALLOW_TOWN_SPAWN_TRAVEL",
                "# Allow regular residents to use /town spawn [town] (TP to other towns if they are public).");
        isAllowingPublicTownSpawnTravel();
        addComment("GLOBAL_TOWN_SETTINGS.TOWN_RESPAWN",
                "# Respawn the player at his town spawn point when he/she dies");
        isTownRespawning();
        addComment("GLOBAL_TOWN_SETTINGS.PREVENT_TOWN_SPAWN_IN",
                "# Prevent players from using /town spawn while within unclaimed areas and/or enemy/neutral towns.",
                "# Allowed options: unclaimed, enemy, neutral");
        getDisallowedTownSpawnZones();
        addComment("GLOBAL_TOWN_SETTINGS.SHOW_TOWN_NOTIFICATIONS",
                "# Enables the [~Home] message.",
                "# If false it will make it harder for enemies to find the home block during a war");
        getShowTownNotifications();

        addComment("plugin", "", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |                 Plugin interfacing                   | #",
                "# +------------------------------------------------------+ #",
                "############################################################", "");
        addComment("plugin.interfacing.USING_ESSENTIALS",
                "# Enable if you are using cooldowns in essentials for teleports.");
        isUsingEssentials();
        isUsingIConomy();
        isUsingPermissions();
        isUsingQuestioner();
        getLoadDatabase();
        getSaveDatabase();
        isBackingUpDaily();
        getFlatFileBackupType();
        addComment("plugin.DAY_INTERVAL",
                "# The time taken between each \"day\". At the start of each day, taxes will be collected.",
                "# Judged in milliseconds. Default is 24 hours.");
        getDayInterval();
        addComment("plugin.DEBUG_MODE",
                "# Lots of messages to tell you what's going on in the server with time taken for events.");
        getDebug();
        addComment("plugin.dev_mode",
                "# Spams the player named in dev_name with all messages related to towny.");
        isDevMode();
        getDevName();
        addComment("plugin.LOGGING",
                "# Record all messages to the towny.log");
        isLogging();
        addComment("plugin.RESET_LOG_ON_BOOT",
                "# If true this will cause the log to be wiped at every startup.");
        isAppendingToLog();

        addComment("filters_colour_chat", "", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |               Filters colour and chat                | #",
                "# +------------------------------------------------------+ #",
                "############################################################", "");
        
        addComment("filters_colour_chat.regex", "",
        		"# Regex fields used in validating inputs.");
        getNameCheckRegex();
        getNameFilterRegex();
        getNameRemoveRegex();
        addComment("filters_colour_chat.NPC_PREFIX", "# Not a good idea to change this at the moment.");
        getNPCPrefix();
        addComment("filters_colour_chat.modify_chat", "",
                "# The format below will specify the changes made to the player name when chatting.",
                "# keys are",
                "# {nation} - Displays nation name in [ ] if a member of a nation.",
                "# {town} - Displays town name in [ ] if a member of a town.",
                "# {permprefix} - Permission group prefix",
                "# {townynameprefix} - Towny name prefix taken from the townLevel/nationLevels",
                "# {playername} - default player name",
                "# {modplayername} - modified player name (use if Towny is over writing some other plugins changes).",
                "# {townynamepostfix} - Towny name postfix taken from the townLevel/nationLevels.",
                "# {permsuffix} - Permission group suffix.");
        getModifyChatFormat();
        isUsingModifyChat();
        addComment("filters_colour_chat.colour", "",
                "# Text colouring",
                "# --------------",
                "# Black = &0, Navy = &1, Green = &2, Blue = &3, Red = &4",
                "# Purple = &5, Gold = &6, LightGray = &7, Gray = &8",
                "# DarkPurple = &9, LightGreen = &a, LightBlue = &b",
                "# Rose = &c, LightPurple = &d, Yellow = &e, White = &f",
                "",
                "# Chat colours");
        getKingColour();
        getMayorColour();

        addComment("protection", "", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |             block/item/mob protection                | #",
                "# +------------------------------------------------------+ #",
                "############################################################", "");
        addComment("protection.item_use_ids", "",
                "# Items that can be blocked within towns via town/plot flags",
                "# 259 - flint and steel",
                "# 325 - bucket",
                "# 326 - water bucket",
                "# 327 - lava bucket",
                "# 351 - bone/bonemeal");
        getItemUseIds();
        addComment("protection.switch_ids", "",
                "# Items which can be blocked or enabled via town/plot flags",
                "# 25 - noteblock",
                "# 54 - chest",
                "# 61 - furnace",
                "# 62 - lit furnace",
                "# 64 - wooden door",
                "# 69 - lever",
                "# 70 - stone pressure plate",
                "# 71 - iron door",
                "# 72 - wooden pressure plate",
                "# 77 - stone button",
                "# 96 - trap door",
                "# 84 - jukebox",
                "# 93/94 - redstone repeater");
        getSwitchIds();
        addComment("protection.town_mob_removal_entities", "",
                "# permitted entities http://jd.bukkit.org/apidocs/org/bukkit/entity/package-summary.html",
                "# Animals, Chicken, Cow, Creature, Creeper, Flying, Ghast, Giant, Monster, Pig, ",
                "# PigZombie, Sheep, Skeleton, Slime, Spider, Squid, WaterMob, Wolf, Zombie",
                "",
                "# Remove living entities within a town's boundaries, if the town has the mob removal flag set.");
        getTownMobRemovalEntities();
        addComment("protection.world_mob_removal_entities", "",
                "# Globally remove living entities in all worlds that have their flag set.");
        getWorldMobRemovalEntities();
        addComment("protection.mob_removal_speed", "",
                "# The maximum amount of time a mob could be inside a town's boundaries before being sent to the void.",
                "# Lower values will check all entities more often at the risk of heavier burden and resource use.",
                "# NEVER set below 1000 (guestimate)");
        getMobRemovalSpeed();
        addComment("protection.cheat_protection", "",
                "# Prevent fly and double block jump cheats.");
        isUsingCheatProtection();

        addComment("unclaimed", "", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |             Unclaimed plot settings                  | #",
                "# +------------------------------------------------------+ #",
                "############################################################", "");
        getUnclaimedZoneBuildRights();
        getUnclaimedZoneDestroyRights();
        getUnclaimedZoneItemUseRights();
        getUnclaimedZoneSwitchRights();
        getUnclaimedZoneIgnoreIds();


        addComment("default_perm_flags", "", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |             Default Town/Plot flags                  | #",
                "# +------------------------------------------------------+ #",
                "############################################################",
                "",
                "# Default permission flags for towns",
                "# These are copied into the town data file at creation",
                "");
        addComment("default_perm_flags.town", "",
                "# Can allies/outsiders/residents perform certain actions in the town",
                "",
                "# build - place blocks and other items",
                "# destroy - break blocks and other items",
                "# itemuse - use items such as flint and steel or buckets (as defined in item_use_ids)",
                "# switch - trigger or activate switches (as defined in switch_ids)");

		getPermFlag_Town_Resident_Build();
		getPermFlag_Town_Resident_Destroy();
		getPermFlag_Town_Resident_Switch();
		getPermFlag_Town_Resident_ItemUse();
		getPermFlag_Town_Ally_Build();
		getPermFlag_Town_Ally_Destroy();
		getPermFlag_Town_Ally_Switch();
		getPermFlag_Town_Ally_ItemUse();
		getPermFlag_Town_Outsider_Build();
		getPermFlag_Town_Outsider_Destroy();
		getPermFlag_Town_Outsider_Switch();
		getPermFlag_Town_Outsider_ItemUse();
        addComment("default_perm_flags.resident", "",
                "# Default permission flags for residents plots within a town",
                "",
                "# Can allies/friends/outsiders perform certain actions in the town",
                "",
                "# build - place blocks and other items",
                "# destroy - break blocks and other items",
                "# itemuse - use items such as furnaces (as defined in item_use_ids)",
                "# switch - trigger or activate switches (as defined in switch_ids)");
        getPermFlag_Resident_Friend_Build();
		getPermFlag_Resident_Friend_Destroy();
		getPermFlag_Resident_Friend_Switch();
		getPermFlag_Resident_Friend_ItemUse();
        getPermFlag_Resident_Ally_Build();
		getPermFlag_Resident_Ally_Destroy();
		getPermFlag_Resident_Ally_Switch();
		getPermFlag_Resident_Ally_ItemUse();
        getPermFlag_Resident_Outsider_Build();
		getPermFlag_Resident_Outsider_Destroy();
		getPermFlag_Resident_Outsider_Switch();
		getPermFlag_Resident_Outsider_ItemUse();

        addComment("resident_settings", "", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |                  Resident settings                   | #",
                "# +------------------------------------------------------+ #",
                "############################################################", "");
        addComment("resident_settings.INACTIVE_AFTER_TIME",
                "# player is flagged as inactive after 1 hour (default)");
        getInactiveAfter();
        addComment("resident_settings.delete_old_residents",
                "# if enabled old residents will be kicked and deleted from a town",
                "# after Two months (default) of not logging in");
        isDeletingOldResidents();
        getDeleteTime();
        addComment("resident_settings.DEFAULT_TOWN_NAME",
                "# The name of the town a resident will automatically join when he first registers.");
        getDefaultTownName();

        addComment("economy", "", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |                  Economy settings                    | #",
                "# +------------------------------------------------------+ #",
                "############################################################", "");
        addComment("economy.PRICE_TOWN_SPAWN_TRAVEL", "# Cost to use /town spawn");
        getTownSpawnTravelPrice();
        addComment("economy.PRICE_TOWN_PUBLIC_SPAWN_TRAVEL",
                "# Cost to use /town spawn [town]",
                "# This is paid to the town you goto.");
        getTownPublicSpawnTravelPrice();
        getDeathPrice();
        getWartimeDeathPrice();
        addComment("economy.PRICE_NATION_NEUTRALITY",
                "# The daily upkeep to remain neutral during a war. Neutrality will exclude you from a war event, as well as deterring enemies.");
        getNationNeutralityCost();
        addComment("economy.PRICE_NEW_NATION",
                "# How much it costs to start a nation.");
        getNewNationPrice();
        addComment("economy.PRICE_NEW_TOWN", "# How much it costs to start a town.");
        getNewTownPrice();
        addComment("economy.PRICE_OUTPOST",
                "# How much it costs to make an outpost. An outpost isn't limited to being on the edge of town.");
        getOutpostCost();
        addComment("economy.PRICE_CLAIM_TOWNBLOCK",
                "# The price for a town to expand one townblock.");
        getClaimPrice();
        addComment("economy.PRICE_NATION_UPKEEP",
                "# The server's daily charge on each nation. If a nation fails to pay this upkeep",
                "# all of it's member town are kicked and the Nation is removed.");
        getNationUpkeep();
        addComment("economy.PRICE_TOWN_UPKEEP",
                "# The server's daily charge on each town. If a town fails to pay this upkeep",
                "# all of it's residents are kicked and the town is removed.");
        getTownUpkeep();

        addComment("war", "", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |                  Wartime settings                    | #",
                "# +------------------------------------------------------+ #",
                "############################################################", "");
        addComment("war.WARTIME_NATION_CAN_BE_NEUTRAL",
                "#This setting allows you disable the ability for a nation to pay to remain neutral during a war.");
        isDeclaringNeutral();
        getBaseSpoilsOfWar();
        getWarzoneHomeBlockHealth();
        getMinWarHeight();
        getWarPointsForKill();
        getWarPointsForNation();
        getWarPointsForTown();
        getWarPointsForTownBlock();
        isRemovingOnMonarchDeath();
        getWarzoneTownBlockHealth();
        getWartimeTownBlockLossPrice();
        getWarTimeWarningDelay();
        
        config = newConfig;
        newConfig = null;
    }

    private static void setDefaultLevels() {
        addComment("levels.town_level", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |                 default Town levels                  | #",
                "# +------------------------------------------------------+ #",
                "############################################################", "");
        List<ConfigurationNode> test = new ArrayList<ConfigurationNode>(config.getNodeList("levels.town_level", null));
        if (test.isEmpty()) {
            List<HashMap<String, Object>> levels = new ArrayList<HashMap<String, Object>>();
            HashMap<String, Object> level = new HashMap<String, Object>();
            level.put("numResidents", 0);
            level.put("namePrefix", "");
            level.put("namePostfix", " Ruins");
            level.put("mayorPrefix", "Spirit ");
            level.put("mayorPostfix", "");
            level.put("townBlockLimit", 1);
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 0);
            level.put("namePrefix", "");
            level.put("namePostfix", " Ruins");
            level.put("mayorPrefix", "Spirit ");
            level.put("mayorPostfix", "");
            level.put("townBlockLimit", 1);
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 1);
            level.put("namePrefix", "");
            level.put("namePostfix", " (Settlement)");
            level.put("mayorPrefix", "Hermit ");
            level.put("mayorPostfix", "");
            level.put("townBlockLimit", 16);
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 2);
            level.put("namePrefix", "");
            level.put("namePostfix", " (Hamlet)");
            level.put("mayorPrefix", "Chief ");
            level.put("mayorPostfix", "");
            level.put("townBlockLimit", 32);
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 6);
            level.put("namePrefix", "");
            level.put("namePostfix", " (Village)");
            level.put("mayorPrefix", "Baron Von ");
            level.put("mayorPostfix", "");
            level.put("townBlockLimit", 96);
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 10);
            level.put("namePrefix", "");
            level.put("namePostfix", " (Town)");
            level.put("mayorPrefix", "Viscount ");
            level.put("mayorPostfix", "");
            level.put("townBlockLimit", 160);
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 14);
            level.put("namePrefix", "");
            level.put("namePostfix", " (Large Town)");
            level.put("mayorPrefix", "Count Von ");
            level.put("mayorPostfix", "");
            level.put("townBlockLimit", 224);
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 20);
            level.put("namePrefix", "");
            level.put("namePostfix", " (City)");
            level.put("mayorPrefix", "Earl ");
            level.put("mayorPostfix", "");
            level.put("townBlockLimit", 320);
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 24);
            level.put("namePrefix", "");
            level.put("namePostfix", " (Large City)");
            level.put("mayorPrefix", "Duke ");
            level.put("mayorPostfix", "");
            level.put("townBlockLimit", 384);
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 28);
            level.put("namePrefix", "");
            level.put("namePostfix", " (Metropolis)");
            level.put("mayorPrefix", "Lord ");
            level.put("mayorPostfix", "");
            level.put("townBlockLimit", 448);
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            newConfig.setProperty("levels.town_level", levels);
        }
        addComment("levels.nation_level", "",
                "############################################################",
                "# +------------------------------------------------------+ #",
                "# |                default Nation levels                 | #",
                "# +------------------------------------------------------+ #",
                "############################################################", "");
        ArrayList<ConfigurationNode> nationLevels = new ArrayList<ConfigurationNode>(config.getNodeList("levels.nation_level", null));
        if (nationLevels.isEmpty()) {
            List<HashMap<String, Object>> levels = new ArrayList<HashMap<String, Object>>();
            HashMap<String, Object> level = new HashMap<String, Object>();
            level.put("numResidents", 0);
            level.put("namePrefix", "Land of ");
            level.put("namePostfix", " (Nation)");
            level.put("capitalPrefix", "");
            level.put("capitalPostfix", "");
            level.put("kingPrefix", "Leader ");
            level.put("kingPostfix", "");
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 10);
            level.put("namePrefix", "Federation of ");
            level.put("namePostfix", " (Nation)");
            level.put("capitalPrefix", "");
            level.put("capitalPostfix", "");
            level.put("kingPrefix", "Count ");
            level.put("kingPostfix", "");
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 20);
            level.put("namePrefix", "Dominion of ");
            level.put("namePostfix", " (Nation)");
            level.put("capitalPrefix", "");
            level.put("capitalPostfix", "");
            level.put("kingPrefix", "Duke ");
            level.put("kingPostfix", "");
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 30);
            level.put("namePrefix", "Kingdom of ");
            level.put("namePostfix", " (Nation)");
            level.put("capitalPrefix", "");
            level.put("capitalPostfix", "");
            level.put("kingPrefix", "King ");
            level.put("kingPostfix", "");
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 40);
            level.put("namePrefix", "The ");
            level.put("namePostfix", " Empire");
            level.put("capitalPrefix", "");
            level.put("capitalPostfix", "");
            level.put("kingPrefix", "Emperor ");
            level.put("kingPostfix", "");
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            level.put("numResidents", 60);
            level.put("namePrefix", "The ");
            level.put("namePostfix", " Realm");
            level.put("capitalPrefix", "");
            level.put("capitalPostfix", "");
            level.put("kingPrefix", "God Emperor ");
            level.put("kingPostfix", "");
            level.put("upkeepModifier", 1.0);
            levels.add(new HashMap<String, Object>(level));
            level.clear();
            newConfig.setProperty("levels.nation_level", levels);
        }
    }


    ///////////////////////////////////
    
	public static String[] getRegistrationMsg(String name) {		
		return parseString(String.format(getLangString("MSG_REGISTRATION"), name));
	}

	public static String[] getNewTownMsg(String who, String town) {
		return parseString(String.format(getLangString("MSG_NEW_TOWN"), who, town));
	}

	public static String[] getNewNationMsg(String who, String nation) {
		return parseString(String.format(getLangString("MSG_NEW_NATION"), who, nation));
	}

	public static String[] getJoinTownMsg(String who) {
		return parseString(String.format(getLangString("MSG_JOIN_TOWN"), who));
	}

	public static String[] getJoinNationMsg(String who) {
		return parseString(String.format(getLangString("MSG_JOIN_NATION"), who));
	}

	public static String[] getNewMayorMsg(String who) {
		return parseString(String.format(getLangString("MSG_NEW_MAYOR"), who));
	}

	public static String[] getNewKingMsg(String who, String nation) {
		return parseString(String.format(getLangString("MSG_NEW_KING"), who, nation));
	}
	
	public static String[] getJoinWarMsg(TownyObject obj) {
		return parseString(String.format(getLangString("MSG_WAR_JOIN"), obj.getName()));
	}

	public static String[] getWarTimeEliminatedMsg(String who) {
		return parseString(String.format(getLangString("MSG_WAR_ELIMINATED"), who));
	}
	
	public static String[] getWarTimeForfeitMsg(String who) {
		return parseString(String.format(getLangString("MSG_WAR_FORFEITED"), who));
	}
	
	public static String[] getWarTimeLoseTownBlockMsg(WorldCoord worldCoord, String town) {
		return parseString(String.format(getLangString("MSG_WAR_LOSE_BLOCK"), worldCoord.toString(), town));
	}
	
	public static String[] getWarTimeScoreMsg(Town town, int n) {
		return parseString(String.format(getLangString("MSG_WAR_SCORE"), town.getName(), n));
	}
	
	public static String[] getCouldntPayTaxesMsg(TownyObject obj, String type) {
		return parseString(String.format(getLangString("MSG_COULDNT_PAY_TAXES"), obj.getName(), type));
	}
	
	public static String getPayedTownTaxMsg() {
		return getLangString("MSG_PAYED_TOWN_TAX");
	}
	
	public static String getPayedResidentTaxMsg() {
		return getLangString("MSG_PAYED_RESIDENT_TAX");
	}
	
	public static String getTaxExemptMsg() {
		return getLangString("MSG_TAX_EXEMPT");
	}
	
	public static String[] getDelResidentMsg(Resident resident) {
		return parseString(String.format(getLangString("MSG_DEL_RESIDENT"), resident.getName()));
	}
	
	public static String[] getDelTownMsg(Town town) {
		return parseString(String.format(getLangString("MSG_DEL_TOWN"), town.getName()));
	}
	
	public static String[] getDelNationMsg(Nation nation) {
		return parseString(String.format(getLangString("MSG_DEL_NATION"), nation.getName()));
	}
	
	public static String[] getBuyResidentPlotMsg(String who, String owner, Integer price) {
		return parseString(String.format(getLangString("MSG_BUY_RESIDENT_PLOT"), who, owner, price));
	}
	
	public static String[] getPlotForSaleMsg(String who, WorldCoord worldCoord) {
		return parseString(String.format(getLangString("MSG_PLOT_FOR_SALE"), who, worldCoord.toString()));
	}
	
	public static String[] getMayorAbondonMsg() {
		return parseString(getLangString("MSG_MAYOR_ABANDON"));
	}
	
	public static String getNotPermToNewTownLine() {
		return parseSingleLineString(getLangString("MSG_ADMIN_ONLY_CREATE_TOWN"));
	}
	
	public static String getNotPermToNewNationLine() {
		return parseSingleLineString(getLangString("MSG_ADMIN_ONLY_CREATE_NATION"));
	}
	
	////////////////////////////////////////////

	public static String getKingPrefix(Resident resident) {
		try {
			return (String)getNationLevel(resident.getTown().getNation()).get(TownySettings.NationLevel.KING_PREFIX);
		} catch (NotRegisteredException e) {
			System.out.println("[Towny] Error: Could not read getKingPrefix.");
			return "";
		}
	}

	public static String getMayorPrefix(Resident resident) {
		try {
			return (String)getTownLevel(resident.getTown()).get(TownySettings.TownLevel.MAYOR_PREFIX);
		} catch (NotRegisteredException e) {
			System.out.println("[Towny] Error: Could not read getMayorPrefix.");
			return "";
		}
	}

	public static String getCapitalPostfix(Town town) {
		try {
			return (String)getNationLevel(town.getNation()).get(TownySettings.NationLevel.CAPITAL_POSTFIX);
		} catch (NotRegisteredException e) {
			System.out.println("[Towny] Error: Could not read getCapitalPostfix.");
			return "";
		}
	}

	public static String getTownPostfix(Town town) {
		try {
			return (String)getTownLevel(town).get(TownySettings.TownLevel.NAME_POSTFIX);
		} catch (Exception e) {
			System.out.println("[Towny] Error: Could not read getTownPostfix.");
			return "";
		}
	}
	
	public static String getNationPostfix(Nation nation) {
		try {
			return (String)getNationLevel(nation).get(TownySettings.NationLevel.NAME_POSTFIX);
		} catch (Exception e) {
			System.out.println("[Towny] Error: Could not read getNationPostfix.");
			return "";
		}
	}
	
	public static String getNationPrefix(Nation nation) {
		try {
			return (String)getNationLevel(nation).get(TownySettings.NationLevel.NAME_PREFIX);
		} catch (Exception e) {
			System.out.println("[Towny] Error: Could not read getNationPrefix.");
			return "";
		}
	}
	
	public static String getTownPrefix(Town town) {
		try {
			return (String)getTownLevel(town).get(TownySettings.TownLevel.NAME_PREFIX);
		} catch (Exception e) {
			System.out.println("[Towny] Error: Could not read getTownPrefix.");
			return "";
		}
	}
	
	public static String getCapitalPrefix(Town town) {
		try {
			return (String)getNationLevel(town.getNation()).get(TownySettings.NationLevel.CAPITAL_PREFIX);
		} catch (NotRegisteredException e) {
			System.out.println("[Towny] Error: Could not read getCapitalPrefix.");
			return "";
		}
	}
	
	public static String getKingPostfix(Resident resident) {
		try {
			return (String)getNationLevel(resident.getTown().getNation()).get(TownySettings.NationLevel.KING_POSTFIX);
		} catch (NotRegisteredException e) {
			System.out.println("[Towny] Error: Could not read getKingPostfix.");
			return "";
		}
	}
	
	public static String getMayorPostfix(Resident resident) {
		try {
			return (String)getTownLevel(resident.getTown()).get(TownySettings.TownLevel.MAYOR_POSTFIX);
		} catch (NotRegisteredException e) {
			System.out.println("[Towny] Error: Could not read getMayorPostfix.");
			return "";
		}
	}
	
	public static String getNPCPrefix() {
		return getString("filters_colour_chat.NPC_PREFIX", "NPC");
	}

	// Towny commands
	/*
	public static List<String> getResidentCommands() {
		return getStrArr("commands.resident.aliases", "res, p, player");
	}
	
	public static List<String> getTownCommands() {
		return getStrArr("commands.town.aliases", "t");
	}
	
	public static List<String> getNationCommands() {
		return getStrArr("commands.nation.aliases", "n, nat");
	}
	
	public static List<String> getWorldCommands() {
		return getStrArr("commands.townyworld.aliases", "tw");
	}
	
	public static List<String> getPlotCommands() {
		return getStrArr("commands.plot.aliases", "");
	}
	
	public static List<String> getTownyCommands() {
		return getStrArr("commands.towny.aliases", "");
	}
	
	public static List<String> getTownyAdminCommands() {
		return getStrArr("commands.townyadmin.aliases", "ta");
	}
	
	public static List<String> getTownChatCommands() {
		return getStrArr("commands.townchat.aliases", "tc");
	}
	
	public static List<String> getNationChatCommands() {
		return getStrArr("commands.nationchat.aliases", "nc");
	}
	
	public static String getFirstCommand(List<String> commands) {
		if (commands.size() > 0)
			return commands.get(0);
		else
			return "/<unknown>";
	}
	*/
	
	///////////////////////////////
	public static long getInactiveAfter() {
		return getLong("resident_settings.INACTIVE_AFTER_TIME", 86400000L);
	}

	public static String getLoadDatabase() {
		return getString("plugin.database.DATABASE_LOAD", "flatfile");
	}

	public static String getSaveDatabase() {
		return getString("plugin.database.DATABASE_SAVE", "flatfile");
	}
	
	/*
	public static boolean isFirstRun() {
		return getBoolean("FIRST_RUN");
	}
	*/

	public static int getMaxTownBlocks(Town town) {
		int ratio = getTownBlockRatio();
		if (ratio == 0)
			return town.getBonusBlocks() + (Integer)getTownLevel(town).get(TownySettings.TownLevel.TOWN_BLOCK_LIMIT);
		else
			return town.getBonusBlocks() + town.getNumResidents()*ratio;
	}

    public static int getTownBlockRatio() {
        return getInt("TOWN.TOWN_BLOCK_RATIO", 16);
    }

	public static int getTownBlockSize() {
		return getInt("TOWN.TOWN_BLOCK_SIZE", 16);
	}

	public static boolean getFriendlyFire() {
		return getBoolean("GLOBAL_TOWN_SETTINGS.FRIENDLY_FIRE", true);
	}

	public static boolean isTownCreationAdminOnly() {
		return getBoolean(ConfigNodes.PERMS_TOWN_CREATION_ADMIN_ONLY.getRoot(), Boolean.parseBoolean(ConfigNodes.PERMS_TOWN_CREATION_ADMIN_ONLY.getDefault()));
	}
	
	public static boolean isNationCreationAdminOnly() {
		return getBoolean(ConfigNodes.PERMS_NATION_CREATION_ADMIN_ONLY.getRoot(), Boolean.parseBoolean(ConfigNodes.PERMS_NATION_CREATION_ADMIN_ONLY.getDefault()));
	}

	public static boolean isUsingIConomy() {
		return getBoolean("plugin.interfacing.USING_ICONOMY", true);
	}

    public static void setUsingIConomy(boolean newSetting) {
        setProperty("plugin.interfacing.USING_ICONOMY", newSetting);
    }
	
	public static boolean isUsingEssentials() {
		return getBoolean("plugin.interfacing.USING_ESSENTIALS", false);
	}

    public static void setUsingEssentials(boolean newSetting) {
        setProperty("plugin.interfacing.USING_ESSENTIALS", newSetting);
    }

	public static double getNewTownPrice() {
		return getDouble("economy.PRICE_NEW_TOWN", 250.0);
	}
	
	public static double getNewNationPrice() {
		return getDouble("economy.PRICE_NEW_NATION", 1000.0);
	}

	public static boolean getUnclaimedZoneBuildRights() {
		return getBoolean("unclaimed.UNCLAIMED_ZONE_BUILD", false);
	}
	
	public static boolean getUnclaimedZoneDestroyRights() {
		return getBoolean("unclaimed.UNCLAIMED_ZONE_DESTROY", false);
	}
	
	public static boolean getUnclaimedZoneItemUseRights() {
		return getBoolean("unclaimed.UNCLAIMED_ZONE_ITEM_USE", false);
	}

	public static boolean getDebug() {
		return getBoolean("plugin.DEBUG_MODE", false);
	}

    public static void setDebug(boolean b) {
        setProperty("plugin.DEBUG_MODE", b);
    }

	public static boolean getShowTownNotifications() {
		return getBoolean("GLOBAL_TOWN_SETTINGS.SHOW_TOWN_NOTIFICATIONS", true);
	}

	public static String getUnclaimedZoneName() {
		return getLangString("UNCLAIMED_ZONE_NAME");
	}

    public static String getModifyChatFormat() {
        return getString("filters_colour_chat.modify_chat.format",
                "{nation}{town}{permprefix}{townynameprefix}{playername}{townynamepostfix}{permsuffix}");
    }

    public static boolean isUsingModifyChat() {
        return getBoolean("filters_colour_chat.modify_chat.enable", true);
    }

    public static String getKingColour() {
        return getString("filters_colour_chat.colour.king", "&6");
    }
    public static String getMayorColour() {
        return getString("filters_colour_chat.colour.mayor", "&b");
    }
	
	public static long getDeleteTime() {
		return getLong("resident_settings.DELETE_OLD_RESIDENTS.DELETED_AFTER_TIME", 5184000000L);
	}

	public static boolean isDeletingOldResidents() {
		return getBoolean("resident_settings.DELETE_OLD_RESIDENTS.enable", false);
	}

	public static int getWarTimeWarningDelay() {
		return getInt("war.WARTIME_WARNING_DELAY", 30);
	}

	public static int getWarzoneTownBlockHealth() {
		return getInt("war.WARTIME_TOWN_BLOCK_HP", 60);
	}
	
	public static int getWarzoneHomeBlockHealth() {
		return getInt("war.WARTIME_HOME_BLOCK_HP", 120);
	}


	public static String[] getWarTimeLoseTownBlockMsg(WorldCoord worldCoord) {
		return getWarTimeLoseTownBlockMsg(worldCoord, "");
	}
	
	public static String getDefaultTownName() {
		return getString("resident_settings.DEFAULT_TOWN_NAME", "");
	}	
	
	public static int getWarPointsForTownBlock() {
		return getInt("war.WARTIME_POINTS_TOWNBLOCK", 1);
	}
	
	public static int getWarPointsForTown() {
		return getInt("war.WARTIME_POINTS_TOWN", 10);
	}
	
	public static int getWarPointsForNation() {
		return getInt("war.WARTIME_POINTS_NATION", 100);
	}

	public static int getWarPointsForKill() {
		return getInt("war.WARTIME_POINTS_KILL", 1);
	}
	
	public static int getMinWarHeight() {
		return getInt("war.WARTIME_MIN_HEIGHT", 60);
	}
	
	public static List<String> getWorldMobRemovalEntities() {
		if (getDebug()) System.out.println("[Towny] Debug: Reading World Mob removal entities. ");
		return getStrArr("protection.WORLD_MOB_REMOVAL_ENTITIES", "WaterMob,Flying,Slime");
	}
	
	public static List<String> getTownMobRemovalEntities() {
		if (getDebug()) System.out.println("[Towny] Debug: Reading Town Mob removal entities. ");
		return getStrArr("protection.TOWN_MOB_REMOVAL_ENTITIES", "Monster,WaterMob,Flying,Slime");
	}
	
	public static int getMobRemovalSpeed() {
		return getInt("protection.MOB_REMOVAL_SPEED", 5000);
	}
	
	/*
	public static boolean isRemovingWorldMobs() {
		return getBoolean("protection.MOB_REMOVAL_WORLD");
	}
	
	public static boolean isRemovingTownMobs() {
		return getBoolean("protection.MOB_REMOVAL_TOWN");
	}
	*/

	public static int getHealthRegenSpeed() {
		return getInt("GLOBAL_TOWN_SETTINGS.HEALTH_REGEN.SPEED", 3000);
	}
	
	public static boolean hasHealthRegen() {
		return getBoolean("GLOBAL_TOWN_SETTINGS.HEALTH_REGEN.ENABLE", true);
	}
	
	public static boolean hasTownLimit() {
		return getTownLimit() == 0;
	}
	
	public static int getTownLimit() {
		return getInt("TOWN.TOWN_LIMIT", 3000);
	}
	
	public static double getNationNeutralityCost() {
		return getDouble("economy.PRICE_NATION_NEUTRALITY", 100.0);
	}
	
	public static boolean isAllowingOutposts() {
		return getBoolean("GLOBAL_TOWN_SETTINGS.ALLOW_OUTPOSTS", true);
	}
	
	public static double getOutpostCost() {
		return getDouble("economy.PRICE_OUTPOST", 500.0);
	} 
	
	public static List<Integer> getSwitchIds() {
		return getIntArr("protection.SWITCH_IDS", "25,54,61,62,64,69,70,71,72,77,96,84,93,94");
	}
	
	public static List<Integer> getUnclaimedZoneIgnoreIds() {
		return getIntArr("unclaimed.UNCLAIMED_ZONE_IGNORE",
                "6,14,15,16,17,18,21,31,37,38,39,40,50,56,65,66,73,74,81,82,83,86,89");
	}
	
	public static List<Integer> getItemUseIds() {
		return getIntArr("protection.ITEM_USE_IDS", "259,325,326,327,351");
	}
	
	public static boolean isUnclaimedZoneIgnoreId(int id) {
		return getUnclaimedZoneIgnoreIds().contains(id);
	}

	public static boolean isSwitchId(int id) {
		return getSwitchIds().contains(id);
	}
	
	public static boolean isItemUseId(int id) {
		return getItemUseIds().contains(id);
	}
	
	private static void setProperty(String root, Object value) {
		config.setProperty(root.toLowerCase(), value);
	}
	
	private static void setNewProperty(String root, Object value) {
		newConfig.setProperty(root.toLowerCase(), value);
	}
	
	public static Object getProperty(String root) {
		return config.getProperty(root.toLowerCase());

	}

	public static double getClaimPrice() {
		return getDouble("economy.PRICE_CLAIM_TOWNBLOCK", 25.0);
	}

	public static boolean getUnclaimedZoneSwitchRights() {
		return getBoolean("unclaimed.UNCLAIMED_ZONE_SWITCH", false);
	}

	public static String getUnclaimedPlotName() {
		return getLangString("UNCLAIMED_PLOT_NAME");
	}

	public static long getDayInterval() {
		return getLong("plugin.DAY_INTERVAL", 86400000L);
	}
	
	public static boolean isAllowingTownSpawn() {
		return getBoolean("GLOBAL_TOWN_SETTINGS.ALLOW_TOWN_SPAWN", true);
	}
	
	public static boolean isAllowingPublicTownSpawnTravel() {
		return getBoolean("GLOBAL_TOWN_SETTINGS.ALLOW_TOWN_SPAWN_TRAVEL", true);
	}

    public static List<String> getDisallowedTownSpawnZones() {
        if (getDebug()) System.out.println("[Towny] Debug: Reading disallowed town spawn zones. ");
        return getStrArr("GLOBAL_TOWN_SETTINGS.PREVENT_TOWN_SPAWN_IN", "enemy");
    }
	
	public static boolean isTaxingDaily() {
		return getBoolean("GLOBAL_TOWN_SETTINGS.DAILY_TAXES", true);
	}
	
	public static boolean isBackingUpDaily() {
		return getBoolean("plugin.database.DAILY_BACKUPS", true);
	}
	
	public static double getTownSpawnTravelPrice() {
		return getDouble("economy.PRICE_TOWN_SPAWN_TRAVEL", 0.0);
	}
	
	public static double getTownPublicSpawnTravelPrice() {
		return getDouble("economy.PRICE_TOWN_PUBLIC_SPAWN_TRAVEL", 10.0);
	}
	
	public static double getBaseSpoilsOfWar() {
		return getDouble("war.WARTIME_BASE_SPOILS", 100.0);
	}
	
	public static double getWartimeDeathPrice() {
		return getDouble("economy.PRICE_DEATH_WARTIME", 200.0);
	}
	
	public static double getDeathPrice() {
		return getDouble("economy.PRICE_DEATH", 1.0);
	}
	
	public static double getWartimeTownBlockLossPrice() {
		return getDouble("war.WARTIME_TOWN_BLOCK_LOSS_PRICE", 100.0);
	}
	
	public static boolean isDevMode() {
		return getBoolean("plugin.dev_mode.ENABLE", false);
	}

    public static void setDevMode(boolean choice) {
        setProperty("plugin.dev_mode.ENABLE", choice);
    }

    public static String getDevName() {
        return getString("plugin.dev_mode.DEV_NAME", "ElgarL");
    }
	
	/*
	public static boolean isPvEWithinNonPvPZones() {
		return getBoolean("PVE_IN_NON_PVP_TOWNS");
	}
	*/

	public static boolean isDeclaringNeutral() {
		return getBoolean("war.WARTIME_NATION_CAN_BE_NEUTRAL", true);
	}

    public static void setDeclaringNeutral(boolean choice) {
        setProperty("war.WARTIME_NATION_CAN_BE_NEUTRAL", choice);
    }

	public static boolean isRemovingOnMonarchDeath() {
		return getBoolean("war.WARTIME_REMOVE_ON_MONARCH_DEATH", false);
	}

	public static double getTownUpkeepCost(Town town) {
		double multiplier;
		
		if (town != null)
			multiplier = Double.valueOf(getTownLevel(town).get(TownySettings.TownLevel.UPKEEP_MULTIPLIER).toString());
		else
			multiplier = 1.0;
			
		return getTownUpkeep() * multiplier;
	}

    public static double getTownUpkeep() {
        return getDouble("economy.PRICE_TOWN_UPKEEP", 10.0);
    }

    public static double getNationUpkeep() {
        return getDouble("economy.PRICE_NATION_UPKEEP", 100.0);
    }
	
	public static double getNationUpkeepCost(Nation nation) {
		double multiplier;
		
		if (nation != null)
			multiplier = Double.valueOf(getNationLevel(nation).get(TownySettings.NationLevel.UPKEEP_MULTIPLIER).toString());
		else
			multiplier = 1.0;
		
		return getNationUpkeep() * multiplier;
	}
	
	public static String getFlatFileBackupType() {
		return getString("plugin.database.FLATFILE_BACKUP", "zip");
	}
	

	public static boolean isForcingPvP() {
		return getBoolean("new_world_settings.FORCE_PVP_ON", false);
	}

    public static boolean isPlayerTramplingCropsDisabled() {
        return getBoolean("new_world_settings.DISABLE_PLAYER_CROP_TRAMPLING", true);
    }

    public static boolean isCreatureTramplingCropsDisabled() {
        return getBoolean("new_world_settings.DISABLE_CREATURE_CROP_TRAMPLING", true);
    }

    public static boolean isWorldMonstersOn() {
        return getBoolean("new_world_settings.WORLD_MONSTERS_ON", true);
    }

	public static boolean isForcingExplosions() {
		return getBoolean("new_world_settings.FORCE_EXPLOSIONS_ON", false);
	}
	
	public static boolean isForcingMonsters() {
		return getBoolean("new_world_settings.FORCE_TOWN_MONSTERS_ON", false);
	}
	
	public static boolean isForcingFire() {
		return getBoolean("new_world_settings.FORCE_FIRE_ON", false);
	}

	public static boolean isTownRespawning() {
		return getBoolean("GLOBAL_TOWN_SETTINGS.TOWN_RESPAWN", false);
	}
	
	public static boolean isTownyUpdating(String currentVersion) {
		if (isTownyUpToDate(currentVersion))
			return false;
		else
			return true; //Assume
	}
	
	public static boolean isTownyUpToDate(String currentVersion) {
		return currentVersion.equals(getLastRunVersion(currentVersion));
	}

	public static String getLastRunVersion(String currentVersion) {
		return getString("version.LAST_RUN_VERSION", currentVersion);
	}

    public static void setLastRunVersion(String currentVersion) {
        setProperty("version.LAST_RUN_VERSION", currentVersion);
    }

	public static int getMinDistanceFromTownHomeblocks() {
		return getInt("TOWN.MIN_DISTANCE_FROM_TOWN_HOMEBLOCK", 5);
	}
	
	public static int getMaxDistanceBetweenHomeblocks() {
		return getInt("TOWN.MAX_DISTANCE_BETWEEN_HOMEBLOCKS", 0);
	}

	public static int getMaxPlotsPerResident() {
		return getInt("TOWN.MAX_PLOTS_PER_RESIDENT", 100);
	}

    public static boolean getPermFlag_Resident_Friend_Build() {
        return getBoolean("default_perm_flags.resident.friend.build", true);
    }
    public static boolean getPermFlag_Resident_Friend_Destroy() {
        return getBoolean("default_perm_flags.resident.friend.destroy", true);
    }
    public static boolean getPermFlag_Resident_Friend_ItemUse() {
        return getBoolean("default_perm_flags.resident.friend.item_use", true);
    }
    public static boolean getPermFlag_Resident_Friend_Switch() {
        return getBoolean("default_perm_flags.resident.friend.switch", true);
    }

    public static boolean getPermFlag_Resident_Ally_Build() {
        return getBoolean("default_perm_flags.resident.ally.build", true);
    }
    public static boolean getPermFlag_Resident_Ally_Destroy() {
        return getBoolean("default_perm_flags.resident.ally.destroy", true);
    }
    public static boolean getPermFlag_Resident_Ally_ItemUse() {
        return getBoolean("default_perm_flags.resident.ally.item_use", true);
    }
    public static boolean getPermFlag_Resident_Ally_Switch() {
        return getBoolean("default_perm_flags.resident.ally.switch", true);
    }

    public static boolean getPermFlag_Resident_Outsider_Build() {
        return getBoolean("default_perm_flags.resident.outsider.build", true);
    }
    public static boolean getPermFlag_Resident_Outsider_Destroy() {
        return getBoolean("default_perm_flags.resident.outsider.destroy", true);
    }
    public static boolean getPermFlag_Resident_Outsider_ItemUse() {
        return getBoolean("default_perm_flags.resident.outsider.item_use", true);
    }
    public static boolean getPermFlag_Resident_Outsider_Switch() {
        return getBoolean("default_perm_flags.resident.outsider.switch", true);
    }

    public static boolean getPermFlag_Town_Resident_Build() {
        return getBoolean("default_perm_flags.town.resident.build", true);
    }
    public static boolean getPermFlag_Town_Resident_Destroy() {
        return getBoolean("default_perm_flags.town.resident.destroy", true);
    }
    public static boolean getPermFlag_Town_Resident_ItemUse() {
        return getBoolean("default_perm_flags.town.resident.item_use", true);
    }
    public static boolean getPermFlag_Town_Resident_Switch() {
        return getBoolean("default_perm_flags.town.resident.switch", true);
    }

    public static boolean getPermFlag_Town_Ally_Build() {
        return getBoolean("default_perm_flags.town.ally.build", true);
    }
    public static boolean getPermFlag_Town_Ally_Destroy() {
        return getBoolean("default_perm_flags.town.ally.destroy", true);
    }
    public static boolean getPermFlag_Town_Ally_ItemUse() {
        return getBoolean("default_perm_flags.town.ally.item_use", true);
    }
    public static boolean getPermFlag_Town_Ally_Switch() {
        return getBoolean("default_perm_flags.town.ally.switch", true);
    }

    public static boolean getPermFlag_Town_Outsider_Build() {
        return getBoolean("default_perm_flags.town.outsider.build", true);
    }
    public static boolean getPermFlag_Town_Outsider_Destroy() {
        return getBoolean("default_perm_flags.town.outsider.destroy", true);
    }
    public static boolean getPermFlag_Town_Outsider_ItemUse() {
        return getBoolean("default_perm_flags.town.outsider.item_use", true);
    }
    public static boolean getPermFlag_Town_Outsider_Switch() {
        return getBoolean("default_perm_flags.town.outsider.switch", true);
    }

	public static boolean getDefaultResidentPermission(TownBlockOwner owner, ActionType type) {
		if (owner instanceof Resident)
			switch (type) {
				case BUILD: return getPermFlag_Resident_Friend_Build();
				case DESTROY: return getPermFlag_Resident_Friend_Destroy();
				case SWITCH: return getPermFlag_Resident_Friend_Switch();
				case ITEM_USE: return getPermFlag_Resident_Friend_ItemUse();
				default: throw new UnsupportedOperationException();
			}
		else if (owner instanceof Town)
			switch (type) {
				case BUILD: return getPermFlag_Town_Resident_Build();
				case DESTROY: return getPermFlag_Town_Resident_Destroy();
				case SWITCH: return getPermFlag_Town_Resident_Switch();
				case ITEM_USE: return getPermFlag_Town_Resident_ItemUse();
				default: throw new UnsupportedOperationException();
			}
		else
			throw new UnsupportedOperationException();
	}
	
	public static boolean getDefaultAllyPermission(TownBlockOwner owner, ActionType type) {
		if (owner instanceof Resident)
			switch (type) {
				case BUILD: return getPermFlag_Resident_Ally_Build();
				case DESTROY: return getPermFlag_Resident_Ally_Destroy();
				case SWITCH: return getPermFlag_Resident_Ally_Switch();
				case ITEM_USE: return getPermFlag_Resident_Ally_ItemUse();
				default: throw new UnsupportedOperationException();
			}
		else if (owner instanceof Town)
			switch (type) {
				case BUILD: return getPermFlag_Town_Ally_Build();
				case DESTROY: return getPermFlag_Town_Ally_Destroy();
				case SWITCH: return getPermFlag_Town_Ally_Switch();
				case ITEM_USE: return getPermFlag_Town_Ally_ItemUse();
				default: throw new UnsupportedOperationException();
			}
		else
			throw new UnsupportedOperationException();
	}
	
	public static boolean getDefaultOutsiderPermission(TownBlockOwner owner, ActionType type) {
		if (owner instanceof Resident)
			switch (type) {
				case BUILD: return getPermFlag_Resident_Outsider_Build();
				case DESTROY: return getPermFlag_Resident_Outsider_Destroy();
				case SWITCH: return getPermFlag_Resident_Outsider_Switch();
				case ITEM_USE: return getPermFlag_Resident_Outsider_ItemUse();
				default: throw new UnsupportedOperationException();
			}
		else if (owner instanceof Town)
			switch (type) {
				case BUILD: return getPermFlag_Town_Outsider_Build();
				case DESTROY: return getPermFlag_Town_Outsider_Destroy();
				case SWITCH: return getPermFlag_Town_Outsider_Switch();
				case ITEM_USE: return getPermFlag_Town_Outsider_ItemUse();
				default: throw new UnsupportedOperationException();
			}
		else
			throw new UnsupportedOperationException();
	}
	
	public static boolean getDefaultPermission(TownBlockOwner owner, PermLevel level, ActionType type) {
		switch (level) {
			case RESIDENT: return getDefaultResidentPermission(owner, type);
			case ALLY: return getDefaultAllyPermission(owner, type);
			case OUTSIDER: return getDefaultOutsiderPermission(owner, type);
			default: throw new UnsupportedOperationException();
		}
	}

	public static boolean isLogging() {
		return getBoolean("plugin.LOGGING", true);
	}

	public static boolean isUsingQuestioner() {
		return getBoolean("plugin.interfacing.USING_QUESTIONER", true);
	}
    public static void setUsingQuestioner(boolean newSetting) {
        setProperty("plugin.interfacing.USING_QUESTIONER", newSetting);
    }
	
	public static boolean isAppendingToLog() {
		return !getBoolean("plugin.RESET_LOG_ON_BOOT", true);
	}

	public static boolean isUsingPermissions() {
		return getBoolean("plugin.interfacing.USING_PERMISSIONS", true);
	}
    public static void setUsingPermissions(boolean newSetting) {
        setProperty("plugin.interfacing.USING_PERMISSIONS", newSetting);
    }
	
	public static String filterName(String input) {
		return input.replaceAll(getNameFilterRegex(), "_").replaceAll(getNameRemoveRegex(), "");
	}

    public static String getNameFilterRegex() {
        return getString("filters_colour_chat.regex.NAME_FILTER_REGEX", "[ /]");
    }

    public static String getNameCheckRegex() {
        return getString("filters_colour_chat.regex.NAME_CHECK_REGEX", "^[a-zA-Z0-9._-]*$");
    }

    public static String getNameRemoveRegex() {
        return getString("filters_colour_chat.regex.NAME_REMOVE_REGEX", "[^a-zA-Z0-9._-]");
    }

    public static boolean isUsingCheatProtection() {
        return getBoolean("protection.cheat_protection", true);
    }

	public static boolean isValidRegionName(String name) {
		
		if ((name.toLowerCase() == "spawn")
				|| (name.equalsIgnoreCase("list"))
				|| (name.equalsIgnoreCase("new"))
				|| (name.equalsIgnoreCase("here"))
				|| (name.equalsIgnoreCase("new"))
				|| (name.equalsIgnoreCase("help"))
				|| (name.equalsIgnoreCase("?"))
				|| (name.equalsIgnoreCase("leave"))
				|| (name.equalsIgnoreCase("withdraw"))
				|| (name.equalsIgnoreCase("deposit"))
				|| (name.equalsIgnoreCase("set"))
				|| (name.equalsIgnoreCase("toggle"))
				|| (name.equalsIgnoreCase("mayor"))
				|| (name.equalsIgnoreCase("assistant"))
				|| (name.equalsIgnoreCase("kick"))
				|| (name.equalsIgnoreCase("add"))
				|| (name.equalsIgnoreCase("claim"))
				|| (name.equalsIgnoreCase("unclaim"))
				|| (name.equalsIgnoreCase("title")))
			return false;
		
		return isValidName(name);
	}
		
	public static boolean isValidName(String name) {
		
		try {
			if (TownySettings.namePattern == null)
				namePattern = Pattern.compile(getNameCheckRegex());
			return TownySettings.namePattern.matcher(name).find();
		} catch (PatternSyntaxException e) {
			e.printStackTrace();
			return false;
		}
	}

	/*
	public static boolean isSavingOnLoad() {
		return getBoolean("SAVE_ON_LOAD");
	}
	*/
	
	/*
	public static boolean isAllowingResidentPlots() {
		return getBoolean("ALLOW_RESIDENT_PLOTS");
	}
	*/
}
