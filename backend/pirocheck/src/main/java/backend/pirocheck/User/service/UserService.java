package backend.pirocheck.User.service;

import backend.pirocheck.User.entity.User;
import backend.pirocheck.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User login(String name, String password) {
        return userRepository.findByName(name)
                .filter(user -> user.getPassword().equals(password))
                .orElseThrow(() -> new IllegalArgumentException("이름 또는 비밀번호가 일치하지 않습니다."));
    }
}
