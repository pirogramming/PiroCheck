package backend.pirocheck.ManageStudents.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonPropertyOrder({"id", "name", "deposit", "defence", "assignmentTitles"})
public class ManageStudentDetailResDto {

    @Schema(description = "학생 ID", example = "1")
    private Long id;

    @Schema(description = "수강생 이름", example = "김피로")
    private String name;

    @Schema(description = "현재 보증금 잔액", example = "11000")
    private int deposit;

    @Schema(description = "방어권 금액", example = "10000")
    private int defence; // 방어권

    @Schema(description = "과제 제목 리스트", example = "[\"제로초 인강\", \"토스 클론\"]")
    private List<String> assignmentTitles; // 과제 제목 리스트
}
