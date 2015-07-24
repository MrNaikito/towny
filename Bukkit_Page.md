[B](B.md)Plugin: [/B]Towny
[RIGHT](RIGHT.md)                                                     [URL='http://webchat.esper.net/?channels=towny']IRC: Join channel #Towny[/URL][/RIGHT]
[RIGHT](RIGHT.md)[URL='https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=8WWJ76KLFKM4G'][IMG](IMG.md)https://www.paypalobjects.com/en_GB/i/btn/btn_donate_LG.gif[/IMG][/URL][/RIGHT]
> [B](B.md)Version:[/B] v0.78.0.0

> Taking Shades Towny a step farther.

[B](B.md)REQUIRES[/B]
[LIST](LIST.md)
[**]No other plugins, but is best with [URL='http://code.google.com/a/eclipselabs.org/p/towny/wiki/Questioner']Questioner[/URL] so that invitations are given by Towns and Nations to residents and towns.
[/LIST]
[B](B.md)Optional[/B]**

[LIST](LIST.md)
[**]Questioner 0.5 - [URL='http://palmergames.com/downloads/qrel/Questioner.jar']Download Jar[/URL]
[**]Essentials 2.5+ - (Towny /town spawn's obey Essentials teleport cooldown)
[**]All permissions plugins are supported and provide greater customization.
[**]Economy plugin of your choice.
[/LIST]
[B](B.md)Supported Economy Plugins[/B]

[LIST](LIST.md)
[**]iConomy 5 - [URL='http://bukkit.org/deadfly.php?id=587691/51']Download Jar[/URL]
[**][URL='http://forums.bukkit.org/threads/1080.40/']iConomy 6[/URL] **[**][URL='http://forums.bukkit.org/threads/1185.15312/']EssentialsEco[/URL] **[**][URL='http://forums.bukkit.org/threads/19025']BOSEconomy[/URL] **[**] **Requires Register.jar 1.5+ in your plugins folder. [URL='http://dev.bukkit.org/media/files/544/120/Register-1.5.jar']Download Register.jar[/URL]
[/LIST]
[B](B.md)Plugins That Support Towny[/B]**

[LIST](LIST.md)
[**][URL='http://forums.bukkit.org/threads/4336']Lockette 1.4.3+[/URL]
[**][URL='http://forums.bukkit.org/threads/35103']Deadbolt 1.2+[/URL]
[**][URL='http://forums.bukkit.org/threads/4150']ChestShop 3.2+[/URL]
[**][URL='http://forums.bukkit.org/threads/29085']BarterSigns 1.2.1+[/URL]
[**][URL='http://forums.bukkit.org/threads/misc-info-dynmap-towny-v0-10-display-towny-towns-and-nations-on-dynmaps-maps-1337-1493.46680/']Dynmap-Towny[/URL]
[/LIST]
[B](B.md)Download Towny[/B]**

[LIST](LIST.md)
[**][B](B.md)Release version:[/B] [URL='http://palmergames.com/downloads/']Download Jar[/URL] | [URL='http://code.google.com/a/eclipselabs.org/p/towny/']Source[/URL]
[**][B](B.md)Dev Version:[/B] [URL='http://palmergames.com/downloads/']Download Jar[/URL] (Majorly Different - Visit IRC channel before using.)
[**]if using register REQUIRES Register 1.5+ version
[/LIST]
[B](B.md)Installing and Upgrading[/B]**

[URL='http://code.google.com/a/eclipselabs.org/p/towny/wiki/InstallingTowny']Installing for the first time[/URL]
[URL='http://code.google.com/a/eclipselabs.org/p/towny/wiki/UpgradingTowny']Upgrading Towny to latest Release[/URL]

[B](B.md)Config[/B]
Read this page to learn what everything in the config does!
[URL='http://code.google.com/a/eclipselabs.org/p/towny/wiki/DefaultConfig']Default Config.yml[/URL]

[B](B.md)Permission Nodes[/B]
Use these permission nodes if you are also using any permissions plugin (Permissions 2/3, GroupManager, PEX, Superperms)
[URL='http://code.google.com/a/eclipselabs.org/p/towny/wiki/PermissionNodes']Permission Nodes[/URL]

[B](B.md)How Towny Works[/B]
Read this wikipage to learn how Towny works, this page is a must-read for every admin and will unlock the features/customizability in Towny.
[URL='http://code.google.com/a/eclipselabs.org/p/towny/wiki/HowTownyWorks']How it works/Features[/URL]

[B](B.md)Wiki[/B]
[URL='http://code.google.com/a/eclipselabs.org/p/towny/']Towny Wiki Site[/URL]

[B](B.md)Suggestions and Issues[/B]
Make a suggestion - [URL='http://code.google.com/a/eclipselabs.org/p/towny/issues/entry?template=Suggestion']Link[/URL]
Report an issue - [URL='http://code.google.com/a/eclipselabs.org/p/towny/issues/entry?template=Defect%20report%20from%20user']Link[/URL]

[spoiler=Whats new][B](B.md)New in 0.78.0.0[/B]
[LIST](LIST.md)
[**]New commands:
[LIST](LIST.md)
[**] /g - Enters global chat, or /g texthere to speak in global chat while remaining in another chatchannel.
[**] /tc - Enters townchat, or /tc texthere to speak in townchat while remaining in another chatchannel.
[**] /nc - Enters nationchat, or /nc texthere to speak in nationchat while remaining in another chatchannel.
[**] /a - Enters adminchat, or /a texthere to speak in adminchat while remaining in another chatchannel.
[**] /m - Enters modchat, or /m texthere to speak in modchat while remaining in another chatchannel.
[**] /res tax - Shows the total taxes a player will have to pay at the next new day.
[**] /townyadmin town {townname} kick {residentname} - Kick players from a town.
[/LIST]
[LIST](LIST.md)
[**]New Permission Nodes:
[LIST](LIST.md)
[**] towny.town.plottype : Users can use the /plot set {plottype} commands (Mayors can always use this command, without the node.)
[**] towny.claimed.** : User can build/destroy/switch/item\_use in all towns. These new nodes are useful for moderators, without giving them towny.admin.
[**] towny.claimed.build : User can build in all towns.
[**] towny.claimed.destroy : User can destroy in all towns.
[**] towny.claimed.switch : User can switch in all towns.
[**] towny.claimed.item\_use : User can use use items in all towns.
[**] towny.claimed.alltown.block.** : User is able to edit specified/all block types in all towns.
[**] towny.claimed.alltown.block.**.build
[**] towny.claimed.alltown.block.**.destroy
[**] towny.claimed.alltown.block.**.switch : User can switch specified/all block types in all towns.
[**] towny.claimed.alltown.block.**.item\_use
[**] towny.claimed.owntown.block.** : User is able to edit specified/all block types in their own town.
[**] towny.claimed.owntown.block.**.build
[**] towny.claimed.owntown.block.**.destroy : (handy to allow clearing of snow '78')
[**] towny.claimed.owntown.block.**.switch
[**] towny.claimed.owntown.block.**.item\_use
[**] towny.town.claim.outpost : to allow/block claiming of outposts via permissions. (Will still require outposts to be enabled in the config.)
[/LIST]
[/LIST]
[LIST](LIST.md)
[**]Added:
[LIST](LIST.md)
[**] Support for CraftIRC admin chat channel, useful for monitoring all chat channels via an IRC channel.
[code](code.md)
> Add a channel tag of 'admin' to the
> receiving channel in the craftIRC config.**

> Disable Auto paths and add the following to the 'paths:' section

> - source: 'minecraft'
> > target: 'admin'
> > formatting:
> > > chat: '%message%'


> - source: 'admin'     # These are endpoint tags
> > target: 'minecraft'    #
> > formatting:

> chat: '%foreground%[%red%%ircPrefix%%sender%%foreground%] %message%'
[/code]
[**] Added daily upkeep to the '/town' and '/nation' outputs.
[**] Added auto modes for chat. (See above new commands.)
[**] Added {worldname} as a chat tag entry.
[**] Added 'delete\_economy\_account' so admins can control whether Towny will delete the economy account when it deletes the resident.
[**] Added minimum distance checks on outpost claims 'min\_distance\_from\_town\_homeblock'.
[**] Added a new entry in 'Default Town/Plot flags' so admins can configure the defaults for pvp,fire,explosions and mob spawns.
[**] Added new settings in 'Default new world settings' for fire, explosions and enderman pickup/place. Admins can now configure settings per world for the wilderness.
[**] Adjusted world PVP so it only affects the wilderness. You can now disable PVP in the wilderness yet allow it in towns (enabled town pvp overrides plot pvp).
[**] Added a new toggle so you can disable block damage during a war event (allow\_block\_griefing).
[**] Added new plot type identifiers on the towny map.
[LIST](LIST.md)
[**] W - Wilds
[**] C - Commercial/Shop
[**] A - Arena
[**] (shop plots which are for sale will show up as a blue $)
[/LIST]
[**] You can now set default modes via permissions. To set a group/user to default to town chat and have the map as they walk...
[LIST](LIST.md)
[**] If using GroupManager...
[LIST](LIST.md)
[**] add an Info node to the group/user towny\_default\_modes: 'tc,map'
[/LIST]
[/LIST]
[LIST](LIST.md)
[**] If using PEX...
[LIST](LIST.md)
[**] add an Options node for towny\_default\_modes: 'tc,map'
[/LIST]
[/LIST]
[LIST](LIST.md)
[**] If using any other perms system (not guaranteed to work)
[LIST](LIST.md)
[**] add a permission node - towny\_default\_modes.tc,map
[/LIST]
[/LIST]
[**] Added thrown potions to the PVP protection tests (not fully implemented in Bukkit yet).
[**] '/townyadmin town/nation' command is now useable at the console.
[**] Implemented new GM event system.
[**] Implemented PEX events.
[**] Added comments to World data files.
[**] Wild block ignore permissions no longer fall back to world settings, if set to use a permissions provider.
[**] Setting town perms now propogate to any townBlock which have not been manually altered.
[**] Setting plot perms will flag that plot as altered so it will not longer update with the towns perms.
[**] Each world now supports seperate chat formatting, if enabled in the Towny config (per\_world).
[**] Modify the channel formats in the worlds data file instead of in the config (plugins/towny/data/worlds).
[LIST](LIST.md)
[**] Added a new taxing system where you can pay plot owners an income for each plot they own.
[LIST](LIST.md)
[**] Set a negative town upkeep and enable 'use\_plot\_payments'.
[**] At a new day the negative upkeep will be used to calculate the towns upkeep, but instead of taking it from the town, it will be divided between the plot owners.
[**] These funds are paid by the server, not the town.
[/LIST]
[/LIST]**

[/LIST]
[/LIST]
[LIST](LIST.md)
[**]Bug Fixes!
[LIST](LIST.md)
[**] The long span of time between proper releases of Towny saw A Very Great Number of bugfixes, these included the required changes for Minecraft and Bukkit's releases of  1.0.0
[**] Please read the changelog linked direcly below in order to get a full list of bugfixes.
[/LIST]
[/LIST]
[LIST](LIST.md)
[**][URL='http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt#832']Click here for a full changelog.[/URL]
[/LIST]
> -Towny Advanced 0.77.1.0 received 18204 downloads.
> -Towny Advanced 0.77.0.0 received 5004 downloads.
> -Towny Advanced 0.76.3.1 received 2627 downloads.
> -Towny Advanced 0.76.2.0 received 1809 downloads.
> -Towny Advanced 0.76.1.0 received 1758 downloads.
> -Towny Advanced 0.76.0 received 2784 downloads.
> -Towny Advanced 0.75.1 received 1644 downloads.
> -Towny Advanced 0.75.0 received  126 downloads.
> -Towny Advanced 0.74.0 received 3012 downloads.
> -Towny Advanced 0.73.2 received 2580 downloads.
> -Towny Advanced 0.73.1 recieved 1501 downloads.
> -Towny Advanced 0.73.0 received  412 downloads.
> -Towny Advanced 0.72.1 received 1230 downloads.
> [/spoiler]

> [spoiler=Changelog][B](B.md)Changelog[/B]
> v0.77.0.0:
> > - Fixed overwriting of config.yml.
> > - Code change due to PEX changing .has() to static (PEX 1.15).
> > - Animals in towns can now only be killed by residents of that town. Unless PVP is enabled.
> > - TownBlocks reposessed by the Mayor/Assistants now correctly remove the block from the Residents data files.
> > - Added Plot level permissions and commands to control them.
> > > '/plot perm' displays the current plots permissions.
> > > '/plot set perm [on/off] or [resident/friend/ally/outsider] [build/destroy/switch/itemuse] [on/off]'

> > - Added 23 and 107 to Switch Id's (Dispenser and Fence Gate).
> > - Changed saving of engligh.yml to ansi until bukkit fix their utf-8 support.
> > - Added 57 (diamond block), 22 (lazuli block), 41 (gold block), 42 (iron block) to plot\_management.revert\_on\_unclaim.block\_ignore.
> > - Removed redundant code in '/ta unclaim' for clearing a resident data. It's already handled in TownyUniverse.
> > - Fixed TownClaim/unclaim thread not correctly selecting the town when the command was issued by an admin.
> > - Towny will now kick players who use hacked clients and join with no name, rather than crash.
> > - Moved all Towny messaging to it's own class. Depreciated old methods in TownyUniverse.
> > - All '/ta' commands are now accessable ingame and at the console, excluding '/ta town/nation name add' for console.
> > - Fixed an error with the new bukkit config failing to set a value (debug/devmode etc)
> > - Temporary fix for prefixes not being updated in Towny when a player changes permissions groups etc.
> > - Revert the PEX change as they reverted their change of .has() for release.
> > - Added Embassy plots. These may be leased by anyone, so long as they belong to a town (they don't have to be a member of the plot owning town).
> > - New commands:-
> > > '/plot set embassy'
> > > '/town set embassyprice $' - Sets the default price for Embassy plots (can be overridden by '/plot fs $')
> > > '/town set embassytax $'   - This is charged on top of the towns normal plot tax.

> > - Changed response when setting resident/plot/town perms to be more readable.
> > - Fixed '/town claim' which I broke when fixing admin unclaim.
> > - When claiming/unclaiming groups of TownBlocks the message will no longer spam up your screen with the plot list.
> > - Embassy plots now show up on the map as 'E'.
> > - Threaded plot claim/unclaim to eliminate lag on LARGE claims.
> > - plot claiming now supports the all suffix (/plot unclaim all).'
> > - Capped claiming by radius to 1000 TownBlocks.
> > - Reduced default plots per resident to 8, instead of 16 (town\_block\_ratio).
> > - Added settings for Questioner accept/deny so server admins can change the default commands to accept or deny invites.
> > - Added Arena plots. These plots default to PVP enabled, and can not have PVP disabled.
> > - Added plot toggle commands...
> > > '/plot toggle pvp'
> > > '/plot toggle fire'
> > > '/plot toggle explosion'
> > > '/plot toggle mobs'

> > - Arena plots now ignore Friendly fire settings.
> > - Town Health Regen is now disabled in Arean plots.
> > - Added protection from Enderman griefing (if town mobs are off).
> > - Added unclaimed\_zone\_enderman\_protect to prevent endermen moving blocks in the wild.
> > - Entity Attack Permissions: Always allow attacks in Warzones and enemy towns, regardless of town or world PvP settings.
> > - Warzones now have their own block permissions (block/switch/item use/fire/explosion). Explosions can also be configured to allow damage, but prevent block breaks.
> > - Warzones will now no longer follow world or town pvp settings (they are always PvP).
> > - Changed EcoObject function pay(n, collector) -> payTo
> > - Added reason argument for all eco functions. Will be put in money log along with transaction details.
> > - logs folder is now placed in backups.
> > - Debug now has it's own log file when it's turned on.
> > - Towny/logs/money.csv is in the format 'timestamp,reason,obj,amount,collector'. In the case that there is no collector or obj, [Server](Server.md) is entered. towny-war-spoils is shown as '[Server](Server.md) towny-war-spoils'.
> > - Town and Nation economy accounts are now restricted to 32 characters in length.
> > - Possible fix for Essentials teleport refusing to engage and towny defaulting to it's own timer.
> > - Moved all chat handling to a seperate jar (TownyChat.jar).
> > > TownyChat is a Fully functional chat manager. If it's enabled it will handle all chat and supports custom chat channels.
> > > This new system supports total customization of chat.
> > > It supports custom chat channels, with access control via permission nodes.
> > > Current channels are:-
> > > > /tc (town chat) permission node: towny.chat.town
> > > > /nc (nation chat) permission node: towny.chat.nation
> > > > /a (admin chat) permission node: towny.chat.admin
> > > > /m (moderators chat) permission node: towny.chat.mod

> > > TownyChat also supports other chat plugins (herochat, iChat etc). To use another chat plugin with Towny...
> > > > Set modify\_chat.enable : false
> > > > Then add any of the towny tags into your chat plugins format line.

> > - Release.

> v0.77.1.0:
> > - Fixed resident colour in config.
> > - Fixed creation of the logs folder for new installs causing an error at startup.
> > - Added..
> > > {townformatted} - Displays town name if a member of a town (using tag\_format.town).
> > > > {nationformatted} - Displays nation name if a member of a nation (using tag\_format.nation).
> > > > - Changed {townyfullnames} to {townyformatted}.
> > > > - Fixed an error on town loading caused by the new plot level permissions.

> > - Towny now accepts worlds with spaces in their names.
> > - Added a 'wilds' plot type. This type allows mayors to set the plot to wilds and it follows wilderness build/break rules for residents only.
> > > Handy for adding a protection zones arround a town where residents can farm tree's and plant sapplings.

> > - changed modify\_chat.format to modify\_chat.Chat\_format so it will force an upgrade of the chat format for those having trouble with copy/paste.
> > - Fixed Arena plots so they properly allow PVP.
> > - Towny cheat protection no longer opperates in worlds where towny is disabled.
> > - Fixed an error displaying the mayor error message when he attempts to leave town.
> > - Fixed Switch permissions allowing users to open chests with certain items in hand.
> > - You can no longer add your own nation as an enemy/ally.
> > - Extra parsing when loading comma delimited lines to prevent badly edited or empty elements causing issues.
> > - Only plot owners/mayors and assistants can now use plot toggle.
> > - Removed 'msg\_not\_town' from english.yml as it's not longer used.
> > - Fixed '/res delete 

&lt;name&gt;

' and made it admin only.
> > > Command will remove all traces of the resident (including deleting the residents data file).

> > - Towny now supports the Essentials Cooldown and delay.
> > - When using the Towny teleport delay, aborted teleports now refund the money.
> > - Added '/townyadmin purge x', where x is a number of days.
> > > Removes all residents who havn't logged in for X amount of days.

> > - The notification when entering a town will now show if the town has PVP enabled.
> > - Plot PVP settings will now cause a notification if you move between plots with different settings.
> > - Fixed ConcurrentModificationException in utilSaveTownBlocks.
> > - added new commands...
> > > '/town set perm reset' - resets all town owned (non resident) plots to the towns default permissions.
> > > '/plot set perm reset' - resets all plots owned by you to their default permissions.

> > - Fixed 'plot claim/unclaim' to set correct plot permissions based upon plot type.
> > - Added temporary support for Beta 1.9 builds.
> > - Release.


> [URL='http://code.google.com/a/eclipselabs.org/p/towny/source/browse/trunk/Towny/src/ChangeLog.txt']Full Changelog[/URL]
> [/spoiler]

> Credits: Many thanks to all contributers including, but not limited to: Fuzziewuzzie, Shadeness, [URL='http://forums.bukkit.org/members/llmdl.10585/']LlmDl[/URL], SwearWord and dumptruckman.
[/LIST]