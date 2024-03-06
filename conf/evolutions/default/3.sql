# --- !Ups

CREATE TABLE class_facilitator (
  classroom_id BIGINT NOT NULL,
  facilitator_id BIGINT NOT NULL,
  CONSTRAINT pk_class_facilitator PRIMARY KEY (classroom_id, facilitator_id),
  CONSTRAINT class_fk FOREIGN KEY (classroom_id) REFERENCES classroom(id),
  CONSTRAINT facilitator_fk FOREIGN KEY (facilitator_id) REFERENCES facilitator(id)
);

# --- !Downs

DROP TABLE IF EXISTS class_facilitator;