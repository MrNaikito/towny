package ca.xshade.bukkit.towny.event;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityExplodeEvent;

import ca.xshade.bukkit.towny.MobRemovalTimerTask;
import ca.xshade.bukkit.towny.NotRegisteredException;
import ca.xshade.bukkit.towny.Towny;
import ca.xshade.bukkit.towny.TownyException;
import ca.xshade.bukkit.towny.TownySettings;
import ca.xshade.bukkit.towny.object.Coord;
import ca.xshade.bukkit.towny.object.TownBlock;
import ca.xshade.bukkit.towny.object.TownyUniverse;
import ca.xshade.bukkit.towny.object.TownyWorld;

public class TownyEntityListener extends EntityListener {
	private final Towny plugin;

	public TownyEntityListener(Towny instance) {
		plugin = instance;
	}
	
	
	@Override
	public void onEntityDamage(EntityDamageEvent event) {

		if (event.isCancelled())
			return;
		
		long start = System.currentTimeMillis();
		
		Entity attacker = null;
		Entity defender = null;

		
		if (event instanceof EntityDamageByProjectileEvent) {
			//plugin.sendMsg("EntityDamageByProjectileEvent");
			EntityDamageByProjectileEvent entityEvent = (EntityDamageByProjectileEvent)event;
			attacker = (entityEvent.getProjectile()).getShooter();
			defender = entityEvent.getEntity();
			
		} else if (event instanceof EntityDamageByEntityEvent) {
			//plugin.sendMsg("EntityDamageByEntityEvent");
			EntityDamageByEntityEvent entityEvent = (EntityDamageByEntityEvent)event;
			attacker = entityEvent.getDamager();
			defender = entityEvent.getEntity();
		}
				
		if (attacker != null) {	
			//plugin.sendMsg("Attacker not null");
			
			TownyUniverse universe = plugin.getTownyUniverse();
			try {
				TownyWorld world = universe.getWorld(defender.getWorld().getName());
				
				// Wartime
				if (universe.isWarTime()) {
					event.setCancelled(false);
					throw new Exception();
				}
				
				Player a = null;
				Player b = null;
				
				if (attacker instanceof Player)
					a = (Player) attacker;
				if (defender instanceof Player)
					b = (Player) defender;
				
				if (preventDamageCall(world, attacker, defender, a, b))
					event.setCancelled(true);
			} catch (Exception e) {
			}
			
			
			plugin.sendDebugMsg("onEntityDamagedByEntity took " + (System.currentTimeMillis() - start) + "ms");
		}
		
	}
	
	@Override
	public void onEntityInteract(EntityInteractEvent event) {
		
		if (event.isCancelled())
			return;
		
		Block block = event.getBlock();
		Entity entity = event.getEntity();
		TownyWorld townyWorld = null;
		
		try {
			townyWorld = plugin.getTownyUniverse().getWorld(block.getLocation().getWorld().getName());
		} catch (NotRegisteredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (townyWorld.isUsingTowny())
			if ((block.getType() == Material.SOIL)) {
					if (((entity instanceof Creature) && (townyWorld.isDisableCreatureTrample())
						|| ((entity instanceof HumanEntity) && (townyWorld.isDisablePlayerTrample()))))   
				event.setCancelled(true);
			}
		
	}
	
	@Override
	public void onEntityExplode(EntityExplodeEvent event) {
		
		Location loc;
		Coord coord;
		List<Block> blocks = event.blockList();
		for (Block block : blocks) {
			
			loc = block.getLocation();
			coord = Coord.parseCoord(loc);
			
			//TODO: expand to protect neutrals during a war
			try {
				TownyWorld townyWorld = plugin.getTownyUniverse().getWorld(loc.getWorld().getName());
				TownBlock townBlock = townyWorld.getTownBlock(coord);
				
				// If explosions are off, or it's wartime and explosions are off and the towns has no nation
				if (!townBlock.getTown().isBANG() || plugin.getTownyUniverse().isWarTime() && !townBlock.getTown().hasNation() && !townBlock.getTown().isBANG()) {
					plugin.sendDebugMsg("onEntityExplode: Canceled " + event.getEntity().getEntityId() + " from exploding within "+coord.toString()+".");
					event.setCancelled(true);
				}
			} catch (TownyException x) {
			}
		
		}	
		
	}
	
	
	public boolean preventDamageCall(TownyWorld world, Entity a, Entity b, Player ap, Player bp) {
		// World using Towny
		if (!world.isUsingTowny())
			return false;
		
		if (ap != null && bp != null)
			if (preventDamagePvP(world, ap, bp) || preventFriendlyFire(ap, bp))
				return true;
		
		
		try {
			// Check Town PvP status
			Coord key = Coord.parseCoord(b);
			TownBlock townblock = world.getTownBlock(key);
			//plugin.sendDebugMsg("is townblock");
			if (!townblock.getTown().isPVP())
				if (bp != null && (ap != null || a instanceof Arrow))
					return true;
			/*
				else if (!TownySettings.isPvEWithinNonPvPZones()) // TODO: Allow EvE >.>
					return true;
			*/
			//plugin.sendDebugMsg("is pvp");
		} catch (NotRegisteredException e) {
		}
		
		return false;
	}
	
	public boolean preventDamagePvP(TownyWorld world, Player a, Player b) {
		// Universe is only PvP
		if (world.isPVP())
			return false;
		//plugin.sendDebugMsg("is not forcing pvp");
		// World PvP
		if (!world.isPVP())
			return true;
		//plugin.sendDebugMsg("world is pvp");
		return false;
	}
	
	public boolean preventFriendlyFire(Player a, Player b) {
		TownyUniverse universe = plugin.getTownyUniverse();
		if (!TownySettings.getFriendlyFire() && universe.isAlly(a.getName(), b.getName()))
			return true;

		return false;
	}
	
	
	@Override
	public void onEntityDeath(EntityDeathEvent event) {
		Entity entity =  event.getEntity();
		
		if (entity instanceof Player) {
			Player player = (Player)entity;
			plugin.sendDebugMsg("onPlayerDeath: " + player.getName() + "[ID: " + entity.getEntityId() + "]");
		}
    }
	
	@Override
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if (event.getEntity() instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity)event.getEntity();
			Location loc = event.getLocation();
			Coord coord = Coord.parseCoord(loc);
			TownyWorld townyWorld = null;
			
			try {
				townyWorld = plugin.getTownyUniverse().getWorld(loc.getWorld().getName());
			} catch (NotRegisteredException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//remove from world if set to remove mobs globally
			if (!townyWorld.hasWorldMobs() && MobRemovalTimerTask.isRemovingWorldEntity(livingEntity)){
						plugin.sendDebugMsg("onCreatureSpawn world: Canceled " + event.getCreatureType() + " from spawning within "+coord.toString()+".");
						event.setCancelled(true);
			}
				
			//remove from towns if in the list and set to remove		
			try {
				
				TownBlock townBlock = townyWorld.getTownBlock(coord);
				if (!townBlock.getTown().hasMobs() && MobRemovalTimerTask.isRemovingTownEntity(livingEntity)) {
					plugin.sendDebugMsg("onCreatureSpawn town: Canceled " + event.getCreatureType() + " from spawning within "+coord.toString()+".");
					event.setCancelled(true);
				}
			} catch (TownyException x) {
			}	

		}
	}
}
