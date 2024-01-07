package game_tracker.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
public class Color implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public enum ColorCode {
        W, U, B, R, G
    }
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, nullable = false)
	@Enumerated(EnumType.STRING)
	private ColorCode code;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
	private List<ColorIdentity> colorIdentity;
	
	public Color() {
		super();
	}

	public Color(Integer id, ColorCode code, List<ColorIdentity> colorIdentity) {
		super();
		this.id = id;
		this.code = code;
		this.colorIdentity = colorIdentity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ColorCode getCode() {
		return code;
	}

	public void setCode(ColorCode code) {
		this.code = code;
	}

	public List<ColorIdentity> getColorIdentity() {
		return colorIdentity;
	}

	public void setColorIdentity(List<ColorIdentity> colorIdentity) {
		this.colorIdentity = colorIdentity;
	}
}
