DELETE FROM users;
ALTER SEQUENCE user_seq RESTART WITH 1;

INSERT INTO users (name, email, surname) VALUES
  ('Ivan', 'ivanov@yandex.ru', 'Ivanov'),
  ('Max', 'max@gmail.com', 'Petrov');
