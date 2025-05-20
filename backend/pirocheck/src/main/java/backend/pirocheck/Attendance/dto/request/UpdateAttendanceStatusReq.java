package backend.pirocheck.Attendance.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "출석 상태 수정 요청")
public class UpdateAttendanceStatusReq {
    @Schema(description = "출석 기록 ID", example = "1")
    private Long attendanceId;
    
    @Schema(description = "변경할 출석 상태", example = "true")
    private boolean status;
} 