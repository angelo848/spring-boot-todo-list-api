INSERT INTO user (id, name, email, password, created_date, last_modified_date) VALUES
(1, 'User 1', 'user@mail.com', '$2a$14$q6qdGty3yNdg2ho6MrZfYet8LVE9KmVDUFXcv7iqU7Qb5QIzjw30m', NOW(), NOW());

INSERT INTO task (id, description, status, user_id, created_date, last_modified_date) VALUES
(1, 'Task 1', 'DOING', 1, NOW(), NOW())