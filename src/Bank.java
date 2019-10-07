import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {
    //    private HashMap<String, Long> accounts;
    private List<Account> accounts;
    //    private CopyOnWriteArrayList<Account> accounts;
    private final Random random = new Random();
    private HashMap<String, Long> accountNumbers;

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
    public void transfer(String fromAccountNum, String toAccountNum, long amount) throws InterruptedException {
        Account accountFrom = null;
        Account accountTo = null;
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).isExist() && !accounts.get(i).isBlocked())
                if (accounts.get(i).getAccNumber().equals(fromAccountNum)) {
                    accountFrom = accounts.get(i);
                }
            if (accounts.get(i).isExist() && !accounts.get(i).isBlocked())
                if (accounts.get(i).getAccNumber().equals(toAccountNum)) {
                    accountTo = accounts.get(i);
                }
        }

        if (accountFrom.getMoney() <= amount) {
            amount = accountFrom.getMoney();
        }

        synchronized (this) {

            accountFrom = new Account(accountFrom.getAccNumber(), accountFrom.getMoney() - amount);
        accountTo = new Account(accountTo.getAccNumber(), accountTo.getMoney() + amount);

        if (amount > 50000) {
            if (isFraud()) {
                accountFrom.setBlocked(true);
                accountTo.setBlocked(true);
            }
        }

            accountsRefresh(accountFrom);
            accountsRefresh(accountTo);
        }
    }

    private void accountsRefresh(Account account) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccNumber().equals(account.getAccNumber())) {
                accounts.set(i, new Account(account.getAccNumber(), account.getMoney()));
                accounts.get(i).setBlocked(account.isBlocked());
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
}
