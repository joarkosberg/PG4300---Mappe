package test;

import carRental.Custommer;
import carRental.RentalCarApp;
import carRental.RentalService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.Assert.assertEquals;

public class RentalCarAppTest {
    private CountDownLatch countDownLatch;
    private RentalService rentalService;


    @Before
    public void onStart(){
        rentalService = new RentalService();
        countDownLatch = new CountDownLatch(0);
    }


    @After
    public void tearDown(){
        System.setIn(System.in);
    }



    @Test
    public void testAllThreadsGetStarted() {
        ExecutorService ex = Executors.newCachedThreadPool();
        CountDownLatch cdl = new CountDownLatch(5);
        ByteArrayInputStream in = new ByteArrayInputStream("Joar Per Pål Karl stopp".getBytes());
        System.setIn(in);
        Scanner input = new Scanner(System.in);

        RentalCarApp rentalCarApp = new RentalCarApp();
        rentalCarApp.threadStarter(input, rentalService, cdl, ex);

        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(0, cdl.getCount());
    }
}
