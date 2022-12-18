package de.realzone.panel.listeners;/*
 
Class Created by: RealZone22
 
Package: de.realzone.panel.gui.user
Project: Pterodactyl-Panel

Date: 18.12.2022
Time: 16:19
    
*/

import de.realzone.panel.Main;
import de.realzone.panel.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class ChatListener implements Listener {

    public static ArrayList<Player> typedMessage = new ArrayList<>();
    public static String identifier = "";

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();

        if(typedMessage.contains(player)) {
            event.setCancelled(true);

            if(message.equalsIgnoreCase("cancel") || message.equalsIgnoreCase("abort")) {
                player.sendMessage(MessageUtils.getProperties("chat.command.send.abort"));
                typedMessage.remove(player);
                return;
            }

            Main.getInstance().getServerManager().sendCommand(identifier, message);
            typedMessage.remove(player);
            player.sendTitle(MessageUtils.getProperties("chat.command.send.title"), "", 10, 100, 10);
        }


    }
}
