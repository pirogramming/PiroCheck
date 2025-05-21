package backend.pirocheck.Attendance.controller;

import backend.pirocheck.Attendance.dto.request.UpdateAttendanceStatusReq;
import backend.pirocheck.Attendance.dto.response.ApiResponse;
import backend.pirocheck.Attendance.dto.response.AttendanceCodeResponse;
import backend.pirocheck.Attendance.dto.response.UserAttendanceStatusRes;
import backend.pirocheck.Attendance.entity.AttendanceCode;
import backend.pirocheck.Attendance.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/attendance")
@Tag(name = "관리자 출석관리", description = "관리자용 출석 관리 API")
public class AdminAttendanceController {

    private final AttendanceService attendanceService;

    // 출석체크 시작
    @Operation(summary = "출석 체크 시작", description = "새로운 출석 코드를 생성하고 출석 체크를 시작합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "출석 코드 생성 성공",
            content = @Content(schema = @Schema(implementation = AttendanceCodeResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/start")
    public ApiResponse<AttendanceCodeResponse> startAttendance() {
        try {
            AttendanceCode code = attendanceService.generateCodeAndCreateAttendances();
            return ApiResponse.success(AttendanceCodeResponse.from(code));
        } catch (IllegalStateException e) {
            // 하루 최대 출석 체크 횟수를 초과한 경우
            return ApiResponse.error(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("출석 코드 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    // 현재 활성화된 출석코드 조회
    @Operation(summary = "현재 활성화된 출석 코드 조회", description = "현재 활성화된 출석 코드 정보를 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200", 
            description = "조회 성공",
            content = @Content(schema = @Schema(implementation = AttendanceCodeResponse.class))
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "활성화된 출석 코드 없음")
    })
    @GetMapping("/active-code")
    public ApiResponse<AttendanceCodeResponse> getActiveCode() {
        Optional<AttendanceCode> codeOpt = attendanceService.getActiveAttendanceCode();
        
        if (codeOpt.isEmpty()) {
            return ApiResponse.error("현재 활성화된 출석코드가 없습니다");
        }
        
        return ApiResponse.success(AttendanceCodeResponse.from(codeOpt.get()));
    }

    // 출석체크 종료 (코드 직접 전달)
    @Operation(summary = "특정 출석 코드 만료", description = "특정 출석 코드를 만료 처리합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "만료 처리 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "출석 코드를 찾을 수 없음")
    })
    @PutMapping("/expire")
    public ApiResponse<Void> expireAttendance(
            @Parameter(description = "만료할 출석 코드", required = true)
            @RequestParam String code) {
        String result = attendanceService.expireAttendanceCode(code);
        
        if (result.equals("출석 코드가 성공적으로 만료되었습니다")) {
            return ApiResponse.success(result, null);
        } else {
            return ApiResponse.error(result);
        }
    }
    
    // 출석체크 종료 (가장 최근 활성화된 코드 자동 만료)
    @Operation(summary = "최근 활성화된 출석 코드 만료", description = "가장 최근 활성화된 출석 코드를 자동으로 만료 처리합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "만료 처리 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "활성화된 출석 코드가 없음")
    })
    @PutMapping("/expire-latest")
    public ApiResponse<Void> expireLatestAttendance() {
        String result = attendanceService.expireLatestAttendanceCode();
        
        if (result.equals("출석 코드가 성공적으로 만료되었습니다")) {
            return ApiResponse.success(result, null);
        } else {
            return ApiResponse.error(result);
        }
    }
    
    // 출석 상태 변경 (관리자 전용)
    @Operation(summary = "출석 상태 변경", description = "관리자가 특정 사용자의 출석 상태를 변경합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "출석 상태 변경 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "출석 기록을 찾을 수 없음")
    })
    @PutMapping("/status")
    public ApiResponse<Void> updateAttendanceStatus(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "출석 상태 변경 요청", 
                required = true, 
                content = @Content(schema = @Schema(implementation = UpdateAttendanceStatusReq.class))
            )
            @RequestBody UpdateAttendanceStatusReq req) {
        
        boolean result = attendanceService.updateAttendanceStatus(
                req.getAttendanceId(),
                req.isStatus()
        );
        
        if (result) {
            return ApiResponse.success("출석 상태가 성공적으로 변경되었습니다", null);
        } else {
            return ApiResponse.error("출석 상태 변경에 실패했습니다. 출석 기록을 찾을 수 없습니다.");
        }
    }
    
    // 특정 날짜와 차수에 대한 모든 학생의 출석 현황 조회
    @Operation(summary = "특정 날짜와 차수의 출석 현황 조회", description = "특정 날짜와 차수에 대한 모든 학생의 출석 현황을 조회합니다.")
    @ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @GetMapping("/list")
    public ApiResponse<List<UserAttendanceStatusRes>> getAllAttendanceByDateAndOrder(
            @Parameter(description = "조회할 날짜 (YYYY-MM-DD)", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "조회할 차수", required = true)
            @RequestParam int order) {
        
        List<UserAttendanceStatusRes> attendances = attendanceService.findAllByDateAndOrder(date, order);
        return ApiResponse.success(attendances);
    }
} 