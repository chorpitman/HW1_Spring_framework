DROP DATABASE IF EXISTS booking_db;

CREATE DATABASE IF NOT EXISTS booking_db
  DEFAULT CHARACTER SET utf8;

USE booking_db;

CREATE TABLE User (
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
  category VARCHAR(50) NOT NULL UNIQUE,
  PRIMARY KEY (ID),
  FOREIGN KEY (userId) REFERENCES USER (ID)
    ON DELETE CASCADE,
  FOREIGN KEY (eventId) REFERENCES EVENT (ID)
    ON DELETE CASCADE
);

CREATE TABLE UserAccount (
  id     INT           NOT NULL AUTO_INCREMENT,
  userId INT           NOT NULL,
  amount DECIMAL(9, 2) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (userId) REFERENCES USER (id)
    ON DELETE CASCADE
);