package SpringCA.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SpringCA.entities.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer,Integer>{
	
	@Query(value="select * from lecturer where lecturer_id=?1",nativeQuery=true)
	Lecturer findByLectId(int id);

}
