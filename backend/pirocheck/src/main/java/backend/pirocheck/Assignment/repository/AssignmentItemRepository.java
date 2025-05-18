package backend.pirocheck.Assignment.repository;

import backend.pirocheck.Assignment.entity.AssignmentItem;
import backend.pirocheck.Assignment.entity.AssignmentStatus;
import backend.pirocheck.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentItemRepository extends JpaRepository<AssignmentItem, Long> {

    // 유저별 과제 목록 조회
    List<AssignmentItem> findByUserId(Long userId);

    // 보증금
    int countByUserAndSubmitted(User user, AssignmentStatus status);

}
