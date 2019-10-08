import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {
    private List<Account> accounts;
    private final Random random = new Random();
    private HashMap<String, Long> accountNumbers;
    private List<Integer> blockedAccounts = new ArrayList<>();

    public Bank(List<Account> accounts) {
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
    public synchronized void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
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
                    System.out.printf("Счет №%s %s %s \n", accountFrom.getAccNumber(), accountFrom.getStatus(), accountFrom.isBlocked());
                    blockedAccounts.add(accountFromIndex);

                    accountTo.setBlocked(true);
                    System.out.printf("Счет №%s %s %s \n", accountTo.getAccNumber(), accountTo.getStatus(), accountTo.isBlocked());
                    blockedAccounts.add(accountToIndex);
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

    public void printBlockedAccounts() {
        Collections.sort(blockedAccounts);
        blockedAccounts.forEach(System.out::println);
    }
}
