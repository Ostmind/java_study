import java.util.concurrent.atomic.AtomicInteger;


class ProcessingThread implements Runnable {

    private AtomicInteger count = new AtomicInteger();
    private String winner = new String();
    private String looser = new String();

    @Override
    public void run() {
        while (count.get() <= 100) {
            count.incrementAndGet();
            if (count.get() == 100)
            {
             winner = Thread.currentThread().getName();
             break;
            }
            if (count.get() > 100)
            {
             looser = Thread.currentThread().getName();
             count.decrementAndGet();
             break;
            }
        }
    }

    public int getCount() {
        return this.count.get();
    }
    
    public String getWinner() {
        return this.winner;
    }
    
    public String getLooser() {
        return this.looser;
    }    
}

public class Main {

    public static void main(String[] args) throws InterruptedException {

        ProcessingThread pt = new ProcessingThread();
        Thread t1 = new Thread(pt, "t1");
        t1.start();
        Thread t2 = new Thread(pt, "t2");
        t2.start();
        t1.join();
        t2.join();
        System.out.println("I'm late " + pt.getLooser());
        System.out.println("I'm winner " + pt.getWinner());
        System.out.println("Processing count=" + pt.getCount());
    }
}
