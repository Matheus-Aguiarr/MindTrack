ALTER TABLE subjects
ADD COLUMN folder_id BIGINT;

ALTER TABLE subjects
ADD CONSTRAINT fk_folder
    FOREIGN KEY (folder_id)
    REFERENCES folders(id)
    ON DELETE SET NULL;