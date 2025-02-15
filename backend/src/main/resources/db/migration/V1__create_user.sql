
-- Create the main UserEntity table
CREATE TABLE user_entity
(
    id                VARCHAR(100) PRIMARY KEY,
    name              VARCHAR(255)        NOT NULL,
    email             VARCHAR(255) UNIQUE NOT NULL,
    password          VARCHAR(255)        NOT NULL,
    profile_image     VARCHAR(255),
    banner_image      VARCHAR(255),
    active            BOOLEAN             NOT NULL,
    role              VARCHAR(20)         NOT NULL,
    total_screen_time BIGINT                       DEFAULT 0,
    points            BIGINT                       DEFAULT 0,
    level             VARCHAR(20)         NOT NULL DEFAULT 'BEGINNER'
);

-- Create a separate table for the milestones element collection
CREATE TABLE user_entity_milestones
(
    user_entity_id   VARCHAR(100),
    milestones VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_entity_id) REFERENCES user_entity (id)
);

