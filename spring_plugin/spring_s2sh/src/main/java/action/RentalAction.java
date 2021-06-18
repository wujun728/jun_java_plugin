package action;

import org.apache.struts2.json.annotations.JSON;

import po.Rental;
import service.RentalService;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class RentalAction extends ActionSupport{
	private int id;
	private Rental rental;
	private RentalService rentalservice;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Rental getRental() {
		return rental;
	}
	public void setRental(Rental rental) {
		this.rental = rental;
	}
	@JSON(serialize=false)
	public RentalService getRentalservice() {
		return rentalservice;
	}
	public void setRentalservice(RentalService rentalservice) {
		this.rentalservice = rentalservice;
	}
	public String execute()  {
		return Action.SUCCESS;
	}
	
	public String delete(){
		rentalservice.deleterental(id);
		return "ok";
	}
	
	public String getinfo(){
		rental=rentalservice.getRentalByid(id);
		return "ok";
	}
	
	public String update(){
		rentalservice.updaterental(rental);
		return "ok";
	}
	
	public String insert(){
		rentalservice.addrental(rental);
		return "ok";
	}
}
