package com.jskingboo.boodev;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    private static Plugin plugin;
    
    @Override
    public void onEnable(){
        plugin = this;
        registerEvents(this, new CompassJumpTo());
        getCommand("gm").setExecutor(new GamemodeSwitch());
        getCommand("enchant").setExecutor(new BetterEnchant());
        getCommand("name").setExecutor(new NameAndLore());
        getCommand("lore").setExecutor(new NameAndLore());
        getCommand("jumpto").setExecutor(new CompassJumpTo());
    }
    
    @Override
    public void onDisable(){
        plugin = null;
    }
    
    public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners){
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
    
    public static Plugin getPlugin() {
        return plugin;
    }
    
}
