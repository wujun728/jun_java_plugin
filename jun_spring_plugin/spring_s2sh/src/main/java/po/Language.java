package po;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="language")
public class Language {
	@Id @Column(name="language_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_update;
	
	@OneToMany(targetEntity=Film.class,mappedBy="language",fetch=FetchType.EAGER)
	private Set<Film> films=new HashSet<Film>();
	
	@OneToMany(targetEntity=Film.class,mappedBy="language2",fetch=FetchType.EAGER)
	private Set<Film> films2=new HashSet<Film>();
	
	
	public Set<Film> getFilms() {
		return films;
	}
	public void setFilms(Set<Film> films) {
		this.films = films;
	}
	public Set<Film> getFilms2() {
		return films2;
	}
	public void setFilms2(Set<Film> films2) {
		this.films2 = films2;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	
}
