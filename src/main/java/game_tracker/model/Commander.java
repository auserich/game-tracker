package game_tracker.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Commander implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "commander", cascade = CascadeType.ALL)
	private List<Deck> deck;
	
	private String colorIdentity;

	public Commander() {
		super();
	}

	public Commander(Integer id, String name, List<Deck> deck, String colorIdentity) {
		super();
		this.id = id;
		this.name = name;
		this.deck = deck;
		this.colorIdentity = colorIdentity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Deck> getDeck() {
		return deck;
	}

	public void setDeck(List<Deck> deck) {
		this.deck = deck;
	}

	public String getColorIdentity() {
		return colorIdentity;
	}

	public void setColorIdentity(String colorIdentity) {
		this.colorIdentity = colorIdentity;
	}
}
