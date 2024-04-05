CREATE TABLE IF NOT EXISTS  utilisateur(id SERIAL PRIMARY KEY, login VARCHAR, pwd VARCHAR(10));
TRUNCATE table utilisateur;
INSERT INTO utilisateur(id,login, pwd) VALUES(1,'user', '123');
COMMIT;