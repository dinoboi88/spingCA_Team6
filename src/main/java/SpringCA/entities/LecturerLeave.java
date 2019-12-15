package SpringCA.entities;

import SpringCA.entities.CompositeId.LecturerLeaveId;

import javax.persistence.*;
import javax.validation.constraints.Future;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@IdClass(LecturerLeaveId.class)
public class LecturerLeave {
    @Id
    @ManyToOne
    @JoinColumn(name = "lecturerId")
    private Lecturer lecturerByLeave;

    @Id
    //@Temporal(TemporalType.DATE)
    @Future
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;
    
    //@Temporal(TemporalType.DATE)
    @Future
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate endDate;
    private String status;

    public LecturerLeave(){}

    public LecturerLeave(Lecturer lecturerByLeave, LocalDate startDate, LocalDate endDate, String status) {
        this.lecturerByLeave = lecturerByLeave;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Lecturer getLecturerByLeave() {
        return lecturerByLeave;
    }

    public void setLecturerByLeave(Lecturer lecturerByLeave) {
        this.lecturerByLeave = lecturerByLeave;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((lecturerByLeave == null) ? 0 : lecturerByLeave.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LecturerLeave other = (LecturerLeave) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (lecturerByLeave == null) {
			if (other.lecturerByLeave != null)
				return false;
		} else if (!lecturerByLeave.equals(other.lecturerByLeave))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
}
