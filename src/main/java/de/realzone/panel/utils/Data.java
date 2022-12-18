package de.realzone.panel.utils;/*
 
Class Created by: RealZone22
 
Package: de.realzone.panel.utils
Project: Pterodactyl-Panel
 
Date: 18.12.2022
Time: 15:05
    
*/

import de.realzone.panel.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Data {

    public static File config = new File(Main.getInstance().getDataFolder(), "config.yml");
    public static File message = new File(Main.getInstance().getDataFolder(), "message.properties");

    public void createFile(){

        if (!config.exists()) {
            try {
                config.getParentFile().mkdirs();
                config.createNewFile();

                YamlConfiguration configCfg = YamlConfiguration.loadConfiguration(config);
                configCfg.set("pterodactyl.host", "https://panel.example.com");
                configCfg.set("pterodactyl.apiKey", "API_KEY");
                configCfg.save(config);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (!message.exists()) {
            try {
                message.getParentFile().mkdirs();
                message.createNewFile();
                Main.getInstance().getMessageUtils().setDefaults();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
