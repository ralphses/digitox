package com.clicks.digitox.domain.milestone.exceptions;

public class MilestoneNotFoundException extends RuntimeException{
    public MilestoneNotFoundException(String message) {
        super(message);
    }
}
