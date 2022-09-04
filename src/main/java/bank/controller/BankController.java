package bank.controller;

import bank.service.SimpleBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bank")
public class BankController {

    private final SimpleBankService simpleBankService;

    @PostMapping("")
    public void transfer(@RequestBody Map<String, String> body) {
        var srcPassport = body.get("srcPassport");
        var srcRequisite = body.get("srcRequisite");
        var destPassport = body.get("destPassport");
        var destRequisite = body.get("destRequisite");
        var amount = Double.parseDouble(body.get("amount"));
        simpleBankService.transferMoney(
                srcPassport, srcRequisite,
                destPassport, destRequisite,
                amount
        );
    }
}
