package com.jskingboo.boodev;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class playerInteraction implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        
        if (args.length != 1){
            sender.sendMessage("Error: Invalid argument length");
            return true;
        }
        
        Player player = Bukkit.getPlayerExact(args[0]);
        
        if (player == null){
            sender.sendMessage("Error: That player cannot be found");
            return true;
        }
        
        if (label.equals("slap")){
            sender.sendMessage("You slapped " + player.getName());
            player.sendMessage("You were slapped by " + sender.getName());
            return true;
        } else if (label.equals("rocket")){
            sender.sendMessage("You rocketed " + player.getName());
            player.sendMessage("You were rocketed by " + sender.getName());       
            player.setVelocity(new Vector(0, 10, 0));
            return true;
        } else if (label.equals("halt")){
            sender.sendMessage("You halted " + player.getName());
            player.sendMessage("You were halted by " + sender.getName());
            player.setVelocity(new Vector(0, 0, 0));
            return true;
        } else if (label.equals("hook")){
            if (sender instanceof Player){
                Player p = (Player) sender;
                p.sendMessage("You hooked " + player.getName());
                player.sendMessage("You were hooked by " + p.getName());
                
                double distX = player.getLocation().getX() - p.getLocation().getX();
                double distY = player.getLocation().getY() - p.getLocation().getY();
                double distZ = player.getLocation().getZ() - p.getLocation().getZ();
                
                double scale = Math.max(distX, Math.max(distY, distZ));
                
                player.setVelocity(new Vector(-distX / scale, -distY / scale, -distZ / scale));
                
                return true;
                
            } else {
                sender.sendMessage("Error: This command must be called in game");
            }

        }
        
        
        return true;
    }
    

    
}
