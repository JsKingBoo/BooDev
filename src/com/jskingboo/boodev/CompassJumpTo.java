package com.jskingboo.boodev;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class CompassJumpTo implements Listener, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        
        if (label.equals("jumpto")){
            if (sender instanceof Player){
                Player player = (Player) sender;
                jumpTo(player);
            }
        }
        
        return true;
    }
    
    @EventHandler(priority=EventPriority.HIGH)
    public void onPlayerUse(PlayerInteractEvent event){
        Player player = event.getPlayer();
        
        if (player.getItemInHand().getType() == Material.COMPASS){
            jumpTo(player);
        }
        
    }
    
    public void jumpTo(Player player){
        Block block = player.getTargetBlock(null, 128);
        Location blockCoords = block.getLocation();
        player.teleport(blockCoords);
    }

}
