package backend.pirocheck.User.entity;

import backend.pirocheck.Assignment.entity.AssignmentItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;  // 전화번호 일부

    private String name;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;  // MEMBER or ADMIN

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignmentItem> assignmentItems = new ArrayList<>();

    private Integer generation;

    // AssignmentItem 입장에서 "user" 필드의 외래 키를 가진 주인
    // assignment를 참조하는 assignmentitem 컬랙션을 가짐
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignmentItem> assignments = new ArrayList<>();

    // 연관관계 편의 메서드 (양방향 시 자주 사용)
    public void addAssignmentItem(AssignmentItem assignmentItem) {
        this.assignments.add(assignmentItem);
        assignmentItem.setUser(this);
    }

}