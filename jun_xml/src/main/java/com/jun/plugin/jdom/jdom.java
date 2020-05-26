package com.jun.plugin.jdom;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

//import com.jun.web.biz.beans.Areaplus;
//import com.jun.web.servlet.Document;
//import com.jun.web.servlet.Element;
//import com.jun.web.servlet.XMLOutputter;

/**
 * Servlet implementation class jdom
 */
@WebServlet("/jdom")
public class jdom extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public jdom() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	

	@SuppressWarnings("unused")
	public void AreaServletsearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		response.setCharacterEncoding("GBK");
		response.setContentType("text/xml");
		String kw = new String(request.getParameter("kw").getBytes("ISO-8859-1"));
		System.out.println(kw);
		List<Areaplus> list = null;
		Document document = new Document();
		Element root = new Element("allplus");
		if (list != null) {
			Iterator<Areaplus> iter = list.iterator();
			while (iter.hasNext()) {
				Areaplus ap = iter.next();
				Element plus = new Element("plus");
				Element title = new Element("title");
				title.addContent(ap.getTitle());
				plus.addContent(title);
				root.addContent(plus);
			}
		}
		document.setRootElement(root);
		PrintWriter out = response.getWriter();
		XMLOutputter outputter = new XMLOutputter();
		// outputter.setEncoding("GBK") ;
		outputter.output(document, out);
		out.close();
	}

	public void AreaServletshowplus(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("GBK");
		response.setCharacterEncoding("GBK");
		response.setContentType("text/xml");
		int upid = Integer.parseInt(request.getParameter("upid"));
		List<Areaplus> list = null;
		PrintWriter out = response.getWriter();
		// StringBuffer buffer = new StringBuffer() ;
		Document document = new Document();
		Element root = new Element("allplus");
		if (list != null) {
			Iterator<Areaplus> iter = list.iterator();
			while (iter.hasNext()) {
				Areaplus ap = iter.next();
				Element plus = new Element("plus");
				Element id = new Element("id");
				Element title = new Element("title");
				id.addContent(ap.getId().toString());
				title.addContent(ap.getTitle());
				plus.addContent(id);
				plus.addContent(title);
				root.addContent(plus);
				// buffer.append(plus.getId().toString()) ;
				// buffer.append(":") ;
				// buffer.append(plus.getTitle()) ;
				// buffer.append("|") ;
			}
			document.setRootElement(root);
			XMLOutputter outputter = new XMLOutputter();
			// outputter.setEncoding("GBK") ;
			outputter.output(document, out);
			// out.print(buffer.substring(0, buffer.length()-1)) ;
		}
		out.close();
	}

}
