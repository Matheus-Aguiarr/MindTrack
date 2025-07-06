ALTER TABLE subjects DROP CONSTRAINT fk_user;

ALTER TABLE subjects
    ADD CONSTRAINT fk_user
    FOREIGN KEY(user_id)
    REFERENCES users(id)
    ON DELETE RESTRICT;