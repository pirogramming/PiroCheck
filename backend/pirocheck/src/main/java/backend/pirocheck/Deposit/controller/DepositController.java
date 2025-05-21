package backend.pirocheck.Deposit.controller;


import backend.pirocheck.Deposit.dto.DepositResDto;
import backend.pirocheck.Deposit.service.DepositService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "보증금 관리", description = "수강생 보증금/차감/방어권 관련 API")
@RestController
@RequestMapping("/api/deposit")
@RequiredArgsConstructor
public class DepositController {

    private final DepositService depositService;

    @Operation(summary = "보증금 조회", description = "해당 유저의 현재 보증금, 차감 내역, 방어권 금액을 반환합니다.")
    @GetMapping("/{userId}")
    public DepositResDto getDeposit(@PathVariable Long userId) {
        return depositService.getDeposit(userId);
    }
}
