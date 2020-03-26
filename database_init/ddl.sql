CREATE TABLE if not exists public."user"
(
    id serial,
    name character varying(150) NOT NULL,
    email character varying(150) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT email_uniq UNIQUE (email)
);
