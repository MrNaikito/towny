package com.palmergames.bukkit.towny.object;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;



/**
 * @author ElgarL
 *
 */
public class TownyRegenAPI extends TownyUniverse {
	
	// table containing snapshot data of active reversions.
	private static Hashtable<String, PlotBlockData> PlotChunks = new Hashtable<String, PlotBlockData>();
	
	// A list of worldCoords which are needing snapshots
	private static List<WorldCoord> worldCoords = new ArrayList<WorldCoord>();
	
	/**
	 * Add a TownBlocks WorldCoord for a snapshot to be taken.
	 * 
	 * @param worldCoord
	 */
	public static void addWorldCoord(WorldCoord worldCoord) {
		if (!worldCoords.contains(worldCoord))
			worldCoords.add(worldCoord);
	}
	/**
	 * @return true if there are any TownBlocks to be processed.
	 */
	public static boolean hasWorldCoords() {
		return worldCoords.size() != 0;
	}
	/**
	 * @return First WorldCoord to be processed.
	 */
	public static WorldCoord getWorldCoord() {
		if (!worldCoords.isEmpty()) {
			WorldCoord wc = worldCoords.get(0);
			worldCoords.remove(0);
			return wc;
		}
		return null;
	}
	
	 /**
	 * @return the plotChunks which are being processed
	 */
	public static Hashtable<String, PlotBlockData> getPlotChunks() {
		return PlotChunks;
	}
	/**
	 * @return true if there are any chunks being processed.
	 */
	public static boolean hasPlotChunks() {
		return !PlotChunks.isEmpty();
	}

	/**
	 * @param plotChunks the plotChunks to set
	 */
	public static void setPlotChunks(Hashtable<String, PlotBlockData> plotChunks) {
		PlotChunks = plotChunks;
	}
	
	/**
	 * Removes a Plot Chunk from the regeneration Hashtable
	 * 
	 * @param PlotBlockData
	 */
	public static void deletePlotChunk(PlotBlockData plotChunk) {
		if (PlotChunks.containsKey(getPlotKey(plotChunk))) {
			PlotChunks.remove(getPlotKey(plotChunk));
			TownyUniverse.getDataSource().saveRegenList();
		}
	}
	
	/**
	 * Adds a Plot Chunk to the regeneration Hashtable
	 * 
	 * @param plotChunks
	 */
	public static void addPlotChunk(PlotBlockData plotChunk, boolean save) {
		if (!PlotChunks.containsKey(getPlotKey(plotChunk))) {
			//plotChunk.initialize();
			PlotChunks.put(getPlotKey(plotChunk), plotChunk);
			if (save)
				TownyUniverse.getDataSource().saveRegenList();
		}
	}
	/**
	 * Saves a Plot Chunk snapshot to the datasource
	 * 
	 * @param PlotBlockData
	 */
	public static void addPlotChunkSnapshot(PlotBlockData plotChunk) {
		if (TownyUniverse.getDataSource().loadPlotData(plotChunk.getWorldName(),plotChunk.getX(),plotChunk.getZ()) == null) {
			TownyUniverse.getDataSource().savePlotData(plotChunk);
		}
	}
	
	/**
	 * Deletes a Plot Chunk snapshot from the datasource
	 * 
	 * @param PlotBlockData
	 */
	public static void deletePlotChunkSnapshot(PlotBlockData plotChunk) {
		TownyUniverse.getDataSource().deletePlotData(plotChunk);
	}
	
	/**
	 * Loads a Plot Chunk snapshot from the datasource
	 * 
	 * @param TownBlock
	 */
	public static PlotBlockData getPlotChunkSnapshot(TownBlock townBlock) {
		return TownyUniverse.getDataSource().loadPlotData(townBlock);
	}
	
	/**
	 * Gets a Plot Chunk from the regeneration Hashtable
	 * 
	 * @param plotChunks
	 */
	public static PlotBlockData getPlotChunk(TownBlock townBlock) {
		if (PlotChunks.containsKey(getPlotKey(townBlock))) {
			return PlotChunks.get(getPlotKey(townBlock));
		}
		return null;
	}
	
	private static String getPlotKey(PlotBlockData plotChunk) {
		return "[" + plotChunk.getWorldName() + "|" + plotChunk.getX() + "|" + plotChunk.getZ() + "]";	
	}
	
	public static String getPlotKey(TownBlock townBlock) {
		return "[" + townBlock.getWorld().getName() + "|" + townBlock.getX() + "|" + townBlock.getZ() + "]";	
	}

	
	
}