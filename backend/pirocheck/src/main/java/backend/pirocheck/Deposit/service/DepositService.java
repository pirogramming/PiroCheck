package backend.pirocheck.Deposit.service;


import backend.pirocheck.Deposit.dto.DepositResDto;
import backend.pirocheck.Deposit.entity.Deposit;
import backend.pirocheck.Deposit.repository.DepositRepository;
import backend.pirocheck.User.entity.User;
import backend.pirocheck.User.repository.UserRepository;
import backend.pirocheck.Assignment.entity.AssignmentStatus;
import backend.pirocheck.Assignment.repository.AssignmentItemRepository;
import backend.pirocheck.Attendance.repository.AttendanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final DepositRepository depositRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final AssignmentItemRepository assignmentItemRepository;

    @Transactional
    public DepositResDto getDeposit(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Deposit deposit = depositRepository.findByUser(user);
        calculateAndSave(user, deposit);

        return DepositResDto.builder()
                .amount(deposit.getAmount())
                .descentAssignment(deposit.getDescentAssignment())
                .descentAttendance(deposit.getDescentAttendance())
                .ascentDefence(deposit.getAscentDefence())
                .build();
    }

    // 보증금 재계산 (내부 로직만 수행)
    @Transactional
    public void recalculateDeposit(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Deposit deposit = depositRepository.findByUser(user);
        calculateAndSave(user, deposit);
    }

    // 공통 계산 로직
    private void calculateAndSave(User user, Deposit deposit) {
        //출석
        int failAttendanceCount = attendanceRepository.countByUserAndStatusFalse(user);
        int descentAttendance = failAttendanceCount * 10_000;

        //과제
        int failAssignmentCount = assignmentItemRepository.countByUserAndSubmitted(user, AssignmentStatus.FAILURE);
        int weakAssignmentCount = assignmentItemRepository.countByUserAndSubmitted(user, AssignmentStatus.INSUFFICIENT);
        int descentAssignment = failAssignmentCount * 20_000 + weakAssignmentCount * 10_000;

        int ascentDefence = deposit.getAscentDefence();

        deposit.updateAmounts(descentAssignment, descentAttendance, ascentDefence);
        depositRepository.save(deposit);
    }
}