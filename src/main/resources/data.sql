insert into restaurants(id, name)
values (1, 'El Mediterrneo');
insert into restaurants(id, name)
values (2, 'Caramel');
insert into restaurants(id, name)
values (3, 'Tanuki');

insert into dishes (id, name, price, restaurant_id, date)
values (1, 'Holdlamis', 592, 1, '2021-05-09 20:41:45');
insert into dishes (id, name, price, restaurant_id, date)
values (2, 'Prodder', 1588, 1, '2021-05-10 10:39:49');
insert into dishes (id, name, price, restaurant_id, date)
values (3, 'Duobam', 481, 1, '2021-05-10 09:59:36');
insert into dishes (id, name, price, restaurant_id, date)
values (4, 'Sonair', 1230, 1, '2021-05-09 08:19:35');
insert into dishes (id, name, price, restaurant_id, date)
values (5, 'Job', 37, 2, '2021-05-10 20:43:13');
insert into dishes (id, name, price, restaurant_id, date)
values (6, 'Tres-Zap', 1419, 2, '2021-05-10 20:12:39');
insert into dishes (id, name, price, restaurant_id, date)
values (7, 'Y-find', 2375, 2, '2021-05-10 16:56:36');
insert into dishes (id, name, price, restaurant_id, date)
values (8, 'Subin', 713, 2, '2021-05-10 11:54:12');
insert into dishes (id, name, price, restaurant_id, date)
values (9, 'Sonsing', 1645, 2, '2021-05-09 03:04:06');
insert into dishes (id, name, price, restaurant_id, date)
values (10, 'Prodder', 1218, 2, '2021-05-09 13:00:02');
insert into dishes (id, name, price, restaurant_id, date)
values (11, 'Namfix', 2255, 3, '2021-05-11 01:08:22');
insert into dishes (id, name, price, restaurant_id, date)
values (12, 'Alphazap', 1426, 3, '2021-05-10 20:26:34');
insert into dishes (id, name, price, restaurant_id, date)
values (13, 'Konklab', 2105, 3, '2021-05-10 00:17:03');
insert into dishes (id, name, price, restaurant_id, date)
values (14, 'Andalax', 1980, 3, '2021-05-09 04:49:32');
insert into dishes (id, name, price, restaurant_id, date)
values (15, 'Cardify', 2252, 3, '2021-05-10 03:39:50');

insert into roles (name)
values ('ROLE_USER');
insert into roles (name)
values ('ROLE_ADMIN');

insert into users(id, email, password, registration_date, activated)
values (1, 'example@example.com', '$2y$12$rvIFb6H3/.iPgZurwr8voeDRiUctnegTRc9TooRDPEqbWl1mBvjnK', '2020-01-01 00:00:00',
        true);
insert into users(id, email, password, registration_date, activated)
values (2, 'example2@example.com', '$2y$12$e41ebFWaEFFUXZ8tFdOkqupwOws.rGgxdPJ/dlhM9NAFDpKJYKMq.',
        '2021-01-01 00:00:00', true);
// bcrypted password 'user'

insert into users_roles (user_id, role_id)
values (1, 1);
insert into users_roles (user_id, role_id)
values (2, 1);