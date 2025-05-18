package backend.pirocheck.Assignment.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 과제명
    private String assignmentName;

    // 주차
    private Long week;

    // 요일
    private String day;

    // 과제 번호
    private Long orderNumber;

    // AssignmentItem 입장에서 "assignment" 필드의 외래 키를 가진 주인
    // assignment를 참조하는 assignmentitem 컬랙션을 가짐
    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignmentItem> assignments = new ArrayList<>();

    // 연관관계 편의 메서드 (양방향 시 자주 사용)
    public void addAssignmentItem(AssignmentItem assignmentItem) {
        this.assignments.add(assignmentItem);
        assignmentItem.setAssignment(this);
    }

    // 관리자가 생성
    public static Assignment create(String assignmentName, Long week, String day, Long orderNumber) {
        return Assignment.builder()
                .assignmentName(assignmentName)
                .week(week)
                .day(day)
                .orderNumber(orderNumber)
                .build();
    }

    // 과제 내용 업데이트
    public void update(String assignmentName, Long week, String day, Long orderNumber) {
        this.assignmentName = assignmentName;
        this.week = week;
        this.day = day;
        this.orderNumber = orderNumber;
    }
}
