
 package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> ChoicheCorsi;

    @FXML
    private Button btnCercaIscritti;

    @FXML
    private TextField txtMatricola;

    @FXML
    private Button btnOK;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCognome;

    @FXML
    private Button btnCercaCorso;

    @FXML
    private Button btnIscrivi;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

    @FXML
    void doCercaCorso(ActionEvent event) {
    	int matricola;
    	String inserito;
    	try {
    	inserito=this.txtMatricola.getText();
    	matricola=Integer.parseInt(inserito);
    	}catch(NumberFormatException ne)
    	{
    		ne.printStackTrace();
    		this.txtResult.setText("devi inserire un numero");
    		return;
    	}
    	if(inserito.length()!=6)
    	{
    		this.txtResult.setText("formato matricola errato");
    		return;
    	}
    	Studente s=this.model.getTuttiStudenti().get(matricola);
    	
    	txtResult.setStyle("-fx-font-family:monospace");
    	StringBuilder sb=new StringBuilder();
    	for(Corso c:this.model.getTuttiICorsiSingoloStudente(s))
    	{
    		//per inclolonnare il testo indichiamo "%-8"
    		// %=tutto questo va formattato
    		//-= incolonnato a sx
    		//8=colonna larga 8
    		// s=stringa d=intero
    		// faccio la stessa append per tutti gli elementi
    		sb.append(String.format("%-8s", c.getCodins()));
    		sb.append(String.format("%-4d", c.getCrediti()));
    		sb.append(String.format("%-50s", c.getNome()));
    		sb.append(String.format("%-4d\n", c.getPd()));
    	}
    	txtResult.appendText(sb.toString());
    	/*
    	String ris="";
    	for(Corso c:this.model.getTuttiICorsiSingoloStudente(s))
    	{
    		ris+=c+"\n";
    	}
    	if(ris.equals(""))
    	{
    		this.txtResult.setText("lo studente non è iscritto ad alcun corso");
    		return;
    	}
    	else
    	{
    		this.txtResult.setText(ris);
    	}*/

    }

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	String nomeCorso=ChoicheCorsi.getValue();
    	if(nomeCorso==null || nomeCorso.equals("altro"))
    	{
    		this.txtResult.setText("Seleziona un corso!");
    	}
    	Corso corsoPassato=null;
    	for(Corso c:this.model.getTuttiICorsi())
    	{
    		if(c.getNome().equals(nomeCorso))
    		{
    			corsoPassato=c;
    		}
    	}
    	this.model.getDao().getStudentiIscrittiAlCorso(corsoPassato);
    	txtResult.setStyle("-fx-font-family:monospace");
    	StringBuilder sb=new StringBuilder();
    	for(Studente e:this.model.getDao().getStudentiPerCorso())
    	{
    		//per inclolonnare il testo indichiamo "%-8"
    		// %=tutto questo va formattato
    		//-= incolonnato a sx
    		//8=colonna larga 8
    		// s=stringa d=intero
    		// faccio la stessa append per tutti gli elementi
    		sb.append(String.format("%-11d", e.getMatricola()));
    		sb.append(String.format("%-30s", e.getCognome()));
    		sb.append(String.format("%-30s", e.getNome()));
    		sb.append(String.format("%-11s\n", e.getCDS()));
    	}
    	txtResult.appendText(sb.toString());
    	
    	
    	/*String risultato="";
    	for(Studente e:this.model.getDao().getStudentiPerCorso())
    	{
    		risultato+=e.toString()+"\n";
    	}
    
    	this.txtResult.setText(risultato);
*/
    }

    @FXML
    void doIscrivi(ActionEvent event) {
    	int matricola;
    	String inserito;
    	String nomeCorso;
    	Corso corsoPassato=null;
    	boolean trovato=false;
    	boolean iscritto=false;
    	try {
    	inserito=this.txtMatricola.getText();
        matricola=Integer.parseInt(inserito);
    	nomeCorso=ChoicheCorsi.getValue();
    	}catch(NumberFormatException ne)
    	{
    		ne.printStackTrace();
    		this.txtResult.setText("devi inserire un numero");
    		return;
    	}
    	if(inserito.length()!=6)
    	{
    		this.txtResult.setText("formato matricola errato");
    		return;
    	}
    	for(Corso c:this.model.getTuttiICorsi())
    	{
    		if(c.getNome().equals(nomeCorso))
    		{
    			corsoPassato=c;
    		}
    	}
    	if(corsoPassato!=null)
    	{
    		Studente s=this.model.getTuttiStudenti().get(matricola);
    		this.model.getDao().getStudentiIscrittiAlCorso(corsoPassato);
    		for(Studente st:this.model.getDao().getStudentiPerCorso())
    		{
    			if(st.equals(s))
    			{
    				trovato=true;
    				iscritto=true;
    			}
    		}
    		if(trovato==true)
    		{
    			this.txtResult.setText("studente già iscritto al corso");
    			return;
    		}
    		else if(trovato==false && iscritto==false)
    		{
    			this.txtResult.appendText("studente NON iscritto al corso \n");
    			this.model.inscriviStudenteACorso(s, corsoPassato);
    			this.txtResult.appendText("studente ora iscritto al corso");
    			return;
    		}
    		
    	}
    	else
    	{
    		this.txtResult.setText("corso inserito non valido");
			return;
    	}
    	
    	
    	

    }

    @FXML
    void doOK(ActionEvent event) {
    	int matricola;
    	String inserito;
    	String nome=null;
    	String cognome=null;
    	try {
    	inserito=this.txtMatricola.getText();
    	matricola=Integer.parseInt(inserito);
    	}catch(NumberFormatException ne)
    	{
    		ne.printStackTrace();
    		this.txtResult.setText("devi inserire un numero");
    		return;
    	}
    	if(inserito.length()!=6)
    	{
    		this.txtResult.setText("formato matricola errato");
    		return;
    	}
    	
    	nome=this.model.getTuttiStudenti().get(matricola).getNome();
    	cognome=this.model.getTuttiStudenti().get(matricola).getCognome();
    	if(nome==null || cognome==null)
    	{
    		this.txtResult.setText("matricola inserita non valida");
    		return;
    	}
    	else
    	{
    	this.txtNome.setText(nome);
    	this.txtCognome.setText(cognome);
    	}

    }

    @FXML
    void doReset(ActionEvent event) {
    	this.txtResult.clear();
    	this.txtMatricola.clear();
    	this.txtNome.clear();
    	this.txtCognome.clear();

    }
     
    public void setModel(Model model)
    {
    	this.model=model;
    	List<String> nomeCorsi=this.model.getNomeCorsi(this.model.getTuttiICorsi());
    	this.ChoicheCorsi.getItems().addAll(nomeCorsi);
    	this.ChoicheCorsi.getItems().add("altro");
    }

    @FXML
    void initialize() {
        assert ChoicheCorsi != null : "fx:id=\"ChoicheCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscritti != null : "fx:id=\"btnCercaIscritti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnOK != null : "fx:id=\"btnOK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorso != null : "fx:id=\"btnCercaCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}
