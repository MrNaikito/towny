

# Introduction #

Here you can find information on old releases of Towny.

## New in 0.85.0.0 ##

  * New commands:
    * /resident spawn - If deny\_bed\_use: true and player has a current bed spawn, command will teleport player to their bed. Behaves according to town spawn command's costs and rules.
    * /ta set capital [name](town.md) - A command for admins to be able to change a nations capital. Town to be set must already be a member of the nation.
    * /town reslist - a command  to obtain a FULL list of all residents in a town.
    * /plot set name - allows a mayor or plot-owner to rename plots they own, overwriting the ~Unowned message. Personal-plots display both the plot's given name and the name of the plot-owner.

  * Changes to Permission nodes:
    * None

  * New Permission Nodes:
    * towny.command.resident.spawn - Allows players to use /resident spawn command. Add this node to the resident section of the townyperms.yml.
    * towny\_maxplots.# - Use this to limit how many plots a player can personally own.
    * towny.tax\_exempt - Grant this permisison to any rank you do not want to pay taxes.  ONLY works in TownyPerms . Will not work in external permission plugins. WARNING: Mayors without this permission will now be taxed along with the rest, add it to the Mayor section of your townyperms.yml.
    * towny.command.plot.asmayor - For town plot management, grant this to any ranks you want to have the ability to...
      * reclaim plots from players for the town.
      * Toggle perms and plot settings on any plot in the town.
      * Put plots up for sale and take them down again.
      * Mayors and Assistants can still set plots for sale without the node.

  * Added:
    * Allow negative upkeep on nations.
    * Add TownyPermissionSource implementation that uses Vault Chat API (ZerothAngel).
    * Incremental price change for bonus plots (by Slind).
    * Add config setting to restrict bed-use to personally-owned plots only. Found in Resident Settings @ Deny\_Bed\_Use. It is false by default.
      * If deny\_bed\_use is true and town\_respawn is true, a player will respawn at their bed if there is a bed spawn set.
    * Added cancellable DisallowedPVPEvent.
    * Expand GroupManager/PEX/bPerms and any other Permission plugin recognised by Vault to handle towny\_maxplots.X as a valid permission node. It is now possible to create ranks within townyperms.yml so mayors can limit how many plots their players can own personally. You can also let different ranks in your permission plugin have the ability to own a set number of plots.
    * Add tab Auto Complete on all commands for Town/Nation names. Any place a town or nation name can be used in a command it is possible to being typing the name and have it autocomplete.
    * Added protected Mobs setting for town animal protection (coaster3000). You can now define which mobs are protected by towny in the config.
    * Added settings to make economy daily tax timer async or synced (coaster3000).
    * Adding the ability to change the Economy prefix from the config (dadealus).
    * Added minimal claim distance from other town plots (coaster3000).

  * Bug Fixes!
    * Fire interact events for players when riding horses/carts/pigs.
    * Fix possible error when a picture is removed by a projectile.
    * Change health regen to implement Bukkits new method for health measurement.
    * Zero Economy accounts when renaming/deleting Towns/Nation as Vault doesn't support deleting them.
    * Minor performance changes to plot regen code.
    * Fix permission check to use nation.kick command.
    * Grammar fix in english.yml.
    * Exclude NPC's from all Tax and upkeep checks.
    * Temporary fix for ItemFrames and Minecart protection (need to rewrite major code to implement the new block naming system).
      * To protect ItemFrames from right click in town add id 389 to the Switch list.
      * To prevent/allow breaking/emptying either deny or grant the perm for 'towny.wild.destroy.389' or relevant town perm.
      * For other items use the following codes...
        * Painting = 321
        * StorageMinecart = 342
        * HopperMinecart = 408
        * PoweredMinecart = 343 (furnace)
        * RideableMinecart = 328
    * Fix negative nation upkeep problems when using Vault.
    * Force shoptax, plottax and embassytax to obey the max tax setting in the config.
    * Fix a bug where you could create new towns in unclaimable worlds.
    * Hide players from '/res list' Who can't be seen (graywolf336).
    * Fix an issue with public town spawns.
    * Fix '/res friend add/remove' to work on offline players.


