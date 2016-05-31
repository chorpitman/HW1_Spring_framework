CREATE TABLE User(
  id    INT         NOT NULL AUTO_INCREMENT,
  name  VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE Event (
  id          INT          NOT NULL AUTO_INCREMENT,
  date        DATETIME     NOT NULL,
  title       VARCHAR(100) NOT NULL,
  ticketPrice INT          NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Ticket (
  id       INT         NOT NULL AUTO_INCREMENT,
  userId   INT         NOT NULL,
  eventId  INT         NOT NULL,
  place    INT         NOT NULL,
  category VARCHAR(50) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES User (id)
    ON DELETE CASCADE,
  FOREIGN KEY (eventId) REFERENCES Event (id)
    ON DELETE CASCADE
);

CREATE TABLE UserAccount (
  id     INT           NOT NULL AUTO_INCREMENT,
  userId INT           NOT NULL,
  amount DECIMAL(9, 2) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES User (id)
    ON DELETE CASCADE
);