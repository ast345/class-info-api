# --- !Ups

CREATE TABLE classroom (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255)
);

# --- !Downs

DROP TABLE classroom;
