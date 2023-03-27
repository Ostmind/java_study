import java.util.concurrent.*;

public class Main
{
    private static final int countStrip = 5; // кол-во полос
    private static final int countPlanes = 10; // кол-во самолетов
    private static Semaphore semaphore = null;

    public static class Plane implements Runnable
    {
        private final int planeNum;
        private static final LinkedBlockingQueue<Integer> controlStrip = new LinkedBlockingQueue<>();
        public Plane(int planeNum)  {

            this.planeNum = planeNum;
        }
        public static void setControlStrip(){
            for (int i =0;i<countStrip;i+=1)
            controlStrip.add(i);
        }
        @Override
        public void run() {
            try {
                // Запрос разрешения
                semaphore.acquire();
                int controlNum = controlStrip.take();
                System.out.println("Самолет "+planeNum+" выруливает на полосу "+controlNum);
                System.out.println("Полоса "+controlNum+" приняла самолет "+planeNum);
                // взлетаем
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Самолет " + planeNum + " взлетел");
                // Освобождение полосы
                controlStrip.add(controlNum);
                System.out.println("Полоса " + controlNum + " освободилась");
                semaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    public static void main(String[] args)
    {
        semaphore = new Semaphore(countStrip,
                true);
        Plane.setControlStrip();//Первоначальное заполнение всех полос
        ExecutorService executor = Executors.newFixedThreadPool(countPlanes);
        for (int i = 1; i <= countPlanes; i++) {
            Runnable worker = new Plane(i);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) { }
        System.out.println("Все самолеты взлетели");
    }
}
