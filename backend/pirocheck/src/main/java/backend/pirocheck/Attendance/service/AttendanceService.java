package backend.pirocheck.Attendance.service;

import backend.pirocheck.Deposit.entity.Deposit;
import backend.pirocheck.Deposit.repository.DepositRepository;
import backend.pirocheck.Deposit.service.DepositService;
import backend.pirocheck.User.entity.Role;
import backend.pirocheck.User.entity.User;
import backend.pirocheck.User.repository.UserRepository;
import backend.pirocheck.Attendance.dto.response.AttendanceMarkResponse;
import backend.pirocheck.Attendance.dto.response.AttendanceSlotRes;
import backend.pirocheck.Attendance.dto.response.AttendanceStatusRes;
import backend.pirocheck.Attendance.dto.response.UserAttendanceStatusRes;
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
    private final DepositService depositService;

    // 출석코드 생성 함수
    @Transactional
    public AttendanceCode generateCodeAndCreateAttendances() {
        LocalDate today = LocalDate.now();

        // 만료되지 않은 출석 코드가 있는지 확인
        List<AttendanceCode> activeAttendanceCodes = attendanceCodeRepository.findByDateAndIsExpiredFalse(today);
        if (!activeAttendanceCodes.isEmpty()) {
            // 만료되지 않은 코드가 있으면 해당 코드 반환
            return activeAttendanceCodes.get(0);
        }

        // 오늘 생성된 출석코드 개수 = 현재까지 생성된 차시 수 + 1 (MAX=3)
        int currentOrder = attendanceCodeRepository.countByDate(today) + 1;
        
        // 하루 최대 3회 출석 체크만 허용
        if (currentOrder > 3) {
            throw new IllegalStateException("하루에 최대 3회까지만 출석 체크를 진행할 수 있습니다.");
        }

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

    // 현재 활성화된 출석코드 조회 함수
    public Optional<AttendanceCode> getActiveAttendanceCode() {
        LocalDate today = LocalDate.now();
        List<AttendanceCode> activeCodes = attendanceCodeRepository.findByDateAndIsExpiredFalse(today);
        
        if (activeCodes.isEmpty()) {
            return Optional.empty();
        }
        
        return Optional.of(activeCodes.get(0));
    }
    
    // 가장 최근 활성화된 출석코드 만료처리 함수
    @Transactional
    public String expireLatestAttendanceCode() {
        Optional<AttendanceCode> activeCodeOpt = getActiveAttendanceCode();
        
        if (activeCodeOpt.isEmpty()) {
            return "현재 활성화된 출석코드가 없습니다";
        }
        
        AttendanceCode code = activeCodeOpt.get();
        code.setExpired(true);
        attendanceCodeRepository.save(code);
        
        return "출석 코드가 성공적으로 만료되었습니다";
    }

    // 출석코드 만료처리 함수
    @Transactional
    public String expireAttendanceCode(String code) {
        Optional<AttendanceCode> codeOpt = attendanceCodeRepository.findByCodeAndDate(code, LocalDate.now());

        if (codeOpt.isEmpty()) {
            return "존재하지 않는 출석 코드입니다";
        }

        AttendanceCode attendanceCode = codeOpt.get();

        if (attendanceCode.isExpired()) {
            return "이미 만료된 출석 코드입니다";
        }

        attendanceCode.setExpired(true);
        attendanceCodeRepository.save(attendanceCode);

        // 보증금
        List<Attendance> absents = attendanceRepository.findByDateAndOrderAndStatusFalse(
                attendanceCode.getDate(), attendanceCode.getOrder());

        for (Attendance attendance : absents) {
            depositService.recalculateDeposit(attendance.getUser().getId());
        }
        return "출석 코드가 성공적으로 만료되었습니다";
    }

    // 출석처리 함수
    @Transactional
    public AttendanceMarkResponse markAttendance(Long userId, String inputCode) {
        // 오늘 날짜
        LocalDate today = LocalDate.now();
        
        // 현재 활성화된 출석 코드가 있는지 확인
        List<AttendanceCode> activeCodes = attendanceCodeRepository.findByDateAndIsExpiredFalse(today);
        
        // 활성화된 출석 코드가 없는 경우
        if (activeCodes.isEmpty()) {
            return AttendanceMarkResponse.noActiveSession();
        }
        
        // 입력한 출석 코드와 일치하는 코드가 있는지 확인
        Optional<AttendanceCode> validCodeOpt = attendanceCodeRepository.findByCodeAndDate(inputCode, today);
        
        // 입력한 출석 코드가 존재하지 않는 경우
        if (validCodeOpt.isEmpty()) {
            return AttendanceMarkResponse.invalidCode();
        }

        AttendanceCode code = validCodeOpt.get();
        
        // 입력한 출석 코드가 만료된 경우
        if (code.isExpired()) {
            return AttendanceMarkResponse.codeExpired();
        }

        // 2. 해당 유저의 출석 레코드 조회
        Optional<Attendance> attendanceOpt = attendanceRepository.findByUserIdAndDateAndOrder(userId, code.getDate(), code.getOrder());

        if (attendanceOpt.isEmpty()) {
            return AttendanceMarkResponse.error("출석 정보를 찾을 수 없습니다");
        }

        // 3. 출석 처리
        Attendance attendance = attendanceOpt.get();
        
        // 이미 출석한 경우
        if (attendance.isStatus()) {
            return AttendanceMarkResponse.alreadyMarked();
        }
        
        attendance.setStatus(true);
        attendanceRepository.save(attendance);

        //보증금 재계산
        depositService.recalculateDeposit(userId);

        return AttendanceMarkResponse.success();
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
    
    // 관리자가 유저의 출석 상태를 변경하는 함수
    @Transactional
    public boolean updateAttendanceStatus(Long attendanceId, boolean status) {
        Optional<Attendance> attendanceOpt = attendanceRepository.findById(attendanceId);
        
        if (attendanceOpt.isEmpty()) {
            return false;
        }
        
        // 출석 상태 변경
        Attendance attendance = attendanceOpt.get();
        attendance.setStatus(status);
        attendanceRepository.save(attendance);

        // 출석 변경 → 보증금 재계산
        depositService.recalculateDeposit(attendance.getUser().getId());

        return true;
    }

    // 특정 날짜와 차수의 모든 학생 출석 현황 조회
    public List<UserAttendanceStatusRes> findAllByDateAndOrder(LocalDate date, int order) {
        // 해당 날짜와 차수에 대한 모든 출석 기록 조회
        List<Attendance> attendances = attendanceRepository.findByDateAndOrder(date, order);
        
        // 사용자별로 DTO 변환
        return attendances.stream()
                .map(attendance -> {
                    User user = attendance.getUser();
                    return UserAttendanceStatusRes.builder()
                            .userId(user.getId())
                            .username(user.getName())
                            .date(attendance.getDate())
                            .order(attendance.getOrder())
                            .status(attendance.isStatus())
                            .attendanceId(attendance.getId())  // 출석 기록 ID 추가
                            .build();
                })
                .sorted(Comparator.comparing(UserAttendanceStatusRes::getUsername))
                .toList();
    }
    
    // 특정 학생의 모든 출석 현황 조회
    public List<UserAttendanceStatusRes> findAllByUserId(Long userId) {
        // 해당 사용자의 모든 출석 기록 조회
        List<Attendance> attendances = attendanceRepository.findByUserId(userId);
        
        // DTO 변환
        return attendances.stream()
                .map(attendance -> {
                    User user = attendance.getUser();
                    return UserAttendanceStatusRes.builder()
                            .userId(user.getId())
                            .username(user.getName())
                            .date(attendance.getDate())
                            .order(attendance.getOrder())
                            .status(attendance.isStatus())
                            .attendanceId(attendance.getId())
                            .build();
                })
                .sorted(Comparator.comparing(UserAttendanceStatusRes::getDate).reversed()
                        .thenComparing(UserAttendanceStatusRes::getOrder))
                .toList();
    }
    
    // 특정 사용자의 특정 출석 기록 삭제
    @Transactional
    public boolean deleteAttendance(Long attendanceId) {
        Optional<Attendance> attendanceOpt = attendanceRepository.findById(attendanceId);
        
        if (attendanceOpt.isEmpty()) {
            return false;
        }
        
        attendanceRepository.delete(attendanceOpt.get());
        return true;
    }
    
    // 특정 사용자의 특정 날짜와 차수 출석 기록 조회
    public UserAttendanceStatusRes findByUserIdAndDateAndOrder(Long userId, LocalDate date, int order) {
        Optional<Attendance> attendanceOpt = attendanceRepository.findByUserIdAndDateAndOrder(userId, date, order);
        
        if (attendanceOpt.isEmpty()) {
            return null;
        }
        
        Attendance attendance = attendanceOpt.get();
        User user = attendance.getUser();
        
        return UserAttendanceStatusRes.builder()
                .userId(user.getId())
                .username(user.getName())
                .date(attendance.getDate())
                .order(attendance.getOrder())
                .status(attendance.isStatus())
                .attendanceId(attendance.getId())
                .build();
    }
    
    // 특정 출석 ID로 출석 기록 조회
    public UserAttendanceStatusRes findById(Long attendanceId) {
        Optional<Attendance> attendanceOpt = attendanceRepository.findById(attendanceId);
        
        if (attendanceOpt.isEmpty()) {
            return null;
        }
        
        Attendance attendance = attendanceOpt.get();
        User user = attendance.getUser();
        
        return UserAttendanceStatusRes.builder()
                .userId(user.getId())
                .username(user.getName())
                .date(attendance.getDate())
                .order(attendance.getOrder())
                .status(attendance.isStatus())
                .attendanceId(attendance.getId())
                .build();
    }
}
