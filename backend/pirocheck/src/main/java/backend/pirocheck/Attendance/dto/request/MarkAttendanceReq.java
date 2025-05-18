package backend.pirocheck.Attendance.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "출석 체크 요청")
public class MarkAttendanceReq {
    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
    
    @Schema(description = "출석 코드", example = "1234")
    private String code;
}
