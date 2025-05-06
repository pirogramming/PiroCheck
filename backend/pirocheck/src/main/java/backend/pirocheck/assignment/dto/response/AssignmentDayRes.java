package backend.pirocheck.assignment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AssignmentDayRes {
    private String day; // 화, 목, 토
    private List<AssignmentDetailRes> details;
}
