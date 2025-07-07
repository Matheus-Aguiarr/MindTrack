CREATE TABLE notes (
    id BIGSERIAL PRIMARY KEY NOT NULL UNIQUE,
    title TEXT NOT NULL,
    content TEXT NOT NULL,
    created_at DATE NOT NULL,
    subject_id BIGINT NOT NULL,

    CONSTRAINT fk_subject
        FOREIGN KEY(subject_id)
        REFERENCES subjects(id)
        ON DELETE RESTRICT
);