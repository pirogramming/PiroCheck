package backend.pirocheck.Deposit.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositResDto {
    private int amount;
    private int descentAssignment;
    private int descentAttendance;
    private int ascentDefence;

}
