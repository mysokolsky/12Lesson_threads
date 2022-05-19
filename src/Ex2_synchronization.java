public class Ex2_synchronization {
    public static int value=0;
    public static void main(String[] args) {

     Thread thread1 = new Thread(Ex2_synchronization::safeInc);
     Thread thread2 = new Thread(Ex2_synchronization::safeInc2);
     Thread thread3 = new Thread(Ex2_synchronization::safeInc);

    thread1.start();
    thread2.start();
    thread3.start();

    }

    public static void dangerInc(){
        System.out.println("Начало");
        System.out.println("Значение value до изменения = "+value);
        value++;
        System.out.println("Значение value после изменения = "+value);
        System.out.println("Конец");
    }

    //команда synchronized ограничивает выполнение метода только для одного потока.
    //Остальные потоки ждут, пока не завершится выполнение метода одним потоком.
    public static synchronized void safeInc(){
        System.out.println("Начало");
        System.out.println("Значение value до изменения = "+value);
        value++;
        System.out.println("Значение value после изменения = "+value);
        System.out.println("Конец");
    }

    public static void safeInc2(){

//        здесь может быть какой-то не потоко-безопасный код
//

        // универсальная конструкция для выполнения нужного
        // участка кода только одним потоком (потокобезопасная)
        synchronized (Ex2_synchronization.class) {
            System.out.println("Начало");
            System.out.println("Значение value до изменения = " + value);
            value++;
            System.out.println("Значение value после изменения = " + value);
            System.out.println("Конец");
        }

        // здесь тоже может быть не потокобезопасный код

    }


}
