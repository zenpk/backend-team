CREATE DATABASE `work2`;

# CREATE TABLE `work2`.`dorm_infos`
# (
#     id         int     NOT NULL AUTO_INCREMENT,
#     build_num  int     NOT NULL,
#     is_empty   boolean NOT NULL,
#     owner_name varchar(128),
#     PRIMARY KEY (id)
# );

CREATE USER 'work2'@'%' IDENTIFIED BY 'work2';
GRANT ALL PRIVILEGES ON `work2`.* TO 'work2'@'%';
FLUSH PRIVILEGES;
