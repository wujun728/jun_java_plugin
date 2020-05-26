	import net.sf.json.JSONArray;
  
  public static String toJson(Object resultobj)   {
		if (resultobj != null) {
			JSONArray obj = JSONArray.fromObject(resultobj);  
			return (obj.toString());
		}
		return "";
    }                  


    	public void toJson(Object resultobj) throws Exception {
			if (resultobj != null) {
				// 返回json字符串
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setContentType("text/html; charset=utf-8");
				JSONArray obj = JSONArray.fromObject(resultobj); // 集合转为json
				PrintWriter out = response.getWriter();
				out.println(obj.toString());
				out.flush();
				out.close();
			}
	}




        //调用
   ArrayList<StRain> list2=getData.StPptnRToStRain();
    	// 返回json字符串
      toJson( list2);






 public class StRain {
	//时间
   String time;
   //雨量
   double val;       


}