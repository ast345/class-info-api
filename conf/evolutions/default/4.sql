# --- !Ups

CREATE TABLE student (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  login_id VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  classroom_id BIGINT NOT NULL,
  CONSTRAINT fk_student_classroom FOREIGN KEY (classroom_id) REFERENCES classroom(id)
);

# --- !Downs

DROP TABLE IF EXISTS student;