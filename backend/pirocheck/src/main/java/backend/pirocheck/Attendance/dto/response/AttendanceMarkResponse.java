package backend.pirocheck.Attendance.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "출석 체크 응답")
public class AttendanceMarkResponse {
    @Schema(description = "응답 메시지", example = "출석이 성공적으로 처리되었습니다")
    private String message;
    
    @Schema(description = "상태 코드 (SUCCESS, ALREADY_MARKED, INVALID_CODE, NO_ACTIVE_SESSION, CODE_EXPIRED, ERROR)", example = "SUCCESS")
    private String statusCode;
    
    // 출석 성공
    public static AttendanceMarkResponse success() {
        return AttendanceMarkResponse.builder()
                .statusCode("SUCCESS")
                .message("출석이 성공적으로 처리되었습니다")
                .build();
    }
    
    // 이미 출석 완료
    public static AttendanceMarkResponse alreadyMarked() {
        return AttendanceMarkResponse.builder()
                .statusCode("ALREADY_MARKED")
                .message("이미 출석처리가 완료되었습니다")
                .build();
    }
    
    // 출석체크 진행중 아님
    public static AttendanceMarkResponse noActiveSession() {
        return AttendanceMarkResponse.builder()
                .statusCode("NO_ACTIVE_SESSION")
                .message("출석 코드가 존재하지 않습니다. 현재 출석 체크가 진행중이 아닙니다")
                .build();
    }
    
    // 잘못된 출석 코드 입력
    public static AttendanceMarkResponse invalidCode() {
        return AttendanceMarkResponse.builder()
                .statusCode("INVALID_CODE")
                .message("잘못된 출석 코드입니다. 다시 확인해주세요")
                .build();
    }
    
    // 출석 코드 만료
    public static AttendanceMarkResponse codeExpired() {
        return AttendanceMarkResponse.builder()
                .statusCode("CODE_EXPIRED")
                .message("출석 코드가 만료되었습니다")
                .build();
    }
    
    // 기타 오류
    public static AttendanceMarkResponse error(String message) {
        return AttendanceMarkResponse.builder()
                .statusCode("ERROR")
                .message(message)
                .build();
    }
}
