INSERT INTO games (image, title, description, genre,release_date)
values ('https://portal-vr.ru/wp-content/uploads/2023/02/skgev4xstvwl9qdwux96p3-768x432.jpg'
       ,'Resident Evil 4 VR','Исследуйте мир Resident Evil 4, полностью переработанный для виртуальной реальности. Попробуйте себя в роли специального агента Леона С. Кеннеди в его миссии по спасению дочери президента США, похищенной таинственным культом. Столкнитесь лицом к лицу с врагами и раскройте секреты игрового процесса, который произвел революцию в жанре ужасов на выживание. Сразитесь с ужасными существами и сразитесь с жителями деревни, контролируемыми разумом, и выясните их связь с культом, стоящим за похищением.'
       ,'SHOOTER','2023-02-15'),
       ('https://portal-vr.ru/wp-content/uploads/2023/07/portal-strike-kids_2.1-768x432.jpg','Portal Strike: Kids',
        'Portal Strike: Kids проходит в зале VR Arena – большом открытом пространстве, где игроки передвигаются своими ногами. В Portal Strike: Kids представлены 5 проработанных карт; юные игроки побывают в Египте, Средневековье, на пиратском корабле и в киберпанке'
           ,'SPORTS','2022-01-10'),
       ('https://portal-vr.ru/wp-content/uploads/2023/10/arena-heroes-v2-9h16-768x432.jpg'
       ,'Portal Arena Heroes','Portal Arena Heroes — это уникальная VR игра в стиле фентези на арене со свободным передвижением. Выберите 1 из 3-х классов и сражайтесь плечом к плечу на различных аренах команда против команды или совместно против армии монстров. Каждый класс имеет свои уникальные атаки и ультимативные способности. Окунитесь в настоящий мир фентези вместе с друзьями в клубах Portal VR!'
       ,'ACTION','2023-02-15');

INSERT INTO users (state,avatar,created_at,"birth_date",nickname,firstname, lastname,phone, role, email, password)
    values  ('CONFIRMED','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAqAZG7zLGhZIxSUV6EVLfQX3WEUawmvM-eA&usqp=CAU','2004-10-19 10:23:54','1990-01-10','Fishka' ,'Ivan', 'Ivanov','+38096584402', 'ADMIN', 'ivan@gmail.com', '{noop}123'),
            ('NOT_CONFIRM','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAqAZG7zLGhZIxSUV6EVLfQX3WEUawmvM-eA&usqp=CAU','2021-01-12 10:23:54','1995-10-19','Stalker' ,'Petr', 'Petrov','+38096384403', 'USER', 'petr@gmail.com', '{noop}123'),
            ('NOT_CONFIRM','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAqAZG7zLGhZIxSUV6EVLfQX3WEUawmvM-eA&usqp=CAU','2020-06-5 10:23:54','2001-12-23','hunter' ,'Sveta', 'Svetikova','+38091884404', 'USER', 'sveta@gmail.com', '{noop}123'),
            ('CONFIRMED','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAqAZG7zLGhZIxSUV6EVLfQX3WEUawmvM-eA&usqp=CAU','2023-03-25 10:23:54','1984-03-14','kolobok' ,'Vlad', 'Vladikov','+38095674405', 'USER', 'vlad@gmail.com', '{noop}123'),
            ('CONFIRMED','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAqAZG7zLGhZIxSUV6EVLfQX3WEUawmvM-eA&usqp=CAU','2022-02-24 10:23:54','1984-03-14','nika88' ,'Kate', 'Smith','+38096114467', 'ADMIN', 'kate@gmail.com', '{noop}123');

