-- Password for this user is 'greatwall'
INSERT INTO users ("username", "password", "salt", "role") VALUES
('user',
'FjZDm+sndmsdEDwNtfr6NA==',
'kidcasB0te7i0jK0fmRIGHSm0mYhdLTaiGkEAiEvLp7dAEHWnuT8n/5bd2V/mqjstQ198iImm1xCmEFu+BHyOz1Mf7vm4LILcrr17y7Ws40Xyx4FOCt8jD03G+jEafpuVJnPiDmaZQXJEpEfekGOvhKGOCtBnT5uatjKEuVWuDA=',
'user');

insert into films (film_id, film_title) values (446893, 'Trolls World Tour');
insert into films (film_id, film_title) values (508439, 'Onward');
insert into films (film_id, film_title) values (338762, 'Bloodshot');
insert into films (film_id, film_title) values (238, 'The Godfather');
insert into films (film_id, film_title) values (16296, 'Killer Klowns From Outer Space');
insert into films (film_id, film_title) values (1584, 'School of Rock');
insert into films (film_id, film_title) values (11379, 'The Adventures of Buckaroo Banzai');


insert into screens (screen_id, film_id) values(1,446893);
insert into screens (screen_id, film_id) values(2,508439);
insert into screens (screen_id, film_id) values(3,338762);
insert into screens (screen_id, film_id) values(4,238);
insert into screens (screen_id, film_id) values(5,16296);
insert into screens (screen_id, film_id) values(6,1584);
insert into screens (screen_id, film_id) values(7,11379);

insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/20/2020', '11:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/20/2020', '18:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/20/2020', '21:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/21/2020', '11:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/21/2020', '18:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/21/2020', '21:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/22/2020', '11:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/22/2020', '18:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/22/2020', '21:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/23/2020', '11:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/23/2020', '18:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/23/2020', '21:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/24/2020', '11:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/24/2020', '18:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/24/2020', '21:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/25/2020', '11:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/25/2020', '18:00' from screens;
insert into showtimes (screen_id, showing_date, showing_time) select screens.screen_id,'4/25/2020', '21:00' from screens;

insert into theater_rows (row_id) values('A');
insert into theater_rows (row_id) values('B');
insert into theater_rows (row_id) values('C');
insert into theater_rows (row_id) values('D');
insert into theater_rows (row_id) values('E');
insert into theater_rows (row_id) values('F');
insert into theater_rows (row_id) values('G');

insert into row_seats (row_seat_id) values(1);
insert into row_seats (row_seat_id) values(2);
insert into row_seats (row_seat_id) values(3);
insert into row_seats (row_seat_id) values(4);
insert into row_seats (row_seat_id) values(5);
insert into row_seats (row_seat_id) values(6);
insert into row_seats (row_seat_id) values(7);
insert into row_seats (row_seat_id) values(8);
insert into row_seats (row_seat_id) values(9);
insert into row_seats (row_seat_id) values(10);
insert into row_seats (row_seat_id) values(11);
insert into row_seats (row_seat_id) values(12);
insert into row_seats (row_seat_id) values(13);
insert into row_seats (row_seat_id) values(14);


insert into seats (screen_id, row_id, row_seat_id) select screens.screen_id, theater_rows.row_id, row_seats.row_seat_id from screens, theater_rows, row_seats;









