import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {

        List<Account> accounts = new ArrayList<Account>() {{
            for (int i = 0; i < 100; i++) {
                add(new Account(String.valueOf(i), Math.round(Math.random() * 200_000)));
            }
        }};

        Bank bank = new Bank(accounts);

        ExecutorService executor = Executors.newFixedThreadPool(100);

        accounts.forEach(account -> System.out.println(account.getMoney() + " " + account.isBlocked() + " " + account.getAccNumber()));

        for (int i = 0; i < 100; i++) {
            int accountFromIndex = random.nextInt(accounts.size());
            int accountToIndex = random.nextInt(accounts.size());
            String accountFrom = accounts.get(accountFromIndex).getAccNumber();
            String accountTo = accounts.get(accountToIndex).getAccNumber();

            if (accountFromIndex != accountToIndex) {
                executor.execute(() -> {
                    try {
                        bank.transfer(accountFrom, accountTo, Math.round(Math.random() * 100_000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
        executor.shutdown();
        executor.awaitTermination(60, TimeUnit.SECONDS);
        executor.shutdownNow();

        accounts.forEach(account -> System.out.println(account.getMoney() + " " + account.isBlocked() + " " + account.getAccNumber()));
        bank.printBlockedAccounts();

//        accounts.forEach(account -> System.out.println(account.getMoney() + " " + account.isBlocked() + " " + account.getAccNumber()));
//
//        System.out.println("\n" + bank.getBalance("1"));
//        System.out.println("\n" + bank.getBalance("4"));
//
//        bank.transfer("1", "4", 60000);
//
//        System.out.println("\n" + bank.getBalance("1"));
//        System.out.println("\n" + bank.getBalance("4"));
//
//        accounts.forEach(account -> System.out.println(account.getMoney() + " " + account.getStatus() + " " + account.getAccNumber()));

    }
}
