BEGIN TRANSACTION;

DROP TABLE IF EXISTS users cascade;
DROP TABLE IF EXISTS films cascade;
DROP TABLE IF EXISTS screens cascade;
DROP TABLE IF EXISTS showtimes cascade;
DROP TABLE IF EXISTS seats cascade;
DROP TABLE IF EXISTS showtimes cascade;
DROP TABLE IF EXISTS reservations cascade;
DROP TABLE IF EXISTS theater_rows cascade;
DROP TABLE IF EXISTS row_seats cascade;

create sequence seq_user_history;
create sequence seq_showtime_id;
create sequence seq_seat_id;
create sequence seq_reservation_id;

CREATE TABLE users (
  id serial PRIMARY KEY,
  username varchar(255) NOT NULL UNIQUE,     -- Username
  password varchar(32) NOT NULL,      -- Password
  salt varchar(256) NOT NULL,          -- Password Salt
  role varchar(255) NOT NULL default('user')
);


CREATE TABLE films
(
  film_id integer not null,
  film_title varchar not null,
  constraint pk_film_id primary key (film_id)
);

CREATE TABLE screens
(
  screen_id integer not null unique,
  film_id integer
  ,constraint pk_screen_id primary key (screen_id)
  ,foreign key(film_id) references films(film_id)
);

CREATE TABLE theater_rows
(
row_id char not null
,constraint pk_row_id primary key (row_id)
);

CREATE TABLE row_seats
(
row_seat_id integer not null
,constraint pk_row_seat_id primary key (row_seat_id)
);

CREATE TABLE seats
(
seat_id integer not null default nextval('seq_seat_id'),
screen_id integer not null,
row_id char,
row_seat_id integer,
constraint pk_seat_id primary key (seat_id),
foreign key(screen_id) references screens(screen_id),
foreign key(row_id) references theater_rows(row_id),
foreign key(row_seat_id) references row_seats(row_seat_id)
);


CREATE TABLE showtimes 
(
showtime_id integer not null default nextval('seq_showtime_id')
,screen_id integer not null
,showing_date date not null
,showing_time time not null
,constraint pk_showtime_id primary key (showtime_id)
,foreign key(screen_id) references screens(screen_id)
);

CREATE TABLE reservations
(
reservation_id integer not null default nextval('seq_reservation_id')
,user_id integer not null
,showtime_id integer not null
,seat_id integer not null
,reservation_type varchar not null
,payment float not null  
,constraint pk_reservation_id primary key (reservation_id)
,foreign key(showtime_id) references showtimes(showtime_id)
,foreign key(user_id) references users(id)
,foreign key(seat_id) references seats(seat_id)
);


COMMIT TRANSACTION;
