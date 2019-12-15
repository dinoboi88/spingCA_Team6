package SpringCA.Repository;

import SpringCA.entities.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LecturerRepository extends JpaRepository<Lecturer, Integer> {
	Lecturer findByLecturerId(int lecturerId);
}
