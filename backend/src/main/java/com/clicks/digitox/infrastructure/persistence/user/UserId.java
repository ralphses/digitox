package com.clicks.digitox.infrastructure.persistence.user;

import org.springframework.util.Assert;

import java.util.UUID;

public record UserId(UUID id) {

    public UserId() {this(UUID.randomUUID());}
    public UserId {
        Assert.notNull(id, "id can't be null");
    }
}
