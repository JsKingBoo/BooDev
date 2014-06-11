package com.jskingboo.boodev;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;


public class BetterEnchant implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("enchant")) {
//            if (sender instanceof Player) {
            
            List<String> enchNames = new ArrayList<String>();
            for (Enchantment enchant : Enchantment.values()) {
                enchNames.add(enchant.getName().toUpperCase());
            }
            
            if (sender instanceof Player){  
                Player player = (Player) sender;   
                
                if (args.length == 2){
                    
                    String enchID = args[0];
                    int enchLevel = (int) Integer.parseInt(args[1]);
                    Enchantment enchType = null;
                    
                    if (enchLevel < 1 || enchLevel > 127){
                        player.sendMessage("Error: Enchantment must be between 1 and 127 inclusive");
                        return true;
                    }
                    
                    if (!enchNames.contains((enchID.toUpperCase()))) {
                        player.sendMessage("Error: Invalid enchantment ID");
                        player.sendMessage("For a list of valid enchantment IDs please preform /enchant list");
                        return true;
                    } else {
                        enchType = Enchantment.getByName(enchID.toUpperCase());
                    }
                    
                    if (player.getItemInHand() == null && player.getItemInHand().getType() == Material.AIR){
                        player.sendMessage("Error: No item held");
                        return true;
                    } else if (player.getItemInHand().getType() == Material.ENCHANTED_BOOK || player.getItemInHand().getType() == Material.BOOK){
                        player.sendMessage("Error: Unsure if enchantment properties or storage of enchantment is wanted");
                        return true;
                    } else {
                       player.getItemInHand().removeEnchantment(enchType);
                       player.getItemInHand().addUnsafeEnchantment(enchType, enchLevel);
                       player.sendMessage("Enchanting successful");
                       return true;
                    }
                    
                } else {
                    if (args.length == 1 && args[0].equalsIgnoreCase("list")){
                        for (int counter = enchNames.size() - 1; counter >= 0; counter = counter - 1 ){
                            player.sendMessage(enchNames.get(counter));
                        }
                        return true;
                    } else {
                        player.sendMessage("/enchant <enchant name> <enchant level>");
                        player.sendMessage("For a list of valid enchantment IDs please preform /enchant list");                        
                    }
                }
            } else {
                sender.sendMessage("Error: This command must be called in game");
            }
        }
        return true;
    }
    
}
