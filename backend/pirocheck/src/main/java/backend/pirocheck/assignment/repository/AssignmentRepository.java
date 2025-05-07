package backend.pirocheck.assignment.repository;

import backend.pirocheck.User.entity.User;
import backend.pirocheck.assignment.entity.Assignment;
import backend.pirocheck.assignment.entity.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    // 유저별 과제 목록 조회
    List<Assignment> findByUserId(Long userId);

    // 보증금
    int countByUserAndSubmitted(User user, AssignmentStatus status);

}
