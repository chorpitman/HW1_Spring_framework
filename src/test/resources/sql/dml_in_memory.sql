INSERT INTO User (name, email) VALUES ('Joe', 'joe@i.ua');
INSERT INTO User (name, email) VALUES ('Jack', 'jack@i.ua');
INSERT INTO User (name, email) VALUES ('John', 'John@i.ua');
INSERT INTO User (name, email) VALUES ('John', 'John1@i.ua');
INSERT INTO User (name, email) VALUES ('John', 'John2@i.ua');
INSERT INTO User (name, email) VALUES ('John', 'John3@i.ua');
INSERT INTO User (name, email) VALUES ('John', 'John5@i.ua');

INSERT INTO Event (date, title, ticketPrice) VALUES ('2016-05-01', 'VELODAY', 101);
INSERT INTO Event (date, title, ticketPrice) VALUES ('2016-06-01', 'VELODAY', 201);
INSERT INTO Event (date, title, ticketPrice) VALUES ('2016-07-01', 'VELODAY', 301);
INSERT INTO Event (date, title, ticketPrice) VALUES ('2016-08-01', 'BOX', 401);

INSERT INTO Ticket (userId, eventId, place, category) VALUES (1, 1, 10, 'STANDARD');
INSERT INTO Ticket (userId, eventId, place, category) VALUES (2, 1, 20, 'PREMIUM');
INSERT INTO Ticket (userId, eventId, place, category) VALUES (3, 2, 30, 'BAR');
INSERT INTO Ticket (userId, eventId, place, category) VALUES (1, 2, 40, 'BAR');

INSERT INTO UserAccount (userId, amount) VALUES (1, 50);
INSERT INTO UserAccount (userId, amount) VALUES (2, 0);
INSERT INTO UserAccount (userId, amount) VALUES (3, 0);
INSERT INTO UserAccount (userId, amount) VALUES (4, 10);
INSERT INTO UserAccount (userId, amount) VALUES (5, 5);
INSERT INTO UserAccount (userId, amount) VALUES (6, 0);
INSERT INTO UserAccount (userId, amount) VALUES (7, 0);