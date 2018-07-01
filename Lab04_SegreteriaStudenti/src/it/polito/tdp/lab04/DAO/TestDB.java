package it.polito.tdp.lab04.DAO;

public class TestDB {

	public static void main(String[] args) {
		
		/*
		 * 	This is a main to check the DB connection
		 */
		

		try {
			CorsoDAO cdao = new CorsoDAO();
			cdao.getTuttiICorsi();
			System.out.println("TestDB passed");

		} catch (RuntimeException e) {
			System.err.println("TestDB failed");
		}
	}

}
