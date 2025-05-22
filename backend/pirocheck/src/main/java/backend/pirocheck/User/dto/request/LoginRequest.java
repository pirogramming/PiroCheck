package backend.pirocheck.User.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @Schema(description = "사용자 이름", example = "김피로")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Schema(description = "비밀번호", example = "qwer1234!")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
