package po;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="film_actor")
public class Film_actor implements java.io.Serializable{
	@Id @Column(name="actor_id")
	private int id;
	@Id
	private int film_id;
	@ManyToOne(targetEntity=Film.class)
	@JoinColumn(name="film_id",referencedColumnName="film_id",nullable=false)
	private Film film;
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_update;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFilm_id() {
		return film_id;
	}
	public void setFilm_id(int film_id) {
		this.film_id = film_id;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	public Film getFilm() {
		return film;
	}
	public void setFilm(Film film) {
		this.film = film;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + film_id;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Film_actor other = (Film_actor) obj;
		if (film_id != other.film_id)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	
}
