import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class Bank {
    private HashMap<String, Account> accounts;
    private final Random random = new Random();
    private List<Account> accountNumbers;
//    private HashMap<String, Long> accountNumbers;

    public Bank(HashMap<String, Account> accounts) {
        this.accounts = accounts;
//        createAccountNumberList(accounts);
    }

    private void createAccountNumberList(List<Account> accountNumbers) {
        accounts.keySet()
                .forEach(name ->
                        accountNumbers.add(
                                new Account(accounts.get(name).getMoney(), accounts.get(name).getAccNumber()
                                )
                        )
                );
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
        try {
//            accountNumbers.put(toAccountNum, accountNumbers.get(toAccountNum) + amount);
//            accountNumbers.put(fromAccountNum, accountNumbers.get(fromAccountNum) - amount);
            if (amount > 50000) {
                isFraud(fromAccountNum, toAccountNum, amount);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum) {
        AtomicLong money = new AtomicLong();
        accounts.keySet()
                .forEach(name -> {
                    if (accounts.get(name).getAccNumber().equals(accountNum)) {
                        money.set(accounts.get(name).getMoney());
                    }
                });

        return money.get();
    }

//    private boolean isAccountExist(String accountNum) {
//        boolean flag = false;
//        for (String name : accounts.keySet()) {
//            if (accounts.get(name).getAccNumber().equals(accountNum)){
//                flag = true;
//            }
//        }
//        return flag;
//    }
}
