create table basic_parking_data (id bigint not null auto_increment, date_of_data DATETIME, free_places integer not null, status varchar(255), total_places integer not null, parking_id bigint not null, primary key (id))
create table city (id bigint not null auto_increment, name varchar(255) not null, primary key (id))
create table parking (id bigint not null auto_increment, last_grab_time DATETIME not null, name varchar(255) not null, city_id bigint not null, primary key (id))
alter table parking add constraint UK_vr5dwfk6ywvevahwy1fuwkg0 unique (city_id)
alter table basic_parking_data add constraint FKowphvt3tmqiykbfhdy1uye6fc foreign key (parking_id) references parking (id)
alter table parking add constraint FKn87sbnjc8t0nvo2tk3h19iavp foreign key (city_id) references city (id)
