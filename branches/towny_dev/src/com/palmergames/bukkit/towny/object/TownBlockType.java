package com.palmergames.bukkit.towny.object;

/**
 * @author dumptruckman
 */
public enum TownBlockType {
    RESIDENTIAL() {
    },

    COMMERCIAL() {
        @Override
        public double getTax(Town town) {
            return town.getCommercialTax() + town.getPlotTax();
        }
    },

    PUBLIC_MINE() {
    }
    ;

    TownBlockType() {
    }

    public double getTax(Town town) {
        return town.getPlotTax();
    }


}
