package SpringCA.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import SpringCA.entities.Lecturer;
import SpringCA.entities.LecturerLeave;
import SpringCA.entities.CompositeId.LecturerLeaveId;

public interface LecturerLeaveRepository extends JpaRepository<LecturerLeave, LecturerLeaveId> {
	public Page<LecturerLeave> findAllLecturerLeaveByLecturerByLeave_LecturerId(int lecturerId,Pageable pageable);
	
	@Query(value="SELECT * FROM lecturer_leave t WHERE t.lecturer_id= :lecturerId AND t.start_date = :startDate",nativeQuery=true)
	LecturerLeave findLecturerLeaveByLecturerIdAndStartDate(@Param("lecturerId") int lecturerId,@Param("startDate") String startDate);

	@Query(value="SELECT * FROM lecturer_leave t WHERE t.lecturer_id= :lecturerId",nativeQuery=true)
	List<LecturerLeave> findAllLecturerLeaveByLecturerId(@Param("lecturerId") int lecturerId);
}
