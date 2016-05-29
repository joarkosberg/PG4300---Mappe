package Innlevering;

/*
 *  Navn: Joar Kosberg - kosjoa14
 *  Filnavn: MultiThreadServer.java
 *  Oppgave: Innlevering 2 - Mappe
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadServer {
    private static final int serverPort = 80;
    private static final int queue = 50;
    private PreparedStatement pstmtGetBooks;


    public static void main(String[] args) {
        new MultiThreadServer().start();
    }


    private void start() {
        try {
            initDBWithResources();
            ServerSocket server = new ServerSocket(serverPort, queue);
            ExecutorService ex = Executors.newCachedThreadPool();
            serverLoop(server, ex);
        } catch (IOException | SQLException e){
            e.printStackTrace();
        }
    }


    private void initDBWithResources() throws SQLException {
        ConnectToDB ctDB = new ConnectToDB("localhost", "pg4100innlevering2");
        Connection con = ctDB.getConnection();
        String tableName = "bokliste";
        pstmtGetBooks = con.prepareStatement("SELECT * FROM " + tableName);
    }


    private void serverLoop(ServerSocket server, ExecutorService ex) throws IOException, SQLException {
        System.out.println("Server er startet");
        while (true) {
            Socket clientConnection = server.accept();
            MultiThreadServerThread o = new MultiThreadServerThread(clientConnection, getBooks());
            ex.submit(o);
        }
    }


    private String [] getBooks() throws SQLException {
        ArrayList<String> bookList = new ArrayList<>();
        ResultSet rs = pstmtGetBooks.executeQuery();
        while(rs.next()){
            bookList.add(rs.getString("forfatter"));
            bookList.add(rs.getString("tittel"));
        }
        return bookList.toArray(new String[bookList.size()]);
    }
}
