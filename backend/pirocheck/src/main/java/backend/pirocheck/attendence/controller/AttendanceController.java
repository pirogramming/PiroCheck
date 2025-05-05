package backend.pirocheck.attendence.controller;

import backend.pirocheck.attendence.entity.AttendanceCode;
import backend.pirocheck.attendence.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    // 특정 유저의 출석 정보
    @GetMapping("/{userId}")
    public AttendanceCode getAttendance() {

    }
    
    // 특정 유저의 특정 일자 출석 정보
    @GetMapping("")
    public
}
