package de.realzone.panel;/*
 
Class Created by: RealZone22
 
Package: org.example
Project: ${PROJECT_NAME}
 
Date: ${DATE}
Time: ${TIME}
    
*/

import com.mattmalec.pterodactyl4j.PteroBuilder;
import com.mattmalec.pterodactyl4j.application.entities.PteroApplication;
import com.mattmalec.pterodactyl4j.client.entities.PteroClient;
import de.realzone.panel.commands.PanelCommand;
import de.realzone.panel.listeners.ChatListener;
import de.realzone.panel.utils.Data;
import de.realzone.panel.utils.MessageUtils;
import de.realzone.panel.utils.ServerManager;
import io.github.rysefoxx.pagination.InventoryManager;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

    private PteroApplication api;
    private PteroClient pteroClient;
    private MessageUtils messageUtils;
    private InventoryManager inventoryManager;
    private ServerManager serverManager;
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        messageUtils = new MessageUtils();
        new Data().createFile();

        serverManager = new ServerManager();

        inventoryManager = new InventoryManager(this);
        inventoryManager.invoke();

        getPteroApi();
        getPteroClient();

        getCommand("panel").setExecutor(new PanelCommand());
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public MessageUtils getMessageUtils() {
        return messageUtils;
    }

    public static Main getInstance() {
        return instance;
    }

    public ServerManager getServerManager() {
        return serverManager;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public PteroClient getPteroClient() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
        pteroClient = PteroBuilder.createClient(config.getString("pterodactyl.host"), config.getString("pterodactyl.apiKey"));
        return pteroClient;
    }

    public PteroApplication getPteroApi() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
        api = PteroBuilder.createApplication(config.getString("pterodactyl.host"), config.getString("pterodactyl.apiKey"));
        return api;
    }
}