package it.uniroma3.siw.spring.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "findAllOperas", query = "SELECT o FROM Opera o")
public class Opera {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable=false)
	private String titolo;
	
	@Column(nullable=false)
	private String immagine;
	
	@Column(nullable=false)
	private String descrizione;
	
	@ManyToMany(mappedBy="opere")
	private List<Collezione> collezione;
	
	@ManyToOne
	private Artista artista;
	
	public Opera(){
		collezione=new ArrayList<Collezione>();
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getImmagine() {
		return immagine;
	}

	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public Artista getArtista() {
		return artista;
	}

	public void setArtista(Artista artista) {
		this.artista = artista;
	}

	public List<Collezione> getCollezione() {
		return collezione;
	}

	public void setCollezione(List<Collezione> collezione) {
		this.collezione = collezione;
	}
	
	
	
	
	
}
