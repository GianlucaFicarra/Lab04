package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {

	/*PATTERN MVC E DAO
	 * controller accede alle info del DB grazie al model che comunica col dao
	 * qui nel dao interrogo il DB tramite le query estrapolando le info che voglio
	 * ed ogni volta provo a connettermi e ad eseguire query per prendere info.
	 * queste le ritorno al model, che le ritorna al controller*/
	
	public List<Studente> getTuttiIStudenti() {

		final String sql = "SELECT * FROM studente";

		List<Studente> studenti = new LinkedList<Studente>();

		try {
			Connection conn = ConnectDB.getConnection(); //uso connectDB
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				int matricola = rs.getInt("matricola");
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				String cds = rs.getString("cds");
				
				// Crea un nuovo JAVA Bean Corso
				Studente s= new Studente(matricola, nome, cognome, cds);
				
				
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				
				studenti.add(s);
				
				
			}
			conn.close();
			return studenti;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	
	/*
	 * Data una matricola ottengo la lista dei corsi (codins) a cui è iscritto
	 */
	public List<Corso> getCorsiStudente(int matricola) {
		final String sql = "SELECT *\n" + 
				"FROM corso as c, iscrizione as i\n" + 
				"WHERE c.codIns = i.codIns and i.matricola=?";

		List<Corso> corsi = new LinkedList<Corso>();
		

		try {
			Connection conn = ConnectDB.getConnection(); //uso connectDB
			PreparedStatement st = conn.prepareStatement(sql);


			st.setInt(1, matricola);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				
				// Crea un nuovo JAVA Bean Corso
				Corso c= new Corso(codins, numeroCrediti, nome, periodoDidattico);
				
				
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				
				corsi.add(c);
				
				
			}
			conn.close();
		

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
			return corsi;
	}

	
	/*
	 * Controllo se uno studente (matricola) è iscritto ad un corso (codins)
	 */
	public boolean isStudenteIscrittoACorso(int matricola, Corso corso) {

		final String sql = "SELECT * FROM iscrizione where codins=? and matricola=?";
		boolean returnValue = false;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodIns());
			st.setInt(2, matricola);

			ResultSet rs = st.executeQuery();

			if (rs.next())
				returnValue = true;

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}

		return returnValue;
	}
	
	
	/*
	 * Data una matricola ottengo lo studente.
	 */
	public Studente getStudente(int matricola) {

		final String sql = "SELECT * FROM studente where matricola=?";
		Studente studente = null;

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, matricola);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				studente = new Studente(matricola, rs.getString("nome"), rs.getString("cognome"), rs.getString("cds"));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}

		return studente;
	}
	
}
