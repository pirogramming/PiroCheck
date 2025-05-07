package backend.pirocheck.Attendance.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AttendanceSlotRes {
    private int order;
    private boolean status;

}
