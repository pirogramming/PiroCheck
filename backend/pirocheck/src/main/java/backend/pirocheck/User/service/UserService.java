package backend.pirocheck.User.service;

import backend.pirocheck.User.entity.User;
import backend.pirocheck.User.exception.InvalidLoginException;
import backend.pirocheck.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User login(String name, String password) {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new InvalidLoginException("해당 사용자가 존재하지 않습니다."));

        if (!user.getPassword().equals(password)) {
            throw new InvalidLoginException("비밀번호가 일치하지 않습니다.");
        }

        return user;
    }
}

