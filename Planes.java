import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main
{
    private static final int countStrip = 5;
    private static final int countPlanes = 10;
    // Флаги мест контроля
    private static boolean[] controlStrip = null;

    private static Semaphore semaphore = null;

    public static class Plane implements Runnable
    {
        private int planeNum;

        public Plane(int planeNum)  {
            this.planeNum = planeNum;
        }

        @Override
        public void run() {
            try {
                // Запрос разрешения
                semaphore.acquire();
                int controlNum = -1;
                synchronized (controlStrip){
                    for (int i = 0;
                         i < countStrip; i++)
                        if (controlStrip[i]) {
                            // Занимаем место
                            controlStrip[i] = false;
                            controlNum = i;
                            System.out.println("Самолет "+planeNum+" выруливает на полосу "+i);
                            System.out.println("Полоса "+i+" приняла самолет "+planeNum);
                            break;
                        }
                }

                // взлетаем
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Самолет " + planeNum + " взлетел");
                // Освобождение полосы
                synchronized (controlStrip) {
                    controlStrip[controlNum] = true;
                }
                System.out.println("Полоса " + controlNum + " освободилась");
                semaphore.release();
            } catch (InterruptedException e) {}
        }
    }
    public static void main(String[] args)
            throws InterruptedException
    {
        controlStrip = new boolean[countStrip];
        for (int i = 0; i < countStrip; i++)
            controlStrip[i] = true;
        semaphore = new Semaphore(controlStrip.length,
                true);
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
