package backend.pirocheck.Attendance.service;

import backend.pirocheck.User.entity.Role;
import backend.pirocheck.User.entity.User;
import backend.pirocheck.User.repository.UserRepository;
import backend.pirocheck.Attendance.dto.response.AttendanceSlotRes;
import backend.pirocheck.Attendance.dto.response.AttendanceStatusRes;
import backend.pirocheck.Attendance.entity.Attendance;
import backend.pirocheck.Attendance.entity.AttendanceCode;
import backend.pirocheck.Attendance.repository.AttendanceCodeRepository;
import backend.pirocheck.Attendance.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AttendanceCodeRepository attendanceCodeRepository;
    private final UserRepository userRepository;

    // 출석코드 생성 함수
    @Transactional
    public AttendanceCode generateCodeAndCreateAttendances() {
        LocalDate today = LocalDate.now();

        // 오늘 생성된 출석코드 개수 = 현재까지 생성된 차시 수 + 1 (MAX=3)
        int currentOrder = attendanceCodeRepository.countByDate(today) + 1;

        // 1. 출석 코드 생성
        String code = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 10000));

        AttendanceCode attendanceCode = new AttendanceCode();
        attendanceCode.setCode(code);
        attendanceCode.setDate(today);
        attendanceCode.setOrder(currentOrder);
        attendanceCodeRepository.save(attendanceCode);

        // 2. user 권한을 가진 학생 리스트 조회
        List<User> users = userRepository.findByRole(Role.MEMBER);

        // 3. 각 학생에 대해 출석 데이터 미리 생성
        for (User user : users) {
            Attendance attendance = new Attendance();
            attendance.setUser(user);
            attendance.setDate(LocalDate.now());
            attendance.setOrder(currentOrder);
            attendance.setStatus(false); // 기본은 false
            attendanceRepository.save(attendance);
        }
        return attendanceCode;
    }

    // 출석코드 만료처리 함수
    @Transactional
    public boolean exprireAttendanceCode(String code) {
        Optional<AttendanceCode> codeOpt = attendanceCodeRepository.findByCodeAndDate(code, LocalDate.now());

        if (codeOpt.isEmpty()) {
            return false;
        }

        AttendanceCode attendanceCode = codeOpt.get();

        if (attendanceCode.isExpired()) {
            return false;
        }

        attendanceCode.setExpired(true);
        attendanceCodeRepository.save(attendanceCode);

        return true;
    }

    // 출석처리 함수
    @Transactional
    public boolean markAttendance(Long userId, String inputCode) {
        // 1. 출석코드 일치 비교
        Optional<AttendanceCode> validCodeOpt = attendanceCodeRepository.findByCodeAndDate(inputCode, LocalDate.now());

        if (validCodeOpt.isEmpty()) return false;

        AttendanceCode code = validCodeOpt.get();

        // 2. 해당 유저의 출석 레코드 조회
        Optional<Attendance> attendanceOpt = attendanceRepository.findByUserIdAndDateAndOrder(userId, code.getDate(), code.getOrder());

        if (attendanceOpt.isEmpty()) return false;

        // 3. 출석 처리
        Attendance attendance = attendanceOpt.get();
        attendance.setStatus(true);
        attendanceRepository.save(attendance);

        return true;
    }

    // 유저의 전체 출석 현황을 조회하는 함수
    public List<AttendanceStatusRes> findByUserId(Long userId) {
        List<Attendance> attendances = attendanceRepository.findByUserId(userId);

        // 날짜별로 그룹화
        Map<LocalDate, List<Attendance>> grouped = attendances.stream()
                .collect(Collectors.groupingBy(Attendance::getDate));

        // 날짜별로 DTO 변환
        return grouped.entrySet().stream()
                .map(entry -> {
                    LocalDate date = entry.getKey();
                    List<AttendanceSlotRes> slots = entry.getValue().stream()
                            .map(a -> new AttendanceSlotRes(a.getOrder(), a.isStatus()))
                            .sorted(Comparator.comparingInt(AttendanceSlotRes::getOrder))
                            .toList();

                    AttendanceStatusRes dto = new AttendanceStatusRes();
                    dto.setDate(date);
                    dto.setSlots(slots);
                    return dto;
                })
                .sorted(Comparator.comparing(AttendanceStatusRes::getDate).reversed())
                .toList();
    }

    // 유저의 특정 날짜의 출석 현황을 조회하는 함수
    public List<AttendanceSlotRes> findByUserIdAndDate(Long userId, LocalDate date) {
        List<Attendance> attendances = attendanceRepository.findByUserIdAndDate(userId, date);

        return attendances.stream()
                .map(a -> new AttendanceSlotRes(a.getOrder(), a.isStatus()))
                .sorted(Comparator.comparingInt(AttendanceSlotRes::getOrder))
                .toList();
    }
}
