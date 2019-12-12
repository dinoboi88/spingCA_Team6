package SpringCA.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringCA.entities.Student;

public interface StudentRepository extends JpaRepository<Student,Integer> {


}
