package com.palmergames.bukkit.towny.tasks;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import com.palmergames.bukkit.towny.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.NotRegisteredException;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyException;
import com.palmergames.bukkit.towny.TownyMessaging;
import com.palmergames.bukkit.towny.TownySettings;
import com.palmergames.bukkit.towny.object.PlotBlockData;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownyRegenAPI;
import com.palmergames.bukkit.towny.object.TownyUniverse;
import com.palmergames.bukkit.towny.object.TownyWorld;
import com.palmergames.bukkit.towny.object.WorldCoord;

public class TownClaim extends Thread {
	
	Towny plugin;
	volatile Player player;
	volatile Town town;
	volatile TownyWorld world;
	List<WorldCoord> selection;
	boolean claim, forced;
	
    /**
     * @param plugin reference to towny
     * @param player Doing the claiming, or null
     * @param town The claiming town
     * @param selection List of WoorldCoords to claim/unclaim
     * @param claim or unclaim
     * @param forced admin forced claim/unclaim
     */
    public TownClaim(Towny plugin, Player player, Town town, List<WorldCoord> selection, boolean claim, boolean forced) {
        super();
        this.plugin = plugin;
        this.player = player;
        try {
			this.world = TownyUniverse.getWorld(player.getWorld().getName());
		} catch (NotRegisteredException e) {
		}
        this.town = town;
        this.selection = selection;
        this.claim = claim;
        this.forced = forced;
        this.setPriority(MIN_PRIORITY);
    }
    
    @Override
	public void run() {
    	if (world == null) {
    		TownyMessaging.sendMsg(player, TownySettings.getLangString("msg_err_not_configured"));
    		return;
    	}
    	
		try {			
			if (selection != null) {
			
				for (WorldCoord worldCoord : selection) {
					
					if (claim)
						townClaim(town, worldCoord);
					else {
						this.town = worldCoord.getTownBlock().getTown();
						townUnclaim(town, worldCoord, forced);
					}
						
		            
		            TownyUniverse.getDataSource().saveTown(town);
				}
			} else if (!claim){
				
				townUnclaimAll(town);
			}
			
			if (player != null) {
				
				if (claim)
					TownyMessaging.sendMsg(player, String.format(TownySettings.getLangString("msg_annexed_area"), Arrays.toString(selection.toArray(new WorldCoord[0]))));
				else if (forced)
					TownyMessaging.sendMsg(player, String.format(TownySettings.getLangString("msg_admin_unclaim_area"), Arrays.toString(selection.toArray(new WorldCoord[0]))));
				
			}
			TownyUniverse.getDataSource().saveWorld(world);
			plugin.updateCache();
					
		} catch (TownyException x) {
			TownyMessaging.sendErrorMsg(player, x.getError());
		}
		
    }
    
    private void townClaim(Town town, WorldCoord worldCoord) throws TownyException {               
        try {
                TownBlock townBlock = worldCoord.getTownBlock();
                try {
                        throw new AlreadyRegisteredException(String.format(TownySettings.getLangString("msg_already_claimed"), townBlock.getTown().getName()));
                } catch (NotRegisteredException e) {
                        throw new AlreadyRegisteredException(TownySettings.getLangString("msg_already_claimed_2"));
                }
        } catch (NotRegisteredException e) {
                TownBlock townBlock = worldCoord.getWorld().newTownBlock(worldCoord);
                townBlock.setTown(town);
                if (!town.hasHomeBlock())
                        town.setHomeBlock(townBlock);
                
                // Set the plot permissions to mirror the towns.
                townBlock.setPermissions(town.getPermissions().toString());
                TownyUniverse.getDataSource().saveTownBlock(townBlock);
                
                if (town.getWorld().isUsingPlotManagementRevert()) {
                	PlotBlockData plotChunk = TownyRegenAPI.getPlotChunk(townBlock);
            		if (plotChunk != null) {
            			TownyRegenAPI.deletePlotChunk(plotChunk); // just claimed so stop regeneration.
            		} else {
            			plotChunk = new PlotBlockData(townBlock); // Not regenerating so create a new snapshot.
            			plotChunk.initialize();
            		}
            		if (!plotChunk.getBlockList().isEmpty() && !(plotChunk.getBlockList() == null))
            			TownyRegenAPI.addPlotChunkSnapshot(plotChunk); // Save a snapshot.
            		
            		plotChunk = null;
                }
        }
    }
    
    private void townUnclaim(Town town, WorldCoord worldCoord, boolean force) throws TownyException {
        try {
                TownBlock townBlock = worldCoord.getTownBlock();
                if (town != townBlock.getTown() && !force)
                        throw new TownyException(TownySettings.getLangString("msg_area_not_own"));
                
                plugin.getTownyUniverse().removeTownBlock(townBlock);
                TownyUniverse.getDataSource().deleteTownBlock(townBlock);
                
                townBlock = null;
                
        } catch (NotRegisteredException e) {
                throw new TownyException(TownySettings.getLangString("msg_not_claimed_1"));
        }
    }
    
    private void townUnclaimAll(Town town) {
        plugin.getTownyUniverse().removeTownBlocks(town);
        TownyMessaging.sendTownMessage(town, TownySettings.getLangString("msg_abandoned_area_1"));
    }
}