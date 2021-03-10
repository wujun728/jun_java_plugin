package jdbc.viewcblob;

import java.io.File;
import java.io.IOException;


public interface ViewDao {	
     public void createViewTable();
     public void insertCBlob()throws Exception;
     public void insertViewCBlob(View view)throws Exception;
     public File getImgFile(int viewid)throws IOException;
}
