package backend.pirocheck.assignment.dto.response;

import backend.pirocheck.assignment.entity.AssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignmentRes {

//    private Long userId;
    private String assignmentName;
    private Long week;
    private Long section;
    private Long orderNumber;
    private AssignmentStatus submitted;

}
