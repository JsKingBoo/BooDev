package com.jskingboo.boodev;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Brush implements Listener, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (sender instanceof Player){
            
            Player player = (Player) sender;
            
            if (label.equals("brush")){
                if (args.length <= 2){
                    player.sendMessage("Error: Too few arguments");
                    player.sendMessage("/brush <sphere/cube> <radius> <block>");
                    return true;
                }
                
                int radius = Integer.parseInt(args[1]);
                String blockid = args[2];
        
                if (Material.getMaterial(blockid) == null){
                    player.sendMessage("Error: Invalid block");
                    return true;
                }
                
                if (args[0].equals("sphere") || args[0].equals("cube")){
                    ItemStack brush = new ItemStack(Material.FEATHER);
                    ItemMeta brushMeta = brush.getItemMeta();
                    List<String> brushLore = new ArrayList<String>();
                    brushLore.add("Ugly currently, will change later");

                    if (args[0].equals("sphere")){
                        brushMeta.setDisplayName("s:" + Integer.toString(radius) + ";" + blockid);                        
                    } else if (args[0].equals("cube")){
                        brushMeta.setDisplayName("c:" + Integer.toString(radius) + ";" + blockid);                        
                    }
                    
                    brushMeta.setLore(brushLore);
                    brush.setItemMeta(brushMeta);                    
                    
                    player.getInventory().addItem(brush);  
                    player.sendMessage("Here's your brush! :D");                   
                }              
            }
            
        } else {
            sender.sendMessage("Error: This command must be called in game");
        }

        return true;
    }
    
    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        
        if (player.getItemInHand().getType() == Material.FEATHER){
            if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                event.setCancelled(true);
            }
            
            ItemStack brush = player.getItemInHand();
            ItemMeta bMeta = brush.getItemMeta();
        
            String name = bMeta.getDisplayName();
        
            String type = name.substring(0, 1);
            String radius = name.substring(name.indexOf(":") + 1, name.indexOf(";"));
            String brushBlock = (name.substring(name.indexOf(";") + 1, name.length())).toUpperCase();

            if (Material.getMaterial(brushBlock) == null){
                player.sendMessage("Error: Invalid block");
            } else {

                Block centerBlock = player.getTargetBlock(null, 128);

                World world = player.getLocation().getWorld();     

                int x1 = centerBlock.getLocation().getBlockX() - Integer.parseInt(radius);
                int y1 = centerBlock.getLocation().getBlockY() - Integer.parseInt(radius);
                int z1 = centerBlock.getLocation().getBlockZ() - Integer.parseInt(radius);
                int x2 = centerBlock.getLocation().getBlockX() + Integer.parseInt(radius);
                int y2 = centerBlock.getLocation().getBlockY() + Integer.parseInt(radius);
                int z2 = centerBlock.getLocation().getBlockZ() + Integer.parseInt(radius);       

                double cX = centerBlock.getLocation().getBlockX() + 0.5;
                double cY = centerBlock.getLocation().getBlockY() + 0.5;
                double cZ = centerBlock.getLocation().getBlockZ() + 0.5;
                
                for (int x3 = x1; x3 < x2; x3++){
                    for (int y3 = y1; y3 < y2; y3++){
                        for (int z3 = z1; z3 < z2; z3++){

                            if (type.equals("s")){ //sphere
                                if (Math.sqrt(Math.pow(cX - x3, 2) + Math.pow(cY - y3, 2) + Math.pow(cZ - z3, 2)) <= Integer.parseInt(radius) + 0.5){ //center block was wonky and off center, this is "correction"
                                    world.getBlockAt(x3, y3, z3).setType(Material.getMaterial(brushBlock));
                                }  
                            } else if (type.equals("c")){ //cube
                                world.getBlockAt(x3, y3, z3).setType(Material.getMaterial(brushBlock));                          
                            }         

                        }
                    }
                }
            }
        }
        

    }
        
    
    

}
