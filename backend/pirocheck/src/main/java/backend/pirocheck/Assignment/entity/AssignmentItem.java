package backend.pirocheck.Assignment.entity;

import backend.pirocheck.User.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 유저별 과제 정보를 저장하는 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 과제 결과
    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private AssignmentStatus submitted; // 수강생의 과제 제출여부
}
