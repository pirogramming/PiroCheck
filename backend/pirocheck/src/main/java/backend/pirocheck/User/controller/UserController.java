package backend.pirocheck.User.controller;

import backend.pirocheck.User.dto.request.LoginRequest;
import backend.pirocheck.User.dto.response.LoginResponse;
import backend.pirocheck.User.entity.User;
import backend.pirocheck.User.repository.UserRepository;
import backend.pirocheck.User.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 인증", description = "로그인 / 로그아웃 API")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 로그인
    @Operation(summary = "로그인", description = "사용자 이름과 비밀번호로 로그인하고 세션을 생성합니다.")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request, HttpSession session) {
        User user = userService.login(request.getName(), request.getPassword());

        //세션에 로그인 정보 저장
        session.setAttribute("loginUser", user);

        // 사용자 정보 응답
        return ResponseEntity.ok(new LoginResponse(user));
    }

    // 로그아웃
    @Operation(summary = "로그아웃", description = "세션을 종료하여 로그아웃합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate(); // 세션 종료 (메모리에서 삭제)
        return ResponseEntity.ok().build();  // 본문은 없음 (void)
    }


}
