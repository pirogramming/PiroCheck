package backend.pirocheck.User.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Integer generation;

}