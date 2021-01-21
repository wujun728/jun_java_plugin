package abc.creater;

public class CreatFactory {
	
	  public static void main(String[] args)
	  {
		String tableName = "gs_fromto_ref";//琛ㄥ悕
		String modelName = "FromtoRef";//瀹炰綋鍚�
		String packageName = "com.wanxue";//鍖呭悕 *.java涓婇潰鐨刾ackage ....
		String modelURL = "src\\com\\wanxue\\model\\";//瀹炰綋璺緞
		String modelsql = "src\\com\\wanxue\\sql\\";//mybatis mapper(sql鐨勯厤缃枃浠�璺緞
		String daoURL = "src\\com\\wanxue\\dao\\";//dao鎺ュ彛璺緞
		String daoImplURL = "src\\com\\wanxue\\dao\\impl\\";//dao瀹炵幇璺緞
		String serviceURL = "src\\com\\wanxue\\service\\";//service鎺ュ彛璺緞
		String serviceImplURL = "src\\com\\wanxue\\service\\impl\\";//service瀹炵幇璺緞
		String serviceManagerURL = "src\\com\\wanxue\\service\\manager\\";//service绠＄悊鍣�鎶婃墍鏈夌殑service鎺ュ彛閮芥敞鍏ュ埌杩欓噷杈�缁熶竴鍒拌繖閲岃竟鍘绘嬁
		String controllerURL = "src\\com\\wanxue\\controller\\";//controller璺緞
		String utilURL = "src\\com\\wanxue\\util\\";		//util璺緞
		String xmlMybatis="src\\META-INF\\mybatis\\";	//mybatis璺緞[杩欎釜鏈�ソ涓嶈鏀�杩欓噷鏀逛簡鐨勮瘽,瑕佸湪鐢熸垚鐨剆pring鏂囦欢閲屽仛鐩稿簲鐨勬洿鏀筣
		String xmlSpring="src\\META-INF\\spring\\";	//spring璺緞[鍚屾牱杩欎釜涔熸渶濂戒笉瑕佹敼,杩欓噷鏀逛簡鐨勮瘽,瑕佸湪web.xml閲屽仛鐩稿簲鐨勬洿鏀筣
		String xmlWeb="WebRoot\\WEB-INF\\";	//web.xml鐨勮矾寰�褰撶劧杩欐槸鍥哄畾鐨�
		String MySQLDataBaseDrive = "com.mysql.jdbc.Driver";
		String MySQLDataBaseConnectStr = "jdbc:mysql://10.60.88.150:10000/gaoshoulog?characterEncoding=utf8";
		String MySQLDataBaseName = "root";
		String MySQLDataBasePassword = "wanxue@#$234ueg";
		/*oracle鐨勬病娴嬭瘯杩� 鍥犱负鑷繁鏈哄瓙涓婃病鏈塷racle,濡傛灉瑕佺敤oracle鐨勮瘽
		 * 灏卞湪涓嬮潰鐨勪唬鐮侀噷鐨勮繖鍙�
		 * gc.writeToFile(gc.mySQLTableToModel().toString(), gc.modelURL ,modelName + ".java");
		 * 鏀规垚
		 * gc.writeToFile(gc.oracleTableToModel().toString(), gc.modelURL ,modelName + ".java");
		 * 搴旇灏卞彲浠ヤ簡(娉ㄦ剰鎴戞病娴嬭瘯杩�
		 * */
		String DataBaseDrive = "oracle.jdbc.driver.OracleDriver";
		String DataBaseConnectStr = "jdbc:oracle:thin:@192.168.200.219:1521:orcl";
		String DataBaseName = "Project";
		String DataBasePassword = "Project";
	    CreateCode gc = new CreateCode(tableName,modelName, packageName, modelURL,modelsql, daoURL, daoImplURL, serviceURL, serviceImplURL, serviceManagerURL, controllerURL,utilURL, MySQLDataBaseDrive, MySQLDataBaseConnectStr, MySQLDataBaseName, MySQLDataBasePassword, DataBaseDrive, DataBaseConnectStr, DataBaseName, DataBasePassword,xmlMybatis,xmlSpring,xmlWeb);
	    gc.writeToFile(gc.mySQLTableToModel().toString(), gc.modelURL ,modelName + ".java");
	    gc.writeToFile(gc.tableToDao(), gc.daoURL, modelName +"Dao.java");
	    gc.writeToFile(gc.tableToDaoImpl(), gc.daoImplURL ,modelName +"DaoImpl.java");
	    gc.writeToFile(gc.tableToService(), gc.serviceURL ,modelName +"Service.java");
	    gc.writeToFile(gc.tableToServiceImpl(), gc.serviceImplURL ,modelName +"ServiceImpl.java");
	    gc.writeToFile(gc.tableToServiceManager(), gc.serviceManagerURL,"ServiceManager.java");
	    gc.writeToFile(gc.tableToController(), gc.controllerURL ,gc.modelName +"Controller.java");	
	    gc.writeToFile(gc.mybatisConfig(),gc.xmlMybatis,"mybatis.xml");
	    System.out.println("Auto create finish!");
	  }
}
