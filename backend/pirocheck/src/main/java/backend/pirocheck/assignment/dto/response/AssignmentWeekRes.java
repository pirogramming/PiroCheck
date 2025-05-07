package backend.pirocheck.assignment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AssignmentWeekRes {
    private Long week;
    private String title; // 각 주차 주제 (e.g, Git / HTML / CSS)
    private List<AssignmentDayRes> days;
}
