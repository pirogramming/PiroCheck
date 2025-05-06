package backend.pirocheck.Attendance.repository;

import backend.pirocheck.Attendance.entity.AttendanceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceCodeRepository extends JpaRepository<AttendanceCode, Long> {
    int countByDate(LocalDate date);
    Optional<AttendanceCode> findByCodeAndDate(String code, LocalDate date);
}
