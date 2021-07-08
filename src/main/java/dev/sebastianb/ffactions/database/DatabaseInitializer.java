package dev.sebastianb.ffactions.database;

import dev.sebastianb.ffactions.FFactions;
import net.minecraft.text.TranslatableText;

import java.sql.*;
import java.util.UUID;

public class DatabaseInitializer {

    public static Connection connection;
    private static Statement statement;

    static {
        // register driver for use with connections
        try {
            Class<?> aClass = Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            FFactions.LOGGER.warning(new TranslatableText("ffactions.logger.database.driverfailed").getString());
        }

        // setup database for connections
        try {
            connection = DriverManager.getConnection("jdbc:h2:./ffactions/ffactions");
            FFactions.LOGGER.info(new TranslatableText("ffactions.logger.database.setupsuccess").getString());
        } catch (SQLException e) {
            e.printStackTrace();
            FFactions.LOGGER.warning(new TranslatableText("ffactions.logger.database.setupfailed").getString());
        }

    }

    // I don't know shit about MySQL so I need to learn.
    // Here's my shabby attempt at trying to SQL
    public static int executeSQL(String sql) {
        int i = 0;
        try {
            statement = connection.createStatement();
            i = statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * if the specified table has a object that matches a specific query
     *
     * @param selector which table to select
     * @param query call a entry in the database
     * @param otherObject takes in any object
     * @return if the otherObject variable matches the specified query
     */
    public static <T> boolean hasMatching(String selector, String query, T otherObject) {
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(selector);
            while (set.next()) {
                if (set.getObject(query).equals(otherObject)) {
                    return true;
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
