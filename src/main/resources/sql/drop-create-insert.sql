-- Drop table user_to_tournament
DROP TABLE IF EXISTS user_to_tournament;

-- Drop table comment
DROP TABLE IF EXISTS comment;

-- Drop table result
DROP TABLE IF EXISTS result;

-- Drop table game
DROP TABLE IF EXISTS game;

-- Drop table message
DROP TABLE IF EXISTS message;

-- Drop table tournament
DROP TABLE IF EXISTS tournament;

-- Drop table user_account
DROP TABLE IF EXISTS user_account;

-- Drop table country
DROP TABLE IF EXISTS country;

--------------------------------------------------

-- Create table country
create table country
(
    id    bigserial
        primary key,
    label varchar(255) not null
        constraint uk_nqlhjw63lbdup0favvgjsnlxb
            unique,
    tag   varchar(255) not null
        constraint uk_8q0y5ylo5eamqbhgqs5qajsm3
            unique
);
-- Create table user_account
create table user_account
(
    id                bigserial
        primary key,
    account_level     varchar(255) not null
        constraint user_account_account_level_check
            check ((account_level)::text = ANY
                   ((ARRAY ['ADMIN'::character varying, 'USER'::character varying])::text[])),
    birth_date        date         not null,
    elo               integer      not null,
    email             varchar(255) not null
        constraint uk_hl02wv5hym99ys465woijmfib
            unique,
    password          varchar(255) not null,
    pseudo            varchar(255) not null
        constraint uk_l3mjqdikxbc1m88nhv0siupnh
            unique,
    registration_date date         not null,
    country_id        bigint       not null
        constraint fkjw2udettleqn650i6baq1ip42
            references country
);
-- Create table tournament
create table tournament
(
    id         bigserial
        primary key,
    end_date   date         not null,
    format     varchar(255) not null
        constraint tournament_format_check
            check ((format)::text = ANY ((ARRAY ['SINGLE'::character varying, 'DOUBLE'::character varying])::text[])),
    label      varchar(255) not null,
    min_age    integer      not null,
    min_elo    integer      not null,
    start_date date         not null,
    status     varchar(255) not null
        constraint tournament_status_check
            check ((status)::text = ANY
                   ((ARRAY ['NOT_STARTED'::character varying, 'IN_PROGRESS'::character varying, 'FINISHED'::character varying])::text[])),
    user_id       bigint        not null
        constraint fkjlulh83p0xvnkj2hpi7eq3in1
            references user_account
);
-- Create table message
create table message
(
    id            bigserial
        primary key,
    content       varchar(2500) not null,
    label         varchar(255)  not null,
    tournament_id bigint        not null
        constraint fkks9jato39gr7eqx3l6a3lremn
            references tournament,
    user_id       bigint        not null
        constraint fkjlulh83p0xvnkj2hpi7eq3in1
            references user_account
);
-- Create table game
create table game
(
    id            bigserial
        primary key,
    match_date    date         not null,
    status        varchar(255) not null
        constraint game_status_check
            check ((status)::text = ANY
                   ((ARRAY ['NOT_STARTED'::character varying, 'IN_PROGRESS'::character varying, 'FINISHED'::character varying])::text[])),
    tournament_id bigint       not null
        constraint fk2xfdbv4n193efuyajlqh0vs6j
            references tournament
);
-- Create table result
create table result
(
    game_id bigint not null
        constraint fkekbtbib5q84jum7wrse4euumh
            references game,
    user_id bigint not null
        constraint fk9msc4h42pspkdvuv65f3ikqsh
            references user_account,
    result  integer,
    primary key (game_id, user_id)
);
-- Create table comment
create table comment
(
    id            bigserial
        primary key,
    content       varchar(2500) not null,
    label         varchar(255)  not null,
    tournament_id bigint        not null
        constraint fkfuu7b728vxdnkpar54r9xvai1
            references tournament,
    user_id       bigint        not null
        constraint fk3y3uou7na66pfn512byon549s
            references user_account,
    message_id    bigint        not null
        constraint fkatlrxw2dnvma9h401t2ql2ri8
            references message
);
-- Create table user_to_tournament
create table user_to_tournament
(
    user_id       bigint not null
        constraint fkiw96bstmodoh85272y8e9ns2v
            references user_account,
    tournament_id bigint not null
        constraint fk7mttikp6wgjumq9j53d7xq5n3
            references tournament
);

