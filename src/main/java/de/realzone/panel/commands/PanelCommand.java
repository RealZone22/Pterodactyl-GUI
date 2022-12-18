package de.realzone.panel.commands;/*
 
Class Created by: RealZone22
 
Package: de.realzone.panel.commands
Project: Pterodactyl-Panel
 
Date: 18.12.2022
Time: 15:08
    
*/

import de.realzone.panel.gui.MainGUI;
import de.realzone.panel.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PanelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@org.jetbrains.annotations.NotNull CommandSender sender, @org.jetbrains.annotations.NotNull Command command, @org.jetbrains.annotations.NotNull String label, @org.jetbrains.annotations.NotNull String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(player.hasPermission("pterodactyl.panel.open")) {
                new MainGUI().createInventory(player);
            } else {
                player.sendMessage(MessageUtils.getNoPerms());
            }
        } else {
            sender.sendMessage(MessageUtils.getOnlyPlayer());
        }

        return false;
    }
}