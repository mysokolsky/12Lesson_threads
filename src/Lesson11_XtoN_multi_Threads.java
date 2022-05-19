
//        Создать программу, для параллельных вычислений. Для имитации долгих расчетов можно использовать sleep.
//        Необходимо создать скрипт принимающий на вход число и его степень, и затем выполняющий операцию возведения.
//        Необходимо реализовать выполнения операции в много поточном режиме, при этом главный поток
//        программы должен отображать меню и запрашивать новые данные, и создавать новые потоки на возведение в степень


import java.util.Scanner;

public class Lesson11_XtoN_multi_Threads {

    public static void main(String[] args) {

        while (true) {

            int x = inputNumber("Введите число для возведения в степень = \n");
            int n = inputNumber("Введите степень, в которую Вы хотите возвести число " + x + " : \n");

            newCalc(x,n);

        }

    }

    // чтение ввода числа с клавиатуры
    public static int inputNumber(String str) {
        System.out.print(str);
        return new Scanner(System.in).nextInt();
    }



    // метод, создающий новый поток для расчёта степени числа
    public static void newCalc(int x, int n){

        new Thread() {          // поток формируем при помощи анонимного класса и специального метода run()
            public void run(){

                int result = 1;
                for (int i = 0; i < n; i++) {
                    result *= x;
                }

                try {
                    Thread.sleep(20000);    // имитируем долгие вычисления текущего потока для того,
                                                  // чтоб успеть создать новые потоки вычислений
                }catch(InterruptedException ignored){}

                System.out.println("Число " + x + " в степени " + n + " = " + result);
                System.out.println("Поток " + Thread.currentThread().getName() + " закончил работу.");
            }
        }.start();

    }


}
