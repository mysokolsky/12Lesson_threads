//Создать программу, которая имитирует работу морского порта.
//
// - Имеются корабли, которые представляют собой списки разных размеров.
// - Имеется склад с товарами. Для товара можно создать отдельный тип данных.
// - Есть поставщики, которые периодически привозят новые товары на склад и
// - есть кран, который перекладывает товары со склада в подходящие корабли.
//


//        * Сделать порт конфигурируемым т.е. возможность выбрать количество кранов.
//
//        * реализовать очередь из кораблей на загрузку.
//
//        Краны, поставщики, и подача новых кораблей должны работать асинхронно








// Сначала происходит инициализация и заполнение склада продуктами, склад представляет собой хэшмапу,
// ключами которой являются названия продуктов, взятые из перечисления, а значениями объекты класса Products.
// После этого случайным образом генерируются и заполняются корабли так же объектами класса Products,
// но с пустыми текущими значениями. Все корабли складываются в один список. После этого запускается поток поставщиков,
// который отслеживает наличие продукции на складе, чтобы значения продукции не были ниже 80% от максимального
// объема данной продукции. Как только значение конкретного продукта на складе снижается ниже 80%,
// поставщик доставляет продукты. Подача кораблей осуществляется через специальный защищённый одноячеистый список,
// в который поток, созданный в методе shipping добавляет очередной корабль, если список становится пустым.
// Из этого списка корабль считывают потоки из метода kran. Они заполняют данный список-корабль товарами,
// а потом когда список полностью заполнен, корабль очищается из списка командой take(), после чего
// в список потоком из метода shipping опять добавляется корабль.






