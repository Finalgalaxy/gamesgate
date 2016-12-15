package models;

public class Videogame{
	private String nome_videogame,immagine_str,nome_produttore;
	private int numero_uscita,prezzo,anno_uscita;
	
	public Videogame(String nome_videogame,String immagine_str,int numero_uscita,int prezzo,String nome_produttore,
			int anno_uscita){
		this.nome_videogame=nome_videogame;
		this.immagine_str=immagine_str;
		this.numero_uscita=numero_uscita;
		this.prezzo=prezzo;
		this.nome_produttore=nome_produttore;
		this.anno_uscita=anno_uscita;
	}
	
	public String getNome(){
		return nome_videogame;
	}
	public String getImg(){
		return immagine_str;
	}
	public int getNumeroUscita(){
		return numero_uscita;
	}
	public int getPrezzo(){
		return prezzo;
	}
	public String getProduttore(){
		return nome_produttore;
	}
	public int getAnnoUscita(){
		return anno_uscita;
	}
	
	public void setNome(String nome_videogame){
		this.nome_videogame=nome_videogame;
	}
	public void setImg(String immagine_str){
		this.immagine_str=immagine_str;
	}
	public void setNumeroUscita(int numero_uscita){
		this.numero_uscita=numero_uscita;
	}
	public void setPrezzo(int prezzo){
		this.prezzo=prezzo;
	}
	public void setAnnoUscita(int anno_uscita){
		this.anno_uscita=anno_uscita;
	}
}
