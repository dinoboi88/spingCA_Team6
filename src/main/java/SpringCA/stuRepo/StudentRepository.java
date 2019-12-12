package SpringCA.stuRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import SpringCA.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>{

	Student findById(int student_id);
}
