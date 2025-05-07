package backend.pirocheck.assignment.entity;

import backend.pirocheck.User.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // user 정보와 과제 정보를 연결해 저장
     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "user_id")
     private User user; // user를 생성했을 때의 해당 user 엔티티

    // 과제명
    private String assignmentName;

    // 주차
    private Long week;

    // 요일
    private Long section;

    // 과제 번호
    private Long orderNumber;

    // 과제 결과
    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private AssignmentStatus submitted;

    // 관리자가 생성
    public static Assignment create(String assignmentName, Long week, Long section, Long orderNumber) {
        return Assignment.builder()
                .assignmentName(assignmentName)
                .week(week)
                .section(section)
                .orderNumber(orderNumber)
                .build();
    }
}
