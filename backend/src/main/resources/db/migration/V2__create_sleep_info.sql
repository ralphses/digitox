CREATE TABLE IF NOT EXISTS sleep_info_entity (
    Id VARCHAR(100) PRIMARY KEY,
    user_email VARCHAR(150) NOT NULL,
    screen_time BIGINT DEFAULT 0,
    sleep_duration BIGINT DEFAULT 0,
    sleep_quality INT DEFAULT 0,
    created_at DATE NOT NULL
);