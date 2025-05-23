package backend.pirocheck.Deposit.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepositResDto {
    @Schema(description = "현재 보증금 잔액", example = "110000")
    private int amount;

    @Schema(description = "과제 차감 총액", example = "10000")
    private int descentAssignment;

    @Schema(description = "출석 차감 총액", example = "10000")
    private int descentAttendance;

    @Schema(description = "방어권 보상 총액", example = "10000")
    private int ascentDefence;

}
