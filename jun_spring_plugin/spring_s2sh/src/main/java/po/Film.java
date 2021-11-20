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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="film")
public class Film {
	@Id @Column(name="film_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	private String description;
	private int release_year;
	@ManyToOne(targetEntity=Language.class)
	@JoinColumn(name="language_id",referencedColumnName="language_id",nullable=false)
	private Language language;
	
	@ManyToOne(targetEntity=Language.class)
	@JoinColumn(name="original_language_id",referencedColumnName="language_id",nullable=false)
	private Language language2;
	
	@OneToMany(targetEntity=Film_actor.class,mappedBy="film",fetch=FetchType.EAGER)
	private Set<Film> films=new HashSet<Film>();
	
	
	@OneToMany(targetEntity=Inventory.class,mappedBy="film",fetch=FetchType.EAGER)
	private Set<Inventory> inventorys=new HashSet<Inventory>();
	
	private int rental_duration;
	private float rental_rate;
	private int length;
	private float replacement_cost;
	private String rating;
	private String special_features;
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_update;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getRelease_year() {
		return release_year;
	}
	public void setRelease_year(int release_year) {
		this.release_year = release_year;
	}
	public int getRental_duration() {
		return rental_duration;
	}
	public void setRental_duration(int rental_duration) {
		this.rental_duration = rental_duration;
	}
	public float getRental_rate() {
		return rental_rate;
	}
	public void setRental_rate(float rental_rate) {
		this.rental_rate = rental_rate;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public float getReplacement_cost() {
		return replacement_cost;
	}
	public void setReplacement_cost(float replacement_cost) {
		this.replacement_cost = replacement_cost;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getSpecial_features() {
		return special_features;
	}
	public void setSpecial_features(String special_features) {
		this.special_features = special_features;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	public Language getLanguage() {
		return language;
	}
	public void setLanguage(Language language) {
		this.language = language;
	}
	public Language getLanguage2() {
		return language2;
	}
	public void setLanguage2(Language language2) {
		this.language2 = language2;
	}
	public Set<Film> getFilms() {
		return films;
	}
	public void setFilms(Set<Film> films) {
		this.films = films;
	}
	public Set<Inventory> getInventoryss() {
		return inventorys;
	}
	public void setInventoryss(Set<Inventory> inventoryss) {
		this.inventorys = inventoryss;
	}
	
}
