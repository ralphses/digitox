package com.clicks.digitox.infrastructure.persistence.milestone;

import org.springframework.util.Assert;

import java.util.UUID;

public record DailyMilestoneId(UUID id) {

    public DailyMilestoneId() {
        this(UUID.randomUUID());
    }

    public DailyMilestoneId {
        Assert.notNull(id, "id can't be null");
    }
}
