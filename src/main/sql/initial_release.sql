create table User (
	user_id int primary key auto_increment,
    email varchar(100) NOT NULL,
    password varchar(8),
    last_update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(email)
) auto_increment = 0;

create table Note (
	id int primary key auto_increment,
    user_id int,
    title varchar(8),
    note varchar(1000),
    last_update_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP
) auto_increment = 0;
