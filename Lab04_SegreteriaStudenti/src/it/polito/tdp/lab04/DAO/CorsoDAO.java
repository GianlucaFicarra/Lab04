package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*PATTERN MVC E DAO
	 * controller accede alle info del DB grazie al model che comunica col dao
	 * qui nel dao interrogo il DB tramite le query estrapolando le info che voglio
	 * ed ogni volta provo a connettermi e ad eseguire query per prendere info.
	 * queste le ritorno al model, che le ritorna al controller*/

	// Ottengo tutti i corsi salvati nel Db
	public List<Corso> getTuttiICorsi() {

		//SQL
		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();
		
		/*Corso primoC= new Corso(); per fare prima riga vuota
		corsi.add(primoC);*/

		//provo a connettermi
		try {
			Connection conn = ConnectDB.getConnection(); //uso connectDB per la connessione
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery(); //esegue QUERY	

			
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

	
	// Dato un codice insegnamento, ottengo il corso
	public void getCorso(Corso corso) {
		
		final String sql = "SELECT * FROM corso where codins=?";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, corso.getCodIns());

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				corso.setCrediti(rs.getInt("crediti"));
				corso.setNome(rs.getString("nome"));
				corso.setPd(rs.getInt("pd"));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
	}

	//Ottengo tutti gli studenti iscritti al Corso
	public List<Studente> getStudentiIscrittiAlCorso(Corso corso) {

		final String sql = "SELECT *\n" + 
				"FROM studente as s, iscrizione as i\n" + 
				"WHERE s.matricola = i.matricola and i.codIns=?";
		//? perche è un valore variabile in base al corso
		
		
		List<Studente> studenti = new LinkedList<Studente>();
		

		try {
			Connection conn = ConnectDB.getConnection(); //uso connectDB
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, corso.getCodIns());//sostituisce al punto interogativo il valore corrente del corso

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
			

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return studenti;
	}

	// Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		String sql = "INSERT IGNORE INTO `iscritticorsi`.`iscrizione` (`matricola`, `codins`) VALUES(?,?)";
		boolean returnValue = false;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, studente.getMatricola());
			st.setString(2, corso.getCodIns());
			
			int res = st.executeUpdate();	

			if (res == 1)
				returnValue = true;//se iscrizione avvenuta con successo

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore Db");
		}
		
		return returnValue;
	}
}
