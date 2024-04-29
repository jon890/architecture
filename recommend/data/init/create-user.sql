USE mysql;
CREATE USER 'test_user'@'%' IDENTIFIED BY 'test_password';
GRANT ALL PRIVILEGES ON *.* TO 'test_db'@'%';
FLUSH PRIVILEGES;