package game_tracker.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Deck implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "player_id", referencedColumnName = "id")
	private Player player;
	
	@ManyToOne(cascade = CascadeType.MERGE)
	@JoinColumn(name = "commander_id", referencedColumnName = "id")
	private Commander commander;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	public Deck() {
		super();
	}

	public Deck(Integer id, Player player, Commander commander, String name) {
		super();
		this.id = id;
		this.player = player;
		this.commander = commander;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Commander getCommander() {
		return commander;
	}

	public void setCommander(Commander commander) {
		this.commander = commander;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
