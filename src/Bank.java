import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {
    private List<Account> accounts;
    private final Random random = new Random();
    private List<Account> blockedAccounts = new ArrayList<>();

    Bank(List<Account> accounts) {
        this.accounts = accounts;
    }

    private synchronized boolean isFraud()
            throws InterruptedException {
        Thread.sleep(1000);
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    synchronized void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        int accountFromIndex = Integer.valueOf(fromAccountNum);
        int accountToIndex = Integer.valueOf(toAccountNum);
        Account accountFrom = accounts.get(accountFromIndex);
        Account accountTo = accounts.get(accountToIndex);

        if (!accountFrom.isBlocked() && !accountTo.isBlocked()) {

            if (accountFrom.getMoney() <= amount) {
                amount = accountFrom.getMoney();
            }

            accountFrom.setMoney(accountFrom.getMoney() - amount);
            accountTo.setMoney(accountTo.getMoney() + amount);

            if (amount > 50000) {
                System.out.println("Транзакция направлена на проверку...");
                if (isFraud()) {
                    accountFrom.setBlocked(true);
                    System.out.printf("Счет №%s %s \n", accountFrom.getAccNumber(), accountFrom.getStatus());
                    blockedAccounts.add(accountFrom);

                    accountTo.setBlocked(true);
                    System.out.printf("Счет №%s %s \n", accountTo.getAccNumber(), accountTo.getStatus());
                    blockedAccounts.add(accountTo);
                }
            }
        }
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        AtomicLong money = new AtomicLong();
        accounts.forEach(accountNumber -> {
            if (accountNumber.getAccNumber().equals(accountNum)) {
                money.set(accountNumber.getMoney());
            }
        });

        return money.get();
    }

    void printBlockedAccounts() {
        Collections.sort(blockedAccounts);
        blockedAccounts.forEach(account ->
                System.out.printf("%3s %6s %s \n", account.getAccNumber(), account.getMoney(), account.getStatus()));
    }
}
