package backend.pirocheck.User.dto.response;


import backend.pirocheck.User.entity.User;
import lombok.Getter;

@Getter
public class LoginResponse {

    private Long id;
    private String name;
    private String role;

    public LoginResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.role = user.getRole().name();  // MEMBER or ADMIN
    }
}
