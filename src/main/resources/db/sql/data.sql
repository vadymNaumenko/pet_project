INSERT INTO games (state,min_age,number_of_players,price,image, title, description, genre,release_date)
values ('PENDING','EIGHT_PLUS','ONE_TWO',23,'https://portal-vr.ru/wp-content/uploads/2023/02/skgev4xstvwl9qdwux96p3-768x432.jpg'
       ,'Resident Evil 4 VR','Исследуйте мир Resident Evil 4, полностью переработанный для виртуальной реальности. Попробуйте себя в роли специального агента Леона С. Кеннеди в его миссии по спасению дочери президента США, похищенной таинственным культом. Столкнитесь лицом к лицу с врагами и раскройте секреты игрового процесса, который произвел революцию в жанре ужасов на выживание. Сразитесь с ужасными существами и сразитесь с жителями деревни, контролируемыми разумом, и выясните их связь с культом, стоящим за похищением.'
       ,'SHOOTER','2023-02-15'),
       ('ACTIVE','SIXTEEN_PLUS','ONE_FOUR',21,'https://portal-vr.ru/wp-content/uploads/2023/07/portal-strike-kids_2.1-768x432.jpg','Portal Strike: Kids',
        'Portal Strike: Kids проходит в зале VR Arena – большом открытом пространстве, где игроки передвигаются своими ногами. В Portal Strike: Kids представлены 5 проработанных карт; юные игроки побывают в Египте, Средневековье, на пиратском корабле и в киберпанке'
           ,'SPORTS','2022-01-10'),
       ('COMPLETED','EIGHTEEN_PLUS','ONE_TWO',13,'https://portal-vr.ru/wp-content/uploads/2023/10/arena-heroes-v2-9h16-768x432.jpg'
       ,'Portal Arena Heroes','Portal Arena Heroes — это уникальная VR игра в стиле фентези на арене со свободным передвижением. Выберите 1 из 3-х классов и сражайтесь плечом к плечу на различных аренах команда против команды или совместно против армии монстров. Каждый класс имеет свои уникальные атаки и ультимативные способности. Окунитесь в настоящий мир фентези вместе с друзьями в клубах Portal VR!'
       ,'ACTION','2023-02-15');

