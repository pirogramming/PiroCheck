package backend.pirocheck.Attendance.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AttendanceStatusRes {
    private LocalDate date;
    private List<AttendanceSlotRes> slots;
}
