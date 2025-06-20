package backend.pirocheck.Attendance.repository;

import backend.pirocheck.User.entity.User;
import backend.pirocheck.Attendance.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByUserId(Long userId);
    List<Attendance> findByUserIdAndDate(Long userId, LocalDate date);
    Optional<Attendance> findByUserIdAndDateAndOrder(Long userId, LocalDate date, int order);

    // 출석 실패
    int countByUserAndStatusFalse(User user);
    
    // 특정 날짜와 차수에 대한 모든 출석 기록 조회
    List<Attendance> findByDateAndOrder(LocalDate date, int order);

}
