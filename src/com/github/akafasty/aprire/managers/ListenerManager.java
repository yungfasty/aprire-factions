package com.github.akafasty.aprire.managers;

import com.github.akafasty.aprire.inventories.listeners.*;
import com.github.akafasty.aprire.listeners.PlayerConnectionListener;
import com.github.akafasty.aprire.main.FactionsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {

    public static void init() {

        FactionsPlugin plugin = FactionsPlugin.getPlugin();
        PluginManager pluginManager = Bukkit.getPluginManager();

        pluginManager.registerEvents(new PlayerConnectionListener(), plugin);
        pluginManager.registerEvents(new DisbandInventoryListener(), plugin);
        pluginManager.registerEvents(new KickInventoryListener(), plugin);
        pluginManager.registerEvents(new InviteInventoryListener(), plugin);
        pluginManager.registerEvents(new MembersInventoryListener(), plugin);
        pluginManager.registerEvents(new InvitesInventoryListener(), plugin);
        pluginManager.registerEvents(new LeftInventoryListener(), plugin);

    }

}
