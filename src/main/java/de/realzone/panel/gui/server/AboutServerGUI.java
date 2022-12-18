package de.realzone.panel.gui.server;/*

Class Created by: RealZone22

Package: de.realzone.panel.gui.server
Project: Pterodactyl-Panel

Date: 18.12.2022
Time: 16:08

*/

import de.realzone.panel.Main;
import de.realzone.panel.listeners.ChatListener;
import de.realzone.panel.utils.ItemBuilder;
import de.realzone.panel.utils.MessageUtils;
import io.github.rysefoxx.content.IntelligentItem;
import io.github.rysefoxx.content.InventoryProvider;
import io.github.rysefoxx.pagination.InventoryContents;
import io.github.rysefoxx.pagination.RyseInventory;
import io.github.rysefoxx.pattern.ContentPattern;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AboutServerGUI {

    public void createInventory(Player p, String serverId, String identifier) {
        RyseInventory inventory = RyseInventory.builder()
                .manager(Main.getInstance().getInventoryManager())
                .title(MessageUtils.getProperties("about.server.title")
                        .replaceAll("%server%", Main.getInstance().getPteroApi().retrieveServerById(serverId).execute().getName()))
                .rows(5)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {
                        ContentPattern pattern = contents.contentPattern();
                        pattern.define(
                                "XXXXXXXXX",
                                "XXXXNXXXX",
                                "XXX1X2XXX",
                                "XXSXRXPXX",
                                "BXXXXXXXX");


                        pattern.set('N', IntelligentItem.empty(new ItemBuilder(Material.COMMAND_BLOCK) // Server Name
                                .setDisplayname(MessageUtils.getProperties("about.server.server_name")
                                        .replaceAll("%server%", Main.getInstance().getPteroApi().retrieveServerById(serverId).execute().getName()))

                                .setLore(MessageUtils.getProperties("about.server.status.lore").replaceAll("%status%",
                                        Main.getInstance().getPteroClient().retrieveServerByIdentifier(identifier)
                                        .execute().retrieveUtilization().execute().getState().toString()
                                        .replaceAll("RUNNING", MessageUtils.getProperties("util.server.online"))
                                        .replaceAll("OFFLINE", MessageUtils.getProperties("util.server.offline"))
                                        .replaceAll("STARTING", MessageUtils.getProperties("util.server.starting")))).build()));

                        pattern.set('1', IntelligentItem.of(new ItemBuilder(Material.WRITABLE_BOOK) // Console Write
                                .setDisplayname(MessageUtils.getProperties("about.server.send_command.title")).build(), event -> {

                            ChatListener.typedMessage.add(p);
                            p.closeInventory();
                            p.sendTitle(MessageUtils.getProperties("about.server.send_command.title"),
                                    MessageUtils.getProperties("about.server.send_command.subtitle"), 10, 100, 10);
                            ChatListener.identifier = identifier;

                        }));
                        pattern.set('2', IntelligentItem.of(new ItemBuilder(Material.BOOKSHELF) // Get Information
                                .setDisplayname(MessageUtils.getProperties("about.server.info.title")).build(), event -> {

                            p.closeInventory();
                            p.sendMessage(MessageUtils.getProperties("about.server.info.title").replaceAll("%server%",
                                    Main.getInstance().getPteroApi().retrieveServerById(serverId).execute().getName()));
                            p.sendMessage(MessageUtils.getProperties("about.server.info.id").replaceAll("%id%", serverId));
                            p.sendMessage(MessageUtils.getProperties("about.server.info.identifier").replaceAll("%identifier%", identifier));
                            p.sendMessage(MessageUtils.getProperties("about.server.info.status").replaceAll("%status%",
                                    Main.getInstance().getPteroClient().retrieveServerByIdentifier(identifier)
                                            .execute().retrieveUtilization().execute().getState().toString()
                                            .replaceAll("RUNNING", MessageUtils.getProperties("util.server.online"))
                                            .replaceAll("OFFLINE", MessageUtils.getProperties("util.server.offline"))
                                            .replaceAll("STARTING", MessageUtils.getProperties("util.server.starting"))));

                        }));
                        pattern.set('S', IntelligentItem.of(new ItemBuilder(Material.LIME_DYE) // Start
                                .setDisplayname(MessageUtils.getProperties("about.server.start.title")).build(), event -> {
                            Main.getInstance().getServerManager().startServer(identifier);
                            p.sendMessage(MessageUtils.getProperties("about.server.start.message").replaceAll("%server%",
                                    Main.getInstance().getPteroApi().retrieveServerById(serverId).execute().getName()));

                        }));
                        pattern.set('R', IntelligentItem.of(new ItemBuilder(Material.ORANGE_DYE) // Restart
                                .setDisplayname(MessageUtils.getProperties("about.server.restart.title")).build(), event -> {
                            Main.getInstance().getServerManager().restartServer(identifier);
                            p.sendMessage(MessageUtils.getProperties("about.server.restart.message").replaceAll("%server%",
                                    Main.getInstance().getPteroApi().retrieveServerById(serverId).execute().getName()));

                        }));
                        pattern.set('P', IntelligentItem.of(new ItemBuilder(Material.RED_DYE) // Stop
                                .setDisplayname(MessageUtils.getProperties("about.server.stop.title")).build(), event -> {
                            Main.getInstance().getServerManager().stopServer(identifier);
                            p.sendMessage(MessageUtils.getProperties("about.server.stop.message").replaceAll("%server%",
                                    Main.getInstance().getPteroApi().retrieveServerById(serverId).execute().getName()));

                        }));
                        pattern.set('B', IntelligentItem.of(new ItemBuilder(Material.BARRIER) // Back
                                .setDisplayname(MessageUtils.getProperties("util.gui.back")).build(), event -> {
                            new ServerListGUI().createInventory(p);

                        }));
                        pattern.set('X', IntelligentItem.empty(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayname("§7").build()));
                    }
                })
                .build(Main.getInstance());
        inventory.open(p);
    }
}