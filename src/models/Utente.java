package models;

public class Utente {
	@SuppressWarnings("unused")
	private String CF_UTENTE, nome, cognome, via, numero_civico;
	@SuppressWarnings("unused")
	private int cap;
	
	public Utente(String nome,String cognome){
		this.nome=nome;
		this.cognome=cognome;
	}
	public Utente(String CF_UTENTE,String nome,String cognome){
		this(nome,cognome);
		this.CF_UTENTE=CF_UTENTE;
	}
	public Utente(String CF_UTENTE,String nome,String cognome,String via,int cap,String numero_civico){
		this(CF_UTENTE,nome,cognome);
		this.via=via;
		this.cap=cap;
		this.numero_civico=numero_civico;
	}
	public String getCF_utente(){
		return CF_UTENTE;
	}
	public String getNome(){
		return nome;
	}
	public String getCognome(){
		return cognome;
	}
	public void setNome(String nome){
		this.nome=nome;
	}
	public void setCognome(String cognome){
		this.cognome=cognome;
	}
}
