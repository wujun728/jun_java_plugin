package jdbc.viewspace;

public class ViewSpace {
  
	private int     spaceId;
	private String  spaceName;
    private String  description;
    private String  address;

//constructor 
    public ViewSpace(){ }
    
    public ViewSpace(int id,String name,String des,String add){
    	this.spaceId=id; this.spaceName=name; this.description=des; this.address=add;
    }
    
//setter getter  
   public String getSpaceName() { return spaceName; }
   public void setSpaceName(String spaceName) { this.spaceName = spaceName; }
   public String getDescription() { return description; }
   public void setDescription(String description) { this.description = description;}
   public String getAddress() { return address; }
   public void setAddress(String address) { this.address = address; }
   public int getSpaceId() { return spaceId;}
   public void setSpaceId(int spaceId) { this.spaceId = spaceId; }

}
