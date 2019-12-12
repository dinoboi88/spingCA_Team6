package SpringCA.stuRepo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import SpringCA.entities.StudentUser;
import SpringCA.entities.CompositeId.UserId;


public interface StudentUserRepo extends JpaRepository<StudentUser, UserId> {

	StudentUser findStudentUserByUserId_Username(String username);
	List<StudentUser> findByUserIdUsername(String username);
	
	StudentUser findStudentUserByUserId_User(int user);
}
