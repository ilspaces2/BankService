package bank.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Account extends Id {

    @NonNull
    private String requisite;

    @NonNull
    private double balance;

    private User user;
}
