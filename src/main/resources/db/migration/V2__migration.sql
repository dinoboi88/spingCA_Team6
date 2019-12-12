insert into semester(semester_label) values ('[2017-2018] Semester 1'), ('[2017-2018] Semester 2'), ('[2018-2019] Semester 1'),
('[2018-2019] Semester 2'), ('[2019-2020] Semester 1'), ('[2019-2020] Semester 2');

insert into degree(degree_label) values('Graduate Diploma in System Analysis'), ('Master of Technology in Software Engineering');

insert into student(first_name, last_name, middle_name, birth_date, gender, email, mobile, level, status, semester_id, degree_id, address)
values('Pei', 'Pei', 'Tham', '1996-08-25', 'Female', 'Thampeipei@gmail.com', '9999991','Year 1', 'Enrolling', 5, 1, ''),
('Bing', 'Bing', 'Li', '1995-09-25', 'Female', 'libingbing@gmail.com', '888888881', 'Year 1', 'Enrolling', 5, 1, ''),
('Bo', 'Wang', 'Yi', '1997-04-23', 'Male', 'wangyibo@gmail.com', 'Year 1', '72738111','Enrolling', 5, 1, ''),
('Zhan', 'Xiao', '', '1991-08-11', 'FMale', 'xiaozhan@gmail.com', 'Year 1', '73283948', 'Enrolling', 5, 1, '' ),
('Fan', 'Wu', 'Yi', '1992-05-21', 'Male', 'kriswu@gmail.com', '92376743', 'Year 1', 'Enrolling', 5, 1, '' ),
('Hun', 'Oh', 'Se', '1990-07-07', 'Male', 'ohsehun@gmail.com', '81387481','Year 1', 'Enrolling', 5, 1, '' ),
('Kyo', 'Song', 'Hye', '1988-02-26', 'Female', 'songhyekyo@gmail.com', '81368434','Year 1', 'Enrolling', 5, 1, '' );


insert into department(department_name) values('Institute of System Science'), ('School of computing'), ('Business school'), ('Faculty of Science');

insert into course(course_code, course_name, course_unit, department_id) values
('SB4101', 'Software and some form Analysis', 6, 1),
('SB4102', 'Enterprise Solution for the horde', 8, 1),
('SB4103', 'Software and tools Engineering', 4, 1),
('SB4104', 'Mobile Application and softwares', 6, 1),
('SB4105', 'Web Application Development for fun', 8, 1);

insert into student_course(course_id, semester_id, student_id) values
(1, 5, 1), (1, 5, 2), (1, 5, 3), (1, 5, 4), (1, 5, 5), (1, 5, 6), (1, 5, 7),
(2, 5, 1), (2, 5, 2), (2, 5, 3), (2, 5, 4), (2, 5, 5), (2, 5, 6), (2, 5, 7),
(3, 5, 1), (3, 5, 2), (3, 5, 3), (3, 5, 4), (3, 5, 5), (3, 5, 6), (3, 5, 7),
(4, 5, 1), (4, 5, 2), (4, 5, 3), (4, 5, 4), (4, 5, 5), (4, 5, 6), (4, 5, 7),
(5, 5, 1), (5, 5, 2), (5, 5, 3), (5, 5, 4), (5, 5, 5), (5, 5, 6), (5, 5, 7);

insert into lecturer(first_name, middle_name, last_name, gender, birth_date, email, mobile, department_id)
values('Mimi', '', 'Tan', 'Female', '1982-07-12', 'mimitan@nns.edu.sg', '89217764', 1),
('Kwok', 'Feng', 'Queh', 'Male', '1962-07-12', 'quehkwokfeng@nns.edu.sg', '89838764', 1),
('Eng', 'Chua', 'Sim', 'Male', '1982-03-12', 'simengchua@nns.edu.sg', '73678489', 1),
('Rin', 'Pin', 'Kim', 'Male', '1988-06-19', 'kimrinpin@nns.edu.sg', '93277643', 1),
('Xiao', 'Long', 'Lee', 'Male', '1972-04-16', 'leexiaolong@nns.edu.sg', '87485376', 1),
('Cindy', '', 'Ang', 'Female', '1972-04-23', 'cindyang@nns.edu.sg', '32766747', 1);

insert into lecturer_course(course_id, lecturer_id, semester_id) values
(1, 1, 5), (1, 2, 5), (2, 2, 5), (2, 3, 5), (2, 4, 5), (3, 5, 5), (3, 6, 5), (4, 2, 5), (4, 3, 5), (5, 3, 5), (5, 4, 5);

insert into lecturer_leave(lecturer_id, start_date, end_date, status) values
(1, '2019-12-23', '2020-01-02', 'approved'),
(2, '2020-01-12', '2020-01-29', 'pending');

insert into admin(first_name, middle_name, last_name, gender, birth_date, email, mobile) values
('Teh', 'Ai', 'Min', 'Female', '1990-12-01', 'tehaimin@nss.edu.sg', '98387643'),
('Carrie', '', 'Soh', 'Female', '1986-11-12', 'carriesoh@nss.edu.sg', '98737646');

insert into admin_user(admin_id, username, password) values
(1, 'tehaimin', '12345678'), (2, 'carriesoh', '12345678');


insert into lecturer_user(lecturer_id, username, password) values
(1, 'mimitan', '12345678'), (2, 'quehkwokfeng', '12345678'),
(3, 'simengchua', '12345678'), (4, 'kimrinpin', '12345678'),
(5, 'leexiaolong', '12345678'), (6, 'cindyang', '12345678');

insert into student_user(student_id, username, password) values
(1, 'phungkhanhchi', '12345678'), (2, 'fengliying', '12345678'),
(3, 'wangyibo', '12345678'), (4, 'xiaozhan', '12345678'),
(5, 'wuyifan', '12345678'), (6, 'ohsehun', '12345678'), (7, 'songhyekyo', '12345678');


