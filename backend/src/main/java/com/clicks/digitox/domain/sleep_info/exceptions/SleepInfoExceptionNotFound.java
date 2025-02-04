package com.clicks.digitox.domain.sleep_info.exceptions;

public class SleepInfoExceptionNotFound extends RuntimeException {
    public SleepInfoExceptionNotFound() {
        super("Sleep Information not found");
    }
}
