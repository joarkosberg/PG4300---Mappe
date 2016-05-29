package carRental;

/*
 *  Navn: Joar Kosberg - kosjoa14
 *  Filnavn: Custommer.java
 *  Oppgave: Innlevering 1 - Mappe
 */

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Custommer implements Runnable{
    private static Lock lock = new ReentrantLock();
    private static boolean stop;
    private static int waitYesCar = 3;
    private static int waitNoCar = 10;
    private CountDownLatch countDown;
    private RentalService RentalService;
    private String name;
    private boolean waiting;


    public Custommer(String name, RentalService RentalService, CountDownLatch countDown){
        this.RentalService = RentalService;
        this.name = name;
        this.countDown = countDown;
        stop = false;
        waiting = false;
    }


    public void run(){
        if(name.equalsIgnoreCase("stopp")) stop = true;
        countdown();
        RentalCar car = null;
        while(!stop){                              //Game loop
            if(car == null && !waiting) wait(waitNoCar);    //Ingen bil leid
            else if(car != null) wait(waitYesCar);            //Bil leid
            lock.lock();
            try {
                car = carAction(car);
            } finally {
                lock.unlock();
            }
        }
    }


    private void countdown(){
        try {
            countDown.countDown();
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void wait(int time){
        try {
            Thread.sleep(randomWait(time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private int randomWait (int time){
        Random r = new Random();
        int i = (r.nextInt(time) + 1) * 1000;
        return i;
    }


    private RentalCar carAction(RentalCar car){
        if (car != null) car = handInCar(car);
        else car = rentCar();
        return car;
    }


    private RentalCar rentCar() {
        RentalCar car = RentalService.rent(this);
        if(car == null) waiting = true;         //Ingen ledige biler
        else waiting = false;                   //Mottok en bil
        return car;
    }


    private RentalCar handInCar(RentalCar car) {
        RentalService.handInCar(car);
        return null;
    }


    public String getName(){
        return name;
    }


    public void setWaitTime(int waitYesCar, int waitNoCar){
        this.waitYesCar = waitYesCar;
        this.waitNoCar = waitNoCar;
    }
}
