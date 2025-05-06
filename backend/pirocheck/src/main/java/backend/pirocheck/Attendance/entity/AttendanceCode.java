package backend.pirocheck.Attendance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "attendance_code")
@Getter @Setter
public class AttendanceCode {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private LocalDate date;

    @Column(name = "order_number")
    private int order;

    private boolean isExpired = false;
}
