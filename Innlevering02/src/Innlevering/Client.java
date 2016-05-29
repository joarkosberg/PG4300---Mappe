package Innlevering;

/*
 *  Navn: Joar Kosberg - kosjoa14
 *  Filnavn: Client.java
 *  Oppgave: Innlevering 2 - Mappe
 */

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final String serverAdress = "127.0.0.1";
    private final int serverPort = 80;


    public static void main(String[] args)  {
        new Client().start();
    }


    public void start(){
        try {
            Socket serverConnection = new Socket(serverAdress, serverPort);
            DataOutputStream output = new DataOutputStream(serverConnection.getOutputStream());
            output.flush();
            DataInputStream input = new DataInputStream(serverConnection.getInputStream());
            Scanner scan = new Scanner(System.in);
            mainGame(input, output, scan);
            closeResources(output, input, serverConnection, scan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void mainGame(DataInputStream input, DataOutputStream output,
                                 Scanner scan) throws IOException {
        if(introduction(input, output, scan)) {
            gameLoop(input, output, scan);
        } else {
            String data = getData(input);
            printData(data);
        }
    }


    private void closeResources(DataOutputStream output, DataInputStream input,
                                       Socket serverConnection, Scanner scan) throws IOException {
        output.close();
        input.close();
        serverConnection.close();
        scan.close();
    }


    private void gameLoop(DataInputStream input, DataOutputStream output,
                                 Scanner scan) throws IOException {
        Boolean run = true;
        Boolean check;
        String data;
        while (run) { //Game Loop
            communicationLoop(input, output, scan);     //Spørsmål
            data = getData(input);                      //Riktig eller galt
            printData(data);
            printData("\n");
            String message = communicationLoop(input, output, scan);     //Vil du spille igjen?
            run = playMoreChecker(message);
            check = allQuestionsAsked(scan, getData(input), output);
            if(!check) run = false;
            printData("\n");
        }
        data = getData(input);
        printData(data);
    }


    private String communicationLoop(DataInputStream input, DataOutputStream output,
                                          Scanner scan) throws IOException {
        String data = getData(input);
        printData(data);
        return writeData(scan, output);
    }


    public void printData(String data) {
        System.out.print(data);
    }


    private Boolean allQuestionsAsked(Scanner scan, String input, DataOutputStream output) throws IOException {
        if(input.equals("")) output.writeUTF("");
        else{
            printData(input);
            String message = writeData(scan, output);
            return playMoreChecker(message);
        }
        return true;
    }


    public String writeData(Scanner scan, DataOutputStream output) throws IOException {
        String message = scan.nextLine();
        output.writeUTF(message);
        output.flush();
        return message;
    }


    private String getData(DataInputStream input) throws IOException {
        return input.readUTF();
    }


    public boolean playMoreChecker(String message) {
        if(message.equalsIgnoreCase("y")) return true;
        if(message.equalsIgnoreCase("yes")) return true;
        if(message.equalsIgnoreCase("j")) return true;
        return message.equalsIgnoreCase("ja");
    }


    private boolean introduction(DataInputStream input, DataOutputStream output,
                                        Scanner scan) throws IOException {
        String message = communicationLoop(input, output, scan);
        return playMoreChecker(message);
    }
}
