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

package com.seailz.joindate.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Logger {

    /**
     * Log something to the console
     *
     * @param level   The level of the logged message
     * @param message What messages to log
     */
    public static void log(LogLevel level, String message) {
        if (message == null) return;

        switch (level) {
            case ERROR:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[JOINDATE] &8[&c&lERROR&r&8] &f" + message));
                break;
            case WARNING:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[JOINDATE] &8[&6&lWARNING&r&8] &f" + message));
                break;
            case INFO:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[JOINDATE] &8[&e&lINFO&r&8] &f" + message));
                break;
            case SUCCESS:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[JOINDATE] &8[&a&lSUCCESS&r&8] &f" + message));
                break;
            case DEBUG:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[JOINDATE] &8[&c&lDEBUG&r&8] &c" + message));
                break;
            case OUTLINE:
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[JOINDATE] &7" + message));
                break;
        }
    }

    public enum LogLevel {ERROR, WARNING, INFO, SUCCESS, OUTLINE, DEBUG}
}
