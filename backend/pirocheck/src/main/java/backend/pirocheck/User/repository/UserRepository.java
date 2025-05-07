package backend.pirocheck.User.repository;

import backend.pirocheck.User.entity.Role;
import backend.pirocheck.User.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);

    List<User> findByRole(Role role);
}
