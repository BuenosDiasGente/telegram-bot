CREATE TABLE IF NOT EXISTS notification_task
(
    id INT8 PRIMARY KEY NOT NULL,
    chat_id INT8,
    message VARCHAR (100),
    date_time TIMESTAMP
    );
CREATE SEQUENCE notification_task_sequence START 1 INCREMENT 1;