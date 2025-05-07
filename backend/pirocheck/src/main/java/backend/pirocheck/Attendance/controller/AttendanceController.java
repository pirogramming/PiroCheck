package backend.pirocheck.Attendance.controller;

import backend.pirocheck.Attendance.dto.request.MarkAttendanceReq;
import backend.pirocheck.Attendance.dto.response.ApiResponse;
import backend.pirocheck.Attendance.dto.response.AttendanceCodeResponse;
import backend.pirocheck.Attendance.dto.response.AttendanceSlotRes;
import backend.pirocheck.Attendance.dto.response.AttendanceStatusRes;
import backend.pirocheck.Attendance.entity.AttendanceCode;
import backend.pirocheck.Attendance.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    // 특정 유저의 출석 정보
    @GetMapping("/user")
    public ApiResponse<List<AttendanceStatusRes>> getAttendanceByUserId(@RequestParam Long userId) {
        return ApiResponse.success(attendanceService.findByUserId(userId));
    }
    
    // 특정 유저의 특정 일자 출석 정보
    @GetMapping("/user/date")
    public ApiResponse<List<AttendanceSlotRes>> getAttendanceByUserIdAndDate(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ApiResponse.success(attendanceService.findByUserIdAndDate(userId, date));
    }

    // 출석체크 시작
    @PostMapping("/start")
    public ApiResponse<AttendanceCodeResponse> postAttendance() {
        AttendanceCode code = attendanceService.generateCodeAndCreateAttendances();
        return ApiResponse.success(AttendanceCodeResponse.from(code));
    }
    
    // 현재 활성화된 출석코드 조회
    @GetMapping("/active-code")
    public ApiResponse<AttendanceCodeResponse> getActiveCode() {
        Optional<AttendanceCode> codeOpt = attendanceService.getActiveAttendanceCode();
        
        if (codeOpt.isEmpty()) {
            return ApiResponse.error("현재 활성화된 출석코드가 없습니다");
        }
        
        return ApiResponse.success(AttendanceCodeResponse.from(codeOpt.get()));
    }

    // 출석코드 비교
    @PostMapping("/mark")
    public ApiResponse<Void> markAttendance(@RequestBody MarkAttendanceReq req) {
        String result = attendanceService.markAttendance(req.getUserId(), req.getCode());
        
        if (result.equals("출석이 성공적으로 처리되었습니다")) {
            return ApiResponse.success(result, null);
        } else {
            return ApiResponse.error(result);
        }
    }

    // 출석체크 종료 (코드 직접 전달)
    @PutMapping("/expire")
    public ApiResponse<Void> expireAttendance(@RequestParam String code) {
        String result = attendanceService.exprireAttendanceCode(code);
        
        if (result.equals("출석 코드가 성공적으로 만료되었습니다")) {
            return ApiResponse.success(result, null);
        } else {
            return ApiResponse.error(result);
        }
    }
    
    // 출석체크 종료 (가장 최근 활성화된 코드 자동 만료)
    @PutMapping("/expire-latest")
    public ApiResponse<Void> expireLatestAttendance() {
        String result = attendanceService.expireLatestAttendanceCode();
        
        if (result.equals("출석 코드가 성공적으로 만료되었습니다")) {
            return ApiResponse.success(result, null);
        } else {
            return ApiResponse.error(result);
        }
    }
}
