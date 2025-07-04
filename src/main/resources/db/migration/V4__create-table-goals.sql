CREATE TABLE goals (
    id BIGSERIAL PRIMARY KEY NOT NULL UNIQUE,
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    deadline DATE NOT NULL,
    done BOOLEAN DEFAULT FALSE,
    subject_id BIGINT NOT NULL,

    CONSTRAINT fk_subject
        FOREIGN KEY(subject_id)
        REFERENCES subjects(id)
        ON DELETE CASCADE
);