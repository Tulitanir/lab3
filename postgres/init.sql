CREATE TABLE important_record (
    id bigserial NOT NULL,
    date timestamp NOT NULL
);

ALTER TABLE important_record ADD CONSTRAINT entity_pk PRIMARY KEY (id)