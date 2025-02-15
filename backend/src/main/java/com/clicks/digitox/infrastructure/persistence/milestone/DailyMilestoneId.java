package com.clicks.digitox.infrastructure.persistence.milestone;

import org.springframework.util.Assert;

import java.util.UUID;

public record DailyMilestoneId(String id) {

    public DailyMilestoneId() {
        this(UUID.randomUUID().toString());
    }

    public DailyMilestoneId {
        Assert.notNull(id, "id can't be null");
    }
}
