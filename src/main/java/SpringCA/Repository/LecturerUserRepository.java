package SpringCA.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import SpringCA.entities.LecturerUser;

public interface LecturerUserRepository extends JpaRepository<LecturerUser,Integer> {
	
	@Query(value="select * from lecturer_user where username=?1 and password=?2",nativeQuery=true)
	LecturerUser findLecturer(String username,String password);

}
