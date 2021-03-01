/**
 * 
 */
package simplefactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Wujun
 *
 */
public class ApiFactory {
	
	private ApiFactory() {
		
	}
	
	public static Api createApiByPropertites() {
		InputStream in = null;
		Properties properties = new Properties();
		try {
			in = ApiFactory.class.getResourceAsStream("api_condition.propertites");
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}	
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		int type = Integer.parseInt(properties.getProperty("type"));
		return createApi(type);
	}

	public static Api createApi(int type) {
		if (type == 1) {
			return new ApiImpA();
		} else if (type == 2) {
			return new ApiImpB();
		} 
		return null;
	}
}
