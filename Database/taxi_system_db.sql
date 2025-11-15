CREATE DATABASE taxi_system_db;                                        
CREATE USER 'taxi_user'@'localhost' IDENTIFIED BY '123456';            
GRANT ALL PRIVILEGES ON taxi_system_db.* TO 'taxi_user'@'localhost';   
FLUSH PRIVILEGES;                                                      

USE taxi_system_db;
SELECT * FROM passenger;