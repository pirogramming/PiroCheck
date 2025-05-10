package backend.pirocheck.User.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class SessionCheckFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();

        // 로그인/로그아웃 요청은 세션 체크 제외
        if (path.startsWith("/api/login") || path.startsWith("/api/logout")) {
            filterChain.doFilter(request, response); // 다음 필터나 컨트롤러로 넘기는 명령어
            return; // 세션 검사 안함
        }

        HttpSession session = request.getSession(false);  // 세션이 없으면 새로 만들지 않고 null을 리턴 (true : 새로 생성)

        if (session == null || session.getAttribute("loginUser") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 설정
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("{\"success\":false,\"message\":\"세션이 만료되었습니다.\",\"data\":null}");
            return;
        }

        filterChain.doFilter(request, response);

    }
}
