package Test;

import Innlevering.MultiThreadServerThread;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class MultiThreadServerThreadTest {
    @Before
    public void onStart(){
    }


    @After
    public void tearDown(){

    }


    @Test
    public void testReturnCorrectOnDifferentFormatedAnswer1() {
        boolean check = MultiThreadServerThread.answerCheck("FOrnaVN EtterNaVN", new String[]{"ETTERNAVN, FORNAVN", "book"});
        assertEquals(true, check);
    }


    @Test
    public void testReturnCorrectOnDifferentFormatedAnswer2() {
        boolean check = MultiThreadServerThread.answerCheck("EtterNaVN", new String[]{"ETTERNAvn, Fornavn", "book"});
        assertEquals(true, check);
    }


    @Test
    public void testReturnWrongAnswer() {
        boolean check = MultiThreadServerThread.answerCheck("Fornaasn Ett", new String[]{"ETTERNAvn, Fornavn", "book"});
        assertEquals(false, check);
    }


    @Test
    public void testPlayMoreCheckerYes() {
        boolean check = MultiThreadServerThread.playMoreChecker("Y");
        assertEquals(true, check);
        check = MultiThreadServerThread.playMoreChecker("Yes");
        assertEquals(true, check);
        check = MultiThreadServerThread.playMoreChecker("j");
        assertEquals(true, check);
        check = MultiThreadServerThread.playMoreChecker("jA");
        assertEquals(true, check);

    }


    @Test
    public void testPlayMoreCheckerNo() {
        boolean check = MultiThreadServerThread.playMoreChecker("no");
        assertEquals(false, check);
    }


    @Test
    public void testWriteDataToClient() throws IOException {
        DataOutputStream output = mock(DataOutputStream.class);
        String message = "Hi, this is a test message :)";
        MultiThreadServerThread.writeToClient(output, message);
        verify(output, times(1)).writeUTF(message);
    }
}
