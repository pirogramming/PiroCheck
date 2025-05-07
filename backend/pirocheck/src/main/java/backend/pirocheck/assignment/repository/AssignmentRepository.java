package backend.pirocheck.assignment.repository;

import backend.pirocheck.assignment.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    // 유저별 과제 목록 조회
    List<Assignment> findByUserId(Long userId);

}
