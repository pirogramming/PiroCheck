package backend.pirocheck.Assignment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignmentRes {

    private Long id;
    private String title;
    private String subtitle;
    private String assignmentName;
    private Long week;
    private String day;
    private Long orderNumber;

}
