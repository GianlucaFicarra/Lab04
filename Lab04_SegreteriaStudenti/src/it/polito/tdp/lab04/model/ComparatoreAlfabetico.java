package it.polito.tdp.lab04.model;

import java.util.Comparator;

public class ComparatoreAlfabetico  implements Comparator<Corso> {

	@Override
	public int compare(Corso c1, Corso c2) {
		return c1.getNome().compareTo(c2.getNome());
	}

}
