public class Ex1_parallel_threads {
    public static void main(String[] args) {


        Thread myThread1 = new Thread(() -> {
            for ( int i=0; i<50; ++i){
                System.out.println(i + " 111 - " + Thread.currentThread().getName());
            }
        });

        Thread myThread2 = new Thread(() -> {
            for ( int i=0; i<50; ++i){
                System.out.println(i + " 22222 - " + Thread.currentThread().getName());
            }
        });


        Thread myThread3 = new Thread(() -> {
            for ( int i=0; i<50; ++i){
                System.out.println(i + " 33333333 - " + Thread.currentThread().getName());
            }
        });

        myThread1.start();
        myThread2.start();
        myThread3.start();

    }
}
