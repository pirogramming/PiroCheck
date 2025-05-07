package backend.pirocheck.Attendance.dto.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetAttendanceByUserIdRes {
    private Long userId;
    private LocalDate date;
    private int order;
    private boolean status;
}
