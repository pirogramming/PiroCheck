package backend.pirocheck.attendence.controller;

import backend.pirocheck.attendence.dto.request.GetAttendanceByDateReq;
import backend.pirocheck.attendence.dto.request.MarkAttendanceReq;
import backend.pirocheck.attendence.dto.response.AttendanceSlotRes;
import backend.pirocheck.attendence.dto.response.AttendanceStatusRes;
import backend.pirocheck.attendence.entity.AttendanceCode;
import backend.pirocheck.attendence.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    // 특정 유저의 출석 정보
    @GetMapping("/user")
    public List<AttendanceStatusRes> getAttendanceByUserId(@RequestParam Long userId) {
        return attendanceService.findByUserId(userId);
    }
    
    // 특정 유저의 특정 일자 출석 정보
    @GetMapping("/user/date")
    public List<AttendanceSlotRes> getAttendanceByUserIdAndDate(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return attendanceService.findByUserIdAndDate(userId, LocalDate.now());
    }

    // 출석체크 시작
    @PostMapping("/start")
    public AttendanceCode postAttendance() {
        return attendanceService.generateCodeAndCreateAttendances();
    }

    // 출석코드 비교
    @PostMapping("/mark")
    public boolean markAttendance(@RequestBody MarkAttendanceReq req) {
        return attendanceService.markAttendance(req.getUserId(), req.getCode());
    }

    // 출석체크 종료
    @PutMapping("/expire")
    public boolean expireAttendance(@RequestParam String code) {
        return attendanceService.exprireAttendanceCode(code);
    }
}