## New in 0.84.0.0 ##

  * New commands:
    * None

  * Changes to Permission nodes:
    * None

  * New Permission Nodes:
    * None

  * Added:
    * Towny will now always try to run no matter what version of CraftBukkit you are running. Instead of disabling it will output a warning message.

  * Bug Fixes!
    * Stop Item\_use perms overriding Switch perms.
    * Regen health in town only upto the maximum (allows for modded health levels).
    * TownyChat: Allow global chat to pass through un-changed if there are no channels to talk in (requires setting modify\_chat: false).


  * [Click here for a full changelog.](https://raw.github.com/ElgarL/Towny/master/src/ChangeLog.txt)


## New in 0.83.0.0 ##

  * New commands:
    * /ta toggle npc {residentname} - Toggles a player's resident file to isNPC=true, this exempts the player from taxes/upkeep.

  * Changes to Permission nodes:
    * Change the tests on emptying buckets so we actually test the bucket we are emptying as well as the bucket we end up with.
      * To fill a water bucket in the wild the player requires...
        * towny.wild.item\_use.326
      * To empty a water bucket in the wild the player requires...
        * towny.wild.item\_use.326
        * towny.wild.item\_use.325
    * Prevent '/res toggle spy' bypassing the perm check for 'towny.chat.spy' permission.
    * Remove assitant override for town perms build/break. These should now only belong to the town Mayor. Assistant is just another rank.
      * To return past abilities give the town assistant rank in the townyperms.yml the towny.claimed.owntown.`*` node.

  * New Permission Nodes:
    * Add ability to limit outposts. Permissions info/option node based 'towny\_maxoutposts' (TomSki).

  * Added:
    * Added new events for other plugins to utilize:
      * DeleteNationEvent, DeleteTownEvent, NewNationEvent, NewTownEvent, RenameNationEvent, RenameTownEvent.
      * Added TownClaim and Unclaim events for plugins to utilise.
      * Added a hook to the Town object for other plugins to foce PvP on in a town 'setAdminEnabledPVP(true/false)'
        * This setting does not save on shutdown/restart adn can not be set via any configuration files.
        * It is solely for War plugins to gain control over a towns PvP setting.
      * Add a PlotClear event.
    * Added a config toggle to disable creatures triggering stone pressure plates.
    * Allow protection of hopper minecarts from being right clicked.
    * Added new block Ids to the switch/item protection lists.
    * Added a 'using\_towny' setting to the 'Default new world settings' of the config. You can now set Towny to default to dsiabled in new worlds.
    * Added a confirmation message to the admin purge command. (Requires Questioner.jar)
    * Added a confirmation message to the town delete command. (Requires Questioner.jar)
    * Added Town and Wilderness PvP entries in the 'Town Notifications' section of the config. You can now disable or customize the PVP message when moving between plots.
    * Added new functionality to Embassy plots:
      * When a player leaves or is kicked from a town they will no longer lose their plots if they are of the Embassy type.
      * Nomads can now purchase Embassy plots, IF they have the plot claim permissions.

  * Bug Fixes!
    * Better directory/file handling when using nested world folders.
    * Fix '/nation ?' to show the new rank command instead of the old assistant one.
    * Update townyperms upon a '/ta reload'.
    * Make wild plots use the relevant worlds 'unclaimed\_zone\_ignore' settings.
    * Update the nations data in SQL when renaming towns.
    * Attempt to load town data even if there is a resident listed who is in another town (flatfile manual editing of files can cause this).
    * Minor english.yml fixes
    * Trap NPE's caused by Spigot unloading a world yet still reporting the world in the world list.
    * Fix Minecart protection so it works in towns again.:
      * Minecart place/break is covered under Item\_use nodes in the config 'block/item/mob protection'.
      * Minecart use is covered under switch\_ids in the config 'block/item/mob protection'.
    * PvP prevention no longer prevents you harming yourself and no longer stops Ender pearl damage.
    * Catch ALL exceptions when deleting a Town/Nation so any economy errors will not prevent them being deleted. (Gringotts)
    * Fixed plot claim to no longer say successful when no plots are claimed.


  * [Click here for a full changelog.](https://raw.github.com/ElgarL/Towny/master/src/ChangeLog.txt)


## New in 0.82.1.0 ##

  * New commands:
    * **TownyChat** Added new channel handling support:
      * 
```
        channel:
        aliases: [ch]
        description: Channel leaving and joining
        usage: /channel leave|join {channel}

        leave:
        description: Leaves a channel
        usage: /leave {channel}
        permission: Requires a permissions per channel to use - towny.chat.leave.{channelname}
		        
        join:
        description: Joins a channel
        usage: /join {channel}
        permission: Requires a permissions per channel to use - towny.chat.join.{channelname}

        chmute:
        aliases: [mute]
        description: Mutes a player in a channel
        usage: /chmute {channel} {player}
        permission: townychat.mod.mute
		        
        mutelist:
        description: Displays mute list for a channel
        usage: /mutelist channel
		        
        chunmute:
        aliases: [unmute]
        description: Unmutes a player in a channel
        usage: /chunmute channel player
        permission: townychat.mod.unmute
```

  * New Permission nodes:
```
      towny.chat.leave.{channelname}
      towny.chat.join.{channelname}
      townychat.mod.mute
      townychat.mod.unmute
```

  * Added:
    * Add support for Wither explosion protection.
    * Add protection for Hanging object protection.
    * Add missing getHandlersList() to Towny events.
    * '/town leave' now triggers a TownRemoveResidentEvent.
    * Resident purges and admin deletion of residents now trigger a TownRemoveResidentEvent.
    * Town status now shows all player ranks, not just assistants (for testing to see if it's too much spam).
    * Players with no destroy perms for crops/soil can no longer trample even if the feature is disabled.
    * Added cancellable mob-removal event.
    * Added events for towns leaving and joining nations.
    * Added Shade's Closed economy system. You can now define an account in the config where any deleted money goes (needs serious testing).

  * Bug Fixes!
    * Prevent players with no build permissions from rotating item frames.
    * Stop throwing NPE's on '/ta unclaim x'.
    * Correctly select townblocks to unclaim when using '/ta unclaim x'.
    * Fix Ender pearl teleports into protected areas. (Only when enderpearl is in the item\_use id list in the config.)
    * Prevent monsters breaking frames unless explosions are enabled.
    * Protect Hanging items from explosions per plot settings.
    * Attach PlayerMoveEvent to its related PlayerMoveChunkEvent (andre).
    * Fix outpost spawns to correctly use the outpost spawn permission when using no outpost number.
    * Fix town and outpost claiming to use the correct permission child nodes.
    * Fix saving a change of plot type.
    * **TownyChat** Fix chat spy.
    * **TownyChat** Only modify the chat format if modify\_chat = true for all channels.

## New in 0.82.0.0 ##

  * Important Change **MUST READ** :
    * This release of Towny contains a new feature, TownyPerms. You will find a new file in your towny\settings\ folder which will require some configuration.
    * Added town/nation ranks so server admins can create custom ranks with custom permission sets. Any existing players set as assistants will lose their rank and need to have it re-assigned.
    * This system pushes permissions directly to Bukkit and works along side all other perms plugins. It allows you to define sets of permissions based upon a players status (nomad/resident/mayor/king). You can also assign additional permissions based upon any assigned town/nation ranks (assistant/vip etc). This system is not limited to Towny perms. You can assign any permissions for any plugins in it's groups.

  * New commands:
    * '/town rank add/remove `<player> <rank>'`
    * '/nation rank add/remove `<player> <rank>'`
    * '/town ranklist' command to display all residents holding ranks in your town.
```
    * Deprecated permission nodes (have replacements)
      * All 'towny.town.toggle...' nodes, and the following...
      * towny.top
      * towny.town.*
      * towny.town.new
      * towny.town.delete
      * towny.town.rename
      * towny.town.claim
      * towny.town.claim.outpost
      * towny.town.plot
      * towny.town.plot.*
      * towny.town.plottype
      * towny.nation.*
      * towny.nation.new
      * towny.nation.delete
      * towny.nation.rename
      * towny.nation.grant-titles
    
    * New permission nodes (auto assigned in townyperms.yml):
      * towny.command.nation.list
      * towny.command.nation.new
      * towny.command.nation.leave
      * towny.command.nation.withdraw
      * towny.command.nation.deposit
      * towny.command.nation.rank.*
      * towny.command.nation.king
      * towny.command.nation.set.*
      * towny.command.nation.set.king
      * towny.command.nation.set.capitol
      * towny.command.nation.set.taxes
      * towny.command.nation.set.name
      * towny.command.nation.set.title
      * towny.command.nation.set.surname
      * towny.command.nation.set.tag
      * towny.command.nation.toggle.*
      * towny.command.nation.toggle.neutral
      * towny.command.nation.ally
      * towny.command.nation.enemy
      * towny.command.nation.delete
      * towny.command.nation.online
      * towny.command.nation.add
      * towny.command.nation.kick

      * towny.command.town.*
      * towny.command.town.here
      * towny.command.town.list
      * towny.command.town.new
      * towny.command.town.leave
      * towny.command.town.withdraw
      * towny.command.town.deposit
      * towny.command.town.rank.*
      * towny.command.town.set.*
      * towny.command.town.set.board
      * towny.command.town.set.mayor
      * towny.command.town.set.homeblock
      * towny.command.town.set.spawn
      * towny.command.town.set.outpost
      * towny.command.town.set.perm
      * towny.command.town.set.taxes
      * towny.command.town.set.plottax
      * towny.command.town.set.shoptax
      * towny.command.town.set.embassytax
      * towny.command.town.set.plotprice
      * towny.command.town.set.shopprice
      * towny.command.town.set.embassyprice
      * towny.command.town.set.name
      * towny.command.town.set.tag
      * towny.command.town.buy
      * towny.command.town.toggle.*
      * towny.command.town.toggle.pvp
      * towny.command.town.toggle.public
      * towny.command.town.toggle.explosion
      * towny.command.town.toggle.fire
      * towny.command.town.toggle.mobs
      * towny.command.town.toggle.taxpercent
      * towny.command.town.toggle.open
      * towny.command.town.mayor
      * towny.command.town.delete
      * towny.command.town.join
      * towny.command.town.add
      * towny.command.town.kick
      * towny.command.town.claim.*
      * towny.command.town.claim.town
      * towny.command.town.claim.outpost
      * towny.command.town.unclaim
      * towny.command.town.online

      * towny.command.plot.*
      * towny.command.plot.claim
      * towny.command.plot.unclaim
      * towny.command.plot.notforsale
      * towny.command.plot.forsale
      * towny.command.plot.perm
      * towny.command.plot.toggle.*
      * towny.command.plot.toggle.pvp
      * towny.command.plot.toggle.explosion
      * towny.command.plot.toggle.fire
      * towny.command.plot.toggle.mobs
      * towny.command.plot.set.*
      * towny.command.plot.set.perm
      * towny.command.plot.set.reset
      * towny.command.plot.set.shop
      * towny.command.plot.set.embassy
      * towny.command.plot.set.arena
      * towny.command.plot.set.wilds
      * towny.command.plot.set.spleef
      * towny.command.plot.clear

      * towny.command.resident.*
      * towny.command.resident.list
      * towny.command.resident.tax
      * towny.command.resident.set.*
      * towny.command.resident.set.perm
      * towny.command.resident.set.mode
      * towny.command.resident.toggle.*
      * towny.command.resident.toggle.pvp
      * towny.command.resident.toggle.explosion
      * towny.command.resident.toggle.fire
      * towny.command.resident.toggle.mobs
      * towny.command.resident.friend
      
      * towny.command.townyadmin.*
      * towny.command.townyadmin.set.*
      * towny.command.townyadmin.set.mayor
      * towny.command.townyadmin.town.*
      * towny.command.townyadmin.town.new
      * towny.command.townyadmin.town.add
      * towny.command.townyadmin.town.kick
      * towny.command.townyadmin.town.delete
      * towny.command.townyadmin.town.rename
      * towny.command.townyadmin.nation.*
      * towny.command.townyadmin.nation.add
      * towny.command.townyadmin.nation.delete
      * towny.command.townyadmin.nation.rename
      * towny.command.townyadmin.toggle.*
      * towny.command.townyadmin.toggle.war
      * towny.command.townyadmin.toggle.neutral
      * towny.command.townyadmin.toggle.devmode
      * towny.command.townyadmin.toggle.debug
      * towny.command.townyadmin.toggle.townwithdraw
      * towny.command.townyadmin.toggle.nationwithdraw
      * towny.command.townyadmin.givebonus
      * towny.command.townyadmin.reload
      * towny.command.townyadmin.reset
      * towny.command.townyadmin.backup
      * towny.command.townyadmin.newday
      * towny.command.townyadmin.purge
      * towny.command.townyadmin.unclaim
      * towny.command.townyadmin.resident.delete

      * towny.command.towny.*
      * towny.command.towny.map
      * towny.command.towny.top
      * towny.command.towny.tree
      * towny.command.towny.time
      * towny.command.towny.universe
      * towny.command.towny.version
      * towny.command.towny.war
      * towny.command.towny.spy

      * towny.command.townyworld.list
      * towny.command.townyworld.set
      * towny.command.townyworld.toggle.*
      * towny.command.townyworld.toggle.claimable
      * towny.command.townyworld.toggle.usingtowny
      * towny.command.townyworld.toggle.pvp
      * towny.command.townyworld.toggle.forcepvp
      * towny.command.townyworld.toggle.explosion
      * towny.command.townyworld.toggle.forceexplosion
      * towny.command.townyworld.toggle.fire
      * towny.command.townyworld.toggle.forcefire
      * towny.command.townyworld.toggle.townmobs
      * towny.command.townyworld.toggle.worldmobs
      * towny.command.townyworld.toggle.revertunclaim
      * towny.command.townyworld.toggle.revertexpl
      * towny.command.townyworld.regen
      * towny.command.townyworld.undo
```
  * Removed:
    * Remove 'resident delete' from the help menu as it's replaced by '/ta delete'.
    * Removed '/Towny top money' command. Use your Eco plugin to perform this action.

  * Added:
    * Added PersonalWorlds and WormholeXTremeWorlds as supported multiworld plugins.
    * Updated mayor help for new rank command.
    * Added the ability to prevent spawning/breeding of villager babies at the world or town level.
    * Added a manual flag on each town allowing a server owner to set a town to disable ALL PvP (adminDisabledPvP) (even when forcepvp is true).
    * All payments into a Town or Nations bank now observe the Bank caps (if enabled).
    * Added plot type prices and taxes to '/towny prices'.

  * Bug Fixes!
    * Make buckets test against item\_use instead of testing the block they are being used on.
    * Fix SQL reading towny-rank when it should be town-ranks.
    * Fix an error setting permissions on Load for Nations.
    * Fix player permissions not being updated when performing a '/ta reload'.
    * Fix explosion regen so containers respawn with the correct inventories.
    * Fix 'ta reload' at console.
    * Be sure we save a town's data if it's the last to leave a nation (causing the nation to be deleted).
    * Fix town and nation withdraws to follow permissions.
    * Prevent a NPE if using '/res' on an invalid player name.
    * Changes to the Town Board are now saved correctly.
    * Fixed plot claim message to correctly indicate success/failure.
    * Embassy plots now charge the correct Plot Tax.
    * Better handling of stale or dead connections for SQL.
    * Fix town limit code.
    * Prevent Shears and other items uses on Entities (sheep) if no permissions.
    * '/town add {name}' now correctly displays the players current town if they are already in one.
    * Fix '/towny prices' so it now shows Taxes instead of Upkeep for Towns/Nations.


## New in 0.81.0.0 ##

  * Important Change **MUST READ** :
    * SQL datbase names changed for h2 and SQLite databases.
> > > If you use this version you need to rename your current h2/sqlite database
> > > in the data folder from 'towny' to 'towny.sqldb' or 'towny.h2db'

  * New commands:
    * /resident toggle {pvp/fire/explosion/mob} - Allows residents to toggle on their personal land, requires towny.town.plot.toggle

  * New permission nodes:
    * None

  * Changed permissions nodes:
    * Add finer control over block permissions. You can now allow the use of specific block types via permissions.
  * eg.
    * Granting '- towny.wild.build.35' allows a player to place wool blocks in the wilderness.
    * Now you can grant '- towny.wild.destroy.35:1' allowing them to only break brown wool.
    * This adds support for custom block types used in mods such as IndustrialCraft, Redpower etc.

  * Removed permission nodes:
    * None

  * Added:
    * Added a config option to only allow attack borders during flag war (disabled allows you to attack any plot).
    * Towny now raises events when a resident joins or leaves a town (handy for other plugins to monitor).
    * Added native support for Tekkit and any mods which require the use of a fake player.
    * Add chest support to wilderness regen/explosion protection (they also no longer drop their contents if being regen'd)
    * You can now disable Enderpearl teleporting buy adding them to the item\_use entry in the config (permissions will override).
    * Allow your own potions to affect you, even if you have friendly fire turned off.
    * Added empty potion bottles to the item\_use protection list.
    * Only show a resident as Online if the requesting player is able to see them (allow for vanish).
    * Remove all resident titles/surnames when deleting a nation.
    * Remove all resident titles/surnames when a town leaves a nation.

  * Bug Fixes!
    * Fix Towny Economy failing to get a world when a town has no townblocks.
    * Fix Forced PVP to actually do something with the new combat code.
    * Fix sign removal upon a town deletion.
    * Fixed integration with Citizens2 for their API changes.
    * Fix '/plot clear' so outsider mayors can't use it.
    * Fix regen tasks so they respawn the correct type of CreatureSpawner.
    * Fix regen tasks so signs keep their text.
    * Fix the backup so it will only backup files it can actually read instead of throwing an error.
    * Fix town animal protection so no one can kill the animals in a plot unless they have perms to break grass in that plot.
    * Fix lava/water bucket hack/exploits.
    * Fix '/plot set' so only the towns mayor or assistants can change town plot types (no outsider mayors).
    * Fix '/plot claim' when not using a permission system.
    * Fix for MySQL using tinyint to hold Booleans.
    * Save the Nation when removing a resident from a town (in case they were an assistant). Possibly the last case of self-corruption found and fixed.
    * Fix NullPointerException when handling fake players on modded servers (aka. Tekkit).


  * [Click here for a full changelog.](https://raw.github.com/ElgarL/Towny/master/src/ChangeLog.txt)

## New in 0.80.1.0 ##

  * New commands:
    * Moved all player modes under the resident object.
      * Modes can now be toggled using the command '/res toggle [mode](mode.md)', as well as the already existing '/res set mode [mode](mode.md)'.
      * An example would be '/res toggle map townclaim'. This would change the state for both of these modes to the opposite of what they were (on/off).
      * Issuing a command of '/res toggle' will clear all modes and set the defaults.
    * Added a new mode '/res toggle plotborder'.
      * This will puff smoke along the plot boundary when you enter a plot to show it's edges. Ideal for marking out plots.
    * Added two new Admin commands...
      * '/townyadmin town [name](name.md) delete'
      * '/townyadmin nation [name](name.md) delete'

  * New permission nodes:
    * None

  * Changed permissions nodes:
    * Simplify Wild and claimed permission nodes to reduce confusion and make the permission tests faster.
      * towny.wild.build.`*`
      * towny.wild.switch.`*`
      * towny.wild.destroy.`*`
      * towny.wild.item\_use.`*`
      * towny.claimed.owntown.build.`*`
      * towny.claimed.alltown.build.`*`
      * towny.claimed.owntown.item\_use.`*`
      * towny.claimed.alltown.item\_use.`*`
      * towny.claimed.owntown.destroy.`*`
      * towny.claimed.alltown.destroy.`*`
      * towny.claimed.owntown.switch.`*`
      * towny.claimed.alltown.switch.`*`

  * Removed permission nodes:
    * 'towny.claimed.build'
    * 'towny.claimed.destroy'
    * 'towny.claimed.switch'
    * 'towny.claimed.item\_use'
    * 'towny.claimed.owntown.`*`'
    * 'towny.claimed.alltown.`*`'

  * Added:
    * Added a new economy handler with support for...
      * Register 1.8+
      * Vault (will require a build newer than 1.2.15-b172)
      * Native iConomy 5.01
    * Add tentative support for Citizens2 to prevent removing NPC created as monsters.
    * Move all Combat test functions to their own class. All now available via 'CombatUtil' (for other plugin devs).
    * Players will now receive a warning message if their town or nation is due for deletion at the next new day tax period.
    * Added Villagers to be protected the same as animals when in a town.
    * Major rewrite of the player caching system. Should make things faster when handling most events.
    * Towny now removes old zip backups when using SQL as well as flatfile.

  * Bug Fixes!
    * Fix monster spawning to obey the 'Force Town Mobs' world setting.
    * Remove the UnclaimedZoneIgnoreId's test when using permissions.
    * Fix Enderman protection block move to follow the world toggle and ignore all other settings.
    * Update the TownBlock data when a plot is taken/given back to the town so it will correctly follow the towns permissions once again.
    * Save the town data to preserve the 'for sale' status when a townblock is put up, or taken off sale.
    * Outposts can no longer be claimed in worlds which are set as unclaimable.
    * Rework all PvP and PvM code to properly prevent damage where approriate.
    * Fix resident purge so it correctly filters up when updating towns/nations.
    * Fix Towny time to count properly with non standard day cycles.
    * Recode Towny time to work with different time zones and daylight savings.
    * Fix an error in the player cache when the player attempting the action has no town.
    * Stop giving point for allied kills in Event War.
    * Fix Arena plots so they are useable by players with friendly fire off.
    * Make war the war results page a little more understandable (event war).
    * Fix sending attack update messages to town defenders as well as attackers (event war).
    * Fix the error with world height on some systems for plot regen (thanks Peewi96).

  * [Click here for a full changelog.](https://github.com/ElgarL/Towny/blob/master/src/ChangeLog.txt)


## New in 0.80.0.0 ##

  * New commands:
    * None


  * New permission nodes:
    * towny.town.spawn.outpost - Allows a player to teleport to the outposts of his town. This node is given as a childnode of towny.town.spawn.town, if you want to disallow teleporting to outpost you must give players the negated node (ex in Groupmanager: - -towny.town.spawn.outpost)
    * Broke admin permissions into seperate nodes. They are all children of 'towny.admin'.
      * towny.admin.town: Allows a player to use '/ta town add/kick'
      * towny.admin.nation: Allows a player to use '/ta nation add/kick'
      * towny.admin.set: Grants access to '/ta set' allowing a player to set and remove NPC mayors.
      * towny.admin.reload: Reload all towny settings and data.
      * towny.admin.reset: Generate a fresh config.yml and perform a full reload of Towny.
      * towny.admin.toggle: Allows a player to toggle any adming settings using '/ta toggle'
      * towny.admin.givebonus: granting of bonus plots to a player/town
      * towny.admin.backup: perform a backup
      * towny.admin.newday: run a new day event for taxes
      * towny.admin.purge: Remove old residents '/ta purge 30'
      * towny.admin.unclaim: Force unclaim of a town plot (any town)
    * Expanded the plot command permission into child nodes.
      * towny.town.plot.claim: Allows use of '/plot claim'
      * towny.town.plot.unclaim: Allows use of '/plot unclaim'
      * towny.town.plot.notforsale: Allows use of '/plot notforsale'
      * towny.town.plot.nfs: Allows use of '/plot nfs'
      * towny.town.plot.forsale: Allows use of '/plot forsale'
      * towny.town.plot.fs: Allows use of '/plot fs'
      * towny.town.plot.perm: Allows use of '/plot perm'
      * towny.town.plot.toggle: Allows use of '/plot toggle...'
      * towny.town.plot.set: Allows use of '/plot set...'
      * towny.town.plot.clear: Allows use of '/plot clear' (mainly for mayors).


  * Changed permissions nodes:
    * 'towny.wild.block.[id](id.md).build' is now 'towny.wild.build.[id](id.md)'
    * 'towny.wild.block.[id](id.md).destroy' is now 'towny.wild.destroy.[id](id.md)'
    * 'towny.wild.block.[id](id.md).switch' is now 'towny.wild.switch.[id](id.md)'
    * 'towny.wild.block.[id](id.md).item\_use' is now 'towny.wild.item\_use.[id](id.md)'
    * 'towny.claimed.owntown.block.[id](id.md).build' is now 'towny.claimed.owntown.build.[id](id.md)'
    * 'towny.claimed.owntown.block.[id](id.md).destroy' is now 'towny.claimed.owntown.destroy.[id](id.md)'
    * 'towny.claimed.owntown.block.[id](id.md).switch' is now 'towny.claimed.owntown.switch.[id](id.md)'
    * 'towny.claimed.owntown.block.[id](id.md).item\_use' is now 'towny.claimed.owntown.item\_use.[id](id.md)'
    * 'towny.claimed.alltown.block.[id](id.md).build' is now 'towny.claimed.alltown.build.[id](id.md)'
    * 'towny.claimed.alltown.block.[id](id.md).destroy' is now 'towny.claimed.alltown.destroy.[id](id.md)'
    * 'towny.claimed.alltown.block.[id](id.md).switch' is now 'towny.claimed.alltown.switch.[id](id.md)'
    * 'towny.claimed.alltown.block.[id](id.md).item\_use' is now 'towny.claimed.alltown.item\_use.[id](id.md)'

  * Added:
    * Updated to support Register 1.7 and Craftconomy.
    * Rework permission inheritances to reduce lag.
    * SQL SUPPORT
      * MySQL, H2, sqlite supported.
    * Traditional /ta toggle war event:
      * Added a new setting under War events 'towns\_are\_neutral'. If set to false, all towns not in nations are attackable during a war event.
    * Shadeness' Flag War Additions:
      * Flag war mode now supports economy payments for specific events. Those being:
      * Payment to server -  to place a warflag.
      * Attacking flagbearer -  pays the defender who broke the flag during a successful defend.
      * Pillage fund - from defending town during a succesfuly attack. A different amount can be set for the homeblock.
        * (Using a negative amount for pillaging will instead cause the attacker to pay a 'rebuilding cost'.)
    * Block 'fire charge' from lighting fires if firespread is disabled (added to item\_use\_ids).
    * Plots lost due to taxes are no longer put up for sale and have their permissions correctly set for their type as per the town settings.
    * You can no longer stand in a non-pvp area and safely shoot players outside in pvp areas.


  * Bug Fixes!
    * Fix saving World lists and world data.
    * Fix world setting checks when not using permissions.
    * Death events in non-towny worlds are no longer tracked for war or death payments.
    * Fixed a bug where you could bypass a toggle permission (plot and town) by using an upper case letter.
    * Creating a new town now correctly sets the plot permissions for the homeblock at creation.\
    * '/plot clear' works again on plots lost due to taxes and all other unowned plots.
    * Fix updating the nation file if the renamed town was the Capital.
    * Fix deleting of nations at newday.
    * Fix saving a towns setting for tax percentage (SQL).
    * Change default permissions on 'shop' plots to follow the owners permissions.
    * Fix for me breaking op's wild override.
    * Fix Admin commands from the console.
    * Fix Wild perms so it will follow the world settings if no permissions are found.
    * There are a lot more bugs which were fixed, see the full changelog.

  * [Click here for a full changelog.](https://github.com/ElgarL/Towny/blob/master/src/ChangeLog.txt)

## New in 0.79.1.0 ##

  * New commands:
    * /tw toggle revertunclaim - toggles the revert-on-unclaim setting for that world.
    * /tw toggle revertexpl - toggles the revert-on-explosion in the wilderness setting for that world.


  * New permission nodes:
    * None


  * Added:
    * Added a new entry in Nation levels 'townBlockLimitBonus'.
      * Joining a nation can now net you bonus TownBlocks.
      * (Configs will not update for this. To enable, delete the nation\_level entries and Towny will add a fresh set of data upon startup)
    * Added Unclaim revert and explosion revert settings to the '/tw' status display.
    * Added an entities list to wild\_revert\_on\_mob\_explosion.
      * You can now add any entities here you want explosions to revent for in the wilderness (including TNTPrimed).
      * These settings are copied to the individual world files, so you must make changes per world.
    * Added 'My Worlds' the the plugin depends list of multiworld plugins.
    * Expand '/plot clear' usage to plot owners.
    * Update to support the new bpermissions api.
    * Don't allow players to kill animals in plots they don't have destroy permissions in.


  * Bug Fixes!
    * There are a lot of bugs which were fixed, see the full changelog.

  * [Click here for a full changelog.](https://github.com/ElgarL/Towny/blob/master/src/ChangeLog.txt)

## New in 0.79.0.0 ##

  * New commands:
    * /tw regen - Regens full chunks back to the current seed. Not to be used if your plot\_size is anything other than 16
    * /tw undo - Undoes regen command, holds up to 5 undos in memory.
    * /ta town {name} rename {newname} - Admins can change town's name.
    * /ta nation {nation} rename {newname} - Admins can change a nation's name.
    * /towny spy or /res set mode spy - Requires towny.chat.spy and TownyChat. Used to see all chat channels.
    * /town toggle open - Set your town to Open status, so anyone can join your town without an invite.
    * /town join {townname} - Used to join an open town.
    * /town set outpost - Set an outpost's spawn.
    * /town outpost x - Teleport to an outpost's spawn, where x is the number of the outpost.


  * New permission nodes:
    * towny.chat.spy - To spy on chat channels.
    * towny.chat.local - To speak in local chat.
    * towny.town.toggle.open - To be able to set a town to Open status.


  * Added:
    * This version contains quite a few additions to Townychat.jar
      * First you will notice two new files Channels.yml and ChatConfig.yml
      * Chat section of the config.yml has been moved to these new files.
    * Local Chat added.
    * Chat spying added.
    * Added a spam filter to TownyChat so players can only send one message every two seconds.
    * Open/Free-to-join towns are now possible.
      * Mayors use /town toggle open and players use /town join {townname} to join a town without being invited.
    * Added potion splash protection for non PvP areas.
    * Outposts can now have their own spawns.
      * The plot you claim as an outpost is the plot which can contain a spawn. It cannot be moved to another plot.
    * Added a spanish translation.
    * Added SAFEMODE
      * If Towny fails to load from data corruption it 'should' now lock everything into safe mode until it's fixed.
      * No one will be able to do anything at all until the fault is cleared.


  * Bug Fixes!
    * The number of bug fixes in this release are HUGE, it is recommended you look over the complete changelog to see what has been fixed.


## New in 0.78.0.0 ##

  * New commands:
    * /g - Enters global chat, or /g texthere to speak in global chat while remaining in another chatchannel.
    * /tc - Enters townchat, or /tc texthere to speak in townchat while remaining in another chatchannel.
    * /nc - Enters nationchat, or /nc texthere to speak in nationchat while remaining in another chatchannel.
    * /a - Enters adminchat, or /a texthere to speak in adminchat while remaining in another chatchannel.
    * /m - Enters modchat, or /m texthere to speak in modchat while remaining in another chatchannel.
    * /res tax - Shows the total taxes a player will have to pay at the next new day.
    * /townyadmin town {townname} kick {residentname} - Kick players from a town.

  * New permission nodes:
    * towny.town.plottype : Users can use the /plot set {plottype} commands (Mayors can always use this command, without the node.)

  * towny.claimed.`*` : User can build/destroy/switch/item\_use in all towns. These new nodes are useful for moderators, without giving them towny.admin.
    * towny.claimed.build : User can build in all towns.
    * towny.claimed.destroy : User can destroy in all towns.
    * towny.claimed.switch : User can switch in all towns.
    * towny.claimed.item\_use : User can use use items in all towns.

  * towny.claimed.alltown.block.`*` : User is able to edit specified/all block types in all towns.
  * towny.claimed.alltown.block.`*`.build
  * towny.claimed.alltown.block.`*`.destroy
  * towny.claimed.alltown.block.`*`.switch : User can switch specified/all block types in all towns.
  * towny.claimed.alltown.block.`*`.item\_use

  * towny.claimed.owntown.block.`*` : User is able to edit specified/all block types in their own town.
  * towny.claimed.owntown.block.`*`.build
  * towny.claimed.owntown.block.`*`.destroy : (handy to allow clearing of snow '78')
  * towny.claimed.owntown.block.`*`.switch
  * towny.claimed.owntown.block.`*`.item\_use

  * towny.town.claim.outpost : to allow/block claiming of outposts via permissions. (Will still require outposts to be enabled in the config.)

  * Added:
    * Support for CraftIRC admin chat channel, useful for monitoring all chat channels via an IRC channel.
```
      	  Add a channel tag of 'admin' to the
      	  receiving channel in the craftIRC config.
      	  
      	  Disable Auto paths and add the following to the 'paths:' section
      
      	    - source: 'minecraft'
      	      target: 'admin'
      	      formatting:
      	        chat: '%message%'
      	      
      	    - source: 'admin'     # These are endpoint tags
      	      target: 'minecraft'    #
      	      formatting:
	        chat: '%foreground%[%red%%ircPrefix%%sender%%foreground%] %message%'
```
    * Added daily upkeep to the '/town' and '/nation' outputs.
    * Added auto modes for chat. (See above new commands.)
    * Added {worldname} as a chat tag entry.
    * Added 'delete\_economy\_account' so admins can control whether Towny will delete the economy account when it deletes the resident.
    * Added minimum distance checks on outpost claims 'min\_distance\_from\_town\_homeblock'.
    * Added a new entry in 'Default Town/Plot flags' so admins can configure the defaults for pvp,fire,explosions and mob spawns.
    * Added new settings in 'Default new world settings' for fire, explosions and enderman pickup/place. Admins can now configure settings per world for the wilderness.
    * Adjusted world PVP so it only affects the wilderness. You can now disable PVP in the wilderness yet allow it in towns (enabled town pvp overrides plot pvp).
    * Added a new toggle so you can disable block damage during a war event (allow\_block\_griefing).
    * Added new plot type identifiers on the towny map.
      * W - Wilds
      * C - Commercial/Shop
      * A - Arena
      * (shop plots which are for sale will show up as a blue $)
    * You can now set default modes via permissions. To set a group/user to default to town chat and have the map as they walk...
      * If using GroupManager...
        * add an Info node to the group/user towny\_default\_modes: 'tc,map'
      * If using PEX...
        * add an Options node for towny\_default\_modes: 'tc,map'
      * If using any other perms system (not guaranteed to work)
        * add a permission node - towny\_default\_modes.tc,map
    * Added thrown potions to the PVP protection tests (not fully implemented in Bukkit yet).
    * '/townyadmin town/nation' command is now useable at the console.
    * Implemented new GM event system.
    * Implemented PEX events.
    * Added comments to World data files.
    * Wild block ignore permissions no longer fall back to world settings, if set to use a permissions provider.
    * Setting town perms now propogate to any townBlock which have not been manually altered.
    * Setting plot perms will flag that plot as altered so it will not longer update with the towns perms.
    * Each world now supports seperate chat formatting, if enabled in the Towny config (per\_world).
    * Modify the channel formats in the worlds data file instead of in the config (plugins/towny/data/worlds).
    * Added a new taxing system where you can pay plot owners an income for each plot they own.
      * Set a negative town upkeep and enable 'use\_plot\_payments'.
      * At a new day the negative upkeep will be used to calculate the towns upkeep, but instead of taking it from the town, it will be divided between the plot owners.
      * These funds are paid by the server, not the town.

  * Bug Fixes!
    * The number of bug fixes in this release are HUGE, it is recommended you look over the complete changelog to see what has been fixed.

  * [Click here for a full changelog.](http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt#823)

## New in 0.77.1.0 ##

  * New commands:
    * /townyadmin purge x - where x is a number of days, removing old residents who haven't logged in.
    * /town set perm reset - Reset all town plots to the perm line shown in /town.
    * /plot set perm reset - Reset all personally-owned plots to the perm line shown in /res.
    * /plot set wilds - Makes a plot into a Wilds plot-type.

  * New permission nodes:
    * None

  * Added:
    * Wilds plot type - A plot type which allows residents to affect the blocks listed in wild ignore ID list (ores, trees, flowers.)
    * Towny now accepts worlds with spaces in their name.
    * Notification when entering plots with PVP on.

  * Bug Fixes!
    * Fixed resident colour in the config.
    * Fixed Arena plots so they properly allow PVP.
    * Towny cheat protection no longer opperates in worlds where towny is disabled.
    * Fixed an error displaying the mayor error message when he attempts to leave town.
    * Fixed Switch permissions allowing users to open chests with certain items in hand.
    * Only plot owners/mayors and assistants can now use plot toggle.
    * Fixed '/res delete 

&lt;name&gt;

' and made it admin only.
    * Fixed 'plot claim/unclaim' to set correct plot permissions based upon plot type.

  * [Click here for a full changelog.](http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt#781)

## New in 0.77.0.0 ##

  * New commands:
    * /plot perm - displays the current plots permissions.
    * /plot toggle {pvp|fire|explosion|mobs} - toggles on/off protections per-plot.
    * /plot set perm {resident/friend/ally/outsider} {build/destroy/switch/itemuse} {on/off} - set plot perms on a per-plot basis.
    * /plot set arena - Change a plot into an arena plot.
    * /plot set embassy - Change a plot into an embassy plot.
    * /town set embassyprice $ - Set default price for embassy plots.
    * /town set embassytax $ - Set default daily-tax on embassy plots.
    * New chat channels: (requires townychat.jar)
      * /tc - Town chat
      * /nc - Nation chat
      * /m - Moderator chat
      * /a - Admin chat


  * New permission nodes:
    * Permission nodes for chat channels added:
      * towny.chat.town
      * towny.chat.nation
      * towny.chat.mod
      * towny.chat.admin

  * Added:
    * Animals in towns can now only be killed by residents of that town. Unless PVP is enabled.
    * Added 23 and 107 to Switch Id's (Dispenser and Fence Gate).
    * Added 57 (diamond block), 22 (lazuli block), 41 (gold block), 42 (iron block) to plot\_management.revert\_on\_unclaim.block\_ignore.
    * Towny will now kick players who use hacked clients and join with no name, rather than crash.
    * Changed response when setting resident/plot/town perms to be more readable.
    * Added per-plot perms and toggles.
    * Added Embassy and Arena plots.
    * Reduced default plots per resident to 8, instead of 16 (town\_block\_ratio).
    * Threaded plot claim/unclaim to eliminate lag on LARGE claims.
    * Capped claiming by radius to 1000 TownBlocks.
    * Added settings for Questioner accept/deny so server admins can change the default commands to accept or deny invites.
    * Added protection from Enderman griefing (if town mobs are off).
    * Added unclaimed\_zone\_enderman\_protect to prevent endermen moving blocks in the wild.
    * Added economy transaction logging. Towny/logs/money.csv will store transactions/purchases made through Towny.
    * Town and Nation economy accounts are now restricted to 32 characters in length.
    * Moved all chat handling to a seperate jar (TownyChat.jar).
    * TownyChat is a Fully functional chat manager. If it's enabled it will handle all chat and supports custom chat channels.
      * This new system supports total customization of chat.
      * It supports custom chat channels, with access control via permission nodes.
      * Current channels are:-
        * /tc (town chat) permission node: towny.chat.town
        * /nc (nation chat) permission node: towny.chat.nation
        * /a (admin chat) permission node: towny.chat.admin
        * /m (moderators chat) permission node: towny.chat.mod
      * TownyChat also supports other chat plugins (herochat, iChat etc). To use another chat plugin with Towny...
        * Set modify\_chat.enable : false
        * Then add any of the towny tags into your chat plugins format line.


  * Bug Fixes!
    * Fixed overwriting of config.yml.
    * Fixed PEX support.
    * TownBlocks reposessed by the Mayor/Assistants now correctly remove the block from the Residents data files.
    * Changed saving of engligh.yml to ansi until bukkit fix their utf-8 support.
    * Fixed '/town claim'.
    * Possible fix for Essentials teleport refusing to engage and towny defaulting to it's own timer.

  * [Click here for a full changelog.](http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt#663)

## New in 0.76.3.0 ##

  * New commands:
    * None

  * New permission nodes:
    * When using SuperPerms you can now use the following nodes with towny.
      * prefix.prefixhere
      * suffix.suffixhere
      * towny\_maxplots.x (with x being a number eg towny\_maxplots.2)

  * Added:
    * Force load of chunks when using /town spawn, to stop voids.
    * If wild\_revert\_on\_mob\_explosion is enabled, there are no drops anymore.
    * Simplfied Plugin interfacing for Economy plugins to a single setting of using\_economy. Towny will first attempt to use Register.jar. If it's not present it will fall back to the legacy support for iConomy 5.01.
    * Added a permission handler, detecting and supporting all Permission plugins.
    * Admins can now pick, choose and modify which notifications are shown when a player walks from plot to plot. Check the new section 'Town Notifications' in the config.
    * Added a seperate thread for Town Claim to relieve the lag caused by Chunk snapshot generation.
    * Added access from console for '/ta givebonus', and expanded to allow a town or a player to give the bonus to. (bonus given to the player is directly transfered to the town)
    * Pistons can now push between town owned townblocks.

  * Bug Fixes!
    * Fixed an error with Essentials Eco's currency type.
    * Fixed '/town new' and '/nation new' to properly follow the settings in config if permissions are disabled.
    * Fixed modplayername in chat format generating duplicate entries.
    * Towny World commands are now admin only again.
    * Fixed a world bug where Plot revert was following wild revent instead of it's own setting.
    * Fixed town spawn when not using any permissiosn plugins.

  * [Click here for a full changelog.](http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt#663)

## New in 0.76.2.0 ##

  * New commands:
    * /plot clear - For mayors. If a plot is owned by the town (not a resident) the mayor can stand in the plot and type this command to remove all block types as specified in mayor\_plotblock\_delete. This setting is per world so transfers to the world data files at startup.
    * /nation set tag clear - Removes a tag for a nation.
    * /town set tag clear - Removes a tag for a town.

  * New permission nodes:
    * - towny.town.spawn.`*` : Grants all Spawn travel nodes
    * - towny.town.spawn.town : Ability to spawn to your own town.
    * - towny.town.spawn.nation : Ability to spawn to other towns in your nation.
    * - towny.town.spawn.ally : Ability to spawn to towns in nations allied with yours.
    * - towny.town.spawn.public : Ability to spawn to unaffilated public towns.

  * Added:
    * Added upkeep charging based upon the number of town plots (town\_plotbased\_upkeep) rather than resident count. - thanks to phex
    * Changed the daily timer to a set time each day. Configure new\_day\_time to set when you want taxes to be collected.
> > > eg. 15h30m would attempt to collect taxes at 3:30pm.

  * Bug Fixes!
    * Fixed economy errors when using register and iCo6.
    * Fixed Wild permissions.
    * Fixed all issues found in 0.76.1.0 release

  * [Click here for a full changelog.](http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt#582)

## New in 0.76.1.0 ##

  * New commands:
    * /towny time - Shows how long until taxes and upkeep are collected.
    * /nation toggle neutral - Replaces /nation set neutral {on/off}
    * All + commands within the /town command have been combined with the non-plus commands. Ex: /town kick+ playername (for kicking offline players from town,) is now /town kick playername.

  * New permission nodes:
    * - towny.town.delete : player can delete their town
    * - towny.town.rename : player can rename their town
    * - towny.town.toggle.`*` : User has access to all town toggle commands (if a mayor or assistant).
    * - towny.town.toggle.pvp : If a mayor or assistant, the user is able to toggle pvp on/off in their town.
    * - towny.town.toggle.public : If a mayor or assistant....
    * - towny.town.toggle.explosions : If a mayor or assistant....
    * - towny.town.toggle.fire : If a mayor or assistant....
    * - towny.town.toggle.mobs :  If a mayor or assistant....
    * - towny.nation.delete : player can delete their nation
    * - towny.nation.rename : player can rename their nation
    * - towny.nation.grant-titles : King can set nation members titles and surnames '/nation set [title/surname]'.
    * - towny.nation.`*` : Grants all Nation permission nodes.

  * Added support for Register 2.0 (Register.jar) - Supports iConomy5, iConomy6, EssentialsEco, BOSEconomy.

> > (https://github.com/iConomy/Register/tree/master/dist)
    * Using iConomy6, EssentialsEco or BOSEconomy requires register.jar in your plugins folder.

  * Tidy of economy settings (**WARNING** - be sure to check the values after upgrading).

  * Added:
    * (Online) to title Resident Status
    * Neutral in Nation status.
    * Plot Regeneration
> > > When a town plot is unclaimed (by a player or through upkeep) it will slowly begin to revert to a pre town state.
> > > Blocks will slowly change back to whatever blocks we're present at the moment the town block was claimed.
> > > You can configure certain block types you don't want restored to prevent players exploiting regen for diamond ores.
> > > Block types to not restore are configured in block\_ignore in the worlds txt file data/worlds/worldname.txt
> > > Does not affect plots claimed pre-0.76.1
    * Added a block deletion function to remove specified blocks when a town block becomes unclaimed (By a player or through upkeep.)
> > > This is useful for removing Lockette/Deadbolt signs on doors and chests.
> > > Default list includes doors, signs, torchs, redstone + redstone accessories and is set in the config.yml
> > > Block types to remove are configured in unclaim\_delete in the worlds txt file data/worlds/worldname.txt
    * Added a max\_tax\_amount and max\_tax\_percent to the config for towns. Defaults to 1000 and 25% respetively.
    * Titles and surnames can now contain spaces.
    * Added a new setting 'regen\_delay'.
> > > This setting is in seconds. It will allow players to break protected blocks in the wild and towns, even if they dont have permission.
> > > Blocks in the switchID list cannot be destroyed.
> > > The blocks will drop no loot and will regenerate after the specified time.
> > > Defaults to 0 (disabled). This shouldn't be used, will probably be worked into war updates.
    * Added a broadcast if a Nation is disbanded due to a town deletion.
    * Added a new Default new world setting (wild\_revert\_on\_mob\_explosion). When enabled this will replace blocks destroyed by creature explosions in the wild.
    * Towny will now clean up it's backup folder. Default time to keep backups is 90 days (configurable in config).
    * Players can no longer attack another players tamed wolf if in a non PVP area (can attack own).

  * Bug Fixes!
    * Fixed timers in the config.
    * Fixed and error with /town claim #|auto
    * Explosions in the nether caused by using beds are now blocked if a town is there and set to block explosions. However, the fires it generates can not be blocked.
    * Fixed when players are added to the default town they are unable to use '/town leave'.
    * Town data files should now be correctly deleted (moved to the deleted folder) upon removal.
    * Fixed Town and Nation tags size limit.
    * Fixed default Bukkit Permissions to deny instead of allow.
    * Fixed last timer for deleting old residents.
    * Fixed '/xxx claim auto' commands for town and resident.
    * Fixed '/plot unclaim xx' command. Now errors if it's an invalid entry.
    * No more sneak jump to avoid the cheat protection.
    * Fixed resetting tags using '/town set tag '.
    * Fixed overwriting custom language files.
    * Fixed default switch and item use permissions for ally and outsider.
    * Finally fixed the elusive error when getLastLocation() throws a NullPointerException.
    * Fixed an infinite loop when a player uses a fly hack over void.

  * [Click here for a full changelog.](http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt#510)

## New in 0.76 ##

  * New commands:
    * /town online - Shows how many people in your town are online.
    * /nation online - Show how many people in your nation are online.
    * /town buy bonus {amount} - Allows mayors to buy more townblocks without adding residents to their town.
> > > The price is set in the config at `price_purchased_bonus_townblock: '25.0'`.
> > > A maximum amount of townblocks that a mayor can purchase is also in the config at `max_purchased_blocks: '0'`.
    * /ta toggle withdraw - Disables mayors, kings and assistants from withdrawing money from their town banks.
> > > An extra command that works together with the option bank cap set in the config at `bank_cap: '0'`.

  * Added town bank caps and a toggle to enable/disable withdrawls from town banks (prevent players hiding money in towns on PVP worlds) - thanks phrstbrn

  * Bug Fixes!
    * Fixed the shutdown of the Teleport warmup timer on restarts.
    * Fixed enabling Devmode, debug and reading integers from the config.
    * Fix for deleted residents in friend lists causing errors upon deletion, and the same error with nation deletion and enemy/ally lists.
    * Prevent sticky pistons pulling blocks across plot boundaries (if different owners).
    * Fix for getLastLocation() needing to throw a NullPointerException. The players location is now set upon creation of the cache.
    * Fixed old resident deletion for the new config system using seconds instead of milliseconds.
    * Towny now correctly defaults to Bukkits Permissions if using\_permissions is set to false in the config.
    * Added XcraftGate to the plugin.yml as a recognised multiworld plugin.

  * Added a new unclaim\_delete under Plot management in global\_town\_settings. This will delete any items listed when a town unclaims a plot or a town is deleted.
    * Ruined towns can now have all torches removed, to make looters exploring the ruins need torches.
    * All signs (including Lockette signs) can be removed automatically.

  * Added a Maxplots permissions node.
    * If a group doesn't have this node or the node is set to -1 Towny will default back to the max\_plots\_per\_resident in config.

> > Setting max\_plots\_per\_resident to -1 with no permission node, or a node set to -1 will allow infinite plots.
    * Example usage:
```
      		groups:
      		  Default:
      		    default: true
      		    permissions:
      		    - general.spawn
      		    inheritance: []
      		    info:
      		      prefix: ''
      		      build: true
      		      suffix: ''
      		      towny_maxplots: 1
      		  Admins:
      		    default: false
      		    permissions:
      		    - '*'
      		    inheritance:
      		    info:
      		      prefix: ''
      		      build: true
      		      suffix: ''
		      towny_maxplots: -1
```

  * [Click here for a full changelog.](http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt#463)


## New in 0.75 ##

  * Added 'disable\_modify\_chat' for those not wanting Towny to make any changes.

  * New config.yml handling code means no need to delete config at each upgrade. (thanks dumptruckman)

  * Fixed town spawn being lost at load when using non standard (16x16) plot sizes.

  * Allow the ignition of obsidian for lighting portals, even when fire spread is disabled in a town.

  * Implemented Towny's own teleport\_warmup\_time for town spawns.

  * Better formatting for times in the config eg. 1d6h20s.

  * Added new plot types and commands to support.
    * Shop Plots added!
    * '/plot set reset/shop'   - Sets a plot as a Shop plot or resets to a normal plot.
    * '/town set shopprice $$' - Set the towns default price on shop plots.
    * '/town set shoptax $$'   - Set the town tax rate on shop plots, (a shop plot is charged an extra tax on top of any plot taxes.)

  * Added /towny map big, which fills the entire chatbox (after pressing t) with the map.

  * Added new Tags for Towns and Nations. If these are set they will be used instead of the full names in chat.
    * '/town set tag [4 characters](upto.md)'
    * '/nation set tag [4 characters](upto.md)'

  * So much MORE! [Click here for a full changelog.](http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt#414)


## New in 0.74 ##

  * .yml files now encoded in UTF-8, allow for easier multi-language support!

  * Fixed multiverse loading after towny and causing world-file corruption.

  * Towny will now attempt to repair corrupted world Towny data files.

  * **New Permission Node**: towny.town.plot - Grants users the ability to purchase/sell plots of land personally.

  * Added separate charges for own town spawn travel and public spawn travel (set in config.yml).

  * Players in Enemy nations can no longer use spawn travel to opposing public towns.

  * Enabled '/townyadmin backup' use from the console.

  * You can now block the use of '/town spawn' if you are in certain zones (unclaimed 'wilderness', Neutral towns and Enemy towns).

  * Anti-griefing additions!
    * Added the ability to disable player and creature trampling of crops (per world).
    * Paintings cannot be destroyed by outsiders!
    * Pistons can now only operate between similarly owned townblocks or wild areas (No piston griefing).

  * Chat overhaul!
    * Added better control over name/chat modifying - modify\_chat: '{permprefix}{townynameprefix}{playername}{townynamepostfix}{permsuffix}'
    * Better HeroChat intergration.
    * Added custom player prefix/suffixes that Kings can apply to their minions.

  * So much MORE! [Click here for a full changelog.](http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt#383)

## New in 0.73 ##

  * /ta reload now reloads both the config.yml _and_ the Towny data folder! It really works!

  * town-Levels and nation-Levels are now read from the config, start customizing your empires!
    * Also added is an upkeep modifier, make larger towns and nations pay higher upkeeps.

  * Essentials teleport times work again, set usingEssentials: true in your config.yml

  * Added chests and furnaces to the switch\_id's (now protected by switch perm-type)
    * Set switch\_id's in the config.

  * Added a new setting of 'cheat\_protection' to prevent flying and exploiting client-lag to hop walls.

  * Added config.yml settings for force\_explosions\_on, force\_monsters\_on and force\_fire\_on. These will prevent Mayors toggling the settings.

  * Added world configs for Forced Fire, Explosions, Town Mobs and World Mobs into the worldname.txt files.

  * Moved some townyworld commands the a new toggle command
    * /townyworld toggle
      * claimable
      * usingtowny
      * pvp
      * explosion
      * fire
      * townmobs
      * worldmobs

  * Changes to protect towns not in nations from explosions during a war.

  * Town renaming works!
    * Added regex parsing on town and nation renaming.
    * Fixed problem with townfiles holding outdated names and causing errors on the next startup.

  * '/town set homeblock' no longer gets confused by your own homeblock when calculating the distance to the closest homeblock.

  * More! [Click here for a full changelog.](http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt)

## New in 0.72 ##

0.72 is a very big update to Towny, including but not limited to:
  * **Anti-Griefing Overhaul:**
    * Explosions on/off, set by mayors at the Town level using /town toggle explosion
    * Firespread on/off, set by mayors at the Town level using /town toggle fire
    * Itemuse bug, being able to pour water + lava and use lighters when it isn't allowed through plot permissions **Fixed**.

  * **PVP Overhaul:**
    * PVP attacks from the wilderness into towns without PVP no longer do damage.
    * There is a global announcement when PVP is enabled in town.
    * Towny War overhaul begun.

  * **Plot Perms Overhaul:**
    * ALL plot permissions on the resident and town level work properly now.
    * Ally plot perms work properly all the way up to alliances between nations.
    * Itemuse and Switch IDs updated to prevent:
      * Lawn-griefing (bonemeal)
      * Note-griefing (noteblocks)
      * Delay-griefers	(repeaters)
      * Record-thieving (jukeboxes)
      * Hatches and doors!
    * Town perm line reads 'rao' instead of 'fao'

  * **Mob-removal Overhaul:**
    * Config.yml has two configurable groups:
      * `town_mob_removal_entities:`
      * `world_mob_removal_entities:`
    * /town toggle mobs
      * Allows mayors to turn the entities listed in `town_mob_removal_entities:` on or off in their town.
      * Remove monsters or don't and besiege your town to the monster hoard.
    * /townyadmin toggle townmobs
      * Allows an admin to toggle use of /t toggle mobs
      * Force towns to fight the monsters at their front doors!
    * /townyadmin toggle worldmobs
      * Allows an admin to turn the entities listed in `world_mob_removal_entities:` on or off in any world where usingtowny=true.
      * Remove unwanted mobs such as squid or slimes.

  * **Config.yml replaces config.properties.**
    * Town-levels.csv and Nation-levels.csv integrated, no longer used.
    * Supports language files.

  * **New and re-organized commands:**
    * /town toggle
      * explosion
      * fire
      * mobs
      * public
      * pvp
    * /townyadmin toggle
      * war
      * neutral
      * townmobs
      * worldmobs
      * debug
      * devmode
    * Takes the some of the burden off of /town set, which displays all sub-commands again.
    * Town and Nation renaming re-enabled with _/town set name_ and _nation set name_.

  * **Nation-invites now checked properly by Questioner,** nation leaders cannot add towns and involuntarily and bankrupt them with high taxes!

  * **Safe to use /townyadmin reload**, edit config.yml without stopping your server.

  * Fixed /towny map, displays cleaner and doesn't flood chat as much.

  * Every bug we could find has been fixed.

  * More! [Click here for a full changelog.](http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt)

## New in 0.77.1.0 ##

  * New commands:
    * /townyadmin purge x - where x is a number of days, removing old residents who haven't logged in.
    * /town set perm reset - Reset all town plots to the perm line shown in /town.
    * /plot set perm reset - Reset all personally-owned plots to the perm line shown in /res.
    * /plot set wilds - Makes a plot into a Wilds plot-type.

  * New permission nodes:
    * None

  * Added:
    * Wilds plot type - A plot type which allows residents to affect the blocks listed in wild ignore ID list (ores, trees, flowers.)
    * Towny now accepts worlds with spaces in their name.
    * Notification when entering plots with PVP on.

  * Bug Fixes!
    * Fixed resident colour in the config.
    * Fixed Arena plots so they properly allow PVP.
    * Towny cheat protection no longer opperates in worlds where towny is disabled.
    * Fixed an error displaying the mayor error message when he attempts to leave town.
    * Fixed Switch permissions allowing users to open chests with certain items in hand.
    * Only plot owners/mayors and assistants can now use plot toggle.
    * Fixed '/res delete 

&lt;name&gt;

' and made it admin only.
    * Fixed 'plot claim/unclaim' to set correct plot permissions based upon plot type.

  * [Click here for a full changelog.](http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt#781)

```
    -Towny Advanced 0.84.0.0 received 14705 downloads.
    -Towny Advanced 0.83.0.0 received 9768 downloads.
    -Towny Advanced 0.82.1.0 received 28039 downloads.
    -Towny Advanced 0.82.0.0 received 56295 downloads.
    -Towny Advanced 0.81.0.0 received 37155 downloads.    
    -Towny Advanced 0.80.1.0 received 36524 downloads.    
    -Towny Advanced 0.80.0.0 received 21928 downloads.    
    -Towny Advanced 0.79.1.0 received 17072 downloads.    
    -Towny Advanced 0.79.0.0 received 16258 downloads.
    -Towny Advanced 0.78.0.0 received 8748 downloads.
    -Towny Advanced 0.77.1.0 received 18204 downloads.
    -Towny Advanced 0.77.0.0 received 5004 downloads.
    -Towny Advanced 0.76.3.1 received 2627 downloads.
    -Towny Advanced 0.76.2.0 received 1809 downloads.
    -Towny Advanced 0.76.1.0 received 1758 downloads.
    -Towny Advanced 0.76.0 received 2784 downloads.
    -Towny Advanced 0.75.1 received 1644 downloads.
    -Towny Advanced 0.75.0 received  126 downloads.
    -Towny Advanced 0.74.0 received 3012 downloads.
    -Towny Advanced 0.73.2 received 2580 downloads.
    -Towny Advanced 0.73.1 recieved 1501 downloads.
    -Towny Advanced 0.73.0 received  412 downloads.
    -Towny Advanced 0.72.1 received 1230 downloads.
```