package com.zozonteq.strictplugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class StrictPlugin extends JavaPlugin implements Listener {

    private Listener listener = new EventListener();
    @Override
    public void onEnable() {
        super.onEnable();
        getServer().getPluginManager().registerEvents(listener,this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        super.onDisable();
    }
}
