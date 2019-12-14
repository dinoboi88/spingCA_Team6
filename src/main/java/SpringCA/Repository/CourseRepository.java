package SpringCA.Repository;

import SpringCA.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findByCourseId(int id);

    @Query(value = "select * from course where course_id not in (select c.course_id from course c "+
            "inner join student_course sc on c.course_id = sc.course_id inner join student s on "+
            "sc.student_id = s.student_id where s.student_id = :studentId and sc.status in ('Approved', 'Pending'))", nativeQuery = true)
    Iterable<Course> getNotEnrolledCourse(@Param("studentId") int studentId);
}

