CREATE TABLE journals (
    id uuid primary key,
    type text not null,
    value text,
    date date
);

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO maebe;