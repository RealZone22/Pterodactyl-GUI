package de.realzone.panel.gui.user;/*
 
Class Created by: RealZone22
 
Package: de.realzone.panel.gui.user
Project: Pterodactyl-Panel

Date: 18.12.2022
Time: 16:18
    
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

public class UserListGUI {

    int serverSlot = 0;

    public void createInventory(Player p) {
        RyseInventory inventory = RyseInventory.builder()
                .manager(Main.getInstance().getInventoryManager())
                .title(MessageUtils.getProperties("user.list.title"))
                .rows(6)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {

                        if (serverSlot == 44) {
                            serverSlot = 0;
                        }


                        Main.getInstance().getPteroApi().retrieveUsers().forEach(users -> {

                            contents.set(serverSlot, IntelligentItem.of(new ItemBuilder(Material.PLAYER_HEAD)
                                    .setDisplayname(MessageUtils.getProperties("user.list.users").replaceAll("%user%", users.getUserName())).build(), event -> {

                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageUtils.getProperties("user.list.actionbar.loading")
                                        .replaceAll("%user%", users.getUserName())));
                                new AboutUserGUI().createInventory(p, users.getId());
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