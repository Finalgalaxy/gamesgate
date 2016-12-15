package models;

public class Achievement {
	private String descrizione,tipo;
	
	public Achievement(String descrizione, String tipo){
		this.descrizione=descrizione;
		this.tipo=tipo;
	}
	public String getDesc(){return descrizione;}
	public String getTipo(){return tipo;}
	public void setDesc(String descrizione){this.descrizione=descrizione;}
	public void setTipo(String tipo){this.tipo=tipo;}
}
