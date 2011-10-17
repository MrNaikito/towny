package com.palmergames.bukkit.towny.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.EconomyException;
import com.palmergames.bukkit.towny.NotRegisteredException;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyException;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.TownyUtil;
import com.palmergames.bukkit.towny.object.Coord;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownBlockOwner;
import com.palmergames.bukkit.towny.object.TownBlockType;
import com.palmergames.bukkit.towny.object.TownyEconomyObject;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.palmergames.bukkit.towny.object.TownyWorld;
import com.palmergames.bukkit.towny.object.WorldCoord;
import com.palmergames.bukkit.towny.tasks.PlotClaim;
import com.palmergames.bukkit.util.ChatTools;
import com.palmergames.bukkit.util.Colors;
import com.palmergames.util.StringMgmt;

/**
 * Send a list of all general towny plot help commands to player
 * Command: /plot
 */

public class PlotCommand implements CommandExecutor  {
        
        private static Towny plugin;
        public static final List<String> output = new ArrayList<String>();
        
        static {
                output.add(ChatTools.formatTitle("/plot"));
                output.add(ChatTools.formatCommand(TownySettings.getLangString("res_sing"), "/plot claim", "", TownySettings.getLangString("msg_block_claim")));
                output.add(ChatTools.formatCommand(TownySettings.getLangString("res_sing"), "/plot claim", "[rect/circle] [radius]", ""));
                output.add(ChatTools.formatCommand(TownySettings.getLangString("res_sing") + "/" + TownySettings.getLangString("mayor_sing"), "/plot notforsale", "", TownySettings.getLangString("msg_plot_nfs")));
                output.add(ChatTools.formatCommand(TownySettings.getLangString("res_sing") + "/" + TownySettings.getLangString("mayor_sing"), "/plot notforsale", "[rect/circle] [radius]", ""));
                output.add(ChatTools.formatCommand(TownySettings.getLangString("res_sing") + "/" + TownySettings.getLangString("mayor_sing"), "/plot forsale [$]", "", TownySettings.getLangString("msg_plot_fs")));
                output.add(ChatTools.formatCommand(TownySettings.getLangString("res_sing") + "/" + TownySettings.getLangString("mayor_sing"), "/plot forsale [$]", "within [rect/circle] [radius]", ""));
                output.add(ChatTools.formatCommand(TownySettings.getLangString("res_sing") + "/" + TownySettings.getLangString("mayor_sing"), "/plot clear", "", ""));
                output.add(ChatTools.formatCommand(TownySettings.getLangString("res_sing") + "/" + TownySettings.getLangString("mayor_sing"), "/plot set ...", "", TownySettings.getLangString("msg_plot_fs")));
                output.add(TownySettings.getLangString("msg_nfs_abr"));
        }

        public PlotCommand(Towny instance) {
                plugin = instance;
        }       

        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
                
