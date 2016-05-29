package Test;

import Innlevering.Client;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static java.lang.System.in;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ClientTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @Before
    public void onStart(){
        System.setOut(new PrintStream(outContent));
    }


    @After
    public void tearDown(){
        System.setOut(null);
        System.setIn(in);
    }


    @Test
    public void testDataGetsPrinted(){
        String print = "This is a string to print.";

        new Client().printData(print);

        assertEquals(print, outContent.toString());
    }


    @Test
    public void testPlayMoreCheckerYes() {
        boolean check = new Client().playMoreChecker("Yes");
        assertEquals(true, check);
        check = new Client().playMoreChecker("Y");
        assertEquals(true, check);
        check = new Client().playMoreChecker("j");
        assertEquals(true, check);
        check = new Client().playMoreChecker("jA");
        assertEquals(true, check);
    }


    @Test
    public void testPlayMoreCheckerNo() {
        boolean check = new Client().playMoreChecker("no");
        assertEquals(false, check);
    }


    @Test
    public void testWriteDataToServer() throws IOException {
        DataOutputStream output = mock(DataOutputStream.class);
        String message = "Hi, this is a test message :)";
        ByteArrayInputStream in = new ByteArrayInputStream(message.getBytes());
        System.setIn(in);
        Scanner scan = new Scanner(in);

        new Client().writeData(scan, output);

        verify(output, times(1)).writeUTF(message);
    }
}
