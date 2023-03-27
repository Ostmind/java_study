import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.*;


public class Main
{
    private static final int countStrip = 5;
    private static final int countPlanes = 10;
    // Флаги мест контроля
    //private static boolean[] controlStrip = null;

    private static Semaphore semaphore = null;

    public static class Plane implements Runnable
    {
        private int planeNum;
        private static final Lock l = new ReentrantLock();
        private static final LinkedList<Integer> controlStrip = new LinkedList<>();
        public Plane(int planeNum)  {
            this.planeNum = planeNum;
        }
        public static void setControlStrip(){
            for (int i =0;i<countStrip;i+=1)
            controlStrip.add(i);
        }
        public static Integer getControlStrip(){
            return controlStrip.removeFirst();
        }
        public static void setStrip(int i){
            controlStrip.addLast(i);
        }
        @Override
        public void run() {
            try {
                l.lock();
                // Запрос разрешения
                semaphore.acquire();
                int controlNum = getControlStrip();
                System.out.println("Самолет "+planeNum+" выруливает на полосу "+controlNum);
                System.out.println("Полоса "+controlNum+" приняла самолет "+planeNum);
                // взлетаем
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Самолет " + planeNum + " взлетел");
                // Освобождение полосы
                setStrip(controlNum);
                System.out.println("Полоса " + controlNum + " освободилась");
                semaphore.release();
            } catch (InterruptedException e) {}
            finally {
                l.unlock();
            }
        }
    }
    public static void main(String[] args)
            throws InterruptedException
    {
        semaphore = new Semaphore(countStrip,
                true);
        Plane.setControlStrip();
        ExecutorService executor = Executors.newFixedThreadPool(countPlanes);
        for (int i = 1; i <= countPlanes; i++) {
            Runnable worker = new Plane(i);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {   }
        System.out.println("Все самолеты взлетели");
    }
}