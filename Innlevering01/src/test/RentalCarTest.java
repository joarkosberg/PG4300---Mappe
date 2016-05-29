package test;

import carRental.RentalCar;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RentalCarTest {
    private RentalCar rentalCar;


    @Before
    public void onStart(){
        rentalCar = new RentalCar("1");
    }


    @Test
    public void testCanChangeStatusOfCar(){
        assertEquals(false, rentalCar.getRented());
        rentalCar.setRented(true);
        assertEquals(true, rentalCar.getRented());
    }


    @Test
    public void testGetsRenterOfCar(){
        String name = "Per";
        rentalCar.setRenter(name);
        assertEquals(name, rentalCar.getRenter());
    }
}
