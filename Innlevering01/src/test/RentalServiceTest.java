package test;

import carRental.Custommer;
import carRental.RentalCar;
import carRental.RentalService;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RentalServiceTest {
    private Custommer custommer;
    private CountDownLatch countDownLatch;
    private RentalService rentalService;


    @Before
    public void onStart(){
        rentalService = new RentalService();
        countDownLatch = new CountDownLatch(0);
        custommer = new Custommer("Karl", rentalService, countDownLatch);
        custommer.setWaitTime(1, 1);
    }


    @Test
    public void testCarsGetHandedIn() {
        ExecutorService ex = Executors.newCachedThreadPool();
        RentalService rsSpy = spy(rentalService);
        Custommer c1 = new Custommer("Karl", rsSpy, countDownLatch);
        Custommer c2 = new Custommer("Per", rsSpy, countDownLatch);
        c1.setWaitTime(1, 1);

        ex.execute(c1);
        ex.execute(c2);

        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        verify(rsSpy, atLeast(2)).handInCar(anyObject());
    }


    @Test
    public void testGetsACarWhenAvailable() {
        RentalCar rentalCar = rentalService.rent(custommer);
        assertFalse(rentalCar == null);
    }


    @Test
    public void testNothingRentedEqualsNothingHandedIn() throws NullPointerException{
        ExecutorService ex = Executors.newCachedThreadPool();
        RentalService rs = mock(RentalService.class);
        when(rs.rent(custommer)).thenReturn(null);
        Custommer c1 = new Custommer("Karl", rs, countDownLatch);
        Custommer c2 = new Custommer("Per", rs, countDownLatch);
        c1.setWaitTime(1, 1);

        ex.execute(c1);
        ex.execute(c2);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        verify(rs, never()).handInCar(anyObject());
    }
}
