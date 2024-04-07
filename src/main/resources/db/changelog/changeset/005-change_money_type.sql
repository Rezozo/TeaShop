ALTER TABLE package
ALTER COLUMN price TYPE DOUBLE PRECISION
USING (price::varchar::double precision);