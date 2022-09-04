package bank.model;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Data()
@RequiredArgsConstructor
public class User extends Id {

    @NonNull
    private String passport;

    @NonNull
    private String username;

    private List<Account> accounts = new CopyOnWriteArrayList<>();

    public void addAccount(Account account) {
        accounts.add(account);
    }
}
