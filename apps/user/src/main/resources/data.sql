insert ignore into user_db.`user` (id) values ('Nick Bronson');
insert ignore into user_db.`user` (id) values ('Jerry Wilkins');
insert ignore into user_db.`user` (id) values ('Mike Brown');
insert ignore into user_db.`user` (id) values ('Lara Grey');

insert ignore into user_db.`quota` (user_id, item_id, `count`) values ('Nick Bronson', 'latch', 10);
insert ignore into user_db.`quota` (user_id, item_id, `count`) values ('Nick Bronson', 'hinge', 12);
insert ignore into user_db.`quota` (user_id, item_id, `count`) values ('Jerry Wilkins', 'gear', 30);
insert ignore into user_db.`quota` (user_id, item_id, `count`) values ('Mike Brown', 'hinge', 39);
insert ignore into user_db.`quota` (user_id, item_id, `count`) values ('Mike Brown', 'gear', 4);
insert ignore into user_db.`quota` (user_id, item_id, `count`) values ('Lara Grey', 'latch', 99);