                if (sender instanceof Player) {
                        Player player = (Player)sender;
                        System.out.println("[PLAYER_COMMAND] " + player.getName() + ": /" + commandLabel + " " + StringMgmt.join(args));
                        if (args == null){
                                for (String line : output)
                                        player.sendMessage(line);
                        } else{
                                try {
                                        parsePlotCommand(player, args);
                                } catch (TownyException x) {
                                        // No permisisons
                                	TownyMessaging.sendErrorMsg(player, x.getError());
                                }
                        }

                } else
                        // Console
                        for (String line : output)
                                sender.sendMessage(Colors.strip(line));
                return true;
        }
        
        public void parsePlotCommand(Player player, String[] split) throws TownyException {
                
            if ((!plugin.isTownyAdmin(player)) && ((plugin.isPermissions()) && (!TownyUniverse.getPermissionSource().hasPermission(player, "towny.town.plot"))))
                throw new TownyException(TownySettings.getLangString("msg_err_command_disable"));
                
            if (split.length == 0 || split[0].equalsIgnoreCase("?")) {
                for (String line : output)
                    player.sendMessage(line);
            } else {
            	Resident resident;
                TownyWorld world;
                Town town;
                try {
                    resident = plugin.getTownyUniverse().getResident(player.getName());
                    world = TownyUniverse.getWorld(player.getWorld().getName());
                    town = resident.getTown();
                } catch (TownyException x) {
                	TownyMessaging.sendErrorMsg(player, x.getError());
                    return;
                }

                try {
                	if (split[0].equalsIgnoreCase("claim")) {
                		
                		if (plugin.getTownyUniverse().isWarTime())
                            throw new TownyException(TownySettings.getLangString("msg_war_cannot_do"));
                		
                        List<WorldCoord> selection = TownyUtil.selectWorldCoordArea(resident, new WorldCoord(world, Coord.parseCoord(player)), StringMgmt.remFirstArg(split));
                        selection = TownyUtil.filterUnownedPlots(selection);
                        int maxPlots = TownySettings.getMaxResidentPlots(resident);
                        
                        if (maxPlots >= 0 && resident.getTownBlocks().size() + selection.size() > maxPlots)
                        	throw new TownyException(String.format(TownySettings.getLangString("msg_max_plot_own"), maxPlots));
                        
                        if (selection.size() > 0) {
                        	
                        	double cost = 0;
                            
                            // Remove any plots Not for sale (if not the mayor) and tally up costs.
                            for (WorldCoord worldCoord : new ArrayList<WorldCoord>(selection)) {
                            	double price = worldCoord.getTownBlock().getPlotPrice();
                            	if (price != -1)
                            		cost += worldCoord.getTownBlock().getPlotPrice();
                            	else {
                            		if (worldCoord.getTownBlock().getTown().isMayor(resident) || worldCoord.getTownBlock().getTown().hasAssistant(resident))
                            		selection.remove(worldCoord);
                            	}
                            }
                            
                            if (TownySettings.isUsingEconomy() && (!resident.canPayFromHoldings(cost)))
                                    throw new TownyException(String.format(TownySettings.getLangString("msg_no_funds_claim"), selection.size(), TownyEconomyObject.getFormattedBalance(cost)));
                    	
                            // Start the claim task
                            new PlotClaim(plugin, player, resident, selection, true).start();

                        } else {
                                player.sendMessage(TownySettings.getLangString("msg_err_empty_area_selection"));
                        }
                	} else if (split[0].equalsIgnoreCase("unclaim")) {
                		
                		if (plugin.getTownyUniverse().isWarTime())
                            throw new TownyException(TownySettings.getLangString("msg_war_cannot_do"));
                		
                		if (split.length == 2 && split[1].equalsIgnoreCase("all"))
                			// Start the unclaim task
                        	new PlotClaim(plugin, player, resident, null, false).start();
                		
                        else {
	                        List<WorldCoord> selection = TownyUtil.selectWorldCoordArea(resident, new WorldCoord(world, Coord.parseCoord(player)), StringMgmt.remFirstArg(split));
	                        selection = TownyUtil.filterOwnedBlocks(resident, selection);
	
	                        if (selection.size() > 0) {
	                        	
	                        	// Start the unclaim task
	                        	new PlotClaim(plugin, player, resident, selection, false).start();
	                        	
	                        } else {
	                                player.sendMessage(TownySettings.getLangString("msg_err_empty_area_selection"));
	                        }
                        }
                	} else if (split[0].equalsIgnoreCase("notforsale") || split[0].equalsIgnoreCase("nfs")) {
                        List<WorldCoord> selection = TownyUtil.selectWorldCoordArea(resident, new WorldCoord(world, Coord.parseCoord(player)), StringMgmt.remFirstArg(split));
                        selection = TownyUtil.filterOwnedBlocks(resident.getTown(), selection);
                        
                        for (WorldCoord worldCoord : selection) {
                                setPlotForSale(resident, worldCoord, -1);
                        }
                	} else if (split[0].equalsIgnoreCase("forsale") || split[0].equalsIgnoreCase("fs")) {
                        WorldCoord pos = new WorldCoord(world, Coord.parseCoord(player));
                        double plotPrice = 0;
                        switch (pos.getTownBlock().getType().ordinal()) {
                        
                        case 0:
                                plotPrice = pos.getTownBlock().getTown().getPlotPrice();
                                break;
                        case 1:
                                plotPrice = pos.getTownBlock().getTown().getCommercialPlotPrice();
                                break;                                                  
                        }

                        if (split.length > 1) {
                                
                                int areaSelectPivot = TownyUtil.getAreaSelectPivot(split);
                                List<WorldCoord> selection;
                                if (areaSelectPivot >= 0) {
                                        selection = TownyUtil.selectWorldCoordArea(resident, new WorldCoord(world, Coord.parseCoord(player)), StringMgmt.subArray(split, areaSelectPivot+1, split.length));
                                        selection = TownyUtil.filterOwnedBlocks(resident.getTown(), selection);
                                        if (selection.size() == 0) {
                                                player.sendMessage(TownySettings.getLangString("msg_err_empty_area_selection"));
                                                return;
                                        }
                                } else {
                                        selection = new ArrayList<WorldCoord>();
                                        selection.add(pos);
                                }
                        
                                // Check that it's not: /plot forsale within rect 3
                                if (areaSelectPivot != 1) {
                                        try {
                                                plotPrice = Double.parseDouble(split[1]);
                                        } catch (NumberFormatException e) {
                                                player.sendMessage(String.format(TownySettings.getLangString("msg_error_must_be_num")));
                                                return;
                                        }
                                }
                                
                                for (WorldCoord worldCoord : selection) {
                                        setPlotForSale(resident, worldCoord, plotPrice);
                                }
                        } else {
                                setPlotForSale(resident, pos, plotPrice);
                        }
                	} else if (split[0].equalsIgnoreCase("perm")) {
                		
                		TownBlock townBlock = new WorldCoord(world, Coord.parseCoord(player)).getTownBlock();
                		TownyMessaging.sendMessage(player, plugin.getTownyUniverse().getStatus(townBlock));
                		
                	} else if (split[0].equalsIgnoreCase("toggle")) {
                		
                		TownBlockOwner owner;
                		
                		TownBlock townBlock = new WorldCoord(world, Coord.parseCoord(player)).getTownBlock();
                		town = townBlock.getTown();
                		
                		if (townBlock.hasResident()){
                			owner = townBlock.getResident();
                			
                			if (resident != owner)
                				throw new TownyException(TownySettings.getLangString("msg_area_not_own"));
                		}
                		
                		plotToggle(player, new WorldCoord(world, Coord.parseCoord(player)).getTownBlock(), StringMgmt.remFirstArg(split));
                		
                	} else if (split[0].equalsIgnoreCase("set")) {
                		
                		split = StringMgmt.remFirstArg(split);

	                    if (split.length > 0) {
	                    	if (split[0].equalsIgnoreCase("perm")) {
	                    		
	                    		//Set plot level permissions (if the plot owner) or Mayor/Assistant of the town.
	                    		
	                    		TownBlockOwner owner;
	                    		
	                    		TownBlock townBlock = new WorldCoord(world, Coord.parseCoord(player)).getTownBlock();
	                    		town = townBlock.getTown();
	                    		
	                    		if (townBlock.hasResident()){
	                    			owner = townBlock.getResident();
	                    			
	                    			if (resident != owner)
	                    				throw new TownyException(TownySettings.getLangString("msg_area_not_own"));
	                    			
	                    		} else {
	                    			owner = townBlock.getTown();
	                    			
	                    			if ((!resident.isMayor()) && (!town.hasAssistant(resident)))
                                            throw new TownyException(TownySettings.getLangString("msg_not_mayor_ass"));
	                    		}
	                    		
	                    		// Check we are allowed to set these perms
	                    		plotToggle(player, townBlock, StringMgmt.remFirstArg(split));
	                    		
	                    		TownCommand.setTownBlockPermissions(player, owner, townBlock.getPermissions(), StringMgmt.remFirstArg(split), true);
								TownyUniverse.getDataSource().saveTownBlock(townBlock);
	                    		return;
	                    	}
	                    	
	                        WorldCoord worldCoord = new WorldCoord(world, Coord.parseCoord(player));
	                        setPlotType(resident, worldCoord, split[0]);
	                        TownyUniverse.getDataSource().saveTownBlock(worldCoord.getTownBlock());
	                        player.sendMessage(String.format(TownySettings.getLangString("msg_plot_set_type"),split[0]));
	
	                    } else {
	                        player.sendMessage(ChatTools.formatCommand("", "/plot set", "reset", ""));
	                        player.sendMessage(ChatTools.formatCommand("", "/plot set", "shop", ""));
	                        player.sendMessage(ChatTools.formatCommand("", "/plot set perm", "?", ""));
	                    }
                	} else if (split[0].equalsIgnoreCase("clear")) {
                		
                		if (!town.isMayor(resident)) {
                			player.sendMessage(TownySettings.getLangString("msg_not_mayor"));
                			return;
                		}
                		
                		TownBlock townBlock = new WorldCoord(world, Coord.parseCoord(player)).getTownBlock();
                        
                        if (townBlock != null) {
                        	if (townBlock.isOwner(town) && (!townBlock.hasResident())) {
                        		for (String material: world.getPlotManagementMayorDelete())
                        		if (Material.matchMaterial(material) != null) {
                        			plugin.getTownyUniverse().deleteTownBlockMaterial(townBlock, Material.getMaterial(material).getId());
                        			player.sendMessage(String.format(TownySettings.getLangString("msg_clear_plot_material"), material));
                        		} else
                        			throw new TownyException(String.format(TownySettings.getLangString("msg_err_invalid_property"), material));
                        	} else
                        		throw new TownyException(String.format(TownySettings.getLangString("msg_already_claimed"), townBlock.getResident().getName()));

                        } else {
                        	// Shouldn't ever reach here as a null townBlock should be caught already in WorldCoord.
                        	player.sendMessage(TownySettings.getLangString("msg_err_empty_area_selection"));
                        }
                        
                	}
                } catch (TownyException x) {
                	TownyMessaging.sendErrorMsg(player, x.getError());
                } catch (EconomyException x) {
                	TownyMessaging.sendErrorMsg(player, x.getError());
                }
            }
        }
        
        public void plotToggle(Player player, TownBlock townBlock, String[] split) {
        	if (split.length == 0) {
            	player.sendMessage(ChatTools.formatTitle("/res toggle"));
            	player.sendMessage(ChatTools.formatCommand("", "/res toggle", "pvp", ""));
                player.sendMessage(ChatTools.formatCommand("", "/res toggle", "explosion", ""));
                player.sendMessage(ChatTools.formatCommand("", "/res toggle", "fire", ""));
                player.sendMessage(ChatTools.formatCommand("", "/res toggle", "mobs", ""));
            } else {

                try {
                    // TODO: Let admin's call a subfunction of this.
                    if (split[0].equalsIgnoreCase("pvp")) {
                    	//Make sure we are allowed to set these permissions.
                    	toggleTest(player,townBlock,StringMgmt.join(split, " "));
                    	townBlock.getPermissions().pvp = !townBlock.getPermissions().pvp;
                        TownyMessaging.sendMessage(player, String.format(TownySettings.getLangString("msg_changed_pvp"), "Plot", townBlock.getPermissions().pvp ? "Enabled" : "Disabled"));
                                        
                    } else if (split[0].equalsIgnoreCase("explosion")) {
                    	//Make sure we are allowed to set these permissions.
                    	toggleTest(player,townBlock,StringMgmt.join(split, " "));
                    	townBlock.getPermissions().explosion = !townBlock.getPermissions().explosion;
                    	TownyMessaging.sendMessage(player, String.format(TownySettings.getLangString("msg_changed_expl"), "the Plot", townBlock.getPermissions().explosion ? "Enabled" : "Disabled"));

                    } else if (split[0].equalsIgnoreCase("fire")) {
                    	//Make sure we are allowed to set these permissions.
                    	toggleTest(player,townBlock,StringMgmt.join(split, " "));
                    	townBlock.getPermissions().fire = !townBlock.getPermissions().fire;
                    	TownyMessaging.sendMessage(player, String.format(TownySettings.getLangString("msg_changed_fire"), "the Plot", townBlock.getPermissions().fire ? "Enabled" : "Disabled"));

                    } else if (split[0].equalsIgnoreCase("mobs")) {
                    	//Make sure we are allowed to set these permissions.
                    	toggleTest(player,townBlock,StringMgmt.join(split, " "));
                    	townBlock.getPermissions().mobs = !townBlock.getPermissions().mobs;
                    	TownyMessaging.sendMessage(player, String.format(TownySettings.getLangString("msg_changed_mobs"), "the Plot", townBlock.getPermissions().mobs ? "Enabled" : "Disabled"));
            
                    } else {
                    	TownyMessaging.sendErrorMsg(player, String.format(TownySettings.getLangString("msg_err_invalid_property"), "town"));
                    	return;
                    } 
                } catch (Exception e) {
                    TownyMessaging.sendErrorMsg(player, e.getMessage());
                }

				TownyUniverse.getDataSource().saveTownBlock(townBlock);
            }
        }  
        
        private void toggleTest(Player player, TownBlock townBlock, String split) throws TownyException {
        	
        	//Make sure we are allowed to set these permissions.
        	Town town = townBlock.getTown();
        	
        	if (split.contains("mobs")) {
        		if (town.getWorld().isForceTownMobs()) 
                    throw new TownyException(TownySettings.getLangString("msg_world_mobs"));
        		if (plugin.isPermissions() && (!TownyUniverse.getPermissionSource().hasPermission(player, "towny.town.toggle.mobs"))) 
        			throw new TownyException(TownySettings.getLangString("msg_err_command_disable"));
        	}
        	
        	if (split.contains("fire")) {
        		if (town.getWorld().isForceFire())
        			throw new TownyException(TownySettings.getLangString("msg_world_fire"));
        		if (plugin.isPermissions() && (!TownyUniverse.getPermissionSource().hasPermission(player, "towny.town.toggle.fire")))
        			throw new TownyException(TownySettings.getLangString("msg_err_command_disable"));
        	}
        	
        	if (split.contains("explosion")) {
        		if (town.getWorld().isForceExpl())
        			throw new TownyException(TownySettings.getLangString("msg_world_expl"));
        		if (plugin.isPermissions() && (!TownyUniverse.getPermissionSource().hasPermission(player, "towny.town.toggle.explosions")))
        			throw new TownyException(TownySettings.getLangString("msg_err_command_disable"));
        	}
        	
        	if (split.contains("pvp")) {
        		if (town.getWorld().isForcePVP())
        			throw new TownyException(TownySettings.getLangString("msg_world_pvp"));
        		if (plugin.isPermissions() && (!TownyUniverse.getPermissionSource().hasPermission(player, "towny.town.toggle.pvp")))
        			throw new TownyException(TownySettings.getLangString("msg_err_command_disable"));
        		if (townBlock.getType().equals(TownBlockType.ARENA))
            		throw new TownyException(TownySettings.getLangString("msg_plot_pvp"));
        	}
        }

	    public void setPlotType(Resident resident, WorldCoord worldCoord, String type) throws TownyException {
	        if (resident.hasTown())
	                        try {
	                                TownBlock townBlock = worldCoord.getTownBlock();
	                                Town town = townBlock.getTown();
	                                if (resident.getTown() != town)
	                                        throw new TownyException(TownySettings.getLangString("msg_err_not_part_town"));
	
	                                if (town.isMayor(resident) || town.hasAssistant(resident))
	                                        townBlock.setType(type);
	                                else
	                    throw new TownyException(TownySettings.getLangString("msg_not_mayor_ass"));
	                        } catch (NotRegisteredException e) {
	                                throw new TownyException(TownySettings.getLangString("msg_err_not_part_town"));
	                        }
	                else
	                        throw new TownyException(TownySettings.getLangString("msg_err_must_belong_town"));
	    }

        public void setPlotForSale(Resident resident, WorldCoord worldCoord, double forSale) throws TownyException {
                if (resident.hasTown())
                        try {
                                TownBlock townBlock = worldCoord.getTownBlock();
                                Town town = townBlock.getTown();
                                if (resident.getTown() != town)
                                        throw new TownyException(TownySettings.getLangString("msg_err_not_part_town"));

                                if (town.isMayor(resident) || town.hasAssistant(resident))
                                        townBlock.setPlotPrice(forSale);
                                else
                                        try {
                                                Resident owner = townBlock.getResident();
                                                if (resident != owner)
                                                        throw new AlreadyRegisteredException(TownySettings.getLangString("msg_not_own_area"));
                                                townBlock.setPlotPrice(forSale);
                                        } catch (NotRegisteredException e) {
                                                throw new TownyException(TownySettings.getLangString("msg_not_own_area"));
                                        }
                                if (forSale != -1)
                                        TownyMessaging.sendTownMessage(town, TownySettings.getPlotForSaleMsg(resident.getName(), worldCoord));
                                else
                                        plugin.getTownyUniverse().getPlayer(resident).sendMessage(TownySettings.getLangString("msg_err_plot_nfs"));
                        } catch (NotRegisteredException e) {
                                throw new TownyException(TownySettings.getLangString("msg_err_not_part_town"));
                        }
                else
                        throw new TownyException(TownySettings.getLangString("msg_err_must_belong_town"));
        }

}
