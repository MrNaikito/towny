package com.palmergames.bukkit.config;


public enum ConfigNodes {
            VERSION("version.version","", "# This is for showing the changelog on updates.  Please do not edit."),
            LAST_RUN_VERSION("version.last_run_version",""),
            LANGUAGE("language","english.yml", "", "# The language file you wish to use"),
            PERMS("permissions","", "",
				                    "############################################################",
				                    "# +------------------------------------------------------+ #",
				                    "# |                   Permission nodes                   | #",
				                    "# +------------------------------------------------------+ #",
				                    "############################################################",
				                    "",
            						"#  Possible permission nodes",
				                    "#",
				                    "#    towny.admin: User is able to use /townyadmin, as well as the ability to build/destroy anywhere. User is also able to make towns or nations when set to admin only.",
				                    "#    towny.cheat.bypass : User is able to use any fly mods and double block jump (disables towny cheat protection for this user).",
				                    "#    towny.town.new :User is able to create a town",
				                    "#    towny.town.claim : User is able to expand his town with /town claim",
				                    "#    towny.town.plot : User is able to use the /plot commands",
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
				                    "# these will be moved to permissions nodes at a later date"),
            PERMS_TOWN_CREATION_ADMIN_ONLY("permissions.town_creation_admin_only","false"),
            PERMS_NATION_CREATION_ADMIN_ONLY("permissions.nation_creation_admin_only","false"),
            LEVELS("levels",""),
            LEVELS_TOWN_LEVEL("levels.town_level",""),
            LEVELS_NATION_LEVEL("levels.nation_level",""),  
            TOWN("town","", "",
		                    "############################################################",
		                    "# +------------------------------------------------------+ #",
		                    "# |               Town Claim/new defaults                | #",
		                    "# +------------------------------------------------------+ #",
		                    "############################################################", ""),
            TOWN_MAX_PLOTS_PER_RESIDENT("town.max_plots_per_resident","100", "# maximum number of plots any single resident can own"),
            TOWN_LIMIT("town.town_limit","3000", "# Maximum number of towns allowed on the server."),
            TOWN_MIN_DISTANCE_FROM_TOWN_HOMEBLOCK("town.min_distance_from_town_homeblock","5", "",
            																				   "# Minimum number of plots any towns home plot must be from the next town.",
																			                   "# This will prevent someone founding a town right on your doorstep"),
            TOWN_MAX_DISTANCE_BETWEEN_HOMEBLOCKS("town.max_distance_between_homeblocks","0", "",
            																				 "# Maximum distance between homblocks.",
            																				 "# This will force players to build close together."),
            TOWN_TOWN_BLOCK_RATIO("town.town_block_ratio","16", "",
            													"# The maximum townblocks available to a town is (numResidents * ratio).",
                    											"# Setting this value to 0 will instead use the level based jump values determined in the town level config."),
            TOWN_TOWN_BLOCK_SIZE("town.town_block_size","16", "# The size of the square grid cell. Changing this value is suggested only when you first install Towny.",
										                      "# Doing so after entering data will shift things unwantedly. Using smaller value will allow higher precision,",
										                      "# at the cost of more work setting up. Also, extremely small values will render the caching done useless.",
										                      "# Each cell is (town_block_size * town_block_size * 128) in size, with 128 being from bedrock to clouds."),
            NWS("new_world_settings","", "", "",
					                     "############################################################",
					                     "# +------------------------------------------------------+ #",
					                     "# |             Default new world settings               | #",
					                     "# +------------------------------------------------------+ #",
					                     "############################################################",
					                     "",
					                     "# These flags are only used at the initial setp of a new world.",
					                     "",
					                     "# Once Towny is running each world can be altered from within game",
					                     "# using '/townyworld toggle'", ""),
            NWS_FORCE_PVP_ON("new_world_settings.force_pvp_on","false", "# force_pvp_on is a global flag and overrides any towns flag setting"),
            NWS_DISABLE_PLAYER_CROP_TRAMPLING("new_world_settings.disable_player_crop_trampling","true", "# Disable players trampling crops"),
            NWS_DISABLE_CREATURE_CROP_TRAMPLING("new_world_settings.disable_creature_crop_trampling","true", "# Disable creatures trampling crops"),
            NWS_WORLD_MONSTERS_ON("new_world_settings.world_monsters_on","true", "# world_monsters_on is a global flag setting per world."),
            NWS_FORCE_EXPLOSIONS_ON("new_world_settings.force_explosions_on","false", "# force_explosions_on is a global flag and overrides any towns flag setting"),
            NWS_FORCE_TOWN_MONSTERS_ON("new_world_settings.force_town_monsters_on","false", "# force_town_monsters_on is a global flag and overrides any towns flag setting"),
            NWS_FORCE_FIRE_ON("new_world_settings.force_fire_on","false", "# force_fire_on is a global flag and overrides any towns flag setting"),
            GTOWN_SETTINGS("global_town_settings","", "", "",
								                      "############################################################",
								                      "# +------------------------------------------------------+ #",
								                      "# |                Global town settings                  | #",
								                      "# +------------------------------------------------------+ #",
								                      "############################################################", ""),
            GTOWN_SETTINGS_FRIENDLY_FIRE("global_town_settings.friendly_fire","true", "# can residents/Allies harm other residents when in a town with pvp enabled?"),
            GTOWN_SETTINGS_HEALTH_REGEN("global_town_settings.health_regen","", "# Players within their town or allied towns will regenerate half a heart after every health_regen_speed seconds."),
            GTOWN_SETTINGS_REGEN_SPEED("global_town_settings.health_regen.speed","3"),
            GTOWN_SETTINGS_REGEN_ENABLE("global_town_settings.health_regen.enable","true"),
            GTOWN_SETTINGS_DAILY_TAXES("global_town_settings.daily_taxes","true",
            		"",
            		"# Enables taxes to be collected daily by town/nation",
                    "# If a town can't pay it's tax then it is kicked from the nation.",
                    "# if a resident can't pay his plot tax he loses his plot.",
                    "# if a resident can't pay his town tax then he is kicked from the town.",
                    "# if a town or nation fails to pay it's upkeep it is deleted."),
            GTOWN_SETTINGS_BANK_CAP_AMOUNT("global_town_settings.bank_cap","0",
                    "",
                    "# Maximum amount of money allowed in town bank",
                    "# Use 0 for no limit"),
            GTOWN_SETTINGS_BANK_ALLOW_WITHDRAWLS("global_town_settings.bank_allow_withdrawls","true",
                    "",
                    "# Set to true to allow withdrawls from town banks"),
            GTOWN_SETTINGS_ALLOW_OUTPOSTS("global_town_settings.allow_outposts","true", "# Allow towns to claim outposts (a townblock not connected to town)."),
            GTOWN_SETTINGS_ALLOW_TOWN_SPAWN("global_town_settings.allow_town_spawn","true", "# Allow the use of /town spawn"),
            GTOWN_SETTINGS_ALLOW_TOWN_SPAWN_TRAVEL("global_town_settings.allow_town_spawn_travel","true", "# Allow regular residents to use /town spawn [town] (TP to other towns if they are public)."),
            GTOWN_SETTINGS_SPAWN_TIMER("global_town_settings.teleport_warmup_time","0", "# If non zero it delays any spawn request by x seconds."),
            GTOWN_SETTINGS_TOWN_RESPAWN("global_town_settings.town_respawn","false", "# Respawn the player at his town spawn point when he/she dies"),
            GTOWN_SETTINGS_PREVENT_TOWN_SPAWN_IN("global_town_settings.prevent_town_spawn_in","enemy",
            		"",
            		"# Prevent players from using /town spawn while within unclaimed areas and/or enemy/neutral towns.",
                    "# Allowed options: unclaimed,enemy,neutral"),
            GTOWN_SETTINGS_SHOW_TOWN_NOTIFICATIONS("global_town_settings.show_town_notifications","true",
                    "# Enables the [~Home] message.",
                    "# If false it will make it harder for enemies to find the home block during a war"),
            PLUGIN("plugin","",
            		"", "",
                    "############################################################",
                    "# +------------------------------------------------------+ #",
                    "# |                 Plugin interfacing                   | #",
                    "# +------------------------------------------------------+ #",
                    "############################################################", ""),
            PLUGIN_DATABASE_LOAD("plugin.database.database_load","flatfile"),
            PLUGIN_DATABASE_SAVE("plugin.database.database_save","flatfile"),
            PLUGIN_DAILY_BACKUPS("plugin.database.daily_backups","true"),
            PLUGIN_FLATFILE_BACKUP("plugin.database.flatfile_backup","zip"),
            PLUGIN_USING_ESSENTIALS("plugin.interfacing.using_essentials","false", "# Enable if you are using cooldowns in essentials for teleports."),
            PLUGIN_USING_ICONOMY("plugin.interfacing.using_iconomy","true"),
            PLUGIN_USING_QUESTIONER("plugin.interfacing.using_questioner","true"),
            PLUGIN_USING_PERMISSIONS("plugin.interfacing.using_permissions","true"),
            PLUGIN_DAY_INTERVAL("plugin.day_interval","1d",
                    "# The time taken between each \"day\". At the start of each day, taxes will be collected.",
                    "# Judged in seconds. Default is 24 hours."),
            PLUGIN_DEBUG_MODE("plugin.debug_mode","false",
                    "# Lots of messages to tell you what's going on in the server with time taken for events."),
            PLUGIN_DEV_MODE("plugin.dev_mode","",
                    "# Spams the player named in dev_name with all messages related to towny."),
            PLUGIN_DEV_MODE_ENABLE("plugin.dev_mode.enable","false"),
            PLUGIN_DEV_MODE_DEV_NAME("plugin.dev_mode.dev_name","ElgarL"),
            PLUGIN_LOGGING("plugin.LOGGING","true", "# Record all messages to the towny.log"),
            PLUGIN_RESET_LOG_ON_BOOT("plugin.reset_log_on_boot","true", "# If true this will cause the log to be wiped at every startup."),
            FILTERS_COLOUR_CHAT("filters_colour_chat","",
            		"", "",
                    "############################################################",
                    "# +------------------------------------------------------+ #",
                    "# |               Filters colour and chat                | #",
                    "# +------------------------------------------------------+ #",
                    "############################################################", ""),
            FILTERS_NPC_PREFIX("filters_colour_chat.npc_prefix","NPC",
            		"# This is the name given to any NPC assigned mayor."),
            FILTERS_REGEX("filters_colour_chat.regex","",
            		"# Regex fields used in validating inputs."),
            FILTERS_REGEX_NAME_FILTER_REGEX("filters_colour_chat.regex.name_filter_regex","[ /]"),
            FILTERS_REGEX_NAME_CHECK_REGEX("filters_colour_chat.regex.name_check_regex","^[a-zA-Z0-9._-]*$"),
            FILTERS_REGEX_NAME_REMOVE_REGEX("filters_colour_chat.regex.name_remove_regex","[^a-zA-Z0-9._-]"),
            FILTERS_MODIFY_CHAT("filters_colour_chat.modify_chat","",
            		"",
                    "# The format below will specify the changes made to the player name when chatting.",
                    "# keys are",
                    "# {nation} - Displays nation name in [ ] if a member of a nation.",
                    "# {town} - Displays town name in [ ] if a member of a town.",
                    "# {permprefix} - Permission group prefix",
                    "# {townynameprefix} - Towny name prefix taken from the townLevel/nationLevels",
                    "# {playername} - default player name",
                    "# {modplayername} - modified player name (use if Towny is over writing some other plugins changes).",
                    "# {townynamepostfix} - Towny name postfix taken from the townLevel/nationLevels.",
                    "# {permsuffix} - Permission group suffix."),
            FILTERS_MODIFY_CHAT_ENABLE("filters_colour_chat.modify_chat.enable","true"),
            FILTERS_MODIFY_CHAT_FORMAT("filters_colour_chat.modify_chat.format","{nation}{town}{permprefix}{townynameprefix}{playername}{townynamepostfix}{permsuffix}"),
            FILTERS_MODIFY_CHAT_MAX_LGTH("filters_colour_chat.modify_chat.max_title_length","15"),
            FILTERS_COLOUR("filters_colour_chat.colour","",
            		"",
                    "# Text colouring",
                    "# --------------",
                    "# Black = &0, Navy = &1, Green = &2, Blue = &3, Red = &4",
                    "# Purple = &5, Gold = &6, LightGray = &7, Gray = &8",
                    "# DarkPurple = &9, LightGreen = &a, LightBlue = &b",
                    "# Rose = &c, LightPurple = &d, Yellow = &e, White = &f",
                    "",
                    "# Chat colours"),
            FILTERS_COLOUR_KING("filters_colour_chat.colour.king","&6"),
            FILTERS_COLOUR_MAYOR("filters_colour_chat.colour.mayor","&b"),
            PROT("protection","",
            		"", "",
                    "############################################################",
                    "# +------------------------------------------------------+ #",
                    "# |             block/item/mob protection                | #",
                    "# +------------------------------------------------------+ #",
                    "############################################################", ""),
            PROT_ITEM_USE_ID("protection.item_use_ids","259,325,326,327,351",
            		"",
                    "# Items that can be blocked within towns via town/plot flags",
                    "# 259 - flint and steel",
                    "# 325 - bucket",
                    "# 326 - water bucket",
                    "# 327 - lava bucket",
                    "# 351 - bone/bonemeal"),
            PROT_SWITCH_ID("protection.switch_ids","25,54,61,62,64,69,70,71,72,77,96,84,93,94",
            		"",
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
                    "# 93/94 - redstone repeater"),
            PROT_MOB_REMOVE_TOWN("protection.town_mob_removal_entities","Monster,WaterMob,Flying,Slime",
            		"",
                    "# permitted entities http://jd.bukkit.org/apidocs/org/bukkit/entity/package-summary.html",
                    "# Animals, Chicken, Cow, Creature, Creeper, Flying, Ghast, Giant, Monster, Pig, ",
                    "# PigZombie, Sheep, Skeleton, Slime, Spider, Squid, WaterMob, Wolf, Zombie",
                    "",
                    "# Remove living entities within a town's boundaries, if the town has the mob removal flag set."),
            PROT_MOB_REMOVE_WORLD("protection.world_mob_removal_entities","WaterMob,Flying,Slime",
            		"",
                    "# Globally remove living entities in all worlds that have their flag set."),
            PROT_MOB_REMOVE_SPEED("protection.mob_removal_speed","5s",
            		"",
                    "# The maximum amount of time a mob could be inside a town's boundaries before being sent to the void.",
                    "# Lower values will check all entities more often at the risk of heavier burden and resource use.",
                    "# NEVER set below 1."),
            PROT_CHEAT("protection.cheat_protection","true",
            		"",
                    "# Prevent fly and double block jump cheats."),
            UNCLAIMED_ZONE("unclaimed","",
            		"", "",
                    "############################################################",
                    "# +------------------------------------------------------+ #",
                    "# |             Unclaimed plot settings                  | #",
                    "# +------------------------------------------------------+ #",
                    "############################################################", ""),
            UNCLAIMED_ZONE_BUILD("unclaimed.unclaimed_zone_build","false"),
            UNCLAIMED_ZONE_DESTROY("unclaimed.unclaimed_zone_destroy","false"),
            UNCLAIMED_ZONE_ITEM_USE("unclaimed.unclaimed_zone_item_use","false"),
            UNCLAIMED_ZONE_IGNORE("unclaimed.unclaimed_zone_ignore","6,14,15,16,17,18,21,31,37,38,39,40,50,56,65,66,73,74,81,82,83,86,89"),
            UNCLAIMED_ZONE_SWITCH("unclaimed.unclaimed_zone_switch","false"),
            //UNCLAIMED_ZONE_NAME("unclaimed.unclaimed_zone_name",""),
            //UNCLAIMED_PLOT_NAME("unclaimed.unclaimed_plot_name",""),
            FLAGS_DEFAULT("default_perm_flags","",
            		"", "",
                    "############################################################",
                    "# +------------------------------------------------------+ #",
                    "# |             Default Town/Plot flags                  | #",
                    "# +------------------------------------------------------+ #",
                    "############################################################",
                    "",
                    ""),
            FLAGS_DEFAULT_RES("default_perm_flags.resident","",
            		"",
                    "# Default permission flags for residents plots within a town",
                    "#",
                    "# Can allies/friends/outsiders perform certain actions in the town",
                    "#",
                    "# build - place blocks and other items",
                    "# destroy - break blocks and other items",
                    "# itemuse - use items such as furnaces (as defined in item_use_ids)",
                    "# switch - trigger or activate switches (as defined in switch_ids)"),
            FLAGS_RES_FR_BUILD("default_perm_flags.resident.friend.build","true"),
            FLAGS_RES_FR_DESTROY("default_perm_flags.resident.friend.destroy","true"),
            FLAGS_RES_FR_ITEM_USE("default_perm_flags.resident.friend.item_use","true"),
            FLAGS_RES_FR_SWITCH("default_perm_flags.resident.friend.switch","true"),
            FLAGS_RES_ALLY_BUILD("default_perm_flags.resident.ally.build","false"),
            FLAGS_RES_ALLY_DESTROY("default_perm_flags.resident.ally.destroy","false"),
            FLAGS_RES_ALLY_ITEM_USE("default_perm_flags.resident.ally.item_use","false"),
            FLAGS_RES_ALLY_SWITCH("default_perm_flags.resident.ally.switch","false"),
            FLAGS_RES_OUTSIDER_BUILD("default_perm_flags.resident.outsider.build","false"),
            FLAGS_RES_OUTSIDER_DESTROY("default_perm_flags.resident.outsider.destroy","false"),
            FLAGS_RES_OUTSIDER_ITEM_USE("default_perm_flags.resident.outsider.item_use","false"),
            FLAGS_RES_OUTSIDER_SWITCH("default_perm_flags.resident.outsider.switch","false"),
            FLAGS_DEFAULT_TOWN("default_perm_flags.town","",
            		"",
                    "# Default permission flags for towns",
                    "# These are copied into the town data file at creation",
                    "#",
                    "# Can allies/outsiders/residents perform certain actions in the town",
                    "#",
                    "# build - place blocks and other items",
                    "# destroy - break blocks and other items",
                    "# itemuse - use items such as flint and steel or buckets (as defined in item_use_ids)",
                    "# switch - trigger or activate switches (as defined in switch_ids)"),
            FLAGS_TOWN_RES_BUILD("default_perm_flags.town.resident.build","true"),
            FLAGS_TOWN_RES_DESTROY("default_perm_flags.town.resident.destroy","true"),
            FLAGS_TOWN_RES_ITEM_USE("default_perm_flags.town.resident.item_use","true"),
            FLAGS_TOWN_RES_SWITCH("default_perm_flags.town.resident.switch","true"),
            FLAGS_TOWN_ALLY_BUILD("default_perm_flags.town.ally.build","false"),
            FLAGS_TOWN_ALLY_DESTROY("default_perm_flags.town.ally.destroy","false"),
            FLAGS_TOWN_ALLY_ITEM_USE("default_perm_flags.town.ally.item_use","false"),
            FLAGS_TOWN_ALLY_SWITCH("default_perm_flags.town.ally.switch","false"),
            FLAGS_TOWN_OUTSIDER_BUILD("default_perm_flags.town.outsider.build","false"),
            FLAGS_TOWN_OUTSIDER_DESTROY("default_perm_flags.town.outsider.destroy","false"),
            FLAGS_TOWN_OUTSIDER_ITEM_USE("default_perm_flags.town.outsider.item_use","false"),
            FLAGS_TOWN_OUTSIDER_SWITCH("default_perm_flags.town.outsider.switch","false"),
            RES_SETTING("resident_settings","",
            		"", "",
                    "############################################################",
                    "# +------------------------------------------------------+ #",
                    "# |                  Resident settings                   | #",
                    "# +------------------------------------------------------+ #",
                    "############################################################", ""),
            RES_SETTING_INACTIVE_AFTER_TIME("resident_settings.inactive_after_time","1h",
                    "# player is flagged as inactive after 1 hour (default)"),
            RES_SETTING_DELETE_OLD_RESIDENTS("resident_settings.delete_old_residents","",
                    "# if enabled old residents will be kicked and deleted from a town",
                    "# after Two months (default) of not logging in"),
            RES_SETTING_DELETE_OLD_RESIDENTS_ENABLE("resident_settings.delete_old_residents.enable","false"),
            RES_SETTING_DELETE_OLD_RESIDENTS_TIME("resident_settings.delete_old_residents.deleted_after_time","60d"),
            RES_SETTING_DEFAULT_TOWN_NAME("resident_settings.default_town_name","",
                    "# The name of the town a resident will automatically join when he first registers."),
            ECO("economy","",
            		"", "",
                    "############################################################",
                    "# +------------------------------------------------------+ #",
                    "# |                  Economy settings                    | #",
                    "# +------------------------------------------------------+ #",
                    "############################################################", ""),
            ECO_PRICE_TOWN_SPAWN_TRAVEL("economy.price_town_spawn_travel","0.0", "# Cost to use /town spawn"),
            ECO_PRICE_TOWN_PUBLIC_SPAWN_TRAVEL("economy.price_town_public_spawn_travel","10.0",
                    "# Cost to use /town spawn [town]",
                    "# This is paid to the town you goto."),
            ECO_PRICE_NATION_NEUTRALITY("economy.price_nation_neutrality","100",
                    "# The daily upkeep to remain neutral during a war. Neutrality will exclude you from a war event, as well as deterring enemies."),
            ECO_PRICE_NEW_NATION("economy.price_new_nation","1000.0",
                    "# How much it costs to start a nation."),
            ECO_PRICE_NEW_TOWN("economy.price_new_town","250.0", "# How much it costs to start a town."),
            ECO_PRICE_OUTPOST("economy.price_outpost","500.0",
                    "# How much it costs to make an outpost. An outpost isn't limited to being on the edge of town."),
            ECO_PRICE_CLAIM_TOWNBLOCK("economy.price_claim_townblock","25.0",
                    "# The price for a town to expand one townblock."),
            ECO_PRICE_NATION_UPKEEP("economy.price_nation_upkeep","100.0",
                    "# The server's daily charge on each nation. If a nation fails to pay this upkeep",
                    "# all of it's member town are kicked and the Nation is removed."),
            ECO_PRICE_TOWN_UPKEEP("economy.price_town_upkeep","10.0",
                    "# The server's daily charge on each town. If a town fails to pay this upkeep",
                    "# all of it's residents are kicked and the town is removed."),
            ECO_PRICE_DEATH("economy.price_death","1.0"),
            ECO_PRICE_DEATH_WARTIME("economy.price_death_wartime","200.0"),
            WAR("war","",
            		"", "",
                    "############################################################",
                    "# +------------------------------------------------------+ #",
                    "# |                  Wartime settings                    | #",
                    "# +------------------------------------------------------+ #",
                    "############################################################", ""),
            WARTIME_NATION_CAN_BE_NEUTRAL("war.wartime_nation_can_be_neutral","true",
                    "#This setting allows you disable the ability for a nation to pay to remain neutral during a war."),
            WARTIME_REMOVE_ON_MONARCH_DEATH("war.wartime_remove_on_monarch_death","false"),
            WARTIME_WARNING_DELAY("war.wartime_warning_delay","30"),
            WARTIME_TOWN_BLOCK_HP("war.wartime_town_block_hp","60"),
            WARTIME_TOWN_BLOCK_LOSS_PRICE("war.wartime_town_block_loss_price","100"),
            WARTIME_HOME_BLOCK_HP("war.wartime_home_block_hp","120"),
            WARTIME_BASE_SPOILS("war.wartime_base_spoils","100"),
            WARTIME_POINTS_TOWNBLOCK("war.wartime_points_townblock","1"),
            WARTIME_POINTS_TOWN("war.wartime_points_town","10"),
            WARTIME_POINTS_NATION("war.wartime_points_nation","100"),
            WARTIME_POINTS_KILL("war.wartime_points_kill","1"),
            WARTIME_MIN_HEIGHT("war.wartime_min_height","60");

                private final String Root;
                private final String Default;
                private String[] comments;
                
                private ConfigNodes(String root, String def, String...comments) {  
                         this.Root = root;  
                         this.Default = def;
                         this.comments = comments;
                }

                /**
                * Retrieves the root for a config option
                * @return The root for a config option
                */
                public String getRoot() {
                        return Root;
                }
                
                /**
                * Retrieves the default value for a config path
                * @return The default value for a config path
                */
                public String getDefault() {
                        return Default;
                }
                
                /**
                * Retrieves the comment for a config path
                * @return The comments for a config path
                */  
                public String[] getComments() {
                    if (comments != null) {
                        return comments;
                    }

                    String[] comments = new String[1];
                    comments[0] = "";
                    return comments;
                }
                        
        }
