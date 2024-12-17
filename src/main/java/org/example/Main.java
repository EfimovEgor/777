package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class Main {
    public static final int CARS_COUNT = 5;
    private static final Object monitor = new Object();

    public static void main(String[] args) {
        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        List<String> results = new ArrayList<>();
        CyclicBarrier startBarrier = new CyclicBarrier(CARS_COUNT, () ->
                System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!")
        );

        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), results, startBarrier, monitor);
        }

        Thread[] threads = new Thread[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            threads[i] = new Thread(cars[i]);
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка завершена!!!");
        System.out.println("Результаты гонки:");

        synchronized (monitor) {
            for (int i = 0; i < Math.min(3, results.size()); i++) {
                System.out.printf("%d место: %s\n", i + 1, results.get(i));
            }
        }
    }
}
