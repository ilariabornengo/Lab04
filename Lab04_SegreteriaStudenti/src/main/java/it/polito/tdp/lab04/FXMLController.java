
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
    	String ris="";
    	for(Corso c:this.model.getTuttiICorsiSingoloStudente(s))
    	{
    		ris+=c+"\n";
    	}
    	this.txtResult.setText(ris);

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
    	String risultato="";
    	for(Studente e:this.model.getDao().getStudentiPerCorso())
    	{
    		risultato+=e.toString()+"\n";
    	}
    
    	this.txtResult.setText(risultato);

    }

    @FXML
    void doIscrivi(ActionEvent event) {

    }

    @FXML
    void doOK(ActionEvent event) {
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
    	String nome=this.model.getTuttiStudenti().get(matricola).getNome();
    	String cognome=this.model.getTuttiStudenti().get(matricola).getCognome();
    	this.txtNome.setText(nome);
    	this.txtCognome.setText(cognome);

    }

    @FXML
    void doReset(ActionEvent event) {

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
