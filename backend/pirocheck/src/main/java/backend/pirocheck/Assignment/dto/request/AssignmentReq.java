package backend.pirocheck.Assignment.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignmentReq {

    private String assignmentName;
    private Long week;
    private String day;
    private Long orderNumber;

}
