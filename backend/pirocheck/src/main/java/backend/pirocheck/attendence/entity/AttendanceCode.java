package backend.pirocheck.attendence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "attendance_code")
@Getter @Setter
public class AttendanceCode {
    @Id
    private Long id;

    private String code;
}
