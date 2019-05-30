insert into users (username, password, enabled)
values ('admin', '$2a$10$eG28hqAjihXGfSyrNUji9OZEGnMNh66uQUjjIBU0OaaE4Os4u1tom', 1);

insert into users (username, password, enabled)
values ('student', '$2a$10$XUil1gwD8eWVxsCl4T0WmuvWr/u/eOR/colwWalMWa.4rw.BK7Unm', 1);

insert into users (username, password, enabled)
values ('filip', '$2a$10$fELedfxZhxWPwyLE3cBFl.RVEYH0rWDqEf5ym1MyeraPEwajuIfRe', 1);

insert into authorities (username, authority) values ('admin', 'ROLE_ADMIN');
insert into authorities (username, authority) values ('admin', 'ROLE_USER');
insert into authorities (username, authority) values ('student', 'ROLE_USER');
insert into authorities (username, authority) values ('filip', 'ROLE_USER');

insert into wallet (id, sum, typeOfWallet, userId)
values (1, 0, 'PHYSICAL', 1);
insert into wallet (id, sum, typeOfWallet, userId)
values (2, 0, 'PHYSICAL', 3);

insert into expense (id, name, cost, TYPEOFEXPENSE, walletid) values (1, 'Test1', 100, 'HRANA', 1);
insert into expense (id, name, cost, TYPEOFEXPENSE, walletid) values (2, 'Test2', 200, 'HRANA', 2);
insert into expense (id, name, cost, TYPEOFEXPENSE, walletid) values (3, 'Test3', 200, 'BENZIN', 1);
