package com.seailz.joindate.commands;

import com.seailz.joindate.core.Locale;
import com.seailz.joindate.core.Profile;
import com.seailz.joindate.core.storage.StorageUtils;
import games.negative.framework.command.Command;
import games.negative.framework.command.annotation.CommandInfo;
import games.negative.framework.message.Message;
import games.negative.framework.util.TimeUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

/**
 * Last Join Command
 * @author Seailz
 */
@CommandInfo(
        name = "lastjoin",
        description = "Get the last join date of a specific player, or yourself!",
        args = {"player"}
)
public class CommandLastJoin extends Command {

    @Override
    public void onCommand(CommandSender sender, String[] args) {
            OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
            if (op == null) {
                Locale.PROFILE_NOT_EXIST.replace("%player%", args[0]).send(sender);
                return;
            }

            Profile profile;
            try {
                profile = StorageUtils.retrieveProfile(String.valueOf(op.getUniqueId()));
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                new Message("&cAn error occurred while retrieving the profile of " + op.getName() + "! Please ask an administrator to check the console.").send(sender);
                throw new RuntimeException(e);
            }

            if (profile == null) {
                Locale.PROFILE_NOT_EXIST.send(sender);
                return;
            }

            String formatted = TimeUtil.format(profile.getFirstJoin(), System.currentTimeMillis());
            Locale.LAST_JOIN.replace("%date%", formatted).replace("%player%", op.getName()).send(sender);
    }

}
