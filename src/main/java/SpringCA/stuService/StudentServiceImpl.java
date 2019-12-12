package SpringCA.stuService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import SpringCA.entities.Student;
import SpringCA.stuRepo.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentRepository stuRepo;
	
	@Override
	public Student updateStudent(int student_id, Student student) {
		Student OriginalStu = stuRepo.findById(student_id);
	if (OriginalStu != null) {
	OriginalStu.setEmail(student.getEmail());
	OriginalStu.setMobile(student.getMobile());
	OriginalStu.setAddress(student.getAddress());
	stuRepo.save(OriginalStu);
	}
	return OriginalStu;
	}

}
