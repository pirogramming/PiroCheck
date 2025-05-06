package backend.pirocheck.Deposit.controller;


import backend.pirocheck.Deposit.dto.DepositResDto;
import backend.pirocheck.Deposit.service.DepositService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/deposit")
@RequiredArgsConstructor
public class DepositController {

    private final DepositService depositService;

    @GetMapping("/{userId}")
    public DepositResDto getDeposit(@PathVariable Long userId) {
        return depositService.getDeposit(userId);
    }
}
