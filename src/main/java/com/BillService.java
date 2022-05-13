package com;
//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document;


import model.samplebill;
@Path("/bill") 
public class BillService {
	samplebill billObject=new samplebill();
	
	
	public String resource() {
		Client c = Client.create();
		WebResource resource = c.resource("");
		String output = resource.get(String.class);
		return "From server-service: "+ output;	
	}
	
	
	@GET
	@Path("/readBill") 
	@Produces(MediaType.TEXT_PLAIN) 
	public String readItems() 
	 { 
	    return billObject.ReadBill(); 
	 } 
	
	@POST
	@Path("/") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String insertBill(@FormParam("category") String category,
	 @FormParam("acno") String acno, 
	 @FormParam("year") String year, 
	 @FormParam("month") String month, 
	 @FormParam("totalunits") String totalunits,
	 @FormParam("due") String due) 
	{ 
	 String output = billObject.insertBill(category, acno, year, month, totalunits,due);
	return output; 
	}


	@DELETE
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes(MediaType.APPLICATION_XML)
	public String deleteBill(String itemData) {
		//Convert the input string to an XML document
		 Document doc = Jsoup.parse(itemData, "", Parser.xmlParser()); 
		 
		//Read the value from the element <itemID>
		 //String acno = doc.select("acno").text();
		 String id = doc.select("id").text();
		// String year = doc.select("year").text();
		// String month = doc.select("month").text();
		 
		 String output = billObject.deleteBill(id);
		 return output;
	}


	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateItems(String itemData) {
		
		//Convert the input string to a JSON object 
		 JsonObject itemUpdateObject = new JsonParser().parse(itemData).getAsJsonObject();
		 
		//Read the values from the JSON object
		 String ID = itemUpdateObject.get("id").getAsString(); 
		 String category= itemUpdateObject.get("category").getAsString(); 
		 String AcNo = itemUpdateObject.get("acno").getAsString(); 
		 String Year = itemUpdateObject.get("year").getAsString(); 
		 String month = itemUpdateObject.get("month").getAsString(); 
		 String totalunits = itemUpdateObject.get("totalunits").getAsString();
		 String due = itemUpdateObject.get("due").getAsString();
		 
		String output= billObject.updateBill(ID, category, AcNo, Year, month, totalunits,due);
		return output;
	}
	
	
	
	
	@GET
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String searchItems(String itemData) {
		
		//Convert the input string to a JSON object 
		 JsonObject itemUpdateObject = new JsonParser().parse(itemData).getAsJsonObject();
		 
		//Read the values from the JSON object
		
		 String AcNo = itemUpdateObject.get("acno").getAsString(); 
		 String Year = itemUpdateObject.get("year").getAsString(); 
		 String month = itemUpdateObject.get("month").getAsString(); 
		
		 
		 
		String output= billObject.SearchBill(AcNo, Year, month);
		return output;
	}

}
