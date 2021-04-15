insert into user_db.`user` (id) values ('Nick Bronson');
insert into user_db.`user` (id) values ('Jerry Wilkins');
insert into user_db.`user` (id) values ('Mike Brown');
insert into user_db.`user` (id) values ('Lara Grey');

insert into user_db.`quota` (user_id, item_id, `count`) values ('Nick Bronson', 'latch', 10);
insert into user_db.`quota` (user_id, item_id, `count`) values ('Nick Bronson', 'hinge', 12);
insert into user_db.`quota` (user_id, item_id, `count`) values ('Jerry Wilkins', 'gear', 30);
insert into user_db.`quota` (user_id, item_id, `count`) values ('Mike Brown', 'hinge', 39);
insert into user_db.`quota` (user_id, item_id, `count`) values ('Mike Brown', 'gear', 4);
insert into user_db.`quota` (user_id, item_id, `count`) values ('Lara Grey', 'latch', 99);

insert into inventory_db.`item` (id, `count`) values ('latch', 1000);
insert into inventory_db.`item` (id, `count`) values ('hinge', 2000);
insert into inventory_db.`item` (id, `count`) values ('gear', 250);
