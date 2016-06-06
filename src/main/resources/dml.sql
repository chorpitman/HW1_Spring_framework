insert into user (name, email) values ('joe', 'joe@i.ua');
insert into user (name, email) values ('jack', 'jack@i.ua');
insert into user (name, email) values ('john', 'John@i.ua');
insert into user (name, email) values ('john', 'John1@i.ua');
insert into user (name, email) values ('john', 'John2@i.ua');
insert into user (name, email) values ('john', 'John3@i.ua');
insert into user (name, email) values ('john', 'John5@i.ua');

insert into event (date, title, ticketPrice) values ('2016-06-02 13:34:00', 'Veloday', 1000.50);
insert into event (date, title, ticketPrice) values ('2016-05-02 13:34:00', 'Veloday', 2000.50);
insert into event (date, title, ticketPrice) values ('2016-04-02 13:34:00', 'Veloday', 3000.50);

insert into ticket (userId, eventId, place, category) values (1, 1, 10, 'STANDART');
insert into ticket (userId, eventId, place, category) values (2, 1, 20, 'PREMIUM');
insert into ticket (userId, eventId, place, category) values (3, 2, 30, 'PREMIUM EDITION');