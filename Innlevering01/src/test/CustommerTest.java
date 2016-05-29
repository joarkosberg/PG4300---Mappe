package test;

import carRental.Custommer;
import carRental.RentalService;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.CountDownLatch;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class CustommerTest {
    private Custommer custommer;
    private CountDownLatch countDownLatch;
    private RentalService rentalService;


    @Before
    public void onStart(){
        rentalService = new RentalService();
        countDownLatch = new CountDownLatch(0);
        custommer = new Custommer("Per", rentalService, countDownLatch);
    }


    @Test
    public void testStopCommandForCustommer() {
        rentalService = mock(RentalService.class);
        custommer = new Custommer("stopp", rentalService, countDownLatch);
        custommer.run();
        verify(rentalService, never()).rent(custommer);
    }


    @Test
    public void testGetsNameOfCustommer(){
        assertEquals("Per", custommer.getName());
    }
}
