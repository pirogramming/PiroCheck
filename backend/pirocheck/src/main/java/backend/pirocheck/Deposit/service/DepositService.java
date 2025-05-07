package backend.pirocheck.Deposit.service;


import backend.pirocheck.Deposit.dto.DepositResDto;
import backend.pirocheck.Deposit.entity.Deposit;
import backend.pirocheck.Deposit.repository.DepositRepository;
import backend.pirocheck.User.entity.User;
import backend.pirocheck.User.repository.UserRepository;
import backend.pirocheck.assignment.entity.AssignmentStatus;
import backend.pirocheck.assignment.repository.AssignmentRepository;
import backend.pirocheck.attendence.repository.AttendanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositService {

    private final DepositRepository depositRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final AssignmentRepository assignmentRepository;  // 확인

    @Transactional
    public DepositResDto getDeposit(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Deposit deposit = depositRepository.findByUser(user);

        // 출석 실패
        int failAttendanceCount = attendanceRepository.countByUserAndStatusFalse(user);
        int descentAttendance = failAttendanceCount * 10000;

        // 과제 실패
        int failAssignmentCount = assignmentRepository.countByUserAndSubmitted(user, AssignmentStatus.FAILURE); // 확인
        int weakAssignmentCount = assignmentRepository.countByUserAndSubmitted(user, AssignmentStatus.INSUFFICIENT); // 확인
        int descentAssignment = failAssignmentCount * 10_000 + weakAssignmentCount * 5_000;

        // 방어권
        int ascentDefence = deposit.getAscentDefence();

        // 보증금 업데이트
        deposit.updateAmounts(descentAssignment, descentAttendance, ascentDefence);
        depositRepository.save(deposit);

        return DepositResDto.builder()
                .amount(deposit.getAmount())
                .descentAssignment(deposit.getDescentAssignment())
                .descentAttendance(deposit.getDescentAttendance())
                .ascentDefence(deposit.getAscentDefence())
                .build();

    }
}
