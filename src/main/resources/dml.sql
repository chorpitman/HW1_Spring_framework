insert into User (name, email) values ('Joe', 'joe@i.ua');
insert into User (name, email) values ('Jack', 'jack@i.ua');
insert into User (name, email) values ('John', 'John@i.ua');
insert into User (name, email) values ('John', 'John1@i.ua');
insert into User (name, email) values ('John', 'John2@i.ua');
insert into User (name, email) values ('John', 'John3@i.ua');
insert into User (name, email) values ('John', 'John5@i.ua');

insert into Event (date, title, ticketPrice) values ('2016-05-01', 'VELODAY', 100.50);
insert into Event (date, title, ticketPrice) values ('2016-06-01', 'VELODAY', 200.50);
insert into Event (date, title, ticketPrice) values ('2016-07-01', 'VELODAY', 300.50);
insert into Event (date, title, ticketPrice) values ('2016-08-01', 'BOX', 400.50);

insert into Ticket (userId, eventId, place, category) values (1, 1, 10, 'STANDART');
insert into Ticket (userId, eventId, place, category) values (2, 1, 20, 'PREMIUM');
insert into Ticket (userId, eventId, place, category) values (3, 2, 30, 'BAR');

insert into UserAccount (userId, amount) values (1, 50);
insert into UserAccount (userId, amount) values (2, 0);
insert into UserAccount (userId, amount) values (3, 0);
insert into UserAccount (userId, amount) values (4, 10);
insert into UserAccount (userId, amount) values (5, 5);
insert into UserAccount (userId, amount) values (6, 0);
insert into UserAccount (userId, amount) values (7, 0);
insert into UserAccount (userId, amount) values (8, 20);