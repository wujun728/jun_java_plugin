package org.lxh.ajaxdemo ;
import java.io.* ;
import org.w3c.dom.* ;
import javax.xml.parsers.* ;
import javax.xml.transform.* ;
import javax.xml.transform.dom.* ;
import javax.xml.transform.stream.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;

public class CityServlet extends HttpServlet {
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		this.doPost(request,response) ;
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		response.setContentType("text/xml;charset=GBK") ;
		PrintWriter out = response.getWriter() ;
		ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
			DocumentBuilder builder = factory.newDocumentBuilder() ;
			Document doc = builder.newDocument() ;
			String data[] = {"北京","南京","上海","天津","香港"} ;
			Element allarea = doc.createElement("allarea") ;
			for(int x=0;x<data.length;x++){
				Element area = doc.createElement("area") ;	
				Element id = doc.createElement("id") ;
				Element title = doc.createElement("title") ;
				id.appendChild(doc.createTextNode("" + (x+1))) ;
				title.appendChild(doc.createTextNode(data[x])) ;
				area.appendChild(id) ;
				area.appendChild(title) ;
				allarea.appendChild(area) ;
			}
			doc.appendChild(allarea) ;
			TransformerFactory tf = TransformerFactory.newInstance() ;
			Transformer t = tf.newTransformer() ;
			t.setOutputProperty(OutputKeys.ENCODING,"GBK") ;
			DOMSource source = new DOMSource(doc) ;
			StreamResult result = new StreamResult(bos) ;
			t.transform(source,result) ;
			out.println(bos) ;
			System.out.println(bos) ;
			out.close() ;
		}catch(Exception e){
			e.printStackTrace() ;
		}
	}
}