DROP TABLE IF EXISTS User;

CREATE TABLE User (
  id             INTEGER          NOT NULL PRIMARY KEY  AUTO_INCREMENT,
  number         VARCHAR(10)      NOT NULL UNIQUE,
  first_name      VARCHAR(30)      NOT NULL,
  last_name       VARCHAR(50)      NOT NULL,
  sex_ype        VARCHAR(1),
  civility_type   VARCHAR(5),
  street         VARCHAR(30)
  city           VARCHAR(30),
  zip_code        VARCHAR(12),
  telephone      VARCHAR(10),
  email          VARCHAR(20)
);