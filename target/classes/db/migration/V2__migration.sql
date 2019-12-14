insert into semester(semester_label, start_date, end_date) values
('[2017-2018] Semester 1', '2019-09-04', '2017-12-20'),
('[2017-2018] Semester 2', '2018-01-15', '2018-05-20'),
('[2018-2019] Semester 1', '2018-09-04', '2018-12-20'),
('[2018-2019] Semester 2', '2019-01-15', '2019-05-20'),
('[2019-2020] Semester 1', '2019-09-04', '2019-12-20'),
('[2019-2020] Semester 2', '2020-01-15', '2020-05-20');

insert into degree(degree_label) values('Graduate Diploma in System Analysis'), ('Master of Technology in Software Engineering');

insert into student(first_name, last_name, middle_name, birth_date, gender, email, mobile, level, status, semester_id, degree_id, address, GPA)
values('Chi', 'Phung', 'Khanh', '1996-08-25', 'Female', 'khanhchi2596@gmail.com', '98912598','Year 1', 'Enrolling', 5, 1, '', 0.0),
('Ying', 'Feng', 'Li', '1995-09-25', 'Female', 'fengliying@gmail.com', '87654321', 'Year 1', 'Enrolling', 5, 1, '', 0.0),
('Bo', 'Wang', 'Yi', '1997-04-23', 'Male', 'wangyibo@gmail.com', '86083571', 'Year 1', 'Enrolling', 5, 1, '', 0.0),
('Zhan', 'Xiao', '', '1991-08-11', 'Male', 'xiaozhan@gmail.com', '71672878', 'Year 1', 'Enrolling', 5, 1, '', 0.0),
('Fan', 'Wu', 'Yi', '1992-05-21', 'Male', 'kriswu@gmail.com', '92376743', 'Year 1', 'Enrolling', 5, 1, '', 0.0),
('Hun', 'Oh', 'Se', '1990-07-07', 'Male', 'ohsehun@gmail.com', '81387481','Year 1', 'Enrolling', 5, 1, '', 0.0),
('Kyo', 'Song', 'Hye', '1988-02-26', 'Female', 'songhyekyo@gmail.com', '81368434','Year 1', 'Enrolling', 5, 1, '', 0.0);


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
(2, 5, 1, 0.0, 'Approved'), (2, 5, 2, 0.0, 'Pending'), (2, 5, 3, 0.0, 'Approved'), (2, 5, 4, 0.0, 'Approved'),
(2, 5, 5, 0.0, 'Approved'), (2, 5, 6, 0.0, 'Approved'), (2, 5, 7, 0.0, 'Approved'),
(3, 5, 1, 0.0, 'Approved'), (3, 5, 2, 0.0, 'Approved'), (3, 5, 3, 0.0, 'Approved'), (3, 5, 4, 0.0, 'Approved'),
(3, 5, 5, 0.0, 'Approved'), (3, 5, 6, 0.0, 'Approved'), (3, 5, 7, 0.0, 'Approved'),
(4, 5, 1, 0.0, 'Approved'), (4, 5, 2, 0.0, 'Approved'), (4, 5, 3, 0.0, 'Approved'), (4, 5, 4, 0.0, 'Approved'),
(4, 5, 5, 0.0, 'Approved'), (4, 5, 6, 0.0, 'Rejected'), (4, 5, 7, 0.0, 'Approved'),
(5, 5, 1, 0.0, 'Approved'), (5, 5, 2, 0.0, 'Approved'), (5, 5, 3, 0.0, 'Approved'), (5, 5, 4, 0.0, 'Approved'),
(5, 5, 5, 0.0, 'Rejected'), (5, 5, 6, 0.0, 'Approved'), (5, 5, 7, 0.0, 'Approved'),
(1, 6, 1, 0.0, 'Pending'), (1, 6, 2, 0.0, 'Pending'), (1, 6, 3, 0.0, 'Pending'), (1, 6, 4, 0.0, 'Pending'),
(1, 6, 5, 0.0, 'Pending'), (1, 6, 6, 0.0, 'Pending'), (1, 6, 7, 0.0, 'Pending'),
(2, 6, 1, 0.0, 'Pending'), (2, 6, 2, 0.0, 'Pending'), (2, 6, 3, 0.0, 'Pending'), (2, 6, 4, 0.0, 'Pending'),
(2, 6, 5, 0.0, 'Pending'), (2, 6, 6, 0.0, 'Pending'), (2, 6, 7, 0.0, 'Pending'),
(3, 6, 1, 0.0, 'Pending'), (3, 6, 2, 0.0, 'Pending'), (3, 6, 3, 0.0, 'Pending'), (3, 6, 4, 0.0, 'Pending'),
(3, 6, 5, 0.0, 'Pending'), (3, 6, 6, 0.0, 'Pending'), (3, 6, 7, 0.0, 'Pending');

insert into lecturer(first_name, middle_name, last_name, gender, birth_date, email, mobile, department_id)
values('Esther', '', 'Tan', 'Female', '1982-07-12', 'esthertan@gmail.com', '89217764', 1),
('Kwan', 'Yuen', 'Chia', 'Male', '1962-07-12', 'chiayuenkwan@gmail.com', '89838764', 1),
('Wah', 'Cher', 'Tan', 'Male', '1982-03-12', 'tancherwah@gmail.com', '73678489', 1),
('Tin', 'Tri', 'Nguyen', 'Male', '1988-06-19', 'nguyentritin@gmail.com', '93277643', 1),
('Kee', 'Boon', 'Lee', 'Male', '1972-04-16', 'leeboonkee@gmail.com', '87485376', 1),
('Fecilitas', '', 'Seah', 'Fale', '1972-04-23', 'felicitas@gmail.com', '32766747', 1);

insert into lecturer_course(course_id, lecturer_id, semester_id) values
(1, 1, 5), (1, 2, 5), (2, 2, 5), (2, 3, 5), (2, 4, 5), (3, 5, 5), (3, 6, 5), (4, 2, 5), (4, 3, 5), (5, 3, 5), (5, 4, 5),
(1, 2, 6), (1, 4, 6), (2, 3, 6), (2, 4, 6), (3, 1, 6), (3, 5, 6);

insert into lecturer_leave(lecturer_id, start_date, end_date, status) values
(1, '2019-12-23', '2020-01-02', 'approved'),
(2, '2020-01-12', '2020-01-29', 'pending');

insert into admin(first_name, middle_name, last_name, gender, birth_date, email, mobile) values
('Lee', 'Chia', 'Tay', 'Female', '1990-12-01', 'taychialee@gmail.com', '98387643'),
('Megan', '', 'Wang', 'Female', '1986-11-12', 'meganwang@gmail.com', '98737646');

insert into admin_user(admin_id, username, password) values
(1, 'A0000001', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(2, 'A0000002', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS');


insert into lecturer_user(lecturer_id, username, password) values
(1, 'L0000001', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(2, 'L0000002', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(3, 'L0000003', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(4, 'L0000004', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(5, 'L0000005', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(6, 'L0000006', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS');

insert into student_user(student_id, username, password) values
(1, 'S0000001', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(2, 'S0000002', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(3, 'S0000003', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(4, 'S0000004', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(5, 'S0000005', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(6, 'S0000006', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS'),
(7, 'S0000007', '$2y$12$MXISci0nld0o6UBqSef0wOg1j137w7aMpI0QSdKry5ybiWneqy9aS');



