package com.jskingboo.boodev;

import com.gmail.fedmanddev.VillagerTrade;
import com.gmail.fedmanddev.VillagerTradeApi;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.inventory.ItemStack;

//3rd party import from http://www.curse.com/bukkit-plugins/minecraft/villager-trade-api#c1

public class VillagerShop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            
            Player player = (Player) sender;
            Location pLoc = player.getLocation();
            World world = pLoc.getWorld();
            
            if (label.equals("villager")){
                if (args.length == 0){
                    player.sendMessage("Error: Too few arguments");
                    return true;
                }
                
                Villager villager = null;

                //the following code has been stolen from Bloxcraft
                int range = 10; // i'm indecisive as well
                Block b = player.getTargetBlock(null, range);
                List<Entity> near = player.getNearbyEntities(range, range, range);
                for (Entity e : near) {
                    if (e.getLocation().distance(b.getLocation()) < 2 && e instanceof Villager) {
                        villager = (Villager) e;
                    }
                }

                if (villager == null){
                    villager = (Villager)world.spawn(pLoc, Villager.class);
                    player.sendMessage("Created new villager since no other was in sight");
                }
                
                if (args[0].equals("set") || args[0].equals("add")){     
                    
                    if (args[0].equals("set")){
                        VillagerTradeApi.clearTrades(villager);                        
                    }
                    
                    ItemStack item1 = player.getInventory().getItem(0);
                    ItemStack item2 = player.getInventory().getItem(1);
                    ItemStack sell = player.getInventory().getItem(2);
                    VillagerTrade trade = new VillagerTrade(item1, item2, sell);
                    VillagerTradeApi.addTrade(villager, trade);
                    
                    player.sendMessage("Added trade");
                    return true;
                } else if (args[0].equals("prof") && args.length >= 1){
                    villager.setProfession(Profession.getProfession(Integer.parseInt(args[1])));   
                    player.sendMessage("Changed profession");
                    return true;
                }
            }  
        } else {
            sender.sendMessage("Error: This command must be run in game");
        }
        
        
        return true;
    }

}
