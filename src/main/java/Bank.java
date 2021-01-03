import java.util.HashMap;
import java.util.Random;

public class Bank
{
    private HashMap<String, Account> accounts = new HashMap<>();
    private final Random random = new Random();

    public synchronized boolean isFraud(String fromAccountNum, String toAccountNum, long amount)
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("interrupt ex" + e.getMessage());
        }
        return random.nextBoolean();
    }

    /**
     * TODO: реализовать метод. Метод переводит деньги между счетами.
     * Если сумма транзакции > 50000, то после совершения транзакции,
     * она отправляется на проверку Службе Безопасности – вызывается
     * метод isFraud. Если возвращается true, то делается блокировка
     * счетов (как – на ваше усмотрение)
     */
    public synchronized void transfer(String fromAccountNum, String toAccountNum, long amount) {
        if (!accounts.containsKey(fromAccountNum)) {
            System.out.println(fromAccountNum + " - счёта не существует.");
            return;
        }
        if (!accounts.containsKey(toAccountNum)) {
            System.out.println(toAccountNum + " - счёта не существует.");
            return;
        }
        if (accounts.get(fromAccountNum).getAccNumber().equals("blocked")) {
            System.out.println(fromAccountNum + " - заблокирован.");
            return;
        }
        if (accounts.get(toAccountNum).getAccNumber().equals("blocked")) {
            System.out.println(toAccountNum + " - заблокирован.");
            return;
        }
        if (amount > 50000 && isFraud(fromAccountNum, toAccountNum, amount)) {
            accounts.get(fromAccountNum).setAccNumber("blocked");
            accounts.get(fromAccountNum).setMoney(0);
            accounts.get(toAccountNum).setAccNumber("blocked");
            accounts.get(toAccountNum).setMoney(0);

            System.out.println(fromAccountNum + ", " + toAccountNum + " - счета заблокированы, мошенничество");
            return;
        }
        if (accounts.get(toAccountNum).getMoney() < amount) {
            System.out.println("На счёте " + fromAccountNum + " недостаточно сердств");
            return;
        }
        Account from = accounts.get(fromAccountNum);
        from.setMoney(from.getMoney() - amount);
        Account to = accounts.get(toAccountNum);
        to.setMoney(to.getMoney() + amount);
    }

    /**
     * TODO: реализовать метод. Возвращает остаток на счёте.
     */
    public long getBalance(String accountNum)
    {
        if (!accounts.containsKey(accountNum)) {
            System.out.println(accountNum + " - такого счёта нет.");
            return 0;
        }
        if (accounts.get(accountNum).getAccNumber().equals("blocked")) {
            System.out.println(accountNum + " - счёт заблокирован.");
            return 0;
        }
        return accounts.get(accountNum).getMoney();
    }

    public Account getAccounts(String account) {
        return accounts.get(account);
    }

    public void setAccounts(Account account) {
        this.accounts.put(account.getAccNumber(), account);
    }
}
