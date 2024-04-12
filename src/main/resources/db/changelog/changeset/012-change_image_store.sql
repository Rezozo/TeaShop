ALTER TABLE image
DROP COLUMN image;

ALTER TABLE image
RENAME COLUMN file_name TO image_url;