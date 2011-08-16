package com.palmergames.bukkit.towny;

import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.TownyUniverse;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * @author dumptruckman
 */
public class TeleportWarmupTimerTask extends TownyTimerTask {

    private static Queue<Resident> teleportQueue;

    public TeleportWarmupTimerTask(TownyUniverse universe) {
        super(universe);
        teleportQueue = new ArrayDeque<Resident>();
    }

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();

        while (true) {
            Resident resident = teleportQueue.peek();
            if (resident == null) break;
            if (currentTime > resident.getTeleportRequestTime() + (TownySettings.getTeleportWarmupTime() * 1000)) {
                resident.clearTeleportRequest();
                try {
                    universe.getPlayer(resident).teleport(resident.getTeleportDestination().getSpawn());
                } catch (TownyException ignore) { }
                teleportQueue.poll();
            } else {
                break;
            }
        }
    }

    public static void requestTeleport(Resident resident, Town town) {
        resident.setTeleportRequestTime();
        resident.setTeleportDestination(town);
        teleportQueue.add(resident);
    }

    public static boolean abortTeleportRequest(Resident resident) {
        return teleportQueue.remove(resident);
    }
}
