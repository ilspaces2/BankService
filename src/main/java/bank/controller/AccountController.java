package bank.controller;

import bank.model.Account;
import bank.service.SimpleBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

    private final SimpleBankService simpleBankService;

    @PostMapping
    public Account addAccount(@RequestBody Map<String, String> body) {
        var passport = body.get("passport");
        var account = new Account(body.get("requisite"), 0);
        simpleBankService.addAccount(passport, account);
        return account;
    }

    /**
     * Выкидываем ResponseStatusException в случае если не найдем реквизиты.
     * В конструкторе указываем статус и сообщение.
     * Чтобы сообщение было видно то в проперти пишем server.error.include-message=always
     */
    @GetMapping
    public Account findByRequisite(@RequestParam String passport, @RequestParam String requisite) {
        return simpleBankService.findByRequisite(passport, requisite)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account is not found. Please, check requisites."
                ));
    }
}
