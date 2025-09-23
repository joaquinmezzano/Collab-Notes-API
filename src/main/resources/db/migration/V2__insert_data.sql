-- Inserting one user and some notes on it
INSERT INTO users (username, email) VALUES ('Joaquin Mezzano', 'mezzanojoaquin@gmail.com');

INSERT INTO notes (title, content, user_id) VALUES
('Fantasy World', 'This is an amazing world, where anything can happen.', 1),
('Favorite Music', '1. Chacareras\n2. Cumbia Santafesina\n3. Regaetton Viejito', 1);