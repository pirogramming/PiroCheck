package backend.pirocheck.User.exception;

import backend.pirocheck.Attendance.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidLoginException(InvalidLoginException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) // 401 상태 코드
                .body(ApiResponse.error(e.getMessage())); // 에러 메시지 전달
    }
}
