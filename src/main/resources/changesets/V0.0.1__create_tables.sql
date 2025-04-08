CREATE TABLE IF NOT EXISTS person(
  	id varchar(255) NOT NULL PRIMARY KEY,
  	name varchar(255),
  	surname varchar(255),
  	birth_date DATE,
  	company varchar(255),
  	creation_date timestamp(6) NOT NULL,
  	modification_date timestamp(6),
  	entity_version int8 NOT NULL
);

CREATE TABLE IF NOT EXISTS task(
  	id varchar(255) NOT NULL PRIMARY KEY,
  	stage varchar(255) NOT NULL,
  	progress INT NOT NULL,
  	creation_date timestamp(6) NOT NULL,
  	modification_date timestamp(6),
  	entity_version int8 NOT NULL
);

CREATE TABLE IF NOT EXISTS task_result(
  	id varchar(255) NOT NULL PRIMARY KEY,
    task_id varchar(255),
  	value_before varchar(255),
  	current_value varchar(255),
  	dissimilarity DOUBLE PRECISION,
  	classification varchar(255) NOT NULL,
  	creation_date timestamp(6) NOT NULL,
  	modification_date timestamp(6),
  	entity_version int8 NOT NULL,

    CONSTRAINT fk_task FOREIGN KEY (task_id) REFERENCES task(id)
);