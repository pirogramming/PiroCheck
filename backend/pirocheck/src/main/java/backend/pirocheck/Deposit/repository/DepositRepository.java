package backend.pirocheck.Deposit.repository;

import backend.pirocheck.Deposit.entity.Deposit;
import backend.pirocheck.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepositRepository extends JpaRepository<Deposit, Long> {
    Deposit findByUser(User user);
    boolean existsByUser(User user); // 중복 방지를 위한 존재 여부 확인
}
