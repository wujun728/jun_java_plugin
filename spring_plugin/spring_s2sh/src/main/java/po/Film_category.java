package po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="film_category")
public class Film_category implements java.io.Serializable{
	@Id @Column(name="film_id")
	private int id;
	@ManyToOne(targetEntity=Film.class)
	@JoinColumn(name="film_id",referencedColumnName="film_id",nullable=false)
	private Film film;
	
	@Id
	private int categroy_id;
	@ManyToOne(targetEntity=Category.class)
	@JoinColumn(name="categroy_id",referencedColumnName="category_id",nullable=false)
	private Category category;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_update;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Film getFilm() {
		return film;
	}
	public void setFilm(Film film) {
		this.film = film;
	}
	
	
	public int getCategroy_id() {
		return categroy_id;
	}
	public void setCategroy_id(int categroy_id) {
		this.categroy_id = categroy_id;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	
}
