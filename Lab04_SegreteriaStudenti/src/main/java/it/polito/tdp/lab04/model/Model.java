package it.polito.tdp.lab04.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	List<Corso> corsi=new LinkedList<Corso>();
	List<String> corsiNome=new LinkedList<String>();
	StudenteDAO studenteDao=new StudenteDAO();
	CorsoDAO dao=new CorsoDAO();
	
	public List<Corso> getTuttiICorsi() {
		
		this.corsi = dao.getTuttiICorsi() ;
		return corsi;
	}
	
	public List<String> getNomeCorsi(List<Corso> corsi)
	{
		for(Corso c:this.corsi)
		{
			corsiNome.add(c.getNome());
		}
		return corsiNome;
	}
	
	public Map<Integer,Studente> getTuttiStudenti() {
		return studenteDao.getTuttiStudenti();
	}

	public StudenteDAO getStudenteDao() {
		return studenteDao;
	}

	public void setStudenteDao(StudenteDAO studenteDao) {
		this.studenteDao = studenteDao;
	}

	public CorsoDAO getDao() {
		return dao;
	}

	public void setDao(CorsoDAO dao) {
		this.dao = dao;
	}
	
	public List<Corso> getTuttiICorsiSingoloStudente(Studente studente) {
		return this.studenteDao.getTuttiICorsiSingoloStudente(studente);
	}
	
	

}