import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Lesson12_Seaport {

    public static int n,k,s, x;

    public static void main(String[] args) throws InterruptedException {


        System.out.print("\nВведите количество кранов: ");
        x=choiceInt();

        //определение количества товаров на складе
        n = ProductNames.values().length;

        System.out.println("\nКоличество продуктов на складе: " + n);

        //склад товаров
        HashMap<ProductNames,Product> store = new HashMap<>(n);

        // заполнение склада товарами
        fillStore(store);

        // распечатка склада
        printStore(store);


        int numberOfShips = 3 + random(8);  // определение количества кораблей от 3 до 10

        System.out.println("\nКоличество кораблей к погрузке = " + numberOfShips + " шт.");

        // создаём список, в который положим все корабли-списки
        ArrayList<ArrayList<Product>> allShips= new ArrayList<>(numberOfShips);

        // док для одного корабля для погрузки товаров
        BlockingQueue<ArrayList<Product>> shipped = new ArrayBlockingQueue<>(1);

        // отчалившие погруженные корабли
        LinkedHashSet<ArrayList<Product>> shipsGone = new LinkedHashSet<>(numberOfShips);

        // создание и назначение определённых товаров кораблям
        createShips(allShips, numberOfShips);



        // запускаем поставщиков склада
        suppliers(store);

        // подача кораблей по очереди на погрузку
        shipping(allShips, shipped);

        // погрузка кораблей кранами
        kran(shipped, store, numberOfShips, shipsGone);

//        sleep(3000);
//        System.out.println("\nВсе отчалившие отгруженные корабли:");
//        shipsGone.forEach(System.out::println);

    }


    //считывание ввода с клавиатуры с обработкой ошибки ввода только целых чисел
    public static int choiceInt(){
        while (true) {
            try {
                return (new Scanner(System.in)).nextInt();
            } catch (Exception e) {
                System.out.print("Ошибка! Введите целое число: ");
            }
        }
    }





        // распечатка склада
        public static void printStore(HashMap<ProductNames,Product> store) {
            int i=0;
            System.out.println("Склад товаров:");

            for (Map.Entry<ProductNames, Product> p : store.entrySet()) {
                System.out.println(++i + ".  " + p.getKey() + " " + p.getValue().quantity + ".." + p.getValue().maxQuantity);
            }
        }




    public synchronized static void shipping(ArrayList<ArrayList<Product>> allShips, BlockingQueue<ArrayList<Product>> shipped) {
            n=0;

        System.out.println(":::::::::::::::::::::::  НАЧИНАЕТСЯ ПОГРУЗКА КОРАБЛЕЙ!!! ::::::::::::::::::::::");
                new Thread(() -> {

                        for (ArrayList<Product> ship : allShips) {
                            try {
//                                Thread.sleep(200);
                                System.out.println("\n" + ++n + "й из " + allShips.size() + " кораблей причалил на погрузку ::::::::::::::: " + ship + "\n");
//                                while(ship!=null && !shipped.contains(ship))
                                    shipped.put(ship);

                                Thread.sleep(2000);
                                Thread.currentThread().notifyAll();

//                                Thread.sleep(1000);
                            } catch (InterruptedException | IllegalMonitorStateException ignored) {}
                        }

                }).start();
    }



public static void kran(BlockingQueue<ArrayList<Product>> shipped, HashMap<ProductNames,Product> store, int numberOfShips, LinkedHashSet<ArrayList<Product>> shipsGone){

        k=0;
        s=0;



        for (int i = 0; i < x; i++) {



            new Thread(() -> {

                Thread.currentThread().setName("№" + ++k);



                while (s!=numberOfShips) {


                    for (ArrayList<Product> ship : shipped) {

                        for (Product product : ship) {


                            while (product.quantity != product.maxQuantity) {
                                store.get(product.name).quantity--;
                                product.quantity++;
                                System.out.println("-1 " + product.name + " отгружено со склада. На складе осталось: " + store.get(product.name).quantity);
                                System.out.println("+1 " + product.name + " погружен на корабль: " + ship + " краном " + Thread.currentThread().getName());
//                                try {
//                                    Thread.sleep(0);
//                                } catch (InterruptedException ignored) {
//                                }

                            }

                        }


//                        synchronized (Lesson12_Seaport.class) {


//                        if (ship.get(ship.size()-1).quantity == ship.get(ship.size()-1).maxQuantity) {

//                        stream().allMatch(el -> el.
//                        quantity == el.maxQuantity)) {

                            try {
//                                Thread.sleep(1000);
//                                System.out.println(ship);
//                                shipsGone.add(ship);
                                shipped.clear();
                                ++s;
//                                System.out.println(s + "   " + Thread.currentThread().getName());
//                                Thread.sleep(1000);
                                Thread.currentThread().wait();
//                                Thread.sleep(1000);


                            } catch (
                                    InterruptedException |
                                    IllegalMonitorStateException
                                            ignored) {}
//                        }



                    }

                }
//                System.out.println(s + " " + numberOfShips);
        }).start();


    }

}




    public static int randomMaxQuantity(){
        return 30 + 10*(random(8));
    }


    public static int randomCurrentQuantity(int maxQuantity){
        return 30 + random(maxQuantity - 29);
    }



    public static void fillStore(HashMap<ProductNames,Product> store)  {

        for (ProductNames names : ProductNames.values()) {

            int max = randomMaxQuantity();
            int current = randomCurrentQuantity(max);
            store.put(names,new Product(names,current,max));

        }

    }


    public static int random(int x){
        return new Random().nextInt(x);
    }


    public static void suppliers(HashMap<ProductNames, Product> store) {

        System.out.println("\n\n :::::: Налаживаем связи с поставщиками :::::: \n");


        new Thread(() -> {

        while (true) {
            for (Map.Entry<ProductNames, Product> p : store.entrySet()) {

                // если количество товара на складе меньше максимального
                if (p.getValue().quantity < p.getValue().maxQuantity * .8) {
//                    System.out.println("\nТовара " + p.getKey() + " на складе менее 80%: " + p.getValue());
//                    System.out.println("Ожидается доставка товара " + p.getKey() +"\n");


                    int addQuantity = (int) (p.getValue().maxQuantity*.9 - p.getValue().quantity + random((int) (p.getValue().maxQuantity - p.getValue().maxQuantity*.9 + 1)));
//                    int addQuantity = (int) (p.getValue().maxQuantity * .05 + random((int) (p.getValue().maxQuantity * .1 + 1)));
                    p.getValue().quantity += addQuantity;
                    System.out.println("\n+" + addQuantity + " ед. товара " + p.getKey() + " завезено на склад: \n" + p.getValue()+"\n");


                }

            }
        }
        }).start();

    }


    public static void createShips(ArrayList<ArrayList<Product>> allShips, int numberOfShips)  {

        // начинаем перебор всех кораблей списка
        for (int i=0; i<numberOfShips; i++){

            System.out.println("\nКорабль № " + (i+1) + " из " + numberOfShips);

            // определяем количество продукции, перевозимое текущим кораблём
            int numberOfProducts = 1 + random(5);

            System.out.println("Запланирована погрузка товаров " + numberOfProducts + " шт. :");

            // создаём корабль, в котором инициализируем ячейки продукции для погрузки
            ArrayList<Product> ship = new ArrayList<>(numberOfProducts);


            // инициализируем вспомогательную переменную k
            int k = n;


            // вспомогательный список имён продукции, из которого будем делать выборку
            // для назначения конкретных отгружаемых товаров данному кораблю
            ArrayList<ProductNames> tempNames = new ArrayList<>(Arrays.asList(ProductNames.values()));

            // инициализируем вспомогательные переменные
            int currentIndexToAdd;
            ProductNames productToAdd;


            // в соответствии с текущим количеством продукции, выбираем случайным образом и назначаем конкретные товары для загрузки в корабль
            for (int j = 0; j<numberOfProducts; ++j){

                currentIndexToAdd= random(k);  // случайным образом выбираем индекс элемента товара
                productToAdd= tempNames.get(currentIndexToAdd); // выбираем данный товар для добавления в корабль

                    System.out.print(j+1);
                    System.out.println(": " +productToAdd);

                    ship.add(new Product(productToAdd, 0, 2 + random(19))); //добавляем выбранный товар в корабль
                    k--;  // уменьшаем вспомогательный индекс на -1 элемент
                    tempNames.remove(currentIndexToAdd);    // удаляем из вспомогательного списка уже добавленный товар в корабль
                                                            // для дальнейшей выборки только уникальных товаров

            }
            System.out.println("Текущий корабль: ");
            System.out.println(ship); // распечатываем получившийся корабль

            allShips.add(ship); // добавляем корабль в список кораблей


        }

        //распечатка всех кораблей
        System.out.println("\nВсе корабли, " + numberOfShips + " шт. :");
        allShips.forEach(System.out::println);


    }

}



enum ProductNames {
    САХАР,
    МУКА,
    СОЛЬ,
    КАРТОФЕЛЬ,
    СВЕКЛА,
    РИС,
    МОРКОВЬ,
    ЛУК,
    МАКАРОНЫ,
    ГРЕЧКА,
    МЁД
}

class  Product {
    ProductNames name;
    int quantity;
    int maxQuantity;

    Product (ProductNames name, int quantity, int maxQuantity) {
        this.name = name;
        this.quantity = quantity;
        this.maxQuantity = maxQuantity;
    }




    @Override
    public String toString() {
        return name + ": " + quantity + ".." + maxQuantity;
    }
}

