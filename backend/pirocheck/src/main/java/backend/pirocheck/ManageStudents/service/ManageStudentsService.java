package backend.pirocheck.ManageStudents.service;

import backend.pirocheck.Deposit.entity.Deposit;
import backend.pirocheck.Deposit.repository.DepositRepository;
import backend.pirocheck.ManageStudents.dto.response.ManageStudentDetailResDto;
import backend.pirocheck.ManageStudents.dto.response.ManageStudentsListResDto;
import backend.pirocheck.User.entity.Role;
import backend.pirocheck.User.entity.User;
import backend.pirocheck.User.repository.UserRepository;
import backend.pirocheck.assignment.entity.Assignment;
import backend.pirocheck.assignment.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManageStudentsService {

    private final UserRepository userRepository;
    private final DepositRepository depositRepository;
    private final AssignmentRepository assignmentRepository;

    // 수강생 조회
    public List<ManageStudentsListResDto> searchMembers(String name) {
        List<User> users;

        if(name == null || name.isBlank()) {
            // 검색어가 없으면 맴버 전체 조회
            users = userRepository.findByRole(Role.MEMBER);
        }
        else {
            // 이름 검색
            users = userRepository.findByNameContainingAndRole(name, Role.MEMBER);
        }

        return users.stream()
                .map(user -> new ManageStudentsListResDto(user.getId(), user.getName()))
                .collect(Collectors.toList());
    }

    // 수강생 상세 조회
    public ManageStudentDetailResDto getMemberDetail(Long studentId) {
        // User 조회
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("해당 맴버가 존재하지 않습니다."));

        // deposit 조회
        Deposit deposit = depositRepository.findByUser(user);
        if (deposit == null) {
            throw new RuntimeException("해당 수강생의 보증금 정보가 없습니다.");
        }

        // Assignment 리스트 조회
        List<Assignment> assignments = assignmentRepository.findByUserId(studentId);

        // 과제 제목만 리스트로 변환
        List<String> assignmentTitles = assignments.stream()
                .map(Assignment::getAssignmentName)
                .toList();

        // ManageStudentDetailResDto 조립
        return ManageStudentDetailResDto.builder()
                .name(user.getName())
                .deposit(deposit.getAmount())
                .defence(deposit.getAscentDefence())
                .assignmentTitles(assignmentTitles)
                .build();
    }

    // 방어권 업데이트
    public void updateDefence(Long studentId, int defence) {
        User user = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("해당 수강생의 보증금 정보가 없습니다."));
        Deposit deposit = depositRepository.findByUser(user);

        // 업데이트
        deposit.updateDefence(defence);

        // 저장
        depositRepository.save(deposit);

    }
}
