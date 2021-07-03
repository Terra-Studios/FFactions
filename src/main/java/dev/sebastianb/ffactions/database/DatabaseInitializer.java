package dev.sebastianb.ffactions.database;

import dev.sebastianb.ffactions.FFactions;
import net.minecraft.text.TranslatableText;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static Connection con;

    static {
        try {
            con = DriverManager.getConnection("jdbc:h2:./ffactions");
            FFactions.LOGGER.warning(new TranslatableText("ffactions.logger.database.setupsuccess").getKey());
        } catch (SQLException e) {
            e.printStackTrace();
            FFactions.LOGGER.warning(new TranslatableText("ffactions.logger.database.setupfailed").getKey());
        }
    }

    public static void createTable() {

    }

}
