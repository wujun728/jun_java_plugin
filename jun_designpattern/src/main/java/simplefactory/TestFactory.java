/**
 * 
 */
package simplefactory;

/**
 * @author Asus
 *
 */
public class TestFactory {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Api api = ApiFactory.createApi(1);
		Api api = ApiFactory.createApi(2);
		api.print();
		
		Api api2 = ApiFactory.createApiByPropertites();
		api2.print();
	}

}
