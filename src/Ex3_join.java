public class Ex3_join {

        public static void main(String[] args) {
            Thread t1 = new Thread(Ex3_join::run);
            Thread t2 = new Thread(Ex3_join::run);
            Thread t3 = new Thread(Ex3_join::run);

            System.out.println("Поток main имеет имя " + Thread.currentThread().getName());

            t1.start();

            // метод join приостанавливает выполнение кода до тех пор,
            // пока не завершится поток, к которому вызван метод join.
            // В данном случае программа продолжится только после завершения выполнения потока t1

            try {
                t1.join();
            } catch (InterruptedException e){}

            // стартуем второй поток только после завершения выполнения первого потока
            t2.start();

            // стартуем 3-й поток через 2 секунды после запуска второго,
            // либо раньше (если второй поток завершит своё выполнение быстрее чем за 2 секунды)
            try {
                t2.join(2000);
            } catch (InterruptedException e) {}

            t3.start();


            // даем потокам t2 и t3 возможность закончить выполнение перед тем,
            // как программа (главный поток) закончит свое выполнение
            try {
                t2.join();
                t3.join();
            } catch (InterruptedException e) {}



            System.out.println("Все потоки отработали, завершаем программу");


        }

    public static void run() {
        System.out.println("Поток начал работу " + Thread.currentThread().getName());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {}

        System.out.println("Поток отработал " + Thread.currentThread().getName());
    }




}

