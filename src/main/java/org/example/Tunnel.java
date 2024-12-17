package org.example;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {
    private Semaphore semaphore;

    public Tunnel() {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        // Ограничение на одного участника в тоннеле
        this.semaphore = new Semaphore(1);
    }

    @Override
    public void go(Car car) {
        try {
            System.out.println(car.getName() + " готовится к этапу(ждет): " + description);
            semaphore.acquire(); // Участник ожидает своей очереди
            System.out.println(car.getName() + " начал этап: " + description);
            Thread.sleep(length / car.getSpeed() * 1000);
            System.out.println(car.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release(); // Участник освобождает тоннель
        }
    }
}
