package de.realzone.panel.utils;/*
 
Class Created by: RealZone22
 
Package: eu.zonecraft.Main.utils.panel
Project: ZoneCraft-Main
 
Date: 16.11.2022
Time: 15:10
    
*/


import com.mattmalec.pterodactyl4j.client.entities.ClientServer;
import de.realzone.panel.Main;

public class ServerManager {

    public void startServer(String identifier) {
        Main.getInstance().getPteroClient().retrieveServerByIdentifier(identifier).flatMap(ClientServer::start).executeAsync();
    }

    public void stopServer(String identifier) {
        Main.getInstance().getPteroClient().retrieveServerByIdentifier(identifier).flatMap(ClientServer::stop).executeAsync();

    }

    public void restartServer(String identifier) {
        Main.getInstance().getPteroClient().retrieveServerByIdentifier(identifier).flatMap(ClientServer::restart).executeAsync();

    }

    public void sendCommand(String identifier, String command) {
        Main.getInstance().getPteroClient().retrieveServerByIdentifier(identifier).flatMap((ClientServer) -> ClientServer.sendCommand(command)).executeAsync();
    }

}
