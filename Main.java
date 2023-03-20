class CounterObject
{
    int counter = 0;
}

class ProcessingThread implements Runnable {

    CounterObject res;
    ProcessingThread(CounterObject res)
    {
        this.res = res;
    }    
    private String winner = new String();
    private String looser = new String();

    @Override
    public void run() {
        while (res.counter <= 100) {
            synchronized(res) {
             res.counter++;
            }
            if (res.counter == 100)
            {
             winner = Thread.currentThread().getName();
             break;
            }
            if (res.counter > 100)
            {
             looser = Thread.currentThread().getName();
             res.counter--;
             break;
            }
        }
    }

    public int getCount() {
        return this.res.counter;
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
        CounterObject counterObject = new CounterObject();
        ProcessingThread pt = new ProcessingThread(counterObject);
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
