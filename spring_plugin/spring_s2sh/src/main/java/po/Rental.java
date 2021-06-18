package po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="rental")
public class Rental {
	@Id @Column(name="rental_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date rental_date;
	private int inventory_id;
	private short customer_id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date return_date;
	private byte staff_id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_update;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getRental_date() {
		return rental_date;
	}
	public void setRental_date(Date rental_date) {
		this.rental_date = rental_date;
	}
	public int getInventory_id() {
		return inventory_id;
	}
	public void setInventory_id(int inventory_id) {
		this.inventory_id = inventory_id;
	}
	
	public short getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(short customer_id) {
		this.customer_id = customer_id;
	}
	public Date getReturn_date() {
		return return_date;
	}
	public void setReturn_date(Date return_date) {
		this.return_date = return_date;
	}
	
	public byte getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(byte staff_id) {
		this.staff_id = staff_id;
	}
	public Date getLast_update() {
		return last_update;
	}
	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}
	
}
