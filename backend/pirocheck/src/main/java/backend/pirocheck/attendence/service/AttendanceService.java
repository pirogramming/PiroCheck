package backend.pirocheck.attendence.service;

import backend.pirocheck.attendence.entity.Attendance;
import backend.pirocheck.attendence.entity.AttendanceCode;
import backend.pirocheck.attendence.repository.AttendanceCodeRepository;
import backend.pirocheck.attendence.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceCodeRepository attendanceCodeRepository;

    // 출석코드 생성 함수
    @Transactional
    public AttendanceCode generateCodeAndCreateAttendances(Long classId, int order) {
        // 1. 출석 코드 생성
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000));

        AttendanceCode attendanceCode = new AttendanceCode();
        attendanceCode.setCode(code);
        attendanceCodeRepository.save(attendanceCode);

        // 2. user 권한을 가진 학생 리스트 조회
        List<User> users = userRepository.findAllByRole(Role.USER);

        // 3. 각 학생에 대해 출석 데이터 미리 생성
        for (User user : users) {
            Attendance attendance = new Attendance();
            attendance.setUser(user);
            attendance.setDate(LocalDate.now());
            attendance.setOrder(order);
            attendance.setStatus(false); // 기본은 false
            attendanceRepository.save(attendance);
        }

        return attendanceCode;
    }


    // 출석코드 삭제 함수

    // 유저 id로 출석 조회하는 함수
    public Attendance findByUserId(Long userId) {

    }
}
