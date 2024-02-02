package game_tracker.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
    private String date;
	
	@Column(nullable = false)
	private Integer gameNumber;
	
	@ManyToOne
    @JoinColumn(name = "deck1_id", referencedColumnName = "id")
	private Deck deck1;
	
	@ManyToOne
    @JoinColumn(name = "deck2_id", referencedColumnName = "id")
	private Deck deck2;
	
	@ManyToOne
    @JoinColumn(name = "deck3_id", referencedColumnName = "id")
	private Deck deck3;
	
	@ManyToOne
    @JoinColumn(name = "deck4_id", referencedColumnName = "id")
	private Deck deck4;
	
	@ManyToOne
    @JoinColumn(name = "winner_id", referencedColumnName = "id")
    private Deck winner;

	public Game() {
		super();
	}

	public Game(Integer id, String date, Integer gameNumber, Deck deck1, Deck deck2, Deck deck3, Deck deck4,
			Deck winner) {
		super();
		this.id = id;
		this.date = date;
		this.gameNumber = gameNumber;
		this.deck1 = deck1;
		this.deck2 = deck2;
		this.deck3 = deck3;
		this.deck4 = deck4;
		this.winner = winner;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getGameNumber() {
		return gameNumber;
	}

	public void setGameNumber(Integer gameNumber) {
		this.gameNumber = gameNumber;
	}

	public Deck getDeck1() {
		return deck1;
	}

	public void setDeck1(Deck deck1) {
		this.deck1 = deck1;
	}

	public Deck getDeck2() {
		return deck2;
	}

	public void setDeck2(Deck deck2) {
		this.deck2 = deck2;
	}

	public Deck getDeck3() {
		return deck3;
	}

	public void setDeck3(Deck deck3) {
		this.deck3 = deck3;
	}

	public Deck getDeck4() {
		return deck4;
	}

	public void setDeck4(Deck deck4) {
		this.deck4 = deck4;
	}

	public Deck getWinner() {
		return winner;
	}

	public void setWinner(Deck winner) {
		this.winner = winner;
	}
}
