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

import games.negative.framework.database.annotation.Column;
import games.negative.framework.database.annotation.constructor.DatabaseConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * Profile object for MySQL
 * @author Seailz
 */
@Getter
public class Profile {

    private final UUID uuid;
    private final long firstJoin;
    private final long lastJoin;

    @DatabaseConstructor
    public Profile(@Column("uuid") String uuid, @Column("firstJoin") long firstJoin, @Column("lastJoin") long lastJoin) {
        this.uuid = UUID.fromString(uuid);
        this.firstJoin = firstJoin;
        this.lastJoin = lastJoin;
    }

}

