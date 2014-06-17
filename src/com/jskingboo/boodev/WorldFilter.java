/* not implemented yet
 * hold on I'm busy
 */

package com.jskingboo.boodev;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WorldFilter implements Listener, CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        int x1 = -1;
        int y1 = -1;
        int z1 = -1;
        int x2 = -1;
        int y2 = -1;
        int z2 = -1;
        
        if (sender instanceof Player){
            
            Player player = (Player) sender;
            
            if (label.equals("wf")){
                
                if (args.length != 0 && args[0].equals("list")){
                    player.sendMessage("JsKingBoo is too lazy to code this");
                    player.sendMessage("He'll tell you right after he finishes his \"homework\"");
                    return true;
                }
                
                if (args.length <= 1){
                    player.sendMessage("Error: Missing arguments");
                    player.sendMessage("For WorldFilter arguments use /wf list");
                    return true;
                }
                
                if (args[0].equals("wand")){
                    //give player selection wand
                    ItemStack cubeWand = new ItemStack(Material.WOOD_AXE);
                    ItemMeta cubeMeta = cubeWand.getItemMeta();
                    cubeMeta.setDisplayName("§rWand");
                    List<String> cubeLore = new ArrayList<String>();
                    cubeLore.add("Select regions, WorldEdit style");
                    cubeMeta.setLore(cubeLore);
                    cubeWand.setItemMeta(cubeMeta);
                    player.getInventory().addItem(cubeWand);
                return true;

                } else if (args[0].equals("pos1")){
                    x1 = player.getLocation().getBlockX();
                    y1 = player.getLocation().getBlockY();
                    z1 = player.getLocation().getBlockZ();
                    player.sendMessage("Set position 1 to " + x1 + y1 + z1);
                    return true;
                } else if (args[0].equals("pos2")){
                    x2 = player.getLocation().getBlockX();
                    y2 = player.getLocation().getBlockY();
                    z2 = player.getLocation().getBlockZ();
                    player.sendMessage("Set position 2 to " + x2 + y2 + z2);
                    return true;
                } else if (args[0].equals("desel")){
                    x1 = -1;
                    y1 = -1;
                    z1 = -1;
                    x2 = -1;
                    y2 = -1;
                    z2 = -1;
                    player.sendMessage("Successfully deselected");
                    return true;
                } else if (args[0].equals("fill")){
                    
                    Location location = player.getLocation();
                    World world = location.getWorld();
                    int id = Integer.parseInt(args[2]);

                    if (Material.getMaterial(id) == null){
                         player.sendMessage("Error: Invalid material ID");
                         return true;
                    }  
                        
                    if (args[1].equals("cube") || args[1] == null){

                        //fill cube
                        for (int x3 = x1; x3 < x2; x3++){
                            for (int y3 = y1; y3 < y2; y3++){
                                for (int z3 = z1; z3 < z2; z3++){
                                    world.getBlockAt(x3, y3, z3).setType(Material.getMaterial(id));
                                }
                            }
                        }
                        
                        return true;
                        
                    } else if (args[1].equals("vcyl")){
                        player.sendMessage("JsKingBoo is lazy. Try again later");
                        return true;
                    } else if (args[1].equals("ellipse")){
                        player.sendMessage("JsKingBoo can't be bothered to try and code this in");
                        return true;
                    }
                }
                
            }
            
        } else {
            sender.sendMessage("Error: This command must be called in game");
        }
        
        return true;
    }

}
