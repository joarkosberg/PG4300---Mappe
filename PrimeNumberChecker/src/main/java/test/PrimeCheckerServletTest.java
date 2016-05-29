package test;

import org.junit.Before;
import org.junit.Test;
import org.apache.commons.math3.primes.Primes;
import servlet.PrimeCheckerServlet;
import static org.junit.Assert.assertEquals;

public class PrimeCheckerServletTest {
    private int primeNumber, notPrimeNumber;
    private PrimeCheckerServlet primeCheckerServlet;


    @Before
    public void onStart(){
        primeNumber = Primes.nextPrime(10000);
        notPrimeNumber = 1000;
        primeCheckerServlet = new PrimeCheckerServlet();
    }


    @Test
    public void testItIsAPrime(){
        boolean prime = primeCheckerServlet.primeChecker(primeNumber);
        assertEquals(true, prime);
    }


    @Test
    public void testItIsNotAPrime(){
        boolean prime = primeCheckerServlet.primeChecker(notPrimeNumber);
        assertEquals(false, prime);
    }


    @Test
    public void testEmptyInput(){
        boolean isValid = primeCheckerServlet.inputChecker("");
        assertEquals(false, isValid);
    }


    @Test
    public void testInvalidInput(){
        boolean isValid = primeCheckerServlet.inputChecker((primeNumber + "e"));
        assertEquals(false, isValid);
    }


    @Test
    public void testValidInput(){
        boolean isValid = primeCheckerServlet.inputChecker(primeNumber + "");
        System.out.println(primeNumber);
        assertEquals(true, isValid);
    }
}
