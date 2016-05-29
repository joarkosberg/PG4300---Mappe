package carRental;

/*
 *  Navn: Joar Kosberg - kosjoa14
 *  Filnavn: RentalCarApp.java
 *  Oppgave: Innlevering 1 - Mappe
 */

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RentalCarApp {
    public static void main(String[] args) {
        new RentalCarApp().start();
    }


    private void start() {
        ExecutorService ex = Executors.newCachedThreadPool();
        CountDownLatch countDown = new CountDownLatch(5);
        Scanner input = new Scanner(System.in);
        RentalService rentalService = new RentalService();
        printIntro(rentalService);
        threadStarter(input, rentalService, countDown, ex);
        closeResources(ex, input);
    }


    private void printIntro(RentalService rentalService) {
        System.out.println("Velkommen til en fantastisk bilutleier!");
        System.out.println("Applikasjonen starter når du har lagt inn 5 kunder, og tar maks 10 kunder.");
        System.out.println("Skriv \"stopp\" viss du ønsker å avslutte applikasjonen.");
        rentalService.printOverview();
    }


    private void closeResources(ExecutorService ex, Scanner input) {
        ex.shutdown();
        input.close();
    }


    public void threadStarter(Scanner input, RentalService rentalService,
                              CountDownLatch countDown, ExecutorService ex){
        int counter = 0;
        while(true){
            String name = input.next();
            if(counter < 10 || name.equalsIgnoreCase("stopp")) {
                Custommer c = new Custommer(name, rentalService, countDown);
                ex.execute(c);
            }
            if(name.equalsIgnoreCase("stopp")) break;
            counter++;
        }
    }
}
