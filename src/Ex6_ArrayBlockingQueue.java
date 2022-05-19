import java.util.concurrent.ArrayBlockingQueue;

public class Ex6_ArrayBlockingQueue {

    public static void main(String[] args) {

        ArrayBlockingQueue<Integer> syncArrayQueue = new ArrayBlockingQueue<>(5);

        new Thread() {
            public void run() {
                int i = 0;
                try {
                    while (true) {
                        syncArrayQueue.put(++i);
                        System.out.println("Добавлен элемент " + i);
                        System.out.println(syncArrayQueue);
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException ignored) {}
            }
        }.start();

        new Thread(() ->{
            try {
                while (true) {
                    int taken = syncArrayQueue.take();
                    System.out.println("Забрали элемент " + taken);
                    System.out.println(syncArrayQueue);
                    Thread.sleep(7000);
                }
            } catch (InterruptedException ignored) {}

        }).start();

    }

}
