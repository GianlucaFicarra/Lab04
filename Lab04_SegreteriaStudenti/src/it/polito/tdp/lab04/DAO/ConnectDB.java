package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*caso dove ho più classi dao che aprono e chiudono la connessione:
 * classe solo per la connessione, dato che ho diverse funzioni uso questa
 * se connesione non esiste o è stata chiusa da funz altre classi dao la apro
 * perche mi serve per la funzione corrente che la invoca */


public class ConnectDB {

	static private final String jdbcUrl = "jdbc:mysql://localhost/iscritticorsi?user=root&password=gualtieri95";
	static private Connection connection = null;

	public static Connection getConnection() {

		try {
			if (connection == null || connection.isClosed()) {
		    //se chiusa o se non è ma stata aperta(null) apri la connessione
				connection = DriverManager.getConnection(jdbcUrl);
			}
			return connection;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException("Cannot get a connection " + jdbcUrl, e);
		}
	}

}
