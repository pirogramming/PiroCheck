package backend.pirocheck.attendence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "attendence")
public class Attendence {
    @Id
    private Long id;

    private Long userId;
    private int week;

}
