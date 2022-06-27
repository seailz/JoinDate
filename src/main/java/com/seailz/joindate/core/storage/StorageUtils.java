package com.seailz.joindate.core.storage;

import com.seailz.joindate.JoinDate;
import com.seailz.joindate.core.Profile;
import games.negative.framework.database.Database;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * The core of the plugin's backend storage.
 * @author Seailz
 */
public class StorageUtils {

    /**
     * Stores a profile
     * @param profile The profile to store
     * @throws SQLException If the profile could not be stored
     * @throws IOException If the profile could not be stored
     */
    public static void storeProfile(Profile profile) throws SQLException, IOException {
        switch (JoinDate.getInstance().getStorageType()) {
            case YAML:
                FileConfiguration config = JoinDate.getInstance().getDataConfig();
                config.set("profiles." + profile.getUuid().toString() + ".firstJoin", profile.getFirstJoin());
                config.set("profiles." + profile.getUuid().toString() + ".lastJoin", profile.getLastJoin());
                config.save(new File(JoinDate.getInstance().getDataFolder(), "data.yml"));
                break;
            case MYSQL:
                Database db = JoinDate.getInstance().getDatabase();
                db.connect();

                db.insert("joindate-profiles", profile);
                db.disconnect();
                break;
        }
    }

    /**
     * Deletes a profile
     * @param profile The profile to delete
     * @throws SQLException If there is an error with the database
     * @throws IOException If there is an error with the file
     */
    public static void deleteProfile(Profile profile) throws SQLException, IOException {
        switch (JoinDate.getInstance().getStorageType()) {
            case YAML:
                FileConfiguration config = JoinDate.getInstance().getDataConfig();
                config.set("profiles." + profile.getUuid().toString() + ".firstJoin", null);
                config.set("profiles." + profile.getUuid().toString() + ".lastJoin", null);
                config.save(new File(JoinDate.getInstance().getDataFolder(), "data.yml"));
                break;
            case MYSQL:
                Database db = JoinDate.getInstance().getDatabase();
                db.connect();

                db.delete("joindate-profiles", "uuid", profile.getUuid().toString());
                db.disconnect();
                break;
        }
    }

    /**
     * Checks if a profile exists
     * @param profile The profile to check
     * @return True if the profile exists, false if not
     * @throws SQLException If there is an error with the database
     */
    public static boolean profileExists(Profile profile) throws SQLException {
        switch (JoinDate.getInstance().getStorageType()) {
            case YAML:
                FileConfiguration config = JoinDate.getInstance().getDataConfig();
                return config.get("profiles." + profile.getUuid().toString() + ".firstJoin") != null;
            case MYSQL:
                Database db = JoinDate.getInstance().getDatabase();
                db.connect();

                return db.rowExists("joindate-profiles", "uuid", profile.getUuid().toString());
        }
        return false;
    }

}
