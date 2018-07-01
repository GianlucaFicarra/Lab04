package it.polito.tdp.lab04.model;

public class Studente {  //JAVA BEAN
	
	private int matricola;
	private String nome;
	private String cognome;
	private String Cds;
	
	
	public Studente(int matricola, String nome, String cognome, String cds) {
		this.matricola = matricola;
		this.nome = nome;
		this.cognome = cognome;
		this.Cds = cds;
	}
	
	/*
	 * Getters and Setters
	 */
	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}


	public void setNome(String nome) {
		
		this.nome = nome;
	}


	public void setCognome(String cognome) {
	
		this.cognome = cognome;
	}


	public void setCds(String cds) {
		Cds = cds;
	}


	public int getMatricola() {
		return matricola;
	}
	public String getNome() {
		if (nome == null)
			return "";
		return nome;
	}
	public String getCognome() {
		if (cognome == null)
			return "";
		return cognome;
	}
	public String getCds() {
		if (Cds == null)
			return "";
		return Cds;
	}
	

	/*
	 * Hash Code and Equals
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matricola;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Studente other = (Studente) obj;
		if (matricola != other.matricola)
			return false;
		return true;
	}

	/*
	 * Metodo toString()
	 */
	@Override
	public String toString() {
		return cognome + " " + nome;
	}
}
