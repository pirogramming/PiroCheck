package backend.pirocheck.User.exception;

import backend.pirocheck.Attendance.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // InvalidLoginException (로그인 실패)
    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidLoginException(InvalidLoginException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) // 401 상태 코드
                .body(ApiResponse.error(e.getMessage())); // 에러 메시지 전달
    }

    // RuntimeException (유저관리 상세페이지)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST) // 404
                .body(ApiResponse.error(ex.getMessage()));
    }
}
