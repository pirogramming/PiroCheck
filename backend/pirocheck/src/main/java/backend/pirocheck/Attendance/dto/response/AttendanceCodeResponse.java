package backend.pirocheck.Attendance.dto.response;

import backend.pirocheck.Attendance.entity.AttendanceCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "출석 코드 응답")
public class AttendanceCodeResponse {
    @Schema(description = "출석 코드", example = "1234")
    private String code;
    
    @Schema(description = "출석 날짜", example = "2025-06-24")
    private LocalDate date;
    
    @Schema(description = "출석 차시 (1, 2, 3)", example = "1")
    private int order;
    
    @Schema(description = "만료 여부", example = "false")
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