INSERT INTO users (state, avatar, created_at, birth_date, nickname, firstname, lastname, phone, role, email, password)
VALUES
    ('CONFIRMED', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAqAZG7zLGhZIxSUV6EVLfQX3WEUawmvM-eA&usqp=CAU', '2004-10-19 10:23:54',DATE '1990-01-10', 'Fishka' ,'Ivan', 'Ivanov', '+38096584402', 'ADMIN', 'ivan@gmail.com', '{noop}1Fishka1!'),
    ('DELETED', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS4slJmC44juXOVibQ0hnx5KCCpmv9Pbi4jrA&usqp=CAU', '2021-01-12 10:23:54', DATE'1995-10-19', 'Stalker' ,'Petr', 'Petrov', '+38096384403', 'USER', 'petr@gmail.com', '{noop}1Fishka1!'),
    ('NOT_CONFIRM', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQGTWzonowUOz5cu8HOh6Z2rKOgMO-5hE0Trw&usqp=CAU', '2020-06-5 10:23:54',DATE'2001-12-23', 'hunter' ,'Sveta', 'Svetikova', '+38091884404', 'USER', 'sveta@gmail.com', '{noop}1Fishka1!'),
    ('CONFIRMED', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIeBYp5sjm31vMJW2hElma9RFKHVwK2XhQkQ&usqp=CAU', '2023-03-25 10:23:54', DATE'1984-03-14', 'kolobok' ,'Vlad', 'Vladikov', '+38095674405', 'USER', 'vlad@gmail.com', '{noop}1Fishka1!'),
    ('BANNED', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAqAZG7zLGhZIxSUV6EVLfQX3WEUawmvM-eA&usqp=CAU', '2022-02-24 10:23:54', DATE'1984-03-14', 'nika88' ,'Kate', 'Smith', '+38096114467', 'ADMIN', 'kate@gmail.com', '{noop}1Fishka1!');

INSERT INTO addresses (country, city, street, street_number, house_number)
VALUES ('Deualchland','Berlin','Pariser Platz','1',1),
       ('Deualchland','Berlin','Pariser Platz','2',23),
       ('Deualchland','Berlin','Pariser Platz','3',3);

INSERT INTO confirmation_code (code, user_id, date_time)
VALUES ('e43fb7c0-16fb-4c4a-a329-8a731303d7c8',(SELECT users.user_id from users where nickname = 'Fishka' ),'20029-10-19 10:23:54'),
       ('93a1cfaf-80a1-4e1f-9ec1-6c160c6e6b41',(SELECT users.user_id from users where nickname = 'Stalker' ),'20014-10-19 10:23:54'),
       ('5a9b1d06-2f3d-4571-95e4-827f6c4d0c49',(SELECT users.user_id from users where nickname = 'hunter' ),'20026-1-19 10:23:54'),
       ('bfe36279-3c8a-4b4e-8c0c-7e5315aa44b0',(SELECT users.user_id from users where nickname = 'kolobok' ),'20028-10-19 10:23:54'),
       ('d8db03a2-3f7f-4a72-8a4b-58b9632f6e05',(SELECT users.user_id from users where nickname = 'nika88' ),'2004-10-19 10:23:54');


INSERT INTO ticket_orders (game_id, user_id, price, state, number, create_at)
VALUES ((SELECT game_id FROM games where title = 'Resident Evil 4 VR'),(SELECT users.user_id from users where nickname = 'kolobok' ),24,'CANCELED','qw234rwfA','20023-10-19 10:23:54'),
       ((SELECT game_id FROM games where title = 'Portal Strike: Kids'),(SELECT users.user_id from users where nickname = 'kolobok' ),24,'PAID','qw234rwfA','20023-11-12 10:23:54'),
       ((SELECT game_id FROM games where title = 'Portal Arena Heroes'),(SELECT users.user_id from users where nickname = 'Fishka' ),24,'NEW','qw234rwfA','20023-1-15 10:23:54'),
       ((SELECT game_id FROM games where title = 'Portal Arena Heroes'),(SELECT users.user_id from users where nickname = 'Stalker' ),24,'CANCELED','qw234rwfA','20023-2-11 10:23:54'),
       ((SELECT game_id FROM games where title = 'Portal Strike: Kids'),(SELECT users.user_id from users where nickname = 'Stalker' ),24,'PAID','qw234rwfA','20023-5-16 10:23:54'),
       ((SELECT game_id FROM games where title = 'Resident Evil 4 VR'),(SELECT users.user_id from users where nickname = 'nika88' ),24,'CANCELED','qw234rwfA','20023-6-18 10:23:54');

INSERT INTO events (is_deleted, title, image_url, text, date)
VALUES
    (false,'Asgard''s Wrath 2 Review: Godly Scale, But At What Cost?', 'https://www.uploadvr.com/content/images/size/w800/format/webp/2023/12/Screenshot-2023-12-14-at-6.45.52-pm.png', 'Asgard''s Wrath 2 is available now, bringing the open world fantasy RPG genre to the VR masses.', DATE'2023-12-20'),
    (false, 'Resident Evil 4 Remake VR Review: The Definitive VR Version Of A Non-Stop, Thrilling Campaign', 'https://www.uploadvr.com/content/images/size/w800/format/webp/2023/12/Resident-Evil-4_20231209110035.png', 'While not completely free of its third-person trappings, this VR version of',DATE'2023-12-16'),
    (false, 'Review: Wallace & Gromit In The Grand Getaway Offers A Fine Day Out', 'https://www.uploadvr.com/content/images/size/w800/format/webp/2023/12/KeyArt_16_9_Without-Logo-1.png', 'Wallace &amp; Gromit in the Grand Getaway successfully translates the films into a VR adventure with a few hiccups. Here''s our full review:</p>',DATE'2023-12-14'),
    (false, 'Review: Racket Club Delivers A Strong Serve', 'https://www.uploadvr.com/content/images/size/w800/format/webp/2023/12/Racket-Club---Hero-Art---Landscape---2560x1440.png', 'Racket Club brings a unique spin on racket sports with a multiplayer arcade game. Available this week on most major VR platforms, read on for our full review:</p>', DATE'2023-12-11'),
    (false,'Arizona Sunshine 2 Review: Reanimating A Dying Genre In The Best Possible Way', 'https://www.uploadvr.com/content/images/size/w800/format/webp/2023/12/Arizona-Sunshine-2---Launch-Screenshot-2.png', 'On a cold February evening in 2016, Arizona Sunshine changed my life', DATE'2023-12-05'),
    (false, 'Review: Assassin''s Creed Nexus VR Stands Proud With The Main Series', 'https://www.uploadvr.com/content/images/size/w800/format/webp/2023/11/assassins-creed-nexus-vr.jpg', 'Assassin''s Creed Nexus offers a thrilling VR first-person spin-off, delivering a varied campaign that lives up to the main series. Read on for our full review:', DATE'2023-11-16'),
    (false, 'Demeo Battles Review: A New Iteration Of A Proven Success', 'https://www.uploadvr.com/content/images/size/w800/format/webp/2023/11/Demeo-Battles-VR-Screenshot-001.jpg', 'When it released in 2021, we named Demeo our  Game of the Year across all VR platforms.', DATE'2023-11-09'),
    (false,'The Foglands Review: A Roguelike Better Left In The Dark', 'https://www.uploadvr.com/content/images/size/w800/format/webp/2023/11/ce8406dc72c1bfe8a03ba0ac86ad1989af0619c8-scaled.webp', 'One might be forgiven for groaning at news of another roguelike shooter landing on Quest headsets.',DATE'2023-11-07'),
    (true, 'New Demeo Battl ccess', 'https://www.uploadvr.com/content/images/size/w800/format/webp/2023/11/Demeo-Battles-VR-Screenshot-001.jpg', 'When it released in 2021, we named Demeo our  Game of the Year across all VR platforms.', DATE'2023-11-09');
