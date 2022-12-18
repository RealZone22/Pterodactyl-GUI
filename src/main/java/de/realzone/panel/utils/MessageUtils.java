package de.realzone.panel.utils;/*
 
Class Created by: RealZone22
 
Package: eu.zonecraft.Main.utils
Project: ZoneCraft-Main
 
Date: 16.11.2022
Time: 08:25
    
*/

import de.realzone.panel.Main;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Level;

public class MessageUtils {

    private static Properties getLangProperties() {
        Properties properties = new Properties();
        try {
            FileInputStream in = new FileInputStream(Main.getInstance().getDataFolder() + "/message.properties");
            properties.load(new InputStreamReader(in, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public static String getProperties(String key) {
        try {
            return getLangProperties().getProperty(key).replaceAll("%prefix%", getLangProperties().getProperty("prefix")).replaceAll("&", "§");
        } catch (NullPointerException e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Could not find key: " + key);
            return "§cCould not find key: §3" + key;
        }
    }

    public static String getNoPerms() {
        return getProperties("util.noPerms").replaceAll("%prefix%", getPrefix()).replaceAll("&", "§");
    }

    public static String getPrefix() {
        return getLangProperties().getProperty("prefix").replaceAll("&", "§");
    }

    public static String getOnlyPlayer() {
        return getProperties("util.only_player").replaceAll("&", "§");
    }

    public void setDefaults() {

        try {
            FileWriter writer = new FileWriter(Main.getInstance().getDataFolder() + "/message.properties");

            writer.write("""
                    prefix=&9Panel &8»
                    util.server.online=&aOnline
                    util.server.offline=&4Offline
                    util.server.starting=&6Starting
                    util.yes=&aYes
                    util.no=&cNo
                    util.gui.back=&cBack
                    util.gui.no_permission=&cYou can't use this!
                    util.only_player=&cOnly players can use this command!
                    util.noPerms=&cYou don't have the permission to do this!
                                      \s
                    about.server.title=&9Panel &8| &6About &7%server%
                    about.server.server_name=&7%server%
                    about.server.status.lore=&7Status: %status%
                    about.server.send_command.title=&7Send Command
                    about.server.send_command.subtitle=&cType cancel / abort to Abort
                                      \s
                    about.server.info.title=&7Information
                    about.server.info.name=&7Server Name: &e%server%
                    about.server.info.id=&7Server ID: &e%id%
                    about.server.info.identifier=&7Server Identifier: &e%identifier%
                    about.server.info.status=&7Server Status: &e%status%
                                        
                    about.server.start.title=&aStart
                    about.server.start.message=&7Server &e%server% &astarted
                                        
                    about.server.restart.title=&6Restart
                    about.server.restart.message=&7Server &e%server% &6restarted
                                     \s
                    about.server.stop.title=&cStop
                    about.server.stop.message=&7Server &e%server% &cstopped
                    \s
                                       
                    server.list.title=&9Panel &8| &6Server\s
                    server.list.server_name=&7%server%
                    server.list.actionbar.loading=&7Loading Server: &e%server%
                                        
                                        
                    user.about.title=&9Panel &8| &6About %user%
                    user.about.user_name=&7%user%
                    user.about.user_name.lore.first_name=&7First Name: &e%first_name%
                    user.about.user_name.lore.last_name=&7last Name: &e%last_name%
                    user.about.user_name.lore.email=&7Email: &e%email%
                                        
                    user.about.info.title=&7Information
                    user.about.info.user_name=&7User Name: &e%user%
                    user.about.info.first_name=&7First Name: &e%first_name%
                    user.about.info.last_name=&7Last Name: &e%last_name%
                    user.about.info.email=&7Email: &e%email%
                    user.about.info.admin=&7Admin: &e%admin%
                    user.about.info.servers=&7Server:
                    user.about.delete.title=&7Delete
                    user.about.delete.admin=&cYou can't delete an admin
                    user.about.delete.success=&7User &e%user% &cdeleted
                    user.about.admin.title=&7Admin Status
                    user.about.admin.lore=&7Admin: %admin%
                                        
                                        
                    user.list.title=&9Panel &8| &6Users
                    user.list.users=&7%user%
                    user.list.actionbar.loading=&7Loading User: &e%user%
                                        
                                        
                    main.title=&9Panel &8| &6Main
                    main.servers=&7Servers
                    main.users=&7Users
                    main.servers.actionbar.loading=&7Loading Servers
                    main.servers.actionbar.no_permission=&cYou can't use this!
                    main.users.actionbar.loading=&7Loading Users
                    main.users.actionbar.no_permission=&cYou can't use this!
                                        
                                        
                                        
                    chat.command.send.title=&7Command Send
                    chat.command.send.abort=&7You have cancelled the process
                            """);

            writer.close();
        } catch (IOException e) {
            Main.getInstance().getLogger().log(Level.SEVERE, "Error while writing into message.properties!");
            Main.getInstance().getLogger().log(Level.SEVERE, "Error: ");
            e.printStackTrace();
        }
    }
}
