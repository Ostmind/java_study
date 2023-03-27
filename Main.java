import java.util.List;
import java.util.concurrent.*;

public class Main {
    private static final int COUNT_STRIP = 5; // кол-во полос
    private static final int COUNT_PLANES = 10; // кол-во самолетов
    private static Semaphore semaphore = null;

    public static class Plane implements Runnable {
        private final int planeNum;
        private static final LinkedBlockingQueue<Integer> controlStrip = new LinkedBlockingQueue<>();

        public Plane(int planeNum) {

            this.planeNum = planeNum;
        }

        public static void setControlStrip() {
            for (int i = 0; i < COUNT_STRIP; i += 1)
                controlStrip.add(i);
        }

        @Override
        public void run() {
            try {
                // Запрос разрешения
                semaphore.acquire();
                int controlNum = controlStrip.take();
                System.out.println("Самолет " + planeNum + " выруливает на полосу " + controlNum);
                System.out.println("Полоса " + controlNum + " приняла самолет " + planeNum);
                // взлетаем
                TimeUnit.SECONDS.sleep(1);
                System.out.println("Самолет " + planeNum + " взлетел");
                // Освобождение полосы
                controlStrip.add(controlNum);
                System.out.println("Полоса " + controlNum + " освободилась");
                semaphore.release();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Critical Thread Error");
            }
        }
    }

    public static void main(String[] args) {
        try {
            semaphore = new Semaphore(COUNT_STRIP,
                    true);
            Plane.setControlStrip();//Первоначальное заполнение всех полос
            ExecutorService executor = Executors.newFixedThreadPool(COUNT_PLANES);
            for (int i = 1; i <= COUNT_PLANES; i++) {
                Runnable worker = new Plane(i);
                executor.execute(worker);
            }
            executor.shutdown();
            final boolean done = executor.awaitTermination(1, TimeUnit.MINUTES);//даем минуту на взлет всех самолетов
            if (done)
            {
                System.out.println("Все самолеты взлетели");
            }
            else {
                final List<Runnable> rejected = executor.shutdownNow();
                System.out.println("Самолетов осталось на земле: " + rejected.size());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.print("Critical Program Error");
        }
    }
}
