insert into semester(semester_label) values ('[2017-2018] Semester 1'), ('[2017-2018] Semester 2'), ('[2018-2019] Semester 1'),
('[2018-2019] Semester 2'), ('[2019-2020] Semester 1'), ('[2019-2020] Semester 2');

insert into degree(degree_label) values('Graduate Diploma in System Analysis'), ('Master of Technology in Software Engineering');

insert into student(first_name, last_name, middle_name, birth_date, gender, email, mobile, level, status, semester_id, degree_id, address, GPA)
values('Csshi', 'Phussng', 'Khssanh', '1996-08-25', 'Female', 'khanssshchi2596@gmail.com', '98912598','Year 1', 'Enrolling', 5, 1, '', 0.0),
('Yinssg', 'Fenssg', 'Lssi', '1995-09-25', 'Female', 'fenglssiying@gmail.com', '87654321', 'Year 1', 'Enrolling', 5, 1, '', 0.0),
('Boss', 'Wanssg', 'Yssi', '1997-04-23', 'Male', 'wangssyibo@gmail.com', '86083571', 'Year 1', 'Enrolling', 5, 1, '', 0.0),
('Zhssn', 'Xiasso', '', '1991-08-11', 'Male', 'xiaosszhan@gmail.com', '71672878', 'Year 1', 'Enrolling', 5, 1, '', 0.0),
('Fassn', 'Wssu', 'Yssi', '1992-05-21', 'Male', 'krsiswu@gmail.com', '92376743', 'Year 1', 'Enrolling', 5, 1, '', 0.0),
('Hussn', 'Ossh', 'Ssse', '1990-07-07', 'Male', 'ohsesshun@gmail.com', '81387481','Year 1', 'Enrolling', 5, 1, '', 0.0),
('Kssyo', 'Sossng', 'Hssye', '1988-02-26', 'Female', 'songhssyekyo@gmail.com', '81368434','Year 1', 'Enrolling', 5, 1, '', 0.0);


insert into department(department_name) values('Institute of System Science'), ('School of computing'), ('Business school'), ('Faculty of Science');

insert into course(course_code, course_name, course_unit, department_id) values
('SA4101', 'Software Analysis and Design', 6, 1),
('SA4102', 'Enterprise Solution Design', 8, 1),
('SA4103', 'Software Engineering', 4, 1),
('SA4104', 'Mobile Application Development', 6, 1),
('SA4105', 'Web Application Development', 8, 1);

insert into student_course(course_id, semester_id, student_id, score, status) values
(1, 5, 1, 0.0, 'Approved'), (1, 5, 2, 0.0, 'Approved'), (1, 5, 3, 0.0, 'Approved'), (1, 5, 4, 0.0, 'Approved'),
(1, 5, 5, 0.0, 'Approved'), (1, 5, 6, 0.0, 'Approved'), (1, 5, 7, 0.0, 'Approved'),
(2, 5, 1, 0.0, 'Approved'), (2, 5, 2, 0.0, 'Approved'), (2, 5, 3, 0.0, 'Approved'), (2, 5, 4, 0.0, 'Approved'),
(2, 5, 5, 0.0, 'Approved'), (2, 5, 6, 0.0, 'Approved'), (2, 5, 7, 0.0, 'Approved'),
(3, 5, 1, 0.0, 'Approved'), (3, 5, 2, 0.0, 'Approved'), (3, 5, 3, 0.0, 'Approved'), (3, 5, 4, 0.0, 'Approved'),
(3, 5, 5, 0.0, 'Approved'), (3, 5, 6, 0.0, 'Approved'), (3, 5, 7, 0.0, 'Approved'),
(4, 5, 1, 0.0, 'Approved'), (4, 5, 2, 0.0, 'Approved'), (4, 5, 3, 0.0, 'Approved'), (4, 5, 4, 0.0, 'Approved'),
(4, 5, 5, 0.0, 'Approved'), (4, 5, 6, 0.0, 'Approved'), (4, 5, 7, 0.0, 'Approved'),
(5, 5, 1, 0.0, 'Approved'), (5, 5, 2, 0.0, 'Approved'), (5, 5, 3, 0.0, 'Approved'), (5, 5, 4, 0.0, 'Approved'),
(5, 5, 5, 0.0, 'Approved'), (5, 5, 6, 0.0, 'Approved'), (5, 5, 7, 0.0, 'Approved');

insert into lecturer(first_name, middle_name, last_name, gender, birth_date, email, mobile, department_id)
values('Esthers', '', 'Tans', 'Female', '1982-07-12', 'estherstan@nussss.essdu.sg', '89217764', 1),
('Kwans', 'Yuens', 'Chias', 'Male', '1962-07-12', 'chiasyuenkwan@nusss.edu.sg', '89838764', 1),
('Wahs', 'Chers', 'Tanss', 'Male', '1982-03-12', 'tanchsssserwah@nus.essdu.sg', '73678489', 1),
('Tinss', 'Trssi', 'Nguyssen', 'Male', '1988-06-19', 'nguyssssentritin@nusss.edu.sg', '93277643', 1),
('Kesse', 'Boon', 'Lesse', 'Male', '1972-04-16', 'leeboossssnkee@nus.essdu.sg', '87485376', 1),
('Fecilisstas', '', 'Sessah', 'Fale', '1972-04-23', 'felicsssitas@nsssus.edssu.sg', '32766747', 1);

insert into lecturer_course(course_id, lecturer_id, semester_id) values
(1, 1, 5), (1, 2, 5), (2, 2, 5), (2, 3, 5), (2, 4, 5), (3, 5, 5), (3, 6, 5), (4, 2, 5), (4, 3, 5), (5, 3, 5), (5, 4, 5);

insert into lecturer_leave(lecturer_id, start_date, end_date, status) values
(1, '2019-12-23', '2020-01-02', 'approved'),
(2, '2020-01-12', '2020-01-29', 'pending');

insert into admin(first_name, middle_name, last_name, gender, birth_date, email, mobile) values
('Lesse', 'Chissa', 'Tays', 'Female', '1990-12-01', 'taychissalee@nuss.edsu.sg', '98387643'),
('Megssan', '', 'Wassng', 'Female', '1986-11-12', 'megansswang@nuss.esdu.sg', '98737646');

insert into admin_user(admin_id, username, password) values
(1, 'taychsialssee', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(2, 'megassnsswang', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS');


insert into lecturer_user(lecturer_id, username, password) values
(1, 'esthesssrtan', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(2, 'chiasssyuenkwan', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(3, 'tancsssherwah', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(4, 'nguyessntsssritin', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(5, 'leebosssonkee', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(6, 'felicissstas', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS');

insert into student_user(student_id, username, password) values
(1, 'phunssgkhsssanhchi', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(2, 'fengsslisssying', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(3, 'wanssgyibsso', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(4, 'xiaossszhan', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(5, 'wuyissfsssan', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(6, 'ohsehssun', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(7, 'songhsssyekyo', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS');



