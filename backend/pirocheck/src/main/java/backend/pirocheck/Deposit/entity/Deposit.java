package backend.pirocheck.Deposit.entity;

import backend.pirocheck.User.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private int amount; // 현재 보증금
    private int descentAssignment; // 과제 차감 총합
    private int descentAttendance; // 출석 차감 총합
    private int ascentDefence;  // 방어권

    // 보증금 계산
    public void updateAmounts(int descentAssignment, int descentAttendance, int ascentDefence) {
        this.descentAssignment = descentAssignment;
        this.descentAttendance = descentAttendance;
        this.ascentDefence = ascentDefence;

        int calculateAmount = 120000 - descentAssignment - descentAttendance + ascentDefence;
        this.amount = Math.max(0, Math.min(calculateAmount, 120000));  // 0 이상 12만원 이하
    }

    // 방어권 업데이트
    public void updateDefence(int newAscentDefence) {
        this.ascentDefence = newAscentDefence;
        int calculateAmount = 120000 - this.descentAssignment - this.descentAttendance + newAscentDefence;
        this.amount = Math.max(0, Math.min(calculateAmount, 120000));  // 0 이상 12만원 이하

    }

}
