package backend.pirocheck.Deposit.init;

import backend.pirocheck.Deposit.entity.Deposit;
import backend.pirocheck.Deposit.repository.DepositRepository;
import backend.pirocheck.User.entity.User;
import backend.pirocheck.User.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepositInitRunner implements CommandLineRunner {

    private final DepositRepository depositRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        for (User user : userRepository.findAll()) {
            if (!depositRepository.existsByUser(user)) {
                // 보증금 레코드가 없으면 새로 생성
                Deposit deposit = Deposit.builder()
                        .user(user)
                        .amount(120000)
                        .descentAssignment(0)
                        .descentAttendance(0)
                        .ascentDefence(0)
                        .build();

                depositRepository.save(deposit);
            }
        }
    }


}
