
CREATE TABLE IF NOT EXISTS notification_task
(
    id        INT8 PRIMARY KEY,
    chat_id   INT8,
    massage   VARCHAR(100),
    date_time TIMESTAMP
);