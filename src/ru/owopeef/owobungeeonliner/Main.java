package ru.owopeef.owobungeeonliner;

import net.md_5.bungee.api.plugin.Plugin;

import java.sql.*;

public class Main extends Plugin
{
    static String query;
    static String name;
    static String url;
    static String user;
    static String password;
    static Connection con;
    static Statement stmt;
    @Override
    public void onEnable()
    {
        name = "server";
        url = "jdbc:mysql://localhost:3306/" + name + "?autoReconnect=true&useSSL=false";
        user = "root";
        try
        {
            con = DriverManager.getConnection(url, user, password);
            this.getLogger().info("[MySQL] Connected to MySQL.");
            getProxy().getPluginManager().registerListener(this, new EventClass());
            try
            {
                stmt = con.createStatement();
                query = "UPDATE `users` SET `online`='0'";
                stmt.executeUpdate(query);
            }
            catch (SQLException sqlEx)
            {
                this.getLogger().warning("[MySQL] Select Error: " + sqlEx.getMessage());
            }
        }
        catch (SQLException sqlEx)
        {
            this.getLogger().warning("[MySQL] Connection Error: " + sqlEx.getMessage());
        }
    }
}
