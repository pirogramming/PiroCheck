package backend.pirocheck.Attendance.dto.response;

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
@Schema(description = "사용자 출석 상태 응답")
public class UserAttendanceStatusRes {
    @Schema(description = "출석 기록 ID", example = "1")
    private Long attendanceId;
    
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    
    @Schema(description = "사용자 이름", example = "홍길동")
    private String username;
    
    @Schema(description = "출석 날짜", example = "2023-10-20")
    private LocalDate date;
    
    @Schema(description = "출석 차수", example = "1")
    private int order;
    
    @Schema(description = "출석 상태", example = "true")
    private boolean status;
} 