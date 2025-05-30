package backend.pirocheck.Assignment.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssignmentUpdateReq {

    @Schema(description = "전체 주제", example = "Git/HTML/CSS")
    @NotNull(message = "전체 주제는 필수입니다.")
    private String title;

    @Schema(description = "소주제", example = "Git/HTML/CSS")
    @NotNull(message = "소주제는 필수입니다.")
    private String subtitle;

    @Schema(description = "과제명", example = "제로초 인강")
    @NotNull(message = "과제명은 필수입니다.")
    private String assignmentName;

    @Schema(description = "주차", example = "1")
    @Positive
    private Long week;

    @Schema(description = "요일", example = "화")
    @NotBlank(message = "요일을 입력해주세요.")
    private String day;

    @Schema(description = "해당 일자 과제 numbering", example = "1")
    private Long orderNumber;

}
