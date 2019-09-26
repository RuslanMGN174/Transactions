import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        HashMap<String, Account> accounts = new HashMap<String, Account>(){{
            for (int i = 0; i < 10; i++) {
                put("somePerson" + i, new Account(Math.round(Math.random() * 100_000), String.valueOf(i)));
            }
        }};

        accounts.keySet().forEach(s ->
                System.out.println(accounts.get(s).getMoney() + " " + accounts.get(s).getAccNumber()));

        Bank bank = new Bank(accounts);
        System.out.println("\n" + bank.getBalance("20"));

    }
}
