CREATE TABLE IF NOT EXISTS journals (
    id uuid primary key,
    type text not null,
    value text,
    date date not null
);

CREATE TABLE IF NOT EXISTS mood_ratings (
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
    date date not null
);

CREATE TABLE IF NOT EXISTS needs (
    id uuid primary key,
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