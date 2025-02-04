package com.clicks.digitox.infrastructure.persistence.sleep_info;

import org.springframework.util.Assert;

import java.util.UUID;

public record SleepInfoEntityId(UUID id) {
    public SleepInfoEntityId {
        Assert.notNull(id, "id can't be null");
    }

    public SleepInfoEntityId() {this(UUID.randomUUID());}
}
