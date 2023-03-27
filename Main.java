class CounterObject
{
    
    private static volatile Integer counter = 0;
    
    private CounterObject(){}
    
    public static Integer increment() {    
     synchronized(CounterObject.class){
     counter+=1;
     return counter;
     }
    }
    
    public static Integer getCounter() {
     return counter;
    }
    
    public static void decrement() {
     counter -=1;
    }
}

class ProcessingThread implements Runnable {

    
    private static final Object lock = new Object();
    
    @Override
    public void run() {
        while (CounterObject.getCounter() <= 100) {
             
             Integer increment = CounterObject.increment();
             synchronized(lock) {
             if (increment == 100)
             {
              
              try {
               lock.wait();
              }
              catch (InterruptedException e) {}
              System.out.println("I'm winner " + Thread.currentThread().getName());
              
             }
             if (increment > 100)
             {
              System.out.println("I'm late.... " + Thread.currentThread().getName());
              lock.notify();
              
             }
          
            }
        }
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
        CounterObject.decrement();
        System.out.println("Processing count=" + CounterObject.getCounter());
    }
}
