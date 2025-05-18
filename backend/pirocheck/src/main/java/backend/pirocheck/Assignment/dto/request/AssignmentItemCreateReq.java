package backend.pirocheck.Assignment.dto.request;

import backend.pirocheck.Assignment.entity.AssignmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentItemCreateReq {

    private Long assignmentId;

    private Long userId;

    @Pattern(regexp = "SUCCESS/INSUFFICIENT/FAILURE", message = "status는 SUCCESS, INSUFFICIENT 혹은 FAILURE 여야 합니다.")
    @Schema(description = "과제 결과", example = "SUCCESS")
    private AssignmentStatus status;
}
