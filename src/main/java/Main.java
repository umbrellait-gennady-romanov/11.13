import java.util.ArrayList;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Bank bank = new Bank();

        Supplier<Long> random52632 = ()-> Math.round(Math.random() * 49999 + 1);
        Supplier<String> random50 = ()-> Long.toString(Math.round(Math.random() * 49 + 1));

        for (int i = 1; i <= 50; i++ ) {
            bank.setAccounts(new Account(random52632.get(), Integer.toString(i)));
        }
        ArrayList<Thread> threads = new ArrayList<>();
        for (int t = 1; t <= 10; t++) {
            threads.add(new Thread(() -> {
                for (int i = 1; i <= 10; i++) {
                    try {
                        bank.transfer(random50.get(), random50.get(), random52632.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
        }
        threads.forEach(t->t.start());
        threads.forEach(t-> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        for (int i =1; i <= 50; i++) {
            System.out.println(bank.getBalance(Integer.toString(i)));
        }
    }
}
