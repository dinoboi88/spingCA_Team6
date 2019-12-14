create table admin (
admin_id integer not null auto_increment,
first_name varchar(45) not null,
middle_name varchar(45),
last_name varchar(45) not null,
gender varchar(6) not null,
birth_date date,
email varchar(45) not null,
mobile varchar(45) not null,
address varchar(255),
primary key (admin_id)
);

create table admin_user (
admin_id integer not null,
username varchar(45) not null,
password varchar(255) not null,
primary key (username)
);

create table course (
course_id integer not null auto_increment,
course_code varchar(45) not null,
course_name varchar(255) not null,
course_unit integer not null,
department_id integer not null,
primary key (course_id)
);

create table department (
department_id integer not null auto_increment,
department_name varchar(255),
primary key (department_id)
);

create table degree (
degree_id integer not null auto_increment,
degree_label varchar(255),
primary key (degree_id)
);

create table lecturer (
lecturer_id integer not null auto_increment,
first_name varchar(45) not null,
middle_name varchar(45),
last_name varchar(45) not null,
gender varchar(6) not null,
birth_date date,
email varchar(45) not null,
mobile varchar(45) not null,
address varchar(255),
department_id integer,
primary key (lecturer_id)
);

create table lecturer_course (
course_id integer not null,
lecturer_id integer not null,
semester_id integer not null,
primary key (course_id, lecturer_id, semester_id)
);

create table lecturer_leave (
lecturer_id integer not null,
start_date date not null,
end_date date not null,
status varchar(45) not null,
primary key (lecturer_id, start_date)
);

create table lecturer_user (
lecturer_id integer not null,
username varchar(45) not null,
password varchar(255) not null,
primary key (username)
);

create table semester (
semester_id integer not null auto_increment,
semester_label varchar(45) not null,
start_date date not null,
end_date date not null,
primary key (semester_id)
);

create table student (
student_id integer not null auto_increment,
first_name varchar(45) not null,
last_name varchar(45) not null,
middle_name varchar(45),
birth_date date,
gender varchar(6) not null,
email varchar(45) not null,
mobile varchar(45) not null,
level varchar(45) not null,
status varchar(45) not null,
semester_id integer not null,
gpa float,
degree_id integer not null,
address varchar(255),
primary key (student_id)
);

create table student_course (
course_id integer not null,
semester_id integer not null,
student_id integer not null,
score float,
status varchar(8),
primary key (course_id, semester_id, student_id)
);

create table student_user (
student_id integer not null,
username varchar(45) not null,
password varchar(255) not null,
primary key (username)
);

create table password_reset_token_student(
id integer not null auto_increment,
token varchar(255) not null,
expiry_date datetime,
student_username varchar(45) not null,
primary key (id)
);

create table password_reset_token_lecturer(
id integer not null auto_increment,
token varchar(255) not null,
expiry_date datetime,
lecturer_username varchar(45) not null,
primary key (id)
);

create table password_reset_token_admin(
id integer not null auto_increment,
token varchar(255) not null,
expiry_date datetime,
admin_username varchar(45) not null,
primary key (id)
);


alter table admin_user add constraint FK6riv77k0nalyptrxhp08k4aw8 foreign key (admin_id) references admin (admin_id);

alter table course add constraint FKi1btm7ma8n3gaj6afdno300wm foreign key (department_id) references department (department_id);

alter table lecturer add constraint FKlhb1ml4s9e5g7t1mx3cxvq3nk foreign key (department_id) references department (department_id);

alter table lecturer_course add constraint FKd4l6ibicfrfoab6llbsr3ixl9 foreign key (course_id) references course (course_id);

alter table lecturer_course add constraint FKg57h4hvlpppor3ogw9rgxeb9m foreign key (lecturer_id) references lecturer (lecturer_id);

alter table lecturer_course add constraint FKf66q3oe6vp69avko192etfv75 foreign key (semester_id) references semester (semester_id);

alter table lecturer_leave add constraint FK6ffyhdxb466ahwlk1er5tshtx foreign key (lecturer_id) references lecturer (lecturer_id);

alter table lecturer_user add constraint FK8nrnd8hfuyvlyq8xrewyyeqmj foreign key (lecturer_id) references lecturer (lecturer_id);

alter table student add constraint FK6a999b6blihn84apfk1sacgtv foreign key (semester_id) references semester (semester_id);

alter table student add constraint FK6a999b6blihn84apfk1saiuqs foreign key (degree_id) references degree (degree_id);

alter table student_course add constraint FKejrkh4gv8iqgmspsanaji90ws foreign key (course_id) references course (course_id);

alter table student_course add constraint FKsy9amf8ludquvxewx1ojyfru9 foreign key (semester_id) references semester (semester_id);

alter table student_course add constraint FKq7yw2wg9wlt2cnj480hcdn6dq foreign key (student_id) references student (student_id);

alter table student_user add constraint FKjeq26fngvtwig8bq45x3ix1wy foreign key (student_id) references student (student_id);

alter table password_reset_token_student add constraint FKjeq26fngvuahg8bq45y3ix1iwe foreign key (student_username) references student_user(username);

alter table password_reset_token_lecturer add constraint FKjeq26aghodjw8bq45y3ix1iwe foreign key (lecturer_username) references lecturer_user(username);

alter table password_reset_token_admin add constraint FKjeq26pwubncxe8bq45y3ix1iwe foreign key (admin_username) references admin_user(username);