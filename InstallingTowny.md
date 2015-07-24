# Introduction #

Here are instructions for installing Towny for the first time.
There are a few steps so follow them all carefully.

# Step One - Preparing your server. #

  1. Stop your server completely.
  1. Download the following and copy to your Plugins folder (Place .jar files in the plugins folder, not the actual .zip):
    * [Download Towny Advanced.zip](http://www.palmergames.com/downloads/tarel/Towny_Advanced.zip) (Includes Towny, TownyChat and Questioner.)
      * Towny.jar is required at all times,
      * TownyChat.jar is required if you want to have Towny modify chatting in any way. (Adding prefixes and suffixes from your permissions plugin to chat, adding town/nation/world to chat, adding chat channels.)
      * Questioner.jar is required if you want players to receive invites to towns, instead of being added automatically.
    * If you use any economy plugin other than iConomy 5.01 you will be required to download [Register.jar](http://www.palmergames.com/downloads/registerrel/Register.jar) or Vault and put it in your plugins folder.
      * If your economy plugin is supported by Vault, do not use Register.jar, instead use only Vault.
  1. Start your server.
  1. Stop your server.

# Step Two - Bypass Craftbukkit Version Check (Optional) #

  * This step is no longer required. Only old builds of Towny will stop non-craftbukkit servers from enabling towny without setting the bypass. If you cannot find this step in the config.yml you don't need to worry.
  * If you are not using the Recommended Build of Craftbukkit meant for your current version of Towny, (if you are using a craftbukkit made for Tekkit, Spigot, Libigot or Craftbukkit++)
  1. Navigate to the newly-created \plugins\Towny\Settings\ folder.
    * Open **config.yml**
    * Set bypass\_version\_check: true
    * Save the config
  1. Start your server.
  1. Stop your server.

# Step Three - Configuring your existing worlds. #

  * Navigate to the newly-created \plugins\Towny\Data\Worlds\ folder.
  * You will find worldname.txt files created for all your existing worlds.
  * Open each worldname.txt file and carefully read the comments and settings. These are settings which are not altered by changing the config, the config is only used to make settings for newly-made worlds.
  * Edit the files accordingly, of note are the regeneration settings used in each world, it is here you will turn off plots regenerating if a townblock is unclaimed/lost by a town. For an explanation on how regenerations work read the [Plot Regeneration section](http://code.google.com/a/eclipselabs.org/p/towny/wiki/HowTownyWorks#Plot_Regeneration_&_Unclaimed_Plots) of the How Towny Works wikipage.


# Step Four - Configuring Towny's config.yml #

  * Navigate to \Plugins\Towny\Settings\
    * Open **config.yml**
    * Carefully configure config.yml to your liking. Including:
      * Set townblock size, default 16x16 (Cannot be changed later!)
      * Set settings for new default worlds. (Anything you saw in your worldname.txt files.)
      * Set settings for new towns.
    * Economy settings:
      * If you do not use an eco plugin find using\_economy and set it to false.
    * Permissions setting:
      * Your permissions plugin should automatically be detected and using\_permissions: should be set to true.
      * If you use no permissions plugin change using\_permissions: to false.
    * Save config.yml and start your server, you are now ready to found your first town.

# Step Five - Configuring Towny's townyperms.yml (Semi-Optional) #

  * Navigate to \Plugins\Towny\Settings\
    * Open **townyperms.yml**
    * Carefully read over the groups/ranks that are included by default.
    * Add and remove permission nodes from the ranks as you see fit. See the wiki's list of [permission nodes](PermissionNodes.md) to understand what each node is doing.
    * Optionally, add your own ranks to towns and nations, allowing mayors to place their residents into these roles as they see fit.
  * Save townyperms.yml and while ingame type '/townyadmin reload' to reload the townyperms settings.

# Step Six - Configuring Towny's channels.yml (Optional) #

  * If using townychat.jar,
  * Navigate to \Plugins\Towny\Settings\
    * Open **channels.yml**
    * Carefully configure channels.yml to your liking.
    * Save channels.yml.
    * Type '/townychat reload' ingame to reload the channels.yml.

# Step Seven - Configuring Towny's chatconfig.yml (Optional) #

  * If using townychat.jar,
  * Navigate to \Plugins\Towny\Settings\
    * Open **chatconfig.yml**
    * Carefull configure chatconfig.yml to your liking.
    * Explanations of the chat-tags can be found in the comments section of chatconfig.yml or on the wiki [here](http://code.google.com/a/eclipselabs.org/p/towny/wiki/HowTownyWorks#Townychat.jar)
    * Type '/townychat reload' ingame to reload the chatconfig.yml.