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
import java.util.Date;

/**
 * Join Date Command
 * @author Seailz
 */
@CommandInfo(
        name = "joindate",
        description = "Get the join date of a specific player, or yourself!"
)
public class CommandJoinDate extends Command {

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) return;
            Player p = (Player) sender;
            Profile profile;
            try {
                profile = StorageUtils.retrieveProfile(String.valueOf(p.getUniqueId()));
            } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                new Message("&cAn error occurred while retrieving your profile! Please ask an administrator to check the console.").send(p);
                throw new RuntimeException(e);
            }

            if (profile == null) {
                Locale.PROFILE_NOT_EXIST.send(p);
                return;
            }

            String formatted = TimeUtil.format(profile.getFirstJoin(), System.currentTimeMillis());
            Locale.JOIN_DATE.replace("%date%", formatted).send(p);
        } else if (args.length == 1) {
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
            Locale.JOIN_DATE_OTHER.replace("%date%", formatted).replace("%player%", op.getName()).send(sender);
        }
    }

}
