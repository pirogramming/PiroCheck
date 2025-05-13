package backend.pirocheck.ManageStudents.service;

import backend.pirocheck.ManageStudents.dto.response.ManageStudentsListResDto;
import backend.pirocheck.User.entity.Role;
import backend.pirocheck.User.entity.User;
import backend.pirocheck.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManageStudentsService {

    private final UserRepository userRepository;

    public List<ManageStudentsListResDto> searchStudents(String name) {
        List<User> users;

        if(name == null || name.isBlank()) {
            // 검색어가 없으면 맴버 전체 조회
            users = userRepository.findByRole(Role.MEMBER);
        }
        else {
            // 이름 검색
            users = userRepository.findByNameContainingAndRole(name, Role.MEMBER);
        }

        return users.stream()
                .map(user -> new ManageStudentsListResDto(user.getId(), user.getName()))
                .collect(Collectors.toList());
    }
}
