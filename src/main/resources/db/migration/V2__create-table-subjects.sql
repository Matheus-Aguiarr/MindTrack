CREATE TABLE subjects (
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    user_id TEXT NOT NULL,

    CONSTRAINT fk_user
        FOREIGN KEY(user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);