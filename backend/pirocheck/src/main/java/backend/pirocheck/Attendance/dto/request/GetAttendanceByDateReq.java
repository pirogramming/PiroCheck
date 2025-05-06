package backend.pirocheck.Attendance.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class GetAttendanceByDateReq {
    private Long userId;
    private LocalDate date;
}
