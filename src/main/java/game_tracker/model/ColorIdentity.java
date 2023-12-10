package game_tracker.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class ColorIdentity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Boolean white;
	
	private Boolean blue;
	
	private Boolean black;
	
	private Boolean red;
	
	private Boolean green;
	
	@OneToMany(mappedBy = "colorIdentity", cascade = CascadeType.ALL)
	private List<Commander> commander;

	public ColorIdentity() {
		super();
	}

	public ColorIdentity(Integer id, Boolean white, Boolean blue, Boolean black, Boolean red, Boolean green,
			List<Commander> commander) {
		super();
		this.id = id;
		this.white = white;
		this.blue = blue;
		this.black = black;
		this.red = red;
		this.green = green;
		this.commander = commander;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getWhite() {
		return white;
	}

	public void setWhite(Boolean white) {
		this.white = white;
	}

	public Boolean getBlue() {
		return blue;
	}

	public void setBlue(Boolean blue) {
		this.blue = blue;
	}

	public Boolean getBlack() {
		return black;
	}

	public void setBlack(Boolean black) {
		this.black = black;
	}

	public Boolean getRed() {
		return red;
	}

	public void setRed(Boolean red) {
		this.red = red;
	}

	public Boolean getGreen() {
		return green;
	}

	public void setGreen(Boolean green) {
		this.green = green;
	}

	public List<Commander> getCommander() {
		return commander;
	}

	public void setCommander(List<Commander> commander) {
		this.commander = commander;
	}
}
