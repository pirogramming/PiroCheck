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
@RequestMapping("/api")
@Tag(name = "관리자 출석관리", description = "관리자용 출석 관리 API")
public class AdminAttendanceController {

    private final AttendanceService attendanceService;

    // 출석체크 시작
    @Operation(summary = "출석 체크 시작", description = "새로운 출석 코드를 생성하고 출석 체크를 시작합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "출석 코드 생성 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @PostMapping("/admin/attendance/start")
    public AttendanceCodeResponse startAttendance() {
        try {
            AttendanceCode code = attendanceService.generateCodeAndCreateAttendances();
            return AttendanceCodeResponse.from(code);
        } catch (IllegalStateException e) {
            // 하루 최대 출석 체크 횟수를 초과한 경우
            throw new IllegalStateException(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("출석 코드 생성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    // 현재 활성화된 출석코드 조회
    @Operation(summary = "현재 활성화된 출석 코드 조회", description = "현재 활성화된 출석 코드 정보를 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "활성화된 출석 코드 없음")
    })
    @GetMapping("/admin/attendance/active-code")
    public AttendanceCodeResponse getActiveCode() {
        Optional<AttendanceCode> codeOpt = attendanceService.getActiveAttendanceCode();
        
        if (codeOpt.isEmpty()) {
            throw new RuntimeException("현재 활성화된 출석코드가 없습니다");
        }
        
        return AttendanceCodeResponse.from(codeOpt.get());
    }

    // 출석체크 종료 (코드 직접 전달)
    @Operation(summary = "특정 출석 코드 만료", description = "특정 출석 코드를 만료 처리합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "만료 처리 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "출석 코드를 찾을 수 없음")
    })
    @PutMapping("/admin/attendance/expire")
    public String expireAttendance(
            @Parameter(description = "만료할 출석 코드", example = "1234")
            @RequestParam String code) {
        return attendanceService.expireAttendanceCode(code);
    }
    
    // 출석체크 종료 (가장 최근 활성화된 코드 자동 만료)
    @Operation(summary = "최근 활성화된 출석 코드 만료", description = "가장 최근 활성화된 출석 코드를 자동으로 만료 처리합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "만료 처리 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "활성화된 출석 코드가 없음")
    })
    @PutMapping("/admin/attendance/expire-latest")
    public String expireLatestAttendance() {
        return attendanceService.expireLatestAttendanceCode();
    }
    
    // 출석 상태 변경 (관리자 전용)
    @Operation(summary = "출석 상태 변경", description = "관리자가 특정 사용자의 출석 상태를 변경합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "출석 상태 변경 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "출석 기록을 찾을 수 없음")
    })
    @PutMapping("/admin/users/{userId}/attendance/{attendanceId}/status")
    public boolean updateAttendanceStatus(
            @Parameter(description = "사용자 ID", example = "1")
            @PathVariable Long userId,
            @Parameter(description = "출석 ID", example = "1")
            @PathVariable Long attendanceId,
            @RequestBody UpdateAttendanceStatusReq req) {
        
        // userId 파라미터 검증은 여기서 할 수 있음 (필요 시)
        return attendanceService.updateAttendanceStatus(attendanceId, req.isStatus());
    }
    
    // 출석 기록 삭제 (관리자 전용)
    @Operation(summary = "출석 기록 삭제", description = "관리자가 특정 사용자의 출석 기록을 삭제합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "출석 기록 삭제 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "출석 기록을 찾을 수 없음")
    })
    @DeleteMapping("/admin/users/{userId}/attendance/{attendanceId}")
    public boolean deleteAttendance(
            @Parameter(description = "사용자 ID", example = "1")
            @PathVariable Long userId,
            @Parameter(description = "출석 ID", example = "1")
            @PathVariable Long attendanceId) {
        
        // userId 파라미터 검증은 여기서 할 수 있음 (필요 시)
        return attendanceService.deleteAttendance(attendanceId);
    }
    
    // 특정 날짜와 차수에 대한 모든 학생의 출석 현황 조회
    @Operation(summary = "특정 날짜와 차수의 출석 현황 조회", description = "특정 날짜와 차수에 대한 모든 학생의 출석 현황을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @GetMapping("/admin/attendance/list")
    public List<UserAttendanceStatusRes> getAllAttendanceByDateAndOrder(
            @Parameter(description = "조회할 날짜 (YYYY-MM-DD)", example = "2023-08-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "조회할 차수", example = "1")
            @RequestParam int order) {
        return attendanceService.findAllByDateAndOrder(date, order);
    }
    
    // 특정 사용자의 특정 날짜와 차수 출석 기록 조회
    @Operation(summary = "특정 사용자의 특정 날짜와 차수 출석 조회", description = "특정 사용자의 특정 날짜와 차수 출석 기록을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "출석 기록을 찾을 수 없음")
    })
    @GetMapping("/admin/users/{userId}/attendance")
    public UserAttendanceStatusRes getUserAttendanceByDateAndOrder(
            @Parameter(description = "사용자 ID", example = "1")
            @PathVariable Long userId,
            @Parameter(description = "조회할 날짜 (YYYY-MM-DD)", example = "2023-08-01")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @Parameter(description = "조회할 차수", example = "1")
            @RequestParam int order) {
        return attendanceService.findByUserIdAndDateAndOrder(userId, date, order);
    }
    
    // 특정 출석 ID로 출석 기록 조회
    @Operation(summary = "특정 출석 기록 조회", description = "특정 학생의 특정 출석 기록을 ID로 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "출석 기록을 찾을 수 없음")
    })
    @GetMapping("/admin/users/{userId}/attendance/{attendanceId}")
    public UserAttendanceStatusRes getAttendanceById(
            @Parameter(description = "사용자 ID", example = "1")
            @PathVariable Long userId,
            @Parameter(description = "출석 ID", example = "1")
            @PathVariable Long attendanceId) {
        
        UserAttendanceStatusRes attendance = attendanceService.findById(attendanceId);
        
        if (attendance == null) {
            throw new RuntimeException("출석 기록을 찾을 수 없습니다");
        }
        
        // 요청된 userId와 조회된 출석 기록의 userId가 일치하는지 확인
        if (!attendance.getUserId().equals(userId)) {
            throw new RuntimeException("요청된 사용자 ID와 출석 기록의 사용자 ID가 일치하지 않습니다");
        }
        
        return attendance;
    }
    
    // 학생용 출석 현황 조회
    @Operation(summary = "학생별 출석 현황 조회", description = "특정 학생의 출석 현황을 조회합니다.")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 요청")
    })
    @GetMapping("/attendance/{userId}")
    public List<UserAttendanceStatusRes> getUserAttendances(
            @Parameter(description = "사용자 ID", example = "1")
            @PathVariable Long userId) {
        return attendanceService.findAllByUserId(userId);
    }
} 