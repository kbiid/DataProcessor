create table user (
	id int not null,
	first_name varchar(100) not null,
	last_name varchar(100) not null,
	email varchar(200) not null,
	gender varchar(100) not null,
	ip_address varchar(100) not null,
	date date not null,
	
	constraint user_PK primary key(id)
);