package backend.pirocheck.assignment.dto.response;

import backend.pirocheck.assignment.entity.AssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignmentDetailRes {
    private String assignmentName;
    private AssignmentStatus status;
}
