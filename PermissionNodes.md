[Towny Homepage](http://palmergames.com/towny/).
# Introduction to Permission Nodes #


Towny makes use of Permission nodes to customize the Towny experience to different player-ranks. It does this via an external permission plugin but also natively via the Townyperms.yml file located in the towny\settings\ folder.

By default the townyperms.yml will have all the nodes given to the necessary players. You can customize it as you like. For more info on configuring townyperms.yml see the How Towny Works wikipage.

Since townyperms handles most of Towny's permission nodes the only ones you will probably have to add to your permission plugin's node file will be the nodes in the admin/moderator section found below.

Using a permission plugin such as Group Manager or bPerms is highly recommended. If you do not use a permissions plugin make sure that Towny's config has using\_permissions: false. (It is not recommended to not use a permissions plugin, there are some very useful parts of towny which cannot be customized without using a permissions plugin.

# Permission Nodes #

## Admin/Moderator Nodes ##

  * towny.admin: User is able to use /townyadmin, as well as the ability to build/destroy anywhere. User can also able to make towns or nations when set to admin only.

  * towny.command.townyadmin.`*`
    * towny.command.townyadmin.set.`*`
      * towny.command.townyadmin.set.mayor
    * towny.command.townyadmin.town.`*` - Allows a player to use '/ta town add/kick'
      * towny.command.townyadmin.town.new
      * towny.command.townyadmin.town.add
      * towny.command.townyadmin.town.kick
      * towny.command.townyadmin.town.delete
      * towny.command.townyadmin.town.rename
    * towny.command.townyadmin.nation.`*` - Allows a player to use '/ta nation add/kick'
      * towny.command.townyadmin.nation.add
      * towny.command.townyadmin.nation.delete
      * towny.command.townyadmin.nation.rename
    * towny.command.townyadmin.toggle.`*` - Allows use of '/ta toggle ...'
      * towny.command.townyadmin.toggle.war
      * towny.command.townyadmin.toggle.neutral
      * towny.command.townyadmin.toggle.npc
      * towny.command.townyadmin.toggle.devmode
      * towny.command.townyadmin.toggle.debug
      * towny.command.townyadmin.toggle.townwithdraw
      * towny.command.townyadmin.toggle.nationwithdraw
    * towny.command.townyadmin.givebonus - Allows use of '/ta givebonus...'
    * towny.command.townyadmin.reload - Allows use of '/ta reload'
    * towny.command.townyadmin.reset - Generate a fresh config.yml and perform a full reload of Towny.
    * towny.command.townyadmin.backup - Perform a backup
    * towny.command.townyadmin.newday - Run a new day event for taxes.
    * towny.command.townyadmin.purge - Remove old resident files 'ta purge 30'
    * towny.command.townyadmin.unclaim - Unclaims the plot you are standing in.
    * towny.command.townyadmin.resident.delete - Deletes a specific resident '/ta res delete {name}'


  * towny.claimed.`*` : User can build/destroy/switch/item\_use in all towns. **These should be given to moderator ranks only in most cases.**
    * towny.claimed.owntown.`*`
      * towny.claimed.owntown.build.`*` : User can build in their town.
      * towny.claimed.owntown.destroy.`*` : User can destroy in their town.
      * towny.claimed.owntown.switch.`*` : User can switch in their town.
      * towny.claimed.owntown.item\_use.`*` : User can use use items in their town.
    * towny.claimed.alltown.`*`
      * towny.claimed.alltown.build.`*` : User can build in all towns.
      * towny.claimed.alltown.destroy.`*` : User can destroy in all towns.
      * towny.claimed.alltown.switch.`*` : User can switch in all towns.
      * towny.claimed.alltown.item\_use.`*` : User can use use items in all towns.
    * towny.claimed.townowned.`*` : User is able to edit specified/all block types in their town's owned plots (Town only, not resident owned).
      * towny.claimed.townowned.build.`*` : User can build in their town.
      * towny.claimed.townowned.destroy.`*` : User can destroy in their town.
      * towny.claimed.townowned.switch.`*` : User can switch in their town.
      * towny.claimed.townowned.item\_use.`*` : User can use items in their town.


## /Plot Nodes ##

  * towny.command.plot.`*`
    * towny.command.plot.asmayor - For town plot management, grant this to any ranks you want to have the ability to...
      * reclaim plots from players for the town.
      * Toggle perms and plot settings on any plot in the town.
      * Put plots up for sale and take them down again.
      * Mayors and Assistants can still set plots for sale without the node.
    * towny.command.plot.claim
    * towny.command.plot.unclaim
    * towny.command.plot.notforsale
    * towny.command.plot.forsale
    * towny.command.plot.perm
    * towny.command.plot.toggle.`*`
      * towny.command.plot.toggle.pvp
      * towny.command.plot.toggle.explosion
      * towny.command.plot.toggle.fire
      * towny.command.plot.toggle.mobs
    * towny.command.plot.set.`*`
      * towny.command.plot.set.perm
      * towny.command.plot.set.reset
      * towny.command.plot.set.shop
      * towny.command.plot.set.embassy
      * towny.command.plot.set.arena
      * towny.command.plot.set.wilds
      * towny.command.plot.set.spleef
    * towny.command.plot.clear


## /Resident Nodes ##

  * towny.command.resident.`*`
    * towny.command.resident.list
    * towny.command.resident.tax
    * towny.command.resident.set.`*`
      * towny.command.resident.set.perm
      * towny.command.resident.set.mode
    * towny.command.resident.spawn
    * towny.command.resident.toggle.`*`
      * towny.command.resident.toggle.pvp
      * towny.command.resident.toggle.explosion
      * towny.command.resident.toggle.fire
      * towny.command.resident.toggle.mobs
    * towny.command.resident.friend


## /Town Nodes ##

  * towny.town.`*` : User has access to all .town permission nodes.
    * towny.town.resident : User is able to join a town.
    * towny.town.spawn.`*` : Grants all Spawn travel nodes
      * towny.town.spawn.town : Ability to spawn to your own town.
      * towny.town.spawn.nation : Ability to spawn to other towns in your nation.
      * towny.town.spawn.ally : Ability to spawn to towns in nations allied with yours.
      * towny.town.spawn.public : Ability to spawn to unaffilated public towns.
      * towny.town.spawn.outpost : Ability to spawn to your own town's outposts. This is a child node of towny.town.spawn.town and must be negated if you do not want players to teleport to their outposts (ex in Groupmanager: - -towny.town.spawn.outpost)


  * towny.command.town.`*`
    * towny.command.town.here
    * towny.command.town.list
    * towny.command.town.new : User is able to create a town (used only when town\_creation\_admin\_only: true)
    * towny.command.town.leave
    * towny.command.town.withdraw
    * towny.command.town.deposit
    * towny.command.town.rank.`*`
    * towny.command.town.reslist
    * towny.command.town.set.`*`
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
      * towny.command.town.set.name : player can rename their town
      * towny.command.town.set.tag
    * towny.command.town.buy
    * towny.command.town.toggle.`*` : User has access to all town toggle commands (if a mayor or assistant, residents can toggle on their personal land.)
      * towny.command.town.toggle.pvp
      * towny.command.town.toggle.public
      * towny.command.town.toggle.explosion
      * towny.command.town.toggle.fire
      * towny.command.town.toggle.mobs
      * towny.command.town.toggle.taxpercent
      * towny.command.town.toggle.open
    * towny.command.town.mayor
    * towny.command.town.delete : player can delete their town
    * towny.command.town.join : a player can join an open town
    * towny.command.town.add : player can add a player to their town.
    * towny.command.town.kick :player can kick a player from their town.
    * towny.command.town.claim.`*`
      * towny.command.town.claim.town : User is able to expand his town with /town claim (used when world is set as unclaimable in /townyworld)
      * towny.command.town.claim.outpost : to allow/block claiming of outposts via permissions. (Will still require outposts to be enabled in the config.)
    * towny.command.town.unclaim : player is able to unclaim town land.
    * towny.command.town.online


## /Nation Nodes ##

  * towny.command.nation.`*`
    * towny.command.nation.list
    * towny.command.nation.new
    * towny.command.nation.leave
    * towny.command.nation.withdraw
    * towny.command.nation.deposit
    * towny.command.nation.rank.`*`
    * towny.command.nation.king
    * towny.command.nation.set.`*`
      * towny.command.nation.set.king
      * towny.command.nation.set.capitol
      * towny.command.nation.set.taxes
      * towny.command.nation.set.name
      * towny.command.nation.set.title
      * towny.command.nation.set.surname
      * towny.command.nation.set.tag
    * towny.command.nation.toggle.`*`
      * towny.command.nation.toggle.neutral
    * towny.command.nation.ally
    * towny.command.nation.enemy
    * towny.command.nation.delete
    * towny.command.nation.online
    * towny.command.nation.add
    * towny.command.nation.kick


## /Towny Nodes ##

  * towny.command.towny.`*`
    * towny.command.towny.map
    * towny.command.towny.top
    * towny.command.towny.tree
    * towny.command.towny.time
    * towny.command.towny.universe
    * towny.command.towny.version
    * towny.command.towny.war
    * towny.command.towny.spy


## /Townyworld Nodes ##

  * towny.command.townyworld.`*`
    * towny.command.townyworld.list
    * towny.command.townyworld.set
    * towny.command.townyworld.toggle.`*`
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


## Chat Nodes ##

  * towny.chat.general : Allows a player to use the globalchat channel
  * towny.chat.town : Allows a player to use townchat
  * towny.chat.nation : Allows a player to use nationchat
  * towny.chat.mod : Allows a player to use moderatorchat
  * towny.chat.admin : Allows a player to use adminchat
  * towny.chat.local : Allows a player to use localchat channel
  * towny.chat.spy : Allows a player to see all chat in all channels

  * towny.chat.join.{channelname} : Allows a player to /join {channelname}
  * towny.chat.leave.{channelname} : Allows a player to /leave {channelname}

  * townychat.mod.mute : Allows a moderator to /chmute {channel} {player}, muting another player in a channel.
  * townychat.mod.unmute: Allows a moderator to /chunmute {channel} {player}, un-muting another player in a channel.


## Wilderness Nodes ##

  * towny.wild.`*`
    * towny.wild.build.`*`
    * towny.wild.destroy.`*`
    * towny.wild.switch.`*`
    * towny.wild.item\_use.`*`
      * towny.wild.build.{block id}
      * towny.wild.destroy.{block id}
      * towny.wild.switch.{block id}
      * towny.wild.itemuse.{block id}

## Miscellaneous Nodes ##
  * towny.cheat.bypass : User is allowed to fly, use WorldEdit's compass.

  * For superperms users only:
    * prefix.prefixhere
    * suffix.suffixhere
      * Used for the towny chat configuration's {permprefix} and {permsuffix} when using a permission plugin without prefix: and suffix: sections.
    * towny\_maxplots.x {with x being a number eg towny\_maxplots.2}
    * towny.tax\_exempt - Grant this permisison to any rank you do not want to pay taxes.  ONLY works in TownyPerms . Will not work in external permission plugins.

# Info/Option Nodes #

> This is similar to a permission node as it is added to your permission nodes file(s). Added to the info: section of Groupmanagers' groups or the options: section of PEX's groups.

  * Setting default chat channels/modes on join to the server.
    * towny\_default\_modes: 'map,townclaim,plotborder,global,local,town,nation,mod,admin'
      * sets the default modes on residents, found in the /res set mode command stub.
      * puts players into a townychat chatchannel when they join the server.

  * towny\_maxoutposts: {number}
> > This info node is used to limit the number of outposts a player can claim based on what permission group they are in.

  * towny\_maxplots: {number}
> > This info node is used to limit the number of plots a player can own based on what permissions group they are in. If a group doesn't have this node or the node is set to -1 Towny will default back to the max\_plots\_per\_resident in config.
> > Setting max\_plots\_per\_resident to -1 with no permission node, or a node set to -1 will allow infinite plots.


> Example:
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
  		      towny_default_modes: 'local'
  		      towny_maxoutposts: 2
  		      
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