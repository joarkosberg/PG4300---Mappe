package Innlevering;

/*
 *  Navn: Joar Kosberg - kosjoa14
 *  Filnavn: MultiThreadServerThread.java
 *  Oppgave: Innlevering 2 - Mappe
 */

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class MultiThreadServerThread implements Runnable {
    private Socket clientConnection;
    private ArrayList<Integer> asked;
    private String [] bookList;


    public MultiThreadServerThread(Socket s, String [] bookList)  {
        clientConnection = s;
        asked = new ArrayList<>();
        this.bookList = bookList;
    }


    public void run() {
        try {
            DataOutputStream output = new DataOutputStream(clientConnection.getOutputStream());
            output.flush();
            DataInputStream input = new DataInputStream(clientConnection.getInputStream());
            mainGame(output, input);
            closeConnections(output, input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void mainGame(DataOutputStream output, DataInputStream input) throws IOException {
        System.out.println("Ny tilkobling: " + clientConnection.getInetAddress());
        writeToClient(output, "Vil du delta i forfatter-QUIZ? (ja/nei) ");
        String message = input.readUTF();
        if(playMoreChecker(message))
            gameLoop(output, input);
        else
            writeToClient(output, "Du er bra kjedelig!");
    }


    private void closeConnections(DataOutputStream output, DataInputStream input) throws IOException {
        System.out.println("Tilkobling koblet ifra: " + clientConnection.getInetAddress());
        output.close();
        input.close();
        clientConnection.close();
    }


    private void gameLoop(DataOutputStream output, DataInputStream input) throws IOException {
        String [] bookInQuestion;
        boolean run = true;
        boolean check;
        while (run) {
            bookInQuestion = getRandomBook();
            writeToClient(output, "Hvem har skrevet " + bookInQuestion[1] + "? ");  //Spørsmål
            answerProcessing(input.readUTF(), output, bookInQuestion);
            writeToClient(output, "Vil du fortsette? (ja/nei) ");                   //Fortsette?
            run = playMoreChecker(input.readUTF());
            check = allQuestionsAskedCheck(output, input);
            if(!check) run = false;
        }
        writeToClient(output, "Takk for at du deltok!");
    }


    private boolean allQuestionsAskedCheck(DataOutputStream output, DataInputStream input) throws IOException {
        boolean allAsked = (asked.size() == bookList.length / 2) ? true : false;
        if(allAsked) {
            writeToClient(output, "Alle spørsmål er stilt! Vil du spille igjen? (j/n) ");
            boolean playMore = playMoreChecker(input.readUTF());
            asked.removeAll(asked);
            return playMore;
        }
        writeToClient(output, "");
        input.readUTF();
        return true;
    }


    private void answerProcessing(String answer, DataOutputStream output, String [] bookInQuestion) throws IOException {
        boolean correct = answerCheck(answer, bookInQuestion);
        if(correct) writeToClient(output, "Riktig! det var " + bookInQuestion[0]);
        else writeToClient(output, "Feil! det er " + bookInQuestion[0]);
    }


    public static boolean answerCheck(String answer, String[] bookInQuestion) {
        String[] nameSplit = bookInQuestion[0].split(", ");         //Example writers: DIAMOND, JARED / TOLKIEN, J. R. R
        if (answer.equalsIgnoreCase(nameSplit[1] + " " + nameSplit[0])) return true;     // (Fornavn Etternavn)
        if (answer.equalsIgnoreCase(nameSplit[0] + " " + nameSplit[1])) return true;     // (Etternavn Fornavn)
        if (answer.equalsIgnoreCase(nameSplit[0])) return true;     // (Etternavn)
        return answer.equalsIgnoreCase(bookInQuestion[0]);          // (Etternavn, Fornavn)
    }


    public static void writeToClient (DataOutputStream output, String message) throws IOException {
        output.writeUTF(message);
        output.flush();
    }


    public static boolean playMoreChecker(String message) {
        if(message.equalsIgnoreCase("y")) return true;
        if(message.equalsIgnoreCase("yes")) return true;
        if(message.equalsIgnoreCase("j")) return true;
        return message.equalsIgnoreCase("ja");
    }


    private String[] getRandomBook() {
        int i;
        while(true) {
            Random r = new Random();
            i = r.nextInt(bookList.length - 1);
            if(i % 2 != 0) i -= 1;
            if(!asked.contains(i)) break;
        }
        asked.add(i);
        return new String[]{bookList[i], bookList[i+1]};
    }
}
