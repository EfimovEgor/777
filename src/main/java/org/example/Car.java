package org.example;

import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private List<String> results;
    private CyclicBarrier startBarrier;
    private Object monitor;

    public Car(Race race, int speed, List<String> results, CyclicBarrier startBarrier, Object monitor) {
        this.race = race;
        this.speed = speed;
        this.results = results;
        this.startBarrier = startBarrier;
        this.monitor = monitor;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed; // Возвращает скорость автомобиля
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(name + " готов");
            startBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }

        for (Stage stage : race.getStages()) {
            stage.go(this);
        }

        synchronized (monitor) {
            results.add(this.name);
        }
    }
}
