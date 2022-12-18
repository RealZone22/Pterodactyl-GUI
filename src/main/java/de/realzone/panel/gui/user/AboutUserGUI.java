package de.realzone.panel.gui.user;/*
 
Class Created by: RealZone22
 
Package: de.realzone.panel.gui.user
Project: Pterodactyl-Panel

Date: 18.12.2022
Time: 16:18
    
*/

import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import de.realzone.panel.Main;
import de.realzone.panel.utils.ItemBuilder;
import de.realzone.panel.utils.MessageUtils;
import io.github.rysefoxx.content.IntelligentItem;
import io.github.rysefoxx.content.InventoryProvider;
import io.github.rysefoxx.pagination.InventoryContents;
import io.github.rysefoxx.pagination.RyseInventory;
import io.github.rysefoxx.pattern.ContentPattern;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class AboutUserGUI {

    public void createInventory(Player p, String userId) {
        RyseInventory inventory = RyseInventory.builder()
                .manager(Main.getInstance().getInventoryManager())
                .title(MessageUtils.getProperties("user.about.title").replaceAll("%user%", Main.getInstance().getPteroApi().retrieveUserById(userId).execute().getUserName()))
                .rows(5)
                .provider(new InventoryProvider() {
                    @Override
                    public void init(Player player, InventoryContents contents) {
                        ContentPattern pattern = contents.contentPattern();
                        pattern.define(
                                "XXXXXXXXX",
                                "XXXXNXXXX",
                                "XXXXXXXXX",
                                "XX1X2X3XX",
                                "BXXXXXXXX");


                        pattern.set('N', IntelligentItem.empty(new ItemBuilder(Material.COMMAND_BLOCK) // User Name
                                .setDisplayname(MessageUtils.getProperties("user.about.user_name")
                                        .replaceAll("%user%", Main.getInstance().getPteroApi().retrieveUserById(userId).execute().getUserName()))

                                .setLore(MessageUtils.getProperties("user.about.user_name.lore.first_name").replaceAll("%first_name%", Main.getInstance().getPteroApi().retrieveUserById(userId).execute()
                                                .getFirstName()),
                                        MessageUtils.getProperties("user.about.user_name.lore.last_name").replaceAll("%last_name%", Main.getInstance().getPteroApi().retrieveUserById(userId).execute()
                                                .getLastName()),
                                        MessageUtils.getProperties("user.about.user_name.lore.email").replaceAll("%email%", Main.getInstance().getPteroApi().retrieveUserById(userId).execute()
                                                .getEmail())).build()));

                        pattern.set('1', IntelligentItem.empty(new ItemBuilder(Material.WRITABLE_BOOK) // Admin Status
                                .setDisplayname("§cAdmin Status")
                                .setLore(MessageUtils.getProperties("user.about.admin.lore").replaceAll("%admin%", (Main.getInstance().getPteroApi().retrieveUserById(userId).execute().isRootAdmin() ?
                                        MessageUtils.getProperties("util.yes") : MessageUtils.getProperties("util.no")))).build()));

                        pattern.set('2', IntelligentItem.of(new ItemBuilder(Material.BOOKSHELF) // Get Information
                                .setDisplayname(MessageUtils.getProperties("user.about.info.title")).build(), event -> {
                            p.closeInventory();
                            p.sendMessage(MessageUtils.getProperties("user.about.info.user_name").replaceAll("%user%", Main.getInstance().getPteroApi().retrieveUserById(userId).execute().getUserName()));
                            p.sendMessage(MessageUtils.getProperties("user.about.info.first_name").replaceAll("%first_name%", Main.getInstance().getPteroApi().retrieveUserById(userId).execute().getFirstName()));
                            p.sendMessage(MessageUtils.getProperties("user.about.info.last_name").replaceAll("%last_name%", Main.getInstance().getPteroApi().retrieveUserById(userId).execute().getLastName()));
                            p.sendMessage(MessageUtils.getProperties("user.about.info.email").replaceAll("%email%", Main.getInstance().getPteroApi().retrieveUserById(userId).execute().getEmail()));
                            p.sendMessage(MessageUtils.getProperties("user.about.info.admin").replaceAll("%admin%", Main.getInstance().getPteroApi().retrieveUserById(userId).execute().isRootAdmin() ?
                                    MessageUtils.getProperties("util.yes") : MessageUtils.getProperties("util.no")));

                            p.sendMessage(MessageUtils.getProperties("user.about.info.servers"));
                            List<ApplicationServer> servers = Main.getInstance().getPteroApi().retrieveUserById(userId).execute().retrieveServers().execute();
                            for (ApplicationServer server : servers) {
                                p.sendMessage("§7- " + server.getName());
                            }

                        }));
                        pattern.set('3', IntelligentItem.of(new ItemBuilder(Material.RED_DYE) // Delete
                                .setDisplayname(MessageUtils.getProperties("user.about.delete.title")).build(), event -> {

                            if (Main.getInstance().getPteroApi().retrieveUserById(userId).execute().isRootAdmin()) {
                                p.sendMessage(MessageUtils.getProperties("user.about.delete.admin"));
                                p.closeInventory();
                                return;
                            }
                            Main.getInstance().getPteroApi().retrieveUserById(userId).execute().delete().execute();
                            p.sendMessage(MessageUtils.getProperties("user.about.delete.success").replaceAll("%user%",
                                    Main.getInstance().getPteroApi().retrieveUserById(userId).execute().getUserName()));
                            p.closeInventory();

                        }));
                        pattern.set('B', IntelligentItem.of(new ItemBuilder(Material.BARRIER) // Back
                                .setDisplayname(MessageUtils.getProperties("util.gui.back")).build(), event -> {
                            new UserListGUI().createInventory(p);

                        }));
                        pattern.set('X', IntelligentItem.empty(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayname("§7").build()));
                    }
                })
                .build(Main.getInstance());
        inventory.open(p);
    }
}