--------------------------------------------------

-- Insert into country
INSERT INTO country (label, tag)
VALUES ('USA', 'US'),
       ('Canada', 'CA'),
       ('Germany', 'DE'),
       ('France', 'FR'),
       ('Italy', 'IT'),
       ('Spain', 'ES'),
       ('Australia', 'AU'),
       ('Japan', 'JP'),
       ('China', 'CN'),
       ('Brazil', 'BR');

-- Insert into user_account
INSERT INTO user_account (account_level, birth_date, elo, email, password, pseudo, registration_date, country_id)
VALUES ('USER', '1990-01-01', 1500, 'john.doe@example.com', 'password123', 'johnny', '2024-01-01', 1),
       ('USER', '1985-05-15', 1600, 'jane.smith@example.com', 'password456', 'janey', '2024-02-01', 2),
       ('ADMIN', '1980-07-20', 1700, 'admin@example.com', 'admin', 'admin', '2024-03-01', 3),
       ('USER', '1992-03-10', 1450, 'alice.jones@example.com', 'alicepass', 'alice', '2024-04-01', 4),
       ('USER', '1988-11-23', 1550, 'bob.brown@example.com', 'bobpass', 'bobby', '2024-05-01', 5),
       ('USER', '1995-09-30', 1400, 'carol.white@example.com', 'carolpass', 'carol', '2024-06-01', 6),
       ('USER', '1993-07-19', 1650, 'david.green@example.com', 'davidpass', 'david', '2024-07-01', 7),
       ('USER', '1991-12-25', 1500, 'eve.black@example.com', 'evepass', 'eve', '2024-08-01', 8),
       ('USER', '1987-04-22', 1600, 'frank.gray@example.com', 'frankpass', 'frank', '2024-09-01', 9),
       ('ADMIN', '1982-02-14', 1750, 'george.adams@example.com', 'georgepass', 'george', '2024-10-01', 10);

-- Insert into tournament
INSERT INTO tournament (end_date, format, label, min_age, min_elo, start_date, status,user_id)
VALUES ('2024-06-30', 'SINGLE', 'Summer Tournament', 18, 1400, '2024-06-01', 'NOT_STARTED', 1),
       ('2024-12-31', 'DOUBLE', 'Winter Tournament', 21, 1500, '2024-12-01', 'IN_PROGRESS', 2),
       ('2024-03-15', 'SINGLE', 'Spring Tournament', 16, 1300, '2024-03-01', 'FINISHED', 3),
       ('2024-09-30', 'DOUBLE', 'Autumn Tournament', 19, 1450, '2024-09-01', 'NOT_STARTED', 4),
       ('2024-05-15', 'SINGLE', 'Early Summer Tournament', 17, 1350, '2024-05-01', 'IN_PROGRESS', 5),
       ('2024-11-15', 'DOUBLE', 'Late Autumn Tournament', 20, 1550, '2024-11-01', 'NOT_STARTED', 6),
       ('2024-02-28', 'SINGLE', 'Winter Classic', 18, 1400, '2024-02-01', 'FINISHED', 7),
       ('2024-07-15', 'DOUBLE', 'Mid Summer Tournament', 22, 1600, '2024-07-01', 'IN_PROGRESS', 8),
       ('2024-08-31', 'SINGLE', 'End of Summer Tournament', 19, 1450, '2024-08-01', 'NOT_STARTED',9),
       ('2024-10-31', 'DOUBLE', 'Fall Classic', 21, 1500, '2024-10-01', 'IN_PROGRESS',10);

