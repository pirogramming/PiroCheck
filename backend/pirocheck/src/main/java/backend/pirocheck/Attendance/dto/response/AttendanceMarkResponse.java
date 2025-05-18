package backend.pirocheck.Attendance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceMarkResponse {
    private String statusCode; // SUCCESS, INVALID_CODE, CODE_EXPIRED, ALREADY_MARKED, NO_ACTIVE_SESSION, ERROR
    private String message;

    // 성공 응답
    public static AttendanceMarkResponse success() {
        return AttendanceMarkResponse.builder()
                .statusCode("SUCCESS")
                .message("출석이 완료되었습니다")
                .build();
    }

    // 유효하지 않은 코드
    public static AttendanceMarkResponse invalidCode() {
        return AttendanceMarkResponse.builder()
                .statusCode("INVALID_CODE")
                .message("유효하지 않은 출석 코드입니다")
                .build();
    }

    // 만료된 코드
    public static AttendanceMarkResponse codeExpired() {
        return AttendanceMarkResponse.builder()
                .statusCode("CODE_EXPIRED")
                .message("만료된 출석 코드입니다")
                .build();
    }

    // 이미 출석 완료
    public static AttendanceMarkResponse alreadyMarked() {
        return AttendanceMarkResponse.builder()
                .statusCode("ALREADY_MARKED")
                .message("이미 출석 처리되었습니다")
                .build();
    }

    // 활성화된 출석 세션 없음
    public static AttendanceMarkResponse noActiveSession() {
        return AttendanceMarkResponse.builder()
                .statusCode("NO_ACTIVE_SESSION")
                .message("현재 활성화된 출석 세션이 없습니다")
                .build();
    }

    // 일반 에러
    public static AttendanceMarkResponse error(String message) {
        return AttendanceMarkResponse.builder()
                .statusCode("ERROR")
                .message(message)
                .build();
    }
} 