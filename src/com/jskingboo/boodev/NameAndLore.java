package com.jskingboo.boodev;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NameAndLore implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {  
        
        if (sender instanceof Player){
            
            Player p = (Player) sender;
            
            if (label.equals("name")){
                
                if (args.length == 0) {
                    p.sendMessage("/name <name>");
                    p.sendMessage("Use a ~ instead of section signs to mark formatting codes");
                    return true;
                }
                
                if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR){
                    p.sendMessage("Error: No item in hand");
                    return true;
                } else {
                    
                    StringBuilder itemNameBuilder = new StringBuilder(args[0]);
                    for (int argCount = 1; argCount < args.length; argCount++) {
                        itemNameBuilder.append(" ").append(args[argCount]);                    
                    }
                    
                    String itemName = itemNameBuilder.toString();
                    ItemMeta itemMeta = p.getItemInHand().getItemMeta();
                    
                    itemName = itemName.replaceAll("~", "§");
                    
                    itemMeta.setDisplayName(itemName);
                    p.getItemInHand().setItemMeta(itemMeta);
                    
                    p.sendMessage("Successfully renamed item");
                    return true;
                    
                }
            } else if (label.equals("lore")){
                if (args.length == 0) {
                    p.sendMessage("/lore <new lore line>");
                    p.sendMessage("Use a ~ instead of section signs to mark formatting codes");
                    return true;
                } else if (args.length == 1 && args[0].equals("clear")){
                    ItemMeta itemMeta = p.getItemInHand().getItemMeta();
                    itemMeta.setLore(null);
                    p.getItemInHand().setItemMeta(itemMeta);
                    
                    p.sendMessage("Successfully removed lore");
                    return true;
                }
                
                if (p.getItemInHand() == null || p.getItemInHand().getType() == Material.AIR){
                    p.sendMessage("Error: No item in hand");
                    return true;
                } else {
                    
                    StringBuilder itemLoreBuilder = new StringBuilder(args[0]);
                    for (int argCount = 1; argCount < args.length; argCount++) {
                        itemLoreBuilder.append(" ").append(args[argCount]);                    
                    }
                    
                    String itemLore = itemLoreBuilder.toString();
                    ItemMeta itemMeta = p.getItemInHand().getItemMeta();
                    
                    itemLore = itemLore.replaceAll("~", "§");
                    
                    List<String> itemLoreList = new ArrayList<String>();
                    
                    if (itemMeta.hasLore() == true){
                        itemLoreList.addAll(itemMeta.getLore());
                    }
                    
                    itemLoreList.add(itemLore);
                    itemMeta.setLore(itemLoreList);
                    p.getItemInHand().setItemMeta(itemMeta);
                    
                    p.sendMessage("Successfully added lore to item");
                    return true;
                    
                }               
                
            }
            
        } else {
            sender.sendMessage("Error: This command must be called in game");
        }
        
        return true;
    }
}
