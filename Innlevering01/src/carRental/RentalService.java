package carRental;

/*
 *  Navn: Joar Kosberg - kosjoa14
 *  Filnavn: RentalService.java
 *  Oppgave: Innlevering 1 - Mappe
 */

import java.util.LinkedList;
import java.util.Queue;

public class RentalService {
    private final String FRAME = "*********************************";
    private RentalCar[] cars;
    private Queue rentalQueue;


    public RentalService(){
        initRentalCars();
        initRentalQueue();
    }


    private void initRentalQueue() {
        rentalQueue = new LinkedList<Custommer>();
    }


    private void initRentalCars() {
        cars = new RentalCar[]{
                new RentalCar("RC11111"), new RentalCar("RC22222"),
                new RentalCar("RC33333"), new RentalCar("RC44444"),
                new RentalCar("RC55555")};
    }


    public void addCustommerToQueue(Custommer custommer){
        if(!rentalQueue.contains(custommer)) {
            System.out.println(custommer.getName() + " venter på ledig bil\n\n");
            rentalQueue.add(custommer);
        }
    }


    public RentalCar rent(Custommer custommer) {
        if (rentalQueue.size() == 0 || rentalQueue.element().equals(custommer)){ //If no queue, all can enter. If queue, only first custommer can enter
            for (RentalCar car : cars) {
                if (!car.getRented()) {
                    return rentCar(car, custommer);
                }
            }
        }
        addCustommerToQueue(custommer);
        return null;
    }


    private RentalCar rentCar(RentalCar car, Custommer custommer) {
        car.setRented(true);
        car.setRenter(custommer.getName());
        if (rentalQueue.size() > 0) rentalQueue.remove();
        System.out.println(custommer.getName() + " leide en bil: " + car.getRegNumber());
        printOverview();
        return car;
    }


    public void handInCar(RentalCar rentedCar){
        for(RentalCar car: cars){
            if(car.equals(rentedCar)) {
                car.setRented(false);
                System.out.println(car.getRegNumber() + " ble levert tilbake av " + car.getRenter());
            }
        }
        printOverview();
    }


    public void printOverview(){
        System.out.println(FRAME);
        System.out.println("Bil oversikt:");
        System.out.print("Utleid: ");
        for(RentalCar car: cars)
            if(car.getRented()) System.out.print(car.getRegNumber() + "(" + car.getRenter() + "), ");
        System.out.print("\nLedig: ");
        for(RentalCar car: cars)
            if(!car.getRented()) System.out.print(car.getRegNumber() + ", ");
        System.out.println("\n" + FRAME + "\n\n");
    }
}
