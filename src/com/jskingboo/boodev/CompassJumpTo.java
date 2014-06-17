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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class CompassJumpTo implements Listener, CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (sender instanceof Player) {
            if (label.equals("jumpto")){
                if (sender instanceof Player){
                    Player player = (Player) sender;
                    jumpTo(player);
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
        
        if (player.getItemInHand().getType() == Material.COMPASS){
            if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR){
                event.setCancelled(true);
            }
            jumpTo(player);
        }
        
    }
    
    public boolean jumpTo(Player player){
        Block block = player.getTargetBlock(null, 128);
        
        if (block.getType() == Material.AIR){
            player.sendMessage("Error: Not looking at any block");
            return true;
        }
        
        Location blockCoords = block.getLocation();
        
        float pitch = player.getLocation().getPitch();
        float yaw = player.getLocation().getYaw();
        
        blockCoords.setPitch(pitch);
        blockCoords.setYaw(yaw);
        blockCoords.setY(blockCoords.getY() + 1);
        
        player.teleport(blockCoords);
        player.sendMessage("Poof!");
        return true;
    }

}
