package backend.pirocheck.Assignment.dto.response;

import backend.pirocheck.Assignment.entity.AssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignmentDetailRes {
    private Long id;
    private String assignmentName;
    private AssignmentStatus status;
}
