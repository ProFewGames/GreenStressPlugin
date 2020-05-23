package xyz.ufactions.queue;

import java.util.UUID;

/**
 * MegaBukkit class (c) Ricardo Barrera 2017-2020
 */
public class QueuedUser {

    private final UUID uuid;
    private int position;

    public QueuedUser(UUID uuid) {
        this.uuid = uuid;
    }

    public int decreaseAndGet() {
        return position -= 1;
    }

    public int getPosition() {
        return position;
    }

    public UUID getUUID() {
        return uuid;
    }
}