package models;

public class Produttore {
	private String nome_produttore,immagine_str,desc;
	private int anni_exp;
	
	public Produttore(String nome_produttore, String immagine_str, int anni_exp){
		this.nome_produttore=nome_produttore;
		this.immagine_str=immagine_str;
		this.anni_exp=anni_exp;
	}
	
	public Produttore(String nome_produttore, String immagine_str, String desc, int anni_exp){
		this(nome_produttore,immagine_str,anni_exp);
		this.desc = desc;
	}
	
	public String getNome(){
		return nome_produttore;
	}
	public String getImg(){
		return immagine_str;
	}
	public int getAnniExp(){
		return anni_exp;
	}
	public String getDescrizione(){
		return desc;
	}
	
	public void setNome(String nome){
		this.nome_produttore=nome;
	}
	public void setImg(String img){
		this.immagine_str=img;
	}
	public void setAnniExp(int anni_exp){
		this.anni_exp=anni_exp;
	}
}
