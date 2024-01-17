package game_tracker.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Player implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")  // Assuming "user_id" is the foreign key column name
    private User user;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
	private List<Deck> deck;
	
	public Player() {
		super();
	}

	public Player(Integer id, String name, User user, List<Deck> deck) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
		this.deck = deck;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Deck> getDeck() {
		return deck;
	}

	public void setDeck(List<Deck> deck) {
		this.deck = deck;
	}
}
