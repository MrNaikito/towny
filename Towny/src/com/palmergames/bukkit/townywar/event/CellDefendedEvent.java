package com.palmergames.bukkit.townywar.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.palmergames.bukkit.townywar.Cell;


public class CellDefendedEvent extends Event {
	private static final long serialVersionUID = 257333278929768100L;
	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
        return handlers;
    }
 
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    //////////////////////////////
    
	
	private Player player;
	private Cell cell;

	public CellDefendedEvent(Player player, Cell cell) {
		super("CellDefended");
		this.player = player;
		this.cell = cell;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Cell getCell() {
		return cell;
	}
}
