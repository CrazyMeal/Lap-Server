create table city (id bigint not null auto_increment, name varchar(255), primary key (id))
create table parking (id bigint not null auto_increment, date_of_datas DATETIME, free_places integer not null, grab_time DATETIME not null, name varchar(255), status varchar(255), total_places integer not null, city_id bigint, primary key (id))
alter table parking add constraint FKn87sbnjc8t0nvo2tk3h19iavp foreign key (city_id) references city (id)
