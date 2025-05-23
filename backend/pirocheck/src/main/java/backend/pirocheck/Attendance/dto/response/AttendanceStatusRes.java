package backend.pirocheck.Attendance.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Schema(description = "사용자 출석 상태")
public class AttendanceStatusRes {
    @Schema(description = "출석 날짜", example = "2025-06-24")
    private LocalDate date;
    
    @Schema(description = "주차", example = "1")
    private int week;
    
    @Schema(description = "출석 차시별 상태 목록")
    private List<AttendanceSlotRes> slots;
}
