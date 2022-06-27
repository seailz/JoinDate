/**
 * Custom JoinDate plugin
 * Copyright (C) 2022 Seailz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.seailz.joindate.core.storage;

import com.seailz.joindate.JoinDate;
import com.seailz.joindate.core.Profile;
import games.negative.framework.database.Database;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    /**
     * Loads a profile
     * @param uuid The UUID of the profile to load
     * @return The profile if it exists, null if not
     * @throws SQLException If there is an error with the database
     * @throws InvocationTargetException If there is an error with the class
     * @throws InstantiationException If there is an error with the class
     * @throws IllegalAccessException If there is an error with the class
     */
    public static Profile retrieveProfile(String uuid) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException {
        switch (JoinDate.getInstance().getStorageType()) {
            case YAML:
                FileConfiguration config = JoinDate.getInstance().getDataConfig();
                return new Profile(uuid, config.getLong("profiles." + uuid + ".firstJoin"), config.getLong("profiles." + uuid + ".lastJoin"));
            case MYSQL:
                Database db = JoinDate.getInstance().getDatabase();
                db.connect();

                return (Profile) db.get("joindate-profiles", "uuid", uuid, Profile.class);
        }
        return null;
    }

}
