package com.palmergames.bukkit.towny.event;

import com.palmergames.util.EconomyTools;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.server.ServerListener;

public class TownyServerListener extends ServerListener
{
    @Override
    public void onPluginEnable(PluginEnableEvent event)
    {
        EconomyTools.Initialize(event.getPlugin());
    }
}
