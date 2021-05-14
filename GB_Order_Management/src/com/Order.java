package com;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Order {
	
	private int quantity;
	private double uPrice;
	private int foreignKey;
	
	public Connection connect()
	{ 
	 Connection con = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.cj.jdbc.Driver"); 
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/order_management", 
	 "root", ""); 
	 //For testing
	 System.out.print("Successfully connected"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return con; 
		}



	public String insertOrder(String cDate , String cName , String cPhone, String cAddress, String cEmail) {
		
		 String output = "";
		 
		 try {
		
		Connection con = connect();
		if (con == null) 
		{ 
		return "Error while connecting to the database"; 
		}
		
		// create a prepared statement
		String query = " insert into orderdetails (`oID`,`cID`,`date`,`cname`,`phone`,`address`,`email`,`total`) "
		         + "values (?, ?, ?, ?, ?, ?,?,?)";
		
		 
		 //calculate total amount
		 String query2 = "select qty,unitPrice from cart order by cID desc limit 1";
		 PreparedStatement preparedStmt2 = con.prepareStatement(query2);
		 ResultSet rs = preparedStmt2.executeQuery(query2);
		
		while(rs.next()) {
			 
			 quantity = (rs.getInt("qty"));
			 uPrice=  (rs.getDouble("unitPrice"));
		 }
		 
		 String total =  Double.toString(quantity * uPrice);
		 preparedStmt2.execute();
		 
        //to get cartID as foreign key
		 
		 String query3 = "select cID from cart order by cID desc limit 1";
		 PreparedStatement preparedStmt3 = con.prepareStatement(query3);
		 ResultSet rs3 = preparedStmt3.executeQuery(query3);
		 
			while(rs3.next()) {
				 
				 foreignKey = (rs3.getInt("cID"));
				 
			 }
			
		 String cartID =  Integer.toString(foreignKey);
		 preparedStmt3.execute();
		 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, cartID);
		 preparedStmt.setString(3, cDate);
		 preparedStmt.setString(4, cName);
		 preparedStmt.setString(5, cPhone);
		 preparedStmt.setString(6, cAddress);
		 preparedStmt.setString(7, cEmail);
		 preparedStmt.setString(8,total);
		 
		System.out.println(cDate);
		 
		 preparedStmt.execute(); 
		 con.close(); 
		 
		 String newOrders = readOrders();
		 output =  "{\"status\":\"success\", \"data\": \"" + 
				 newOrders + "\"}"; 
		 } 

		catch (Exception e) 
		 { 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item to cart.\"}";  
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
		}


	public String readOrders()
	{ 
			 String output = ""; 
			try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for reading."; 
			 } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Order Date</th>" +
	        		 "<th>Customer Name</th>" +
	        		 "<th>Phone number</th>" +
	                  "<th>Address</th>" +
	                   "<th>Email</th>" +
	                   "<th>Total Price</th>" +
	                   "<th>Update</th><th>Remove</th></tr>";
			 String query = "select * from orderdetails"; 
			 java.sql.Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
				 String oID = Integer.toString(rs.getInt("oID"));
				 String cID = Integer.toString(rs.getInt("cID"));
				 String date = rs.getString("date");
				 String cname = rs.getString("cname");
				 String phone =rs.getString("phone");
				 String address = rs.getString("address");
				 String email=rs.getString("email");
				 String total = Double.toString( rs.getDouble("total"));
			 // Add a row into the html table
			 output += "<tr><td><input id='hidOrderIDUpdate' name='hidOrderIDUpdate' type='hidden' value='" + oID + "'>"
					 + date + "</td>";
			 output += "<td>" + cname + "</td>";
			 output += "<td>" + phone + "</td>";
			 output += "<td>" + address + "</td>";
			 output += "<td>" + email + "</td>";
			 output += "<td>" + total + "</td>";
			 
			 // buttons
			 output += "<td><input name='btnUpdate' " 
			 + " type='button' value='Update' class =' btnUpdate btn btn-secondary'data-oid='" + oID + "'></td>"
			 + "<td><form method='post' action='order.jsp'>"
			 + "<input name='btnRemove' " 
			 + " type='button' value='Remove' class='btnRemove btn btn-danger' data-oid='" + oID + "'>"
			 + "<input name='hidOrderIDDelete' type='hidden' " 
			 + " value='" + oID + "'>" + "</form></td></tr>"; 
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
			 } 
			catch (Exception e) 
			 { 
			 output = "Error while reading the  order details."; 
			 System.err.println(e.getMessage()); 
			 } 
			return output; 
		}

	public String deleteOrder(String oID)
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for deleting."; 
	 } 
	 // create a prepared statement
	 String query = "delete from orderdetails where oID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(oID)); 
	 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 String newOrders = readOrders();
	 output =  "{\"status\":\"success\", \"data\": \"" + 
			 newOrders + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while deleting the  order details.\"}";  
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
		}

	public String updateOrder(String ID, String cDate , String cName , String cPhone, String cAddress, String cEmail)
	//4
	{
	String output = "";
	try {
	Connection conn = connect();
	if (conn == null) {
	return "Error while connecting to the database for updating.";
	}

	// create a prepared statement
	String query = "UPDATE orderdetails SET date=?,cname=?,phone=?,address=? ,email=? WHERE oID=?";
	PreparedStatement preparedStmt = conn.prepareStatement(query);
	//binding values

	 preparedStmt.setString(1, cDate);
	 preparedStmt.setString(2, cName);
	 preparedStmt.setString(3, cPhone);
	 preparedStmt.setString(4, cAddress);
	 preparedStmt.setString(5, cEmail);
	 preparedStmt.setInt(6, Integer.parseInt(ID));
	//execute the statement
	preparedStmt.execute();
	conn.close();
	String newOrders = readOrders();
	 output =  "{\"status\":\"success\", \"data\": \"" + 
			 newOrders + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while Updating the order details.\"}";  
	
	System.err.println(e.getMessage());
	}
	return output;
	}


}
