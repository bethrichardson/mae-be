CREATE TABLE IF NOT EXISTS journals (
    id uuid primary key,
    type text not null,
    alexa text not null,
    value text,
    date date not null
);

CREATE TABLE IF NOT EXISTS mood_ratings (
    id uuid primary key,
    big5_agreeableness double PRECISION not null,
    big5_conscientiousness double PRECISION not null,
    big5_extraversion double PRECISION not null,
    big5_openness double PRECISION not null,
    facet_anger double PRECISION not null,
    facet_anxiety double PRECISION not null,
    facet_depression double PRECISION not null,
    facet_immoderation double PRECISION not null,
    facet_self_consciousness double PRECISION not null,
    facet_vulnerability double PRECISION not null,
    alexa text,
    date date not null
);

CREATE TABLE IF NOT EXISTS needs (
    id uuid primary key,
    need_challenge double PRECISION not null,
    need_closeness double PRECISION not null,
    need_curiosity double PRECISION not null,
    need_excitement double PRECISION not null,
    need_harmony double PRECISION not null,
    need_ideal double PRECISION not null,
    need_liberty double PRECISION not null,
    need_love double PRECISION not null,
    need_practicality double PRECISION not null,
    need_self_expression double PRECISION not null,
    need_stability double PRECISION not null,
    need_structure double PRECISION not null,
    alexa text,
    date date not null
);