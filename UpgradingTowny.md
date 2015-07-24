# Introduction #

Here are instructions for upgrading from an older version of Towny:


# Upgrade Instructions #

  * Stop your server completely.
  * **Backup your entire server!**
  * Download Towny\_Advanced.zip and extract the 4 .jar files into your plugins folder. Register is not required if your server already uses Vault.jar.
  * If you are upgrading from a Dev Build read [here](UsingDevBuilds.md) for possible extra instructions.
  * Start your server.
  * Stop your server.

  * Upgrade from pre-0.79.0.0
    * The chat section of config.yml has been move to a new file, channels.yml. Edit the new channels.yml to your liking.

  * Upgrade from pre-0.82.0.0
    * Towny has had TownyPerms added. This new file located at towny\settings\townyperms.yml will be generated when you first start your server using Towny 0.82.0.0 or higher.
    * TownyPerms adds town/nation ranks so server admins can specify what each rank can do (nomads, residents, mayors, kings, etc) Admins can also create custom ranks with custom permission sets. Any existing players set as assistants will lose their rank and need to have it re-assigned.
    * This system pushes permissions directly to Bukkit and works along side all other perms plugins. It allows you to define sets of permissions based upon a players status (nomad/resident/mayor/king). You can also assign additional permissions based upon any assigned town/nation ranks (assistant/vip etc). This system is not limited to Towny perms. You can assign any permissions for any plugins in it's groups.

  * Start your server.