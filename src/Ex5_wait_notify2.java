public class Ex5_wait_notify2 {

    public static void main(String[] args) {

        final Object object = new Object();     //создаём стандартный универсальный объект класс Object

        Thread t1 = new Thread(() -> {
            synchronized (object) {
                System.out.println("T1 start!");
                try {
                    object.wait();
                } catch (InterruptedException e) {}
                System.out.println("T1 end!");
            }
        });

        Thread t2 = new Thread() {    // код потока описываем в виде анонимного класса
            public void run() {
                synchronized (object) {
                    System.out.println("T2 start!");
                    object.notify();
                    System.out.println("T2 end!");
                }
            }
        };

        t1.start();
        t2.start();

    }

}



