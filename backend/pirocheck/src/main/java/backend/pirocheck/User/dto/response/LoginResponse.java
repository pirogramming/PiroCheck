package backend.pirocheck.User.dto.response;


import backend.pirocheck.User.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class LoginResponse {

    @Schema(description = "유저 고유 ID", example = "1")
    private Long id;

    @Schema(description = "유저 이름", example = "김피로")
    private String name;

    @Schema(description = "유저 권한", example = "MEMBER")
    private String role;

    public LoginResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.role = user.getRole().name();  // MEMBER or ADMIN
    }
}
