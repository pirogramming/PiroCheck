package backend.pirocheck.ManageStudents.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefenceUpdateReqDto {

    @Schema(description = "방어권 금액", example = "20000")
    private int defence;

}
