
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ex7_fixedThreads {

    public static void main(String[] args) {

        // определяем фиксированное количество потоков для нашей
        // программы, которые будут выполняться одновременно
        ExecutorService service = Executors.newFixedThreadPool(2);


        // ниже задаём блок потоков для выполнения, но выполняться параллельно
        // будут только в количестве, определённом в предыдущей строке
        service.submit(new Printer("1й"));
        service.submit(new Printer("2й"));
        service.submit(new Printer("3й"));
        service.submit(new Printer("4й"));
        service.submit(new Printer("5й"));

    }


}


class Printer extends Thread {

    String name;

    public Printer(String name) {
        this.name = name;
    }

    public void run() {
        int i = 0;

        while (i<100) {

            System.out.println(name + " поток " + ++i);

            try{
                Thread.sleep(50);
            } catch (InterruptedException ignored){}

        }

    }
}