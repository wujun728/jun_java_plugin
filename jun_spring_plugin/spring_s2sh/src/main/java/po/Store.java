package po;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="store")
public class Store {
	@Id @Column(name="store_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private byte id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date last_update;

	@OneToOne(targetEntity=Staff.class)
	@JoinColumn(name="manager_staff_id",referencedColumnName="staff_id",unique=true)
	Staff staff;

	public byte getId() {
		return id;
	}

	public void setId(byte id) {
		this.id = id;
	}

	public Date getLast_update() {
		return last_update;
	}

	public void setLast_update(Date last_update) {
		this.last_update = last_update;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

}
