package backend.pirocheck.Attendance.controller;

import backend.pirocheck.Attendance.dto.request.MarkAttendanceReq;
import backend.pirocheck.Attendance.dto.response.ApiResponse;
import backend.pirocheck.Attendance.dto.response.AttendanceMarkResponse;
import backend.pirocheck.Attendance.dto.response.AttendanceSlotRes;
import backend.pirocheck.Attendance.dto.response.AttendanceStatusRes;
import backend.pirocheck.Attendance.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
@Tag(name = "출석관리", description = "학생용 출석 관련 API")
public class AttendanceController {

    private final AttendanceService attendanceService;

    // 특정 유저의 출석 정보
    @Operation(summary = "사용자 출석 정보 조회", description = "특정 사용자의 전체 출석 정보를 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자를 찾을 수 없음")
    })
    @GetMapping("/user")
    public ApiResponse<List<AttendanceStatusRes>> getAttendanceByUserId(
            @Parameter(description = "사용자 ID", required = true)
            @RequestParam Long userId) {
        return ApiResponse.success(attendanceService.findByUserId(userId));
    }
    
    // 특정 유저의 특정 일자 출석 정보
    @Operation(summary = "특정 날짜 출석 정보 조회", description = "특정 사용자의 특정 날짜 출석 정보를 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "사용자 또는 날짜 정보를 찾을 수 없음")
    })
    @GetMapping("/user/date")
    public ApiResponse<List<AttendanceSlotRes>> getAttendanceByUserIdAndDate(
            @Parameter(description = "사용자 ID", required = true)
            @RequestParam Long userId,
            @Parameter(description = "조회할 날짜 (YYYY-MM-DD)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ApiResponse.success(attendanceService.findByUserIdAndDate(userId, date));
    }

    // 출석코드 비교
    @Operation(summary = "출석 체크", description = "출석 코드를 입력하여 출석을 체크합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "출석 성공 또는 이미 출석 완료"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 출석 코드 또는 출석 체크 진행중이 아님")
    })
    @PostMapping("/mark")
    public ApiResponse<AttendanceMarkResponse> markAttendance(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "출석 체크 요청", required = true, 
                content = @Content(schema = @Schema(implementation = MarkAttendanceReq.class)))
            @RequestBody MarkAttendanceReq req) {
        AttendanceMarkResponse response = attendanceService.markAttendance(req.getUserId(), req.getCode());
        
        // statusCode가 SUCCESS 또는 ALREADY_MARKED인 경우 성공으로 처리
        boolean isSuccess = "SUCCESS".equals(response.getStatusCode()) || 
                           "ALREADY_MARKED".equals(response.getStatusCode());
        
        if (isSuccess) {
            return ApiResponse.success(response);
        } else {
            // 그 외의 경우 (NO_ACTIVE_SESSION, CODE_EXPIRED, ERROR)는 오류로 처리
            return ApiResponse.<AttendanceMarkResponse>builder()
                    .success(false)
                    .message(response.getMessage())
                    .data(response)
                    .build();
        }
    }
}
