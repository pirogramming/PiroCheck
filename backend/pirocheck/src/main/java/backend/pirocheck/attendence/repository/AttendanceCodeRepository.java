package backend.pirocheck.attendence.repository;

import backend.pirocheck.attendence.entity.AttendanceCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceCodeRepository extends JpaRepository<AttendanceCode, Long> {
}
