package Innlevering;

/*
 *  Navn: Joar Kosberg - kosjoa14
 *  Filnavn: ConnectToDB.java
 *  Oppgave: Innlevering 2 - Mappe
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDB implements AutoCloseable{
	Connection con;


	public ConnectToDB(String server, String database)throws SQLException{
		con = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database, "root", "root");
	}


	public void close() throws SQLException {
		con.close();
	}


	public Connection getConnection() {
		return con;
	}
}
