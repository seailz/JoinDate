package com.seailz.joindate;

import com.seailz.joindate.core.Locale;
import com.seailz.joindate.core.Logger;
import com.seailz.joindate.core.storage.StorageType;
import games.negative.framework.BasePlugin;
import games.negative.framework.database.Database;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;

import java.io.File;

/**
 * The main class of the plugin.
 * @author Seailz
 */
public final class JoinDate extends BasePlugin {

    @Getter
    @Setter
    public static JoinDate instance;

    @Getter
    @Setter
    private Database database;

    @Getter
    @Setter
    private FileConfiguration dataConfig;

    @Getter
    @Setter
    private StorageType storageType;

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();
        long start = System.currentTimeMillis();

        setInstance(this);

        // Set details and register things
        register(RegisterType.COMMAND);
        register(RegisterType.LISTENER);

        Locale.init(this);
        saveDefaultConfig();

        if (getConfig().getString("storage-type").equals("mysql")) {
            setDatabase(new Database(
                            getConfig().getString("mysql.host"),
                            getConfig().getInt("mysql.port"),
                            getConfig().getString("mysql.database"),
                            getConfig().getString("mysql.username"),
                            getConfig().getString("mysql.password")
                    ));
            setStorageType(StorageType.MYSQL);
        } else if (getConfig().getString("storage-type").equals("local")) {
            loadFiles(this, "data.yml");
            setDataConfig(YamlConfiguration.loadConfiguration(new File(getDataFolder(), "data.yml")));
            setStorageType(StorageType.YAML);
        } else {
            Logger.log(Logger.LogLevel.ERROR, "Invalid storage type! Shutting down...");
            setEnabled(false);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


        long finish = System.currentTimeMillis() - start;
        Logger.log(Logger.LogLevel.SUCCESS, "Started in " + finish + "ms!");
    }

    public void register(RegisterType type) {
        switch (type) {
            case COMMAND:
                registerCommands(
                        // Insert commands
                );
                break;
            case LISTENER:
                registerListeners(
                        // Register Listeners
                );
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public enum RegisterType {COMMAND, LISTENER}
}
