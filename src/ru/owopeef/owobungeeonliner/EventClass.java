package ru.owopeef.owobungeeonliner;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SuppressWarnings("deprecation")
public class EventClass implements Listener
{
    String query;
    String name;
    String url;
    String user;
    String password;
    Connection con;
    Statement stmt;
    @EventHandler
    public void onConnect(PostLoginEvent event)
    {
        name = "server";
        url = "jdbc:mysql://localhost:3306/" + name + "?autoReconnect=true&useSSL=false";
        user = "root";
        try
        {
            con = DriverManager.getConnection(url, user, password);
            try
            {
                stmt = con.createStatement();
                query = "UPDATE `users` SET `online`='1' WHERE `uuid`='{uuid}' OR `nickname`='{nickname}'".replace("{uuid}", event.getPlayer().getUUID()).replace("{nickname}", event.getPlayer().getName().toLowerCase());
                stmt.executeUpdate(query);
            }
            catch (SQLException sqlEx)
            {
                System.out.println("UPDATE ERROR: " + sqlEx.getMessage());
            }
        }
        catch (SQLException sqlEx)
        {
            System.out.println("CONNECT ERROR: " + sqlEx.getMessage());
        }
    }
    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event)
    {
        name = "server";
        url = "jdbc:mysql://localhost:3306/" + name + "?autoReconnect=true&useSSL=false";
        user = "root";
        try
        {
            con = DriverManager.getConnection(url, user, password);
            try
            {
                stmt = con.createStatement();
                query = "UPDATE `users` SET `online`='0' WHERE `uuid`='{uuid}' OR `nickname`='{nickname}'".replace("{uuid}", event.getPlayer().getUUID()).replace("{nickname}", event.getPlayer().getName().toLowerCase());
                stmt.executeUpdate(query);
            }
            catch (SQLException sqlEx)
            {
                System.out.println("UPDATE ERROR: " + sqlEx.getMessage());
            }
        }
        catch (SQLException sqlEx)
        {
            System.out.println("CONNECT ERROR: " + sqlEx.getMessage());
        }
    }
}
