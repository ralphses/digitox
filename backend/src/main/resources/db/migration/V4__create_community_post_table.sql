CREATE TABLE community_post_entity
(
    id VARCHAR(100) PRIMARY KEY,
    content      TEXT         NOT NULL,
    no_of_likes  INT DEFAULT 0,
    created_at   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by   VARCHAR(255) NOT NULL,
    milestone_id VARCHAR(255) NOT NULL
);

CREATE TABLE community_post_entity_comments
(
    community_post_entity_id VARCHAR(100) NOT NULL,
    comments TEXT NOT NULL,
    FOREIGN KEY (community_post_entity_id) REFERENCES community_post_entity (id) ON DELETE CASCADE
);
