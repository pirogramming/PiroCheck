package backend.pirocheck.attendence.repository;

import backend.pirocheck.attendence.entity.Attendance;
import backend.pirocheck.attendence.entity.AttendanceCode;
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
}
