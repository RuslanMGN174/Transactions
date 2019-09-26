import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {
    private HashMap<String, Account> accounts;
    private final Random random = new Random();

    public Bank(HashMap<String, Account> accounts) {
        this.accounts = accounts;
    }

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
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
    public void transfer(String fromAccountNum, String toAccountNum, long amount) {

    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        AtomicLong money = new AtomicLong();
        accounts.keySet()
                .forEach(name -> {if (accounts.get(name).getAccNumber().equals(accountNum)){
                    money.set(accounts.get(name).getMoney());
                }});

        return money.get();
    }

    private boolean isAccountExist(String accountNum) {
        boolean flag = false;
        for (String name : accounts.keySet()) {
            if (accounts.get(name).getAccNumber().equals(accountNum)){
                flag = true;
            }
        }
        return flag;
    }
}
