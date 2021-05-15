insert into restaurants(name)
values ('El Mediterrneo');
insert into restaurants(name)
values ('Caramel');
insert into restaurants(name)
values ('Tanuki');

insert into dishes (name, price, restaurant_id, date)
values ('Holdlamis', 592, 1, '2021-05-09 20:41:45');
insert into dishes (name, price, restaurant_id, date)
values ('Prodder', 1588, 1, '2021-05-10 10:39:49');
insert into dishes (name, price, restaurant_id, date)
values ('Duobam', 481, 1, '2021-05-10 09:59:36');
insert into dishes (name, price, restaurant_id, date)
values ('Sonair', 1230, 1, '2021-05-09 08:19:35');
insert into dishes (name, price, restaurant_id, date)
values ('Job', 37, 2, '2021-05-10 20:43:13');
insert into dishes (name, price, restaurant_id, date)
values ('Tres-Zap', 1419, 2, '2021-05-10 20:12:39');
insert into dishes (name, price, restaurant_id, date)
values ('Y-find', 2375, 2, '2021-05-10 16:56:36');
insert into dishes (name, price, restaurant_id, date)
values ('Subin', 713, 2, '2021-05-10 11:54:12');
insert into dishes (name, price, restaurant_id, date)
values ('Sonsing', 1645, 2, '2021-05-09 03:04:06');
insert into dishes (name, price, restaurant_id, date)
values ('Prodder', 1218, 2, '2021-05-09 13:00:02');
insert into dishes (name, price, restaurant_id, date)
values ('Namfix', 2255, 3, '2021-05-11 01:08:22');
insert into dishes (name, price, restaurant_id, date)
values ('Alphazap', 1426, 3, '2021-05-10 20:26:34');
insert into dishes (name, price, restaurant_id, date)
values ('Konklab', 2105, 3, '2021-05-10 00:17:03');
insert into dishes (name, price, restaurant_id, date)
values ('Andalax', 1980, 3, '2021-05-09 04:49:32');
insert into dishes (name, price, restaurant_id, date)
values ('Cardify', 2252, 3, '2021-05-10 03:39:50');

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
values ('user2@i.c', '$2y$10$AkDvMXC3lEBRvhbdBUdeS.NjN6SCT5o.T0kGqlZFbPBm0ZQN.pRbe', '2020-01-01 00:00:00',
        true);
insert into users(email, password, registration_date, activated)
values ('user3@i.c', '$2y$10$wCCDJU5.yEPctdwh2iDX9uxcR1Zcjpa2uF35rTjZDMd.VbeoLZELe',
        '2021-01-01 00:00:00', false);

insert into users_roles (user_id, role_id)
values (1, 1);
insert into users_roles (user_id, role_id)
values (2, 2);
insert into users_roles (user_id, role_id)
values (3, 2);
insert into users_roles (user_id, role_id)
values (4, 2);

//All passwords are same as user emails before @
