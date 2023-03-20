class CounterObject
{
    int counter = 0;
    
    public void increment() {
     counter++;
    }
    
    public void decrement() {
     counter--;
    }
}

class ProcessingThread implements Runnable {

    private CounterObject res;
    ProcessingThread(CounterObject res)
    {
        this.res = res;
    }    
    private static final Object lock = new Object();
    @Override
    public void run() {
        while (res.counter <= 100) {
            synchronized(res) {
             res.increment();
             System.out.println(Thread.currentThread().getName() + " " + res.counter);
             if (res.counter == 100)
             {
              
              try {
               wait();
              }
              catch (InterruptedException e) {}
              System.out.println("I'm winner " + Thread.currentThread().getName());
              //break;
             }
             if (res.counter > 100)
             {
              System.out.println("I'm late.... " + Thread.currentThread().getName());
              notify();
              // res.decrement();
              //break;
             }
          
            }
        }
    }
   

    public int getCount() {
        return this.res.counter;
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
        //System.out.println("I'm late " + pt.getLooser());
        //System.out.println("I'm winner " + pt.getWinner());
        System.out.println("Processing count=" + pt.getCount());
    }
}
