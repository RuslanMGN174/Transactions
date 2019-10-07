import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        List<Account> accounts = new ArrayList<Account>() {{
            for (int i = 0; i < 10; i++) {
                add(new Account(String.valueOf(i), Math.round(Math.random() * 200_000)));
            }
        }};

        accounts.forEach(account -> System.out.println(account.getMoney() + " " + account.isBlocked() + " " + account.getAccNumber()));

        Bank bank = new Bank(accounts);
        System.out.println("\n" + bank.getBalance("1"));
        System.out.println("\n" + bank.getBalance("4"));

        bank.transfer("1", "4", 60000);

        System.out.println("\n" + bank.getBalance("1"));
        System.out.println("\n" + bank.getBalance("4"));

        accounts.forEach(account -> System.out.println(account.getMoney() + " " + account.getStatus() + " " + account.getAccNumber()));

    }
}
