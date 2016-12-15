package models;

public class Genere {
	private String nome_genere;
	
	public Genere(String nome_genere){
		this.nome_genere=nome_genere;
	}
	public String getNome(){
		return nome_genere;
	}
	public void setNome(String nome){
		this.nome_genere=nome;
	}
}
