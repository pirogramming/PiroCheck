package backend.pirocheck.Attendance.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "출석 차시별 상태")
public class AttendanceSlotRes {
    @Schema(description = "출석 차시 (1, 2, 3)", example = "1")
    private int order;
    
    @Schema(description = "출석 여부", example = "true")
    private boolean status;
}
