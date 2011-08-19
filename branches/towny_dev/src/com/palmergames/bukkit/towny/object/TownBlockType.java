package com.palmergames.bukkit.towny.object;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dumptruckman
 */
public enum TownBlockType {
    RESIDENTIAL(0) {  // The default Block Type.
    },

    COMMERCIAL(1) {  // Just like residential but has additional tax
        @Override
        public double getTax(Town town) {
            return town.getCommercialPlotTax() + town.getPlotTax();
        }
    },

    // These are subject to change:

    ARENA(2){  // Will have a y-range where pvp is allowed
    },

    EMBASSY(3) {  // For other towns to own a plot in your town.
    },

    PUBLIC(4) {  // Will have it's own permission set
    },

    MINE(5) {  // Will have it's own permission set within a y range
    },

    HOTEL(6) {  // Will stack multiple y-ranges and function like a micro town
    },

    JAIL(7) {  // Where people will spawn when they die in enemy (neutral) towns
    },
    ;

    private int id;
    private static final Map<Integer,TownBlockType> lookup
          = new HashMap<Integer,TownBlockType>();

    TownBlockType(int id) {
        this.id = id;
    }

    static {
          for(TownBlockType s : EnumSet.allOf(TownBlockType.class))
               lookup.put(s.getId(), s);
     }

    public double getTax(Town town) {
        return town.getPlotTax();
    }

    public int getId() {
        return id;
    }

    public static TownBlockType lookup(int id) {
        return lookup.get(id);
    }
}
