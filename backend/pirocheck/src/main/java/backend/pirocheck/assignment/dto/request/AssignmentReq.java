package backend.pirocheck.assignment.dto.request;

import backend.pirocheck.assignment.entity.AssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignmentReq {

    private String assignmentName;
    private Long week;
    private Long section;
    private Long orderNumber;

}
