insert into semester(semester_label) values ('[2017-2018] Semester 1'), ('[2017-2018] Semester 2'), ('[2018-2019] Semester 1'),
('[2018-2019] Semester 2'), ('[2019-2020] Semester 1'), ('[2019-2020] Semester 2');

insert into degree(degree_label) values('Graduate Diploma in System Analysis'), ('Master of Technology in Software Engineering');

insert into student(first_name, last_name, middle_name, birth_date, gender, email, mobile, level, status, semester_id,gpa, degree_id, address)
values('Chi', 'Phung', 'Khanh', '1996-08-25', 'Female', 'khanhchi2596@gmail.com', '98912598','Year 1', 'Enrolling', 5,0.0, 1, ''),
('Ying', 'Feng', 'Li', '1995-09-25', 'Female', 'fengliying@gmail.com', '87654321', 'Year 1', 'Enrolling', 5, 0.0,1, ''),
('Bo', 'Wang', 'Yi', '1997-04-23', 'Male', 'wangyibo@gmail.com', '86083571', 'Year 1','Enrolling', 5,0.0,1, ''),
('Zhan', 'Xiao', '', '1991-08-11', 'FMale', 'xiaozhan@gmail.com', '71672878','Year 1' , 'Enrolling', 5,0.0,1, ''),
('Fan', 'Wu', 'Yi', '1992-05-21', 'Male', 'kriswu@gmail.com', '92376743', 'Year 1', 'Enrolling', 5,0.0,1, ''),
('Hun', 'Oh', 'Se', '1990-07-07', 'Male', 'ohsehun@gmail.com', '81387481','Year 1', 'Enrolling', 5, 0.0,1, ''),
('Kyo', 'Song', 'Hye', '1988-02-26', 'Female', 'songhyekyo@gmail.com', '81368434','Year 1', 'Enrolling', 5,0.0,1, '');


insert into department(department_name) values('Institute of System Science'), ('School of computing'), ('Business school'), ('Faculty of Science');

insert into course(course_code, course_name, course_unit, department_id) values
('SA4101', 'Software Analysis and Design', 6, 1),
('SA4102', 'Enterprise Solution Design', 8, 1),
('SA4103', 'Software Engineering', 4, 1),
('SA4104', 'Mobile Application Development', 6, 1),
('SA4105', 'Web Application Development', 8, 1);

insert into student_course(course_id, semester_id, student_id) values
(1, 5, 1), (1, 5, 2), (1, 5, 3), (1, 5, 4), (1, 5, 5), (1, 5, 6), (1, 5, 7),
(2, 5, 1), (2, 5, 2), (2, 5, 3), (2, 5, 4), (2, 5, 5), (2, 5, 6), (2, 5, 7),
(3, 5, 1), (3, 5, 2), (3, 5, 3), (3, 5, 4), (3, 5, 5), (3, 5, 6), (3, 5, 7),
(4, 5, 1), (4, 5, 2), (4, 5, 3), (4, 5, 4), (4, 5, 5), (4, 5, 6), (4, 5, 7),
(5, 5, 1), (5, 5, 2), (5, 5, 3), (5, 5, 4), (5, 5, 5), (5, 5, 6), (5, 5, 7);

insert into lecturer(first_name, middle_name, last_name, gender, birth_date, email, mobile, department_id)
values('Esther', '', 'Tan', 'Female', '1982-07-12', 'esthertan@nus.edu.sg', '89217764', 1),
('Kwan', 'Yuen', 'Chia', 'Male', '1962-07-12', 'chiayuenkwan@nus.edu.sg', '89838764', 1),
('Wah', 'Cher', 'Tan', 'Male', '1982-03-12', 'tancherwah@nus.edu.sg', '73678489', 1),
('Tin', 'Tri', 'Nguyen', 'Male', '1988-06-19', 'nguyentritin@nus.edu.sg', '93277643', 1),
('Kee', 'Boon', 'Lee', 'Male', '1972-04-16', 'leeboonkee@nus.edu.sg', '87485376', 1),
('Fecilitas', '', 'Seah', 'Fale', '1972-04-23', 'felicitas@nus.edu.sg', '32766747', 1);

insert into lecturer_course(course_id, lecturer_id, semester_id) values
(1, 1, 5), (1, 2, 5), (2, 2, 5), (2, 3, 5), (2, 4, 5), (3, 5, 5), (3, 6, 5), (4, 2, 5), (4, 3, 5), (5, 3, 5), (5, 4, 5);

insert into lecturer_leave(lecturer_id, start_date, end_date, status) values
(1, '2019-12-23', '2020-01-02', 'approved'),
(2, '2020-01-12', '2020-01-29', 'pending');

insert into admin(first_name, middle_name, last_name, gender, birth_date, email, mobile) values
('Lee', 'Chia', 'Tay', 'Female', '1990-12-01', 'taychialee@nus.edu.sg', '98387643'),
('Megan', '', 'Wang', 'Female', '1986-11-12', 'meganwang@nus.edu.sg', '98737646');

insert into admin_user(admin_id, username, password) values
(1, 'taychialee', '12345678'), (2, 'meganwang', '12345678');


insert into lecturer_user(lecturer_id, username, password) values
(1, 'esthertan', '12345678'), (2, 'chiayuenkwan', '12345678'),
(3, 'tancherwah', '12345678'), (4, 'nguyentritin', '12345678'),
(5, 'leeboonkee', '12345678'), (6, 'felicitas', '12345678');

insert into student_user(student_id, username, password) values
(1, 'phungkhanhchi', '12345678'), (2, 'fengliying', '12345678'),
(3, 'wangyibo', '12345678'), (4, 'xiaozhan', '12345678'),
(5, 'wuyifan', '12345678'), (6, 'ohsehun', '12345678'), (7, 'songhyekyo', '12345678');



