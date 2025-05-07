package backend.pirocheck.attendence.dto.response;

import lombok.AllArgsConstructor;
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
