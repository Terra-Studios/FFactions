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
    // Here's my shabby attempt at trying to create a test table and read
    public static void createTestTable() {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Test (" +
                            "uuid UUID PRIMARY KEY," +
                            ");"
            );
            statement.executeUpdate(
                    "INSERT INTO Test (uuid) VALUES (" + UUID.randomUUID() + ")"
            );
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void testUpdate() {
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery("SELECT VALUES from test");
            System.out.println(set.getArray(0));
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
