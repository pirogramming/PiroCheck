package backend.pirocheck.Assignment.repository;

import backend.pirocheck.Assignment.entity.Assignment;
import backend.pirocheck.Assignment.entity.AssignmentItem;
import backend.pirocheck.Assignment.entity.AssignmentStatus;
import backend.pirocheck.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentItemRepository extends JpaRepository<AssignmentItem, Long> {

    // 유저별 과제 목록 조회
    List<AssignmentItem> findByUserId(Long userId);

    // 보증금
    int countByUserAndSubmitted(User user, AssignmentStatus status);

    Optional<AssignmentItem> findByUserAndAssignment(User user, Assignment assignment);
    // Optional 처리로 오류 발생 check

}
