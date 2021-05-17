insert into restaurants(name)
values ('El Mediterraneo');
insert into restaurants(name)
values ('Caramel');
insert into restaurants(name)
values ('Tanuki');

insert into dishes (name, price, restaurant_id, date)
values ('Key lime pie', 592, 1, '2021-05-09 20:41:45');
insert into dishes (name, price, restaurant_id, date)
values ('Tater tots', 1588, 1, '2021-05-10 10:39:49');
insert into dishes (name, price, restaurant_id, date)
values ('Cobb salad', 481, 1, '2021-05-10 09:59:36');
insert into dishes (name, price, restaurant_id, date)
values ('Pot roast', 1230, 1, '2021-05-09 08:19:35');
insert into dishes (name, price, restaurant_id, date)
values ('Twinkies', 137, 2, '2021-05-10 20:43:13');
insert into dishes (name, price, restaurant_id, date)
values ('Jerky', 1419, 2, '2021-05-10 20:12:39');
insert into dishes (name, price, restaurant_id, date)
values ('Fajitas', 2375, 2, '2021-05-10 16:56:36');
insert into dishes (name, price, restaurant_id, date)
values ('Banana split', 713, 2, '2021-05-10 11:54:12');
insert into dishes (name, price, restaurant_id, date)
values ('Cornbread', 1645, 2, '2021-05-09 03:04:06');
insert into dishes (name, price, restaurant_id, date)
values ('GORP', 1218, 2, '2021-05-09 13:00:02');
insert into dishes (name, price, restaurant_id, date)
values ('Jambalaya', 2255, 3, '2021-05-11 01:08:22');
insert into dishes (name, price, restaurant_id, date)
values ('Biscuits ''n'' gravy', 1426, 3, '2021-05-10 20:26:34');
insert into dishes (name, price, restaurant_id, date)
values ('Smithfield ham', 2105, 3, '2021-05-10 00:17:03');
insert into dishes (name, price, restaurant_id, date)
values ('Chicken fried steak', 1980, 3, '2021-05-09 04:49:32');
insert into dishes (name, price, restaurant_id, date)
values ('Wild Alaska salmon', 2252, 3, '2021-05-10 03:39:50');

insert into roles (name)
values ('ROLE_ADMIN');
insert into roles (name)
values ('ROLE_USER');

insert into users(email, password, registration_date, activated)
values ('admin@i.c', '$2y$12$1iLw6CfVjgCuvnKnKfoLJOLase/9d0Imtxw5WyUKdMxLkWaKyr5i2', '2020-01-01 00:00:00',
        true);
insert into users(email, password, registration_date, activated)
values ('user@i.c', '$2y$12$tAYuwYaLmKp0jxZguKNDSOs9uLbmCRmG5/U0CY5l/g4T5YdCd0d5.',
        '2021-01-01 00:00:00', true);
insert into users(email, password, registration_date, activated)
values ('user2@i.c', '$2y$10$U.iRvvxlYAE3FCTgkmaqIu12wMdArbZuyHU4A0DMZxq6Z/ZzNBuvW', '2020-01-01 00:00:00',
        true);
insert into users(email, password, registration_date, activated)
values ('user3@i.c', '$2y$10$eJI8y5A/SuBjZNaWJ.1G2.Jc46bRJ46Rf4UVi1l2ci42j./p2r2X2',
        '2021-01-01 00:00:00', true);
insert into users(email, password, registration_date, activated)
values ('notactivated@i.c', '$2y$10$vmRnsIss/Pxw.oj8yghhxOaHJvntqEb9GbAgY6B/.tlvp0dafngYS',
        '2021-01-01 00:00:00', false);


insert into users_roles (user_id, role_id)
values (1, 1);
insert into users_roles (user_id, role_id)
values (2, 2);
insert into users_roles (user_id, role_id)
values (3, 2);
insert into users_roles (user_id, role_id)
values (4, 2);
insert into users_roles (user_id, role_id)
values (5, 2);

//All passwords are same as user emails before @
