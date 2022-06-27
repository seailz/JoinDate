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

