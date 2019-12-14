package SpringCA.entities.CompositeId;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class LecturerLeaveId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private int lecturerByLeave;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate startDate;

    public LecturerLeaveId(){}

    public LecturerLeaveId(int lecturerByLeave, LocalDate startDate) {
        this.lecturerByLeave = lecturerByLeave;
        this.startDate = startDate;
    }

    public int getLecturerByLeave() {
        return lecturerByLeave;
    }

    public void setLecturerByLeave(int lecturerByLeave) {
        this.lecturerByLeave = lecturerByLeave;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result+ lecturerByLeave;
        result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());;
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
        LecturerLeaveId other = (LecturerLeaveId) obj;
        if (lecturerByLeave != other.lecturerByLeave)
            return false;
        if (startDate == null) {
            if (other.startDate != null)
                return false;
        } else if (!startDate.equals(other.startDate))
            return false;
        return true;
    }
}
