package com.jskingboo.boodev;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeSwitch implements CommandExecutor {
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("gm")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                String gamemodeName = "";
                if (player.getGameMode().getValue() == 0) { //is currently survival
                    player.setGameMode(GameMode.CREATIVE);
                    gamemodeName = "CREATIVE";
                } else {
                    player.setGameMode(GameMode.SURVIVAL);
                    gamemodeName = "SURVIVAL";
                }
                player.sendMessage("Gamemode updated to " + gamemodeName);             
            } else {
                sender.sendMessage("Error: This command must be called in game");
            }
        }
        return true;
    }
}