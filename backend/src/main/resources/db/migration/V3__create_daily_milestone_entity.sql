CREATE TABLE IF NOT EXISTS daily_milestone_entity
(
    id            BINARY(16)     NOT NULL, -- Embedded primary key using UUID (stored as CHAR(36))
    user_email     VARCHAR(255) NOT NULL, -- User email
    label         VARCHAR(255) NOT NULL, -- Label of the milestone
    date          DATE         NOT NULL, -- Date of the milestone
    progress      INT          NOT NULL, -- Progress of the milestone
    completed     TINYINT(1)   NOT NULL, -- Completion status (BOOLEAN equivalent in MySQL)
    max_screen_time BIGINT       NOT NULL, -- Maximum screen time
    type          VARCHAR(50)  NOT NULL, -- Milestone type as a string
    PRIMARY KEY (id)                     -- Primary key definition
);
