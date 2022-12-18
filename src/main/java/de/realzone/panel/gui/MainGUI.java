package de.realzone.panel.gui;/*
 
Class Created by: RealZone22
 
Package: de.realzone.panel.gui
Project: Pterodactyl-Panel

Date: 18.12.2022
Time: 15:00
    
*/

import de.realzone.panel.Main;
import de.realzone.panel.gui.server.ServerListGUI;
import de.realzone.panel.gui.user.UserListGUI;
import de.realzone.panel.utils.ItemBuilder;
import de.realzone.panel.utils.MessageUtils;
import io.github.rysefoxx.content.IntelligentItem;
import io.github.rysefoxx.content.InventoryProvider;
import io.github.rysefoxx.pagination.InventoryContents;
import io.github.rysefoxx.pagination.RyseInventory;
import io.github.rysefoxx.pattern.ContentPattern;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MainGUI {

    public void createInventory(Player p) {
        RyseInventory inventory = RyseInventory.builder()
                .manager(Main.getInstance().getInventoryManager())
                .title(MessageUtils.getProperties("main.title"))
                .rows(3)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {
                        ContentPattern pattern = contents.contentPattern();
                        pattern.define(
                                "XXXXXXXXX",
                                "XX1XXX2XX",
                                "XXXXXXXXX");

                        if(p.hasPermission("pterodactyl.panel.gui.server")) {
                            pattern.set('1', IntelligentItem.of(new ItemBuilder(Material.COMMAND_BLOCK)
                                    .setDisplayname(MessageUtils.getProperties("main.servers")).build(), event -> {
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageUtils.getProperties("main.servers.actionbar.loading")));
                                new ServerListGUI().createInventory((Player) event.getWhoClicked());
                            }));
                        }else{
                            pattern.set('1', IntelligentItem.of(new ItemBuilder(Material.BARRIER)
                                    .setDisplayname(MessageUtils.getProperties("util.gui.no_permission")).build(), event -> {
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageUtils.getProperties("main.servers.actionbar.no_permission")));
                                new ServerListGUI().createInventory((Player) event.getWhoClicked());
                            }));
                        }

                        if(p.hasPermission("pterodactyl.panel.gui.user")) {
                            pattern.set('2', IntelligentItem.of(new ItemBuilder(Material.PLAYER_HEAD)
                                    .setDisplayname(MessageUtils.getProperties("main.users")).build(), event -> {
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageUtils.getProperties("main.users.actionbar.loading")));
                                new UserListGUI().createInventory((Player) event.getWhoClicked());
                            }));
                        }else{
                            pattern.set('2', IntelligentItem.of(new ItemBuilder(Material.BARRIER)
                                    .setDisplayname(MessageUtils.getProperties("util.gui.no_permission")).build(), event -> {
                                p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(MessageUtils.getProperties("panel.actionbar.main.user.no_permission")));
                            }));
                        }
                        pattern.set('X', IntelligentItem.empty(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayname("§7").build()));
                    }
                })
                .build(Main.getInstance());
        inventory.open(p);
    }

}