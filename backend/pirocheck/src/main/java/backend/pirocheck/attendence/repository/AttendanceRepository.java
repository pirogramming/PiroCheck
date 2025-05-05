package backend.pirocheck.attendence.repository;

import backend.pirocheck.attendence.entity.Attendance;
import backend.pirocheck.attendence.entity.AttendanceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceCode, Long> {
    List<Attendance> findByUserId(Long userId);
}
