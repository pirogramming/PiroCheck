package backend.pirocheck.Attendance.dto.response;

import backend.pirocheck.Attendance.entity.AttendanceCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCodeResponse {
    private String code;
    private LocalDate date;
    private int order;
    private boolean isExpired;
    
    public static AttendanceCodeResponse from(AttendanceCode attendanceCode) {
        return AttendanceCodeResponse.builder()
                .code(attendanceCode.getCode())
                .date(attendanceCode.getDate())
                .order(attendanceCode.getOrder())
                .isExpired(attendanceCode.isExpired())
                .build();
    }
} 