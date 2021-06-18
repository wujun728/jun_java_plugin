package jdbc.viewspace;

import java.util.List;

public interface ViewSpaceDao {
	
	public abstract void createViewSpaceTable();
	
	public void addViewSpace(ViewSpace viewSpace);
	
	public void addViewSpaceList(List<ViewSpace> list);
	
	public ViewSpace getViewSpaceOne(int spaceId);

	public List<ViewSpace> getViewSpaceList(int startrow,int endrow); 
	
	public List<ViewSpace> getViewSpaces(int startrow, int endrow);
	
	public void procedure();
	
	public int procedureExe(int spaceid);
	
}
