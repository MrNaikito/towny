package com.palmergames.bukkit.config;


public enum ConfigNodes {
	    VERSION("version.version",""),
	    LAST_RUN_VERSION("version.last_run_version",""),
	    LANGUAGE("language","english.yml"),
	    PERMS("permissions",""),
	    PERMS_TOWN_CREATION_ADMIN_ONLY("permissions.town_creation_admin_only","false"),
	    PERMS_NATION_CREATION_ADMIN_ONLY("permissions.nation_creation_admin_only","false"),
	    LEVELS("levels",""),
	    LEVELS_TOWN_LEVEL("levels.town_level",""),
	    LEVELS_NATION_LEVEL("levels.nation_level",""),  
	    TOWN("town",""),
	    TOWN_MAX_PLOTS_PER_RESIDENT("town.max_plots_per_resident","100"),
	    TOWN_LIMIT("town.town_limit","3000"),
	    TOWN_MIN_DISTANCE_FROM_TOWN_HOMEBLOCK("town.min_distance_from_town_homeblock","5"),
	    TOWN_MAX_DISTANCE_BETWEEN_HOMEBLOCKS("town.max_distance_between_homeblocks","0"),
	    TOWN_TOWN_BLOCK_RATIO("town.town_block_ratio","16"),
	    TOWN_TOWN_BLOCK_SIZE("town.town_block_size","16"),
	    FLAGS_DEFAULT("default_perm_flags",""),
	    FLAGS_DEFAULT_RES("default_perm_flags.resident",""),
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
	    FLAGS_DEFAULT_TOWN("default_perm_flags.town",""),
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
	    NWS("new_world_settings",""),
	    NWS_FORCE_PVP_ON("new_world_settings.force_pvp_on","false"),
	    NWS_DISABLE_PLAYER_CROP_TRAMPLING("new_world_settings.disable_player_crop_trampling","true"),
	    NWS_DISABLE_CREATURE_CROP_TRAMPLING("new_world_settings.disable_creature_crop_trampling","true"),
	    NWS_WORLD_MONSTERS_ON("new_world_settings.world_monsters_on","true"),
	    NWS_FORCE_EXPLOSIONS_ON("new_world_settings.force_explosions_on","false"),
	    NWS_FORCE_TOWN_MONSTERS_ON("new_world_settings.force_town_monsters_on","false"),
	    NWS_FORCE_FIRE_ON("new_world_settings.force_fire_on","false"),
	    GTOWN_SETTINGS("global_town_settings",""),
	    GTOWN_SETTINGS_FRIENDLY_FIRE("global_town_settings.friendly_fire","true"),
	    GTOWN_SETTINGS_HEALTH_REGEN("global_town_settings.health_regen",""),
	    GTOWN_SETTINGS_REGEN_SPEED("global_town_settings.health_regen.speed","3"),
	    GTOWN_SETTINGS_REGEN_ENABLE("global_town_settings.health_regen.enable","true"),
	    GTOWN_SETTINGS_DAILY_TAXES("global_town_settings.daily_taxes","true"),
	    GTOWN_SETTINGS_ALLOW_OUTPOSTS("global_town_settings.allow_outposts","true"),
	    GTOWN_SETTINGS_ALLOW_TOWN_SPAWN("global_town_settings.allow_town_spawn","true"),
	    GTOWN_SETTINGS_ALLOW_TOWN_SPAWN_TRAVEL("global_town_settings.allow_town_spawn_travel","true"),
	    GTOWN_SETTINGS_SPAWN_TIMER("global_town_settings.teleport_warmup_time","0"),
	    GTOWN_SETTINGS_TOWN_RESPAWN("global_town_settings.town_respawn","false"),
	    GTOWN_SETTINGS_PREVENT_TOWN_SPAWN_IN("global_town_settings.prevent_town_spawn_in","enemy"),
	    GTOWN_SETTINGS_SHOW_TOWN_NOTIFICATIONS("global_town_settings.show_town_notifications","true"),
	    PLUGIN("plugin",""),
	    PLUGIN_DATABASE_LOAD("plugin.database.database_load","flatfile"),
	    PLUGIN_DATABASE_SAVE("plugin.database.database_save","flatfile"),
	    PLUGIN_DAILY_BACKUPS("plugin.database.daily_backups","true"),
	    PLUGIN_FLATFILE_BACKUP("plugin.database.flatfile_backup","zip"),
	    PLUGIN_USING_ESSENTIALS("plugin.interfacing.using_essentials","false"),
	    PLUGIN_USING_ICONOMY("plugin.interfacing.using_iconomy","true"),
	    PLUGIN_USING_QUESTIONER("plugin.interfacing.using_questioner","true"),
	    PLUGIN_USING_PERMISSIONS("plugin.interfacing.using_permissions","true"),
	    PLUGIN_DAY_INTERVAL("plugin.day_interval","1d"),
	    PLUGIN_DEBUG_MODE("plugin.debug_mode","false"),
	    PLUGIN_DEV_MODE("plugin.dev_mode",""),
	    PLUGIN_DEV_MODE_ENABLE("plugin.dev_mode.enable","false"),
	    PLUGIN_DEV_MODE_DEV_NAME("plugin.dev_mode.dev_name","ElgarL"),
	    PLUGIN_LOGGING("plugin.LOGGING","true"),
	    PLUGIN_RESET_LOG_ON_BOOT("plugin.reset_log_on_boot","true"),
	    FILTERS_COLOUR_CHAT("filters_colour_chat",""),
	    FILTERS_NPC_PREFIX("filters_colour_chat.npc_prefix","NPC"),
	    FILTERS_REGEX("filters_colour_chat.regex",""),
	    FILTERS_REGEX_NAME_FILTER_REGEX("filters_colour_chat.regex.name_filter_regex","[ /]"),
	    FILTERS_REGEX_NAME_CHECK_REGEX("filters_colour_chat.regex.name_check_regex","^[a-zA-Z0-9._-]*$"),
	    FILTERS_REGEX_NAME_REMOVE_REGEX("filters_colour_chat.regex.name_remove_regex","[^a-zA-Z0-9._-]"),
	    FILTERS_MODIFY_CHAT("filters_colour_chat.modify_chat",""),
	    FILTERS_MODIFY_CHAT_ENABLE("filters_colour_chat.modify_chat.enable","true"),
	    FILTERS_MODIFY_CHAT_FORMAT("filters_colour_chat.modify_chat.format","{nation}{town}{permprefix}{townynameprefix}{playername}{townynamepostfix}{permsuffix}"),
	    FILTERS_MODIFY_CHAT_MAX_LGTH("filters_colour_chat.modify_chat.max_title_length","15"),
	    FILTERS_COLOUR("filters_colour_chat.colour",""),
	    FILTERS_COLOUR_KING("filters_colour_chat.colour.king","&6"),
	    FILTERS_COLOUR_MAYOR("filters_colour_chat.colour.mayor","&b"),
	    PROT("protection",""),
	    PROT_ITEM_USE_ID("protection.item_use_ids","259,325,326,327,351"),
	    PROT_SWITCH_ID("protection.switch_ids","25,54,61,62,64,69,70,71,72,77,96,84,93,94"),
	    PROT_MOB_REMOVE_TOWN("protection.town_mob_removal_entities","Monster,WaterMob,Flying,Slime"),
	    PROT_MOB_REMOVE_WORLD("protection.world_mob_removal_entities","WaterMob,Flying,Slime"),
	    PROT_MOB_REMOVE_SPEED("protection.mob_removal_speed","5s"),
	    PROT_CHEAT("protection.cheat_protection","true"),
	    UNCLAIMED_ZONE("unclaimed",""),
	    UNCLAIMED_ZONE_BUILD("unclaimed.unclaimed_zone_build","false"),
	    UNCLAIMED_ZONE_DESTROY("unclaimed.unclaimed_zone_destroy","false"),
	    UNCLAIMED_ZONE_ITEM_USE("unclaimed.unclaimed_zone_item_use","false"),
	    UNCLAIMED_ZONE_IGNORE("unclaimed.unclaimed_zone_ignore","6,14,15,16,17,18,21,31,37,38,39,40,50,56,65,66,73,74,81,82,83,86,89"),
	    UNCLAIMED_ZONE_SWITCH("unclaimed.unclaimed_zone_switch","false"),
	    //UNCLAIMED_ZONE_NAME("unclaimed.unclaimed_zone_name",""),
	    //UNCLAIMED_PLOT_NAME("unclaimed.unclaimed_plot_name",""),
	    RES_SETTING("resident_settings",""),
	    RES_SETTING_INACTIVE_AFTER_TIME("resident_settings.inactive_after_time","1h"),
	    RES_SETTING_DELETE_OLD_RESIDENTS("resident_settings.delete_old_residents",""),
	    RES_SETTING_DELETE_OLD_RESIDENTS_ENABLE("resident_settings.delete_old_residents.enable","false"),
	    RES_SETTING_DELETE_OLD_RESIDENTS_TIME("resident_settings.delete_old_residents.deleted_after_time","60d"),
	    RES_SETTING_DEFAULT_TOWN_NAME("resident_settings.default_town_name",""),
	    ECO("economy",""),
	    ECO_PRICE_TOWN_SPAWN_TRAVEL("economy.price_town_spawn_travel","0.0"),
	    ECO_PRICE_TOWN_PUBLIC_SPAWN_TRAVEL("economy.price_town_public_spawn_travel","10.0"),
	    ECO_PRICE_NATION_NEUTRALITY("economy.price_nation_neutrality","100"),
	    ECO_PRICE_NEW_NATION("economy.price_new_nation","1000.0"),
	    ECO_PRICE_NEW_TOWN("economy.price_new_town","250.0"),
	    ECO_PRICE_OUTPOST("economy.price_outpost","500.0"),
	    ECO_PRICE_CLAIM_TOWNBLOCK("economy.price_claim_townblock","25.0"),
	    ECO_PRICE_NATION_UPKEEP("economy.price_nation_upkeep","100.0"),
	    ECO_PRICE_TOWN_UPKEEP("economy.price_town_upkeep","10.0"),
	    ECO_PRICE_DEATH("economy.price_death","1.0"),
	    ECO_PRICE_DEATH_WARTIME("economy.price_death_wartime","200.0"),
	    WAR("war",""),
	    WARTIME_NATION_CAN_BE_NEUTRAL("war.wartime_nation_can_be_neutral","true"),
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
		
		private ConfigNodes(String root, String def) {  
			 this.Root = root;  
			 this.Default = def;
		}

		public String getRoot() {
			return Root;
		}
		
		public String getDefault() {
			return Default;
		}
			
	}