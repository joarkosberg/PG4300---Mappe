package carRental;

/*
 *  Navn: Joar Kosberg - kosjoa14
 *  Filnavn: RentalCar.java
 *  Oppgave: Innlevering 1 - Mappe
 */

public class RentalCar {
    private boolean rented;
    private String renter;
    private String regNumber;


    public RentalCar(String regNumber){
        this.regNumber = regNumber;
        rented = false;
    }


    public String getRegNumber(){
        return regNumber;
    }


    public boolean getRented(){
        return rented;
    }


    public String getRenter() {
        return renter;
    }


    public void setRented(boolean rented){
        this.rented = rented;
    }


    public void setRenter(String renter) {
        this.renter = renter;
    }
}