-- Insert into message
INSERT INTO message (content, label, tournament_id, user_id)
VALUES ('Welcome to the Summer Tournament!', 'Announcement', 1, 1),
       ('Don''t forget to register for the Winter Tournament!', 'Reminder', 2, 2),
       ('The Spring Tournament was a great success!', 'Feedback', 3, 3),
       ('Autumn Tournament registration is open now!', 'Announcement', 4, 4),
       ('The Early Summer Tournament is approaching!', 'Reminder', 5, 5),
       ('Join us for the Late Autumn Tournament!', 'Announcement', 6, 6),
       ('Winter Classic was amazing, thanks for participating!', 'Feedback', 7, 7),
       ('Mid Summer Tournament is in full swing!', 'Update', 8, 8),
       ('End of Summer Tournament details have been updated!', 'Update', 9, 9),
       ('Don''t miss the Fall Classic!', 'Reminder', 10, 10);

-- Insert into game
INSERT INTO game (match_date, status, tournament_id)
VALUES ('2024-06-15', 'NOT_STARTED', 1),
       ('2024-06-16', 'IN_PROGRESS', 1),
       ('2024-12-15', 'IN_PROGRESS', 2),
       ('2024-12-16', 'FINISHED', 2),
       ('2024-03-15', 'FINISHED', 3),
       ('2024-03-16', 'NOT_STARTED', 3),
       ('2024-09-15', 'NOT_STARTED', 4),
       ('2024-09-16', 'IN_PROGRESS', 4),
       ('2024-05-15', 'IN_PROGRESS', 5),
       ('2024-05-16', 'FINISHED', 5),
       ('2024-11-15', 'NOT_STARTED', 6),
       ('2024-11-16', 'IN_PROGRESS', 6),
       ('2024-02-28', 'FINISHED', 7),
       ('2024-03-01', 'NOT_STARTED', 7),
       ('2024-07-15', 'IN_PROGRESS', 8),
       ('2024-07-16', 'FINISHED', 8),
       ('2024-08-31', 'NOT_STARTED', 9),
       ('2024-09-01', 'IN_PROGRESS', 9),
       ('2024-10-31', 'IN_PROGRESS', 10),
       ('2024-11-01', 'FINISHED', 10);

-- Insert into result
INSERT INTO result (game_id, user_id, result)
VALUES (1, 1, NULL),
       (2, 2, NULL),
       (3, 3, NULL),
       (4, 1, 4),
       (5, 2, 3),
       (6, 3, NULL),
       (7, 4, NULL),
       (8, 5, NULL),
       (9, 6, NULL),
       (10, 7, 1),
       (11, 8, NULL),
       (12, 9, NULL),
       (13, 10, 4),
       (14, 1, NULL),
       (15, 2, NULL),
       (16, 3, 4),
       (17, 4, NULL),
       (18, 5, NULL),
       (19, 6, NULL),
       (20, 7, 4);

-- Insert into comment
INSERT INTO comment (content, label, tournament_id, user_id, message_id)
VALUES ('Great match, very exciting!', 'Feedback', 1, 1, 1),
       ('Looking forward to the next tournament!', 'Suggestion', 2, 2, 2),
       ('Spring Tournament was well-organized!', 'Feedback', 3, 3, 3),
       ('Autumn Tournament had great participation!', 'Comment', 4, 4, 4),
       ('Early Summer Tournament exceeded expectations!', 'Feedback', 5, 5, 5),
       ('Late Autumn Tournament is going to be awesome!', 'Suggestion', 6, 6, 6),
       ('Winter Classic had some intense games!', 'Comment', 7, 7, 7),
       ('Mid Summer Tournament was fantastic!', 'Feedback', 8, 8, 8),
       ('End of Summer Tournament was well-run!', 'Comment', 9, 9, 9),
       ('Fall Classic was an incredible experience!', 'Feedback', 10, 10, 10);

-- Insert into user_to_tournament
INSERT INTO user_to_tournament (user_id, tournament_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 2),
       (5, 3),
       (6, 3),
       (7, 4),
       (8, 4),
       (9, 5),
       (10, 5),
       (1, 6),
       (2, 6),
       (3, 7),
       (4, 7),
       (5, 8),
       (6, 8),
       (7, 9),
       (8, 9),
       (9, 10),
       (10, 10);
