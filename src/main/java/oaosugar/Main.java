package oaosugar;

import java.io.IOException;


public class Main {

    private static final Object lock = new Object(); //некоторый объект потока
    private static int currentThreadIndex = 0; //текущий индекс потока



    public static void main(String[] args) {
        System.out.println("Zadanie 1.14 Milovantseva Irina RIBO-01-22 Variant 16(1)");
        System.out.println("Для прерывания цикла в коммандной строке нажмите Enter");

        // Создаем три потока
        Thread thread0 = new Thread(new PrintThread(0));
        Thread thread1 = new Thread(new PrintThread(1));
        Thread thread2 = new Thread(new PrintThread(2));

        // Запускаем потоки
        thread0.start();
        thread1.start();
        thread2.start();



    }

    private static class PrintThread implements Runnable {

        private final int index; //определяем характеристику - индекс потока

        public PrintThread(int index) {
            this.index = index;
        }

        @Override
        public void run() { //переопределяем метод для вывода определенным образом
            while (true) {
                try {
                    if (!(System.in.available() == 0)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock) { //синхронизируем блок кода
                    while (currentThreadIndex != index) {
                        try {
                            Thread.sleep(100); //вывод потоков со временем 10с
                            lock.wait();
                        } catch (InterruptedException e) {
                            System.out.println("Ошибка"+e.getMessage());
                        }
                    }
                    System.out.println("Поток-" + index);
                    currentThreadIndex = (currentThreadIndex + 1) % 3; //определяем текущий индекс
                    lock.notifyAll();
                }
            }
        }
    }
}