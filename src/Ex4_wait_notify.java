

public class Ex4_wait_notify {

    public static void main(String[] args) {

        //создаём объект класса Store
        Store store = new Store();

        // запускаем потоки покупателя и поставщика
        new Thread(new Consumer(store)).start();
        new Thread(new Producer(store)).start();

    }


}

// основной класс магазина, который считает продукты на складе магазина
// и работает с остановкой и оживлением потоков поставщика и покупателя
class Store{

    int product = 0;  // изначально продуктов на складе магазина нет

    // метод, который убавляет продукт на -1, когда приходит покупатель
    public synchronized void get() {
        while (product<1) {
            try {
                wait();  // если продуктов вообще не остаётся на складе, покупатель замирает и ждёт,
                         // пока поставщик не пополнит магазин хотя бы одним продуктом.
            } catch (InterruptedException ignored) {}

        }
        product--;  // Если на складе есть хотя бы 1 продукт, покупатель может купить его
        System.out.println("1 товар был приобретён." + Thread.currentThread().getName());
        System.out.println("Товаров на складе: " + product);
        notify();  // пока покупатель покупает продукты,
                   // поставщик может их поставлять на склад магазина
    }

    // метод, который прибавляет продукт на +1, когда приходит поставщик
    public synchronized void put() {
        while (product>=4) {    // склад магазина вмещает только 4 продукта.

            try {               // когда продуктов становится 4, поставщик замирает
                wait();         // и ждёт, когда его разморозит покупатель, выкупив хотя бы 1 продукт.
            } catch (InterruptedException ignored) {}

        }
        product++;      // пока продуктов на складе меньше 4, поставщик привозит новый продукт.
        System.out.println("1 товар был добавлен на склад." + Thread.currentThread().getName());
        System.out.println("Товаров на складе: " + product);
        notify();      // пока поставщик возит продукты, покупатель может их покупать
    }


}

class Producer implements Runnable{

    Store store;

    Producer (Store store) {
        this.store = store;
    }

    public void run(){
        for (int i = 1; i < 1000000; i++) {         // цикл, задающий количество поставок продукта в магазин
            store.put();    // вызов метода поставки продукта

//            try {
//                Thread.sleep(0);
//            } catch (Exception ex){
//            }

        }
    }
}

class Consumer implements Runnable{

    Store store;

    Consumer (Store store) {
        this.store = store;
    }

    public void run(){
        for (int j = 1; j < 1000000; j++){          // цикл, задающий количество покупок продукта в магазине
            store.get();  // вызов метода покупки продукта
        }
    }
}