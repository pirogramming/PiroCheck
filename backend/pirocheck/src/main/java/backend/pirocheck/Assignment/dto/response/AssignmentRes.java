package backend.pirocheck.Assignment.dto.response;

import backend.pirocheck.Assignment.entity.AssignmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignmentRes {

//    private Long userId;
    private String assignmentName;
    private Long week;
    private String day;
    private Long orderNumber;
    private AssignmentStatus submitted;

}
