package it.polito.tdp.lab04.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;


public class Model {

	//creo metodo per ottenere corsi e studenti dal DB
	//non direttamente perchè i dati sono nel database
	//controller richiama i metodi di mode, il quale può comunicare col dao che si interfaccia col databse
	//se implementano corso uso corsoDao altriemnti studentiDao
	
	private StudenteDAO studenteDao;
	private CorsoDAO corsoDao;

	public Model() {

		studenteDao = new StudenteDAO();
		corsoDao = new CorsoDAO();
	}
	
	public List<Corso> listaCorsi() {
	    return corsoDao.getTuttiICorsi();
	}
	
	public List<Studente> listaStudenti() {
		return studenteDao.getTuttiIStudenti();
    }
	
	public Studente getStudente(int matricola) {
		return studenteDao.getStudente(matricola);
	}
	
	
	public List<Studente> studentiIscrittiAlCorso(Corso corso) {
	return corsoDao.getStudentiIscrittiAlCorso(corso);
	}

	public List<Corso> cercaCorsiDatoStudente(int matricola) {
		return studenteDao.getCorsiStudente(matricola);
	}
	
	public boolean isIscritto(Corso corso, int matricola) {
		return studenteDao.isStudenteIscrittoACorso(matricola, corso);
	}
	
	/*
	 * Iscrivi una studente ad un corso. Ritorna TRUE se l'operazione va a buon fine.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {

		return corsoDao.inscriviStudenteACorso(studente, corso);
	}

}
