package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;





public class samplebill {
	
	String Msg="hi";
	
	public samplebill() {
	
	}
	
	public Connection connect() 
	{ 
	 Connection con = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.jdbc.Driver"); 
	 con= DriverManager.getConnection("jdbc:mysql://localhost:3306/sampleleco", 
	 "root", ""); 
	 //For testing
	 System.out.print("Successfully connected-----------------------"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return con; 

	
	}
	
	public String insertBill(String category,String acno, String year, String month, String totalunits,String due) {
		Connection con = connect(); 
		Double FixedCharge=0.00;
		String output = "no";
		if (con == null) 
		{ 
		return "Error while connecting to the database"; 
		}
		
		else {
			
			Double totalunitCharge=0.00;
            Double total=0.00;
            
			if(category.equals("religious")) {
				
			FixedCharge=240.00;
			if(Double.parseDouble(totalunits)<60) {
            	totalunitCharge=Double.parseDouble(totalunits)* 4.00;
            	
			}else if(Double.parseDouble(totalunits)<120) {
				totalunitCharge=60 * 4.00 +(Double.parseDouble(totalunits)-60)* 10.00;
			}else if(Double.parseDouble(totalunits)>120){
				totalunitCharge=(60 * 4.00) + (60 * 10.00) +(Double.parseDouble(totalunits)-120)* 12.00;
			}else {
				System.out.print("enter a valid number of unit");
			}
            
			}else {
				FixedCharge=540.00;
				if(Double.parseDouble(totalunits)<60) {
	            	totalunitCharge=Double.parseDouble(totalunits)* 6.00;
	            	
				}else if(Double.parseDouble(totalunits)<120) {
					totalunitCharge=60 * 6.00 +(Double.parseDouble(totalunits)-60)* 12.00;
				}else if(Double.parseDouble(totalunits)>120){
					totalunitCharge=(60 * 6.00) + (60 * 12.00) +(Double.parseDouble(totalunits)-120)* 14.00;
				}else {
					System.out.print("enter a valid number of unit");
				}
				
			}
			total=totalunitCharge+FixedCharge+Double.parseDouble(due);

			String Query="select * from samplebill";
	    	try {
	    		Statement stmt=con.createStatement();
		    	
	    	
	    	    ResultSet rs=stmt.executeQuery(Query);
	    	
	    	while(rs.next()) {
	    		
	    		String AcNoCheck=rs.getString("acno");
	    		String YearCheck=rs.getString("years");
	    		String monthCheck=rs.getString("months");
	    		
	    	   
				if(AcNoCheck.equals(acno) && YearCheck.equals(year) && monthCheck.equals(month) ) {
					output="alreadyAvailable";
					break;
				}
				
				
				//output="already available so failed";
			}
	    	}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			if(!output.equals("alreadyAvailable")) {
			
					String query="insert into samplebill(id,acno,years,months,totalunits,totalunitcharge,fixedcharge,total,due) values(?,?,?,?,?,?,?,?,?)";
				   try {
					PreparedStatement ps=con.prepareStatement(query);
					
					
					ps.setInt(1, 0);
					ps.setString(2, acno);
					ps.setString(3, year);
					ps.setString(4, month);
					ps.setDouble(5,Double.parseDouble(totalunits));
					ps.setDouble(6,totalunitCharge);
					ps.setDouble(7,FixedCharge);
					ps.setDouble(8,total);
					ps.setDouble(9,Double.parseDouble(due));
		
					
					ps.execute();
					output="inserted";
					con.close();
					String newItems = ReadBill(); 
					 output = "{\"status\":\"success\", \"data\": \"" + 
					 newItems + "\"}"; 
				 } 
				catch (Exception e) 
				 { 
					 output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
					 System.err.println(e.getMessage()); 
			     } 
			}
		   return output;
		     
			
		}	
		
	}
	
	
	
    public String ReadBill() {
		
    	
    	String out="";
    	
    	try
    	{ 
    	 Connection con = connect(); 
    	if (con == null) 
    	 { 
    	 return "Error while connecting to the database for reading."; 
    	 }
    	
    	out="<table border='1'>"
    			+ "<tr>"
    			+ "<th>AC No</th><th>Year</th><th>Month</th><th>total units</th><th>total units charge</th><th>fixed charge</th><th>total</th><th>due</th><th>Update</th><th>delete</th>"
    			+ "</tr>";
    	
    	Statement stmt=con.createStatement();
    	String Query="select * from samplebill";
    	ResultSet rs=stmt.executeQuery(Query);
    	
    	while(rs.next()) {
    		Integer id=rs.getInt("id");
    		String AcNo=rs.getString("acno");
    		String Year=rs.getString("years");
    		String month=rs.getString("months");
    		String totalunits=Double.toString(rs.getDouble("totalunits"));
    		String totalunitCharge=Double.toString(rs.getDouble("totalunitcharge"));
    		String fixedcharge=Double.toString(rs.getDouble("fixedcharge"));
    		String total=Double.toString(rs.getDouble("total"));
    		String due=Double.toString(rs.getDouble("due"));
    	    
    	    out+="<tr>"
    	    		+"<td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + id + "'>"+ AcNo + "</td><td>"+Year +"</td><td>"+month+"</td><td>"+totalunits+"</td><td>"+totalunitCharge+"</td><td>"+fixedcharge+"</td><td>"+total+"</td><td>"+due+"</td>";
    	   
    		
    	   
    	   
    	          
    				
    		// buttons
    		out+= "<td><input name='btnUpdate' type='button' value='Update' "
    		+ "class='btnUpdate btn btn-secondary' data-itemid='" + id + "'></td>"
    		
    		+ "<td><input name='btnRemove' type='button' value='Remove' "
    		+ "class='btnRemove btn btn-danger' data-itemid='" + id + "'></td></tr>";
    		
    	}
    	
    	out+="</table>";
    	con.close();
    	
    	
    	
    	} 
    	catch (Exception e) 
    	{ 
    	 out = "Error while reading the bill."; 
    	 System.err.println(e.getMessage()); 
    	}
    	
    	
    	
    	return out;
    	
    }

	
    public String deleteBill(String ID)
    { 
	 String output = ""; 
	 Connection con = null ;
	 try
	 { 
	   con = connect(); 
	 if (con == null) { 
	  return "Error while connecting to the database for deleting."; 
	  } 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while deleting the Bill."; 
	  System.err.println(e.getMessage()); 
	 }
	 
	 
	 
	 String query="delete from samplebill where id = ? ";
	 try {
		PreparedStatement ps=connect().prepareStatement(query);
		ps.setInt(1, Integer.parseInt(ID));
		
		ps.execute();
		output="deleted success";
		con.close(); 
		String newItems = ReadBill(); 
		 output = "{\"status\":\"success\", \"data\": \"" + 
		 newItems + "\"}"; 
	 } 
	catch (Exception e) 
	 { 
		 output = "{\"status\":\"error\", \"data\": \"Error while inserting the item.\"}"; 
		 System.err.println(e.getMessage()); 
    } 
	 
	 return output;
	 
 
 
  }
	
    public String updateBill(String ID,String category,String acno, String year, String month, String totalunits,String due)
    { 
    Double Fixed=0.00;	
    String output="";
    Connection con = null ;
    try
    { 
         con = connect(); 
      if (con == null) { 
    	  	return "Error while connecting to the database"; 
         } else {
    	  
      	
		      	      Double totalunitCharge=0.00;
		              Double total=0.00;
			          if(category.equals("religious")) {
			      		
			      		         Fixed=240.00;
					      	if(Double.parseDouble(totalunits)<60) {
					          	        totalunitCharge=Double.parseDouble(totalunits)* 4.00;
					             }else if(Double.parseDouble(totalunits)<120) {
					      		                    totalunitCharge=60 * 4.00 +(Double.parseDouble(totalunits)-60)* 10.00;
					               	}     else if(Double.parseDouble(totalunits)>120){
					      		                          totalunitCharge=(60 * 4.00) + (60 * 10.00) +(Double.parseDouble(totalunits)-120)* 12.00;
					                   	}        else {
					      		                              System.out.print("enter a valid number of unit");
					      	                        }
	      	          }else {
      		                     Fixed=540.00;
                              if(Double.parseDouble(totalunits)<60) {
		              	                totalunitCharge=Double.parseDouble(totalunits)* 6.00;
		                         }else if(Double.parseDouble(totalunits)<120) {
		      			                totalunitCharge=60 * 6.00 +(Double.parseDouble(totalunits)-60)* 12.00;
		      		              }      else if(Double.parseDouble(totalunits)>120){
		      			                              totalunitCharge=(60 * 6.00) + (60 * 12.00) +(Double.parseDouble(totalunits)-120)* 14.00;
		      		                   }    else {
		      			                                   System.out.print("enter a valid number of unit");
		      		                           }
      		
      		
      	                  }
      	
   
    total=totalunitCharge+Fixed+Double.parseDouble(due);
    
    String query="Update samplebill set acno=?,years=?,months=?,totalunits=?,totalunitcharge=?,fixedcharge=?,total=?,due=? Where ID=?";
    PreparedStatement ps =con.prepareStatement(query);
    ps.setString(1, acno);
	ps.setString(2, year);
	ps.setString(3, month);
	ps.setDouble(4,Double.parseDouble(totalunits));
	ps.setDouble(5,totalunitCharge);
	ps.setDouble(6,Fixed);
	ps.setDouble(7,total);
	ps.setDouble(8,Double.parseDouble(due));
	ps.setInt(9,Integer.parseInt(ID));
	
    ps.execute();
    output ="update success";
    con.close();
    
    String newItems = ReadBill(); 
	 output = "{\"status\":\"success\", \"data\": \"" + 
	 newItems + "\"}"; 
    
    
     }}catch(Exception e){
    	 output = "{\"status\":\"error\", \"data\":  \"Error while updating the item.\"}"; 
		 System.err.println(e.getMessage());
    }
    
    return output;	
    	
    }
    
    
    public String SearchBill(String acno,String year,String month)
    { 
	 String output = ""; 
	 Connection con = null ;
	 try
	 { 
	   con = connect(); 
	 if (con == null) { 
	  return "Error while connecting to the database for deleting."; 
	  } 
	 } 
	 catch (Exception e) 
	 { 
	 output = "Error while search the Bill."; 
	  System.err.println(e.getMessage()); 
	 }
	 
	 output="<table border='1'>"
	  			+ "<tr>"
	  			+ "<th>ID No</th><th>AC No</th><th>Year</th><th>Month</th><th>total units</th><th>total units charge</th><th>fixed charge</th><th>total</th><th>due</th><th>Update</th><th>delete</th>"
	  			+ "</tr>";
	 
	 String Query="select * from samplebill";
 	try {
 		Statement stmt=con.createStatement();
	    ResultSet rs=stmt.executeQuery(Query);
 	
 	    while(rs.next()) {
		 		Integer id=rs.getInt("id");
		 		String AcNoCheck=rs.getString("acno");
		 		String YearCheck=rs.getString("years");
		 		String monthCheck=rs.getString("months");
		 		String totalunits=Double.toString(rs.getDouble("totalunits"));
				String totalunitCharge=Double.toString(rs.getDouble("totalunitcharge"));
				String fixedcharge=Double.toString(rs.getDouble("fixedcharge"));
				String total=Double.toString(rs.getDouble("total"));
				String due=Double.toString(rs.getDouble("due"));
				
					if(AcNoCheck.equals(acno) && YearCheck.equals(year) && monthCheck.equals(month) ) {
						Integer id2=id;
						String AcNo2=AcNoCheck;
			    		String Year2=YearCheck;
			    		String month2=monthCheck;
			    		String totalunits2=totalunits;
			    		String totalunitCharge2=totalunitCharge;
			    		String fixedcharge2=fixedcharge;
			    		String total2=total;
			    		String due2=due;
			    		
			    		 
			    	    output+="<tr>"
			    	    		+ "<td>"+ id2 + "</td><td>"+ AcNo2 + "</td><td>"+Year2 +"</td><td>"+month2+"</td><td>"+totalunits2+"</td><td>"+totalunitCharge2+"</td><td>"+fixedcharge2+"</td><td>"+total2+"</td><td>"+due2+"</td>";
			    	    
			    		
			    		output+="<td><form action='billUpdate.jsp' method='post'>"
			    				+ "<input type='hidden'   name='id' value='"+ id2 +"' >"
			    				+ "<input type='hidden'   name='acno' value='"+ AcNo2 +"' >"
			    				+ "<input type='hidden'   name='year' value='"+ Year2 +"'>"
			    				+ "<input type='hidden'   name='month' value='"+month2 +"'>"
			    			    + "<input type='hidden'   name='month' value='"+totalunits2 +"'>"
			    			    + "<input type='hidden'   name='month' value='"+fixedcharge2 +"'>"
			    			    + "<input type='submit' name='update' value='update'></td>"
			    				
			    				+ "<td><form action='bill.jsp' method='post'>"
			    				
			                    + "<input type='hidden'   name='id' value='"+ id2 +"' > "
			    				+ "<input type='hidden'   name='acno' value='"+ AcNo2 +"' > "
			    				+ "<input type='hidden'   name='year' value='"+ Year2 +"' > "
			    				+ "<input type='hidden'   name='month' value='"+ month2 +"' > "
			    				+ "<input type='submit' value='delete' name='btnRemove'>"
			    				+ "</form>"
			    				+ "</td>"
			    				+ "</tr>";
	    		
	    	          }
			
			
			//output="already available so failed";
		}
 	}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	 
	 return output;
	 
 
 
  }
    
    
 }
	
	

	
	
	
	
	

