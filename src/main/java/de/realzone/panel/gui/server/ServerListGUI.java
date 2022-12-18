package de.realzone.panel.gui.server;/*
 
Class Created by: RealZone22
 
Package: de.realzone.panel.gui.server
Project: Pterodactyl-Panel

Date: 18.12.2022
Time: 16:08
    
*/

import de.realzone.panel.Main;
import de.realzone.panel.gui.MainGUI;
import de.realzone.panel.utils.ItemBuilder;
import de.realzone.panel.utils.MessageUtils;
import io.github.rysefoxx.content.IntelligentItem;
import io.github.rysefoxx.content.InventoryProvider;
import io.github.rysefoxx.pagination.InventoryContents;
import io.github.rysefoxx.pagination.RyseInventory;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ServerListGUI {

    int serverSlot = 0;

    public void createInventory(Player p) {
        RyseInventory inventory = RyseInventory.builder()
                .manager(Main.getInstance().getInventoryManager())
                .title(MessageUtils.getProperties("server.list.title"))
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {

                        if (serverSlot == 44) {
                            serverSlot = 0;
                        }


                        Main.getInstance().getPteroApi().retrieveServers().forEach(server -> {

                            contents.set(serverSlot, IntelligentItem.of(new ItemBuilder(Material.COMMAND_BLOCK)
                                    .setDisplayname(MessageUtils.getProperties("server.list.server_name").replaceAll("%server%", server.getName())).build(), event -> {

                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageUtils.getProperties("server.list.actionbar.loading").replaceAll("%server%", server.getName())));
                                new AboutServerGUI().createInventory(p, server.getId(), server.getIdentifier());
                            }));
                            serverSlot++;
                        });

                        contents.set(45, IntelligentItem.of(new ItemBuilder(Material.BARRIER) // Back
                                .setDisplayname(MessageUtils.getProperties("util.gui.back")).build(), event -> {
                            new MainGUI().createInventory(p);

                        }));

                    }
                })
                .build(Main.getInstance());
        inventory.open(p);
    }


}