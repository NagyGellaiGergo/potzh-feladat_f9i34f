CREATE DATABASE potzh_database;
GRANT ALL PRIVILEGES ON potzh_database.* to 'potzh_user';

CREATE TABLE potzh_database.movie (
					ID int NOT NULL PRIMARY KEY AUTO_INCREMENT,
					title varchar(255),
					director_name varchar(255),
					release_year int,
					genre varchar(100)
);

CREATE TABLE potzh_database.user (
                      ID int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                      username varchar(255) NOT NULL UNIQUE,
                      password varchar(100) NOT NULL
);

CREATE TABLE potzh_database.role (
                      ID int NOT NULL PRIMARY KEY AUTO_INCREMENT,
                      code varchar(255) NOT NULL UNIQUE,
                      description varchar(255)
);

create table potzh_database.user_role (
                           user_id int,
                           role_id int,
                           PRIMARY KEY (user_id, role_id),
                           FOREIGN KEY (user_id) REFERENCES user(id),
                           FOREIGN KEY (role_id) REFERENCES role(id)
);

insert into potzh_database.movie (title, director_name, release_year, genre) values ("The Dark Knight", "Christopher Nolan", 2008, "Action");
insert into potzh_database.movie (title, director_name, release_year, genre) values ("The Lord of the Rings: The Return of the King", "Peter Jackson", 2003, "Fantasy");
insert into potzh_database.movie (title, director_name, release_year, genre) values ("Avatar", "James Cameron", 2009, "Science Fiction");
insert into potzh_database.movie (title, director_name, release_year, genre) values ("Joker", "Todd Phillips", 2019, "Drama");
insert into potzh_database.movie (title, director_name, release_year, genre) values ("Everything Everywhere All at Once", "Daniel Kwan and Daniel Scheinert", 2022, "Science Fiction");

insert into potzh_database.role (code, description) values ("viewer", "Use the web app.");
insert into potzh_database.role (code, description) values ("admin", "Has right to create new objects.");

insert into potzh_database.user (username, password) values ("user1", "$2a$06$qkg0bF5ZbgNHj/ichvlAWepmKgJjguwGq1E//xVIS5O687BxMh5T.");
insert into potzh_database.user (username, password) values ("user2", "$2a$06$qkg0bF5ZbgNHj/ichvlAWepmKgJjguwGq1E//xVIS5O687BxMh5T.");

insert into potzh_database.user_role (user_id, role_id) values ((select id from potzh_database.user where username = "user1"), (select id from potzh_database.role where code = "viewer"));
insert into potzh_database.user_role (user_id, role_id) values ((select id from potzh_database.user where username = "user2"), (select id from potzh_database.role where code = "viewer"));
insert into potzh_database.user_role (user_id, role_id) values ((select id from potzh_database.user where username = "user2"), (select id from potzh_database.role where code = "admin"));
