package dev.sebastianb.ffactions.database;

import dev.sebastianb.ffactions.FFactions;
import net.minecraft.text.Text;

import java.sql.*;
import java.util.HashSet;

public class DatabaseInitializer {

    public static Connection connection;
    private static Statement statement;

    static {
        // register driver for use with connections
        try {
            Class<?> aClass = Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            FFactions.LOGGER.warning(Text.translatable("ffactions.logger.database.driverfailed").getString());
        }

        // setup database for connections
        try {
            connection = DriverManager.getConnection("jdbc:h2:./ffactions/ffactions");
            FFactions.LOGGER.info(Text.translatable("ffactions.logger.database.setupsuccess").getString());
        } catch (SQLException e) {
            e.printStackTrace();
            FFactions.LOGGER.warning(Text.translatable("ffactions.logger.database.setupfailed").getString());
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

    // gets another object in the row from a query to the wanted query
    public static <T> T getObject(String selector, String query, String wantedQuery, Object otherObject) {
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet set = statement.executeQuery(selector);
            int i = 1;
            HashSet<Integer> rowNumber = new HashSet<>();
            while (set.next()) {
                if (!set.getObject(query).equals(otherObject))
                    continue;
                rowNumber.add(set.getRow());
            }
            set.absolute(0); // move cursor to row 0 to run SQL again
            i = 1;
            while (set.next()) {
                if (rowNumber.contains(i)) {
                    return (T) set.getObject(wantedQuery);
                }
                i++;
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        FFactions.LOGGER.info("PLEASE REPORT THIS SHOULD NOT HAPPEN!"); // TODO: Replace with translatable
        return null;
    }

}
