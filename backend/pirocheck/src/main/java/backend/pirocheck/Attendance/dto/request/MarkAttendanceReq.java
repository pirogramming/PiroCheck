package backend.pirocheck.Attendance.dto.request;

import lombok.Getter;

@Getter
public class MarkAttendanceReq {
    private Long userId;
    private String code;
}
