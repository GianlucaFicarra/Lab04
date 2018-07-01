package it.polito.tdp.lab04.controller;

/**
 * Sample Skeleton for 'SegreteriaStudenti.fxml' Controller Class
 */

import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.ComparatoreAlfabetico;
import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SegreteriaStudentiController {
	
	private Model model;
	
	/*PATTERN MVC E DAO
	 * voglio ottenere info sui corsi e sugli studenti,
	 * queste info sono nel DAO, così ci accedo tramite il model:
	 * richiamo metodi del model che richiamano metodi del dao*/

    @FXML
    private ResourceBundle resources;

    @FXML 
    private URL location;

    @FXML 
    private ComboBox<Corso> txtTendina; 
    /*DEVO SPECIFICARE IL TIPO DI ELEMENTI NEL MENU
     * dopo la vado a popolare col setCombo()*/

    
    @FXML
    private Button txtBIscrittiCorso;

    @FXML 
    private TextField txtMatricola;

    @FXML 
    private TextField txtNome;

    @FXML 
    private TextField txtCognome; 

    @FXML
    private Button txtBCercaCorsi;

    @FXML 
    private Button txtBIscrivi; 

    @FXML 
    private TextArea txtAreaStampa; 

    @FXML
    private Button txtBreset;
    
    @FXML
    private ImageView btnSpunta; 
    
    @FXML 
    private Button btnIscrizione; 
    

    List<Corso> corsi;//per popolare menu a tendina
    
    //collegamento controller --->model
    public void setModel(Model model) {
		this.model = model;
		
		//popolo il menu a tendina
		setComboItems();
	
	}
    
    private void setComboItems() {

		// Ottieni tutti i corsi dal model
		corsi = model.listaCorsi();

		// Aggiungi tutti i corsi alla ComboBox
		Collections.sort(corsi, new ComparatoreAlfabetico());
		txtTendina.getItems().addAll(corsi);
		
		//opp direttamente: txtTendina.getItems().addAll(model.listaCorsi());
	}
    

    @FXML
    void doReset(ActionEvent event) {

    	txtMatricola.clear();
    	txtNome.clear();
    	txtCognome.clear();
    	txtAreaStampa.clear();
    	txtTendina.getSelectionModel().clearSelection();
  
    }
    

    @FXML
    void doStudente(MouseEvent event) {
    	/*data matricola voglio ottenere nome e cognome dello studente
    	 * queste info sono nel DAO, così ci accedo tramite il model:
    	 * richiamo metodi del model che richiamano metodi del dao*/

    	txtAreaStampa.clear();
		txtNome.clear();
		txtCognome.clear();
		
    	int matricola=Integer.parseInt(txtMatricola.getText());
    	
    	Studente studente = model.getStudente(matricola);//metodo model che richiama metodo dao

		if (studente == null) {
			txtAreaStampa.appendText("Nessun risultato: matricola inesistente");
			return;
		}

		txtNome.setText(studente.getNome());
		txtCognome.setText(studente.getCognome());
    	
    }
    

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	
    	txtAreaStampa.clear();
    	

    	try {
		    	int matricola=Integer.parseInt(txtMatricola.getText());
		    	
		    	//controllo esistenza studente
		    	Studente studente = model.getStudente(matricola);
				if (studente == null) {
					txtAreaStampa.appendText("Nessun risultato: matricola inesistente");
					return;
				}
				
		    	
		    //dammi corsi a cui è iscritto studente
		    	List<Corso> corsi= new LinkedList<Corso>();
		    	corsi=model.cercaCorsiDatoStudente(matricola); //metodo model che richiama metodo dao
		    	
		    //stampo iscritti al corso
		    	for(Corso c: corsi) {
		    		txtAreaStampa.setText(txtAreaStampa.getText()+c.getNome()+"\n");
		    	}

    	}catch(NumberFormatException e) {
    		txtAreaStampa.setText("Matricola sbagliata");
    	}

    }

    @FXML
    void doCercaIscritti(ActionEvent event) {

    	txtAreaStampa.clear();
		txtNome.clear();
		txtCognome.clear();
		
		try {

	    	List<Studente> iscritti= new LinkedList<Studente>();
	    	Corso corso=txtTendina.getValue(); //get value per prendere valore del box
	    	
	    	if(corso==null ) {
	    		txtAreaStampa.setText("Seleziona un corso.");
	    		return;
	    	}
	    	
	    	if(corso.getCodIns()==null) {
	    		txtAreaStampa.setText("Selezionato corso vuoto.");
	    		return;
	    	}
	    	
	        //dammi lista studenti
	    	iscritti=model.studentiIscrittiAlCorso(corso); //metodo model che richiama metodo dao
	       
	    	//stampo iscritti al corso
	    	for(Studente s: iscritti) {
	    		txtAreaStampa.setText(txtAreaStampa.getText()+s.getNome()+" "+s.getCognome()+"\n");
	    	}
    	
		} catch (RuntimeException e) {
			txtAreaStampa.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
    }


    
    @FXML
    void doIscrivi(ActionEvent event) {

    	//PUNTO OPZIONALE

    	txtAreaStampa.clear();

		try {

			if (txtMatricola.getText().isEmpty()) {
				txtAreaStampa.setText("Inserire una matricola.");
				return;
			}

			if (txtTendina.getValue() == null) {
				txtAreaStampa.setText("Selezionare un corso.");
				return;
			}

			// Prendo la matricola in input
			int matricola = Integer.parseInt(txtMatricola.getText());

			// (opzionale)
			// Inserisco il Nome e Cognome dello studente nell'interfaccia
			Studente studente = model.getStudente(matricola);   ;//metodo model che richiama metodo dao
			if (studente == null) {
				txtAreaStampa.appendText("Nessun risultato: matricola inesistente");
				return;
			}

			txtNome.setText(studente.getNome());
			txtCognome.setText(studente.getCognome());

			// Ottengo il nome del corso
			Corso corso = txtTendina.getValue();

			// Controllo se lo studente è già iscritto al corso
			if (model.isIscritto(corso, matricola)) {
				txtAreaStampa.appendText("Studente già iscritto a questo corso");
				return;
			}

			// Iscrivo lo studente al corso.
			// Controllo che l'inserimento vada a buon fine
			if (!model.inscriviStudenteACorso(studente, corso)) {  //metodo model che richiama metodo dao
				txtAreaStampa.appendText("Errore durante l'iscrizione al corso");
				return;
			} else {
				txtAreaStampa.appendText("Studente iscritto al corso!");
			}

		} catch (NumberFormatException e) {
			txtAreaStampa.setText("Inserire una matricola nel formato corretto.");
		} catch (RuntimeException e) {
			txtAreaStampa.setText("ERRORE DI CONNESSIONE AL DATABASE!");
		}
    }


    

    @FXML
    void doVerificaIscrizione(ActionEvent event) {

    	int matricola=Integer.parseInt(txtMatricola.getText());
    	Corso corso=txtTendina.getValue(); //get value per BOX
    	
    	if(model.isIscritto(corso, matricola)==true)
    		txtAreaStampa.setText("Studente iscritto al corso");
    	else
    		txtAreaStampa.setText("Studente non iscritto al corso");
    	
    	
    }

    


	@FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtTendina != null : "fx:id=\"txtTendina\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtBIscrittiCorso != null : "fx:id=\"txtBIscrittiCorso\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtBCercaCorsi != null : "fx:id=\"txtBCercaCorsi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtBIscrivi != null : "fx:id=\"txtBIscrivi\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtAreaStampa != null : "fx:id=\"txtAreaStampa\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert txtBreset != null : "fx:id=\"txtBreset\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnSpunta != null : "fx:id=\"btnSpunta\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";
        assert btnIscrizione != null : "fx:id=\"btnIscrizione\" was not injected: check your FXML file 'SegreteriaStudenti.fxml'.";

     // Utilizzare questo font per incolonnare correttamente i dati
        txtAreaStampa.setStyle("-fx-font-family: monospace");
    }
}

