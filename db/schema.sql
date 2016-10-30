CREATE TABLE diaperEntries (
    id uuid primary key,
    hasPee boolean not null,
    hasPoop boolean not null,
    date date not null
);

CREATE TABLE journals (
    id uuid primary key,
    type text not null,
    value text,
    date date not null
);

CREATE TABLE journals (
    id uuid primary key,
    agreeableness int not null,
    conscientiousness int not null,
    extraversion int not null,
    openness int not null,
    anger int not null,
    anxiety int not null,
    depression int not null,
    immoderation int not null,
    selfConciousness int not null,
    vulnerability int not null,
    challenge int not null,
    closeness int not null,
    curiosity int not null,
    excitement int not null,
    harmony int not null,
    ideal int not null,
    liberty int not null,
    love int not null,
    practicality int not null,
    selfExpression int not null,
    stability int not null,
    structure int not null,
    date date not null
);

CREATE TABLE statsEntries (
    id uuid primary key,
    heightInCm double not null,
    weightInKg double not null,
    date date not null
);

GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO maebe;