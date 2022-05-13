package model;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException; 
import java.util.HashMap; 
import java.util.Map;
import java.util.Scanner;



@WebServlet("/BillAPI")
public class BillAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
     samplebill itemObj=new samplebill(); 
   
    public BillAPI() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException 
			{ 
			 String output = itemObj.insertBill(request.getParameter("category"),
			request.getParameter("acno"),		 
		    request.getParameter("year"),
			request.getParameter("month"),
			request.getParameter("due"), 
			request.getParameter("noofunits")); 
			response.getWriter().write(output); 
			}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException 
			{ 
			 Map paras = getParasMap(request); 
			 String output = itemObj.updateBill(paras.get("hidItemIDSave").toString(), 
			paras.get("category").toString(),
		    paras.get("acno").toString(), 
			paras.get("year").toString(), 
			paras.get("month").toString(),
			paras.get("due").toString(), 
			paras.get("noofunits").toString()); 
			response.getWriter().write(output); 
			} 
	
			
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
			 throws ServletException, IOException 
			{ 
			 Map paras = getParasMap(request); 
			 String output = itemObj.deleteBill(paras.get("itemID").toString()
					 
					 ); 
			response.getWriter().write(output); 
			}

	
	// Convert request parameters to a Map
	private static Map getParasMap(HttpServletRequest request) 
	{ 
	 Map<String, String> map = new HashMap<String, String>(); 
	try
	 { 
	 Scanner scanner = new Scanner(request.getInputStream(), "UTF-8"); 
	 String queryString = scanner.hasNext() ? 
	 scanner.useDelimiter("\\A").next() : ""; 
	 scanner.close(); 
	 String[] params = queryString.split("&"); 
	 for (String param : params) 
	 {
		 String[] p = param.split("="); 
		 map.put(p[0], p[1]); 
	  } 
	
	  } catch (Exception e) 
		 { 
		 } 
		return map; 
		}
	
}
