-- facilitatorテーブル

# --- !Ups

CREATE TABLE facilitator (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  login_id VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL
);

# --- !Downs

DROP TABLE IF EXISTS facilitator;
