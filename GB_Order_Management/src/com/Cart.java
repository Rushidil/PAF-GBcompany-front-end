package com;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Cart {
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



	public String insertCart(String pname, String price, String quantity) {
		
		 String output = "";
		 
		 try {
		
		Connection con = connect();
		if (con == null) 
		{ 
		return "Error while connecting to the database"; 
		}
		
		// create a prepared statement
		String query = "insert into cart (cID, ptname, unitPrice, qty)"
				 + " values (?, ?, ?, ?)"; 
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		preparedStmt.setInt(1, 0); 
		preparedStmt.setString(2, pname); 
		preparedStmt.setDouble(3, Double.parseDouble(price)); 
		preparedStmt.setInt(4,Integer.parseInt (quantity));
		
		System.out.println(pname);
		 
		 preparedStmt.execute(); 
		 con.close(); 
		 
		 String newCarts = readCarts();
		 output =  "{\"status\":\"success\", \"data\": \"" + 
				 newCarts + "\"}"; 
		 } 

		catch (Exception e) 
		 { 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the item to cart.\"}";  
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
		}


	public String readCarts()
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
			 output = "<table border='1'><tr><th>Product Name</th>" 
			 +"<th>Unit Price</th><th>Quantity</th>"
			 + "<th>Update</th><th>Remove</th></tr>"; 
			 String query = "select * from cart"; 
			 java.sql.Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
			 String cID = Integer.toString(rs.getInt("cID")); 
			 String ptname = rs.getString("ptname"); 
			 String unitPrice = Double.toString(rs.getDouble("unitPrice")); 
			 String qty = Integer.toString(rs.getInt("qty")); 
			 // Add a row into the html table
			 output += "<tr><td><input id='hidCartIDUpdate' name='hidCartIDUpdate' type='hidden' value='" + cID + "'>"
					 + ptname + "</td>";
			 output += "<td>" + unitPrice+ "</td>"; 
			 output += "<td>" + qty + "</td>"; 
			 
			 
			 // buttons
			 output += "<td><input name='btnUpdate' " 
			 + " type='button' value='Update' class =' btnUpdate btn btn-secondary'data-cid='" + cID + "'></td>"
			 + "<td><form method='post' action='cart.jsp'>"
			 + "<input name='btnRemove' " 
			 + " type='button' value='Remove' class='btnRemove btn btn-danger' data-cid='" + cID + "'>"
			 + "<input name='hidCartIDDelete' type='hidden' " 
			 + " value='" + cID + "'>" + "</form></td></tr>"; 
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
			 } 
			catch (Exception e) 
			 { 
			 output = "Error while reading the  cart items."; 
			 System.err.println(e.getMessage()); 
			 } 
			return output; 
		}

	public String deleteCart(String cID)
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
	 String query = "delete from cart where cID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(cID)); 
	 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 String newCarts = readCarts();
	 output =  "{\"status\":\"success\", \"data\": \"" + 
			 newCarts + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while deleting the  cart item.\"}";  
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
		}

	public String updateCart(String ID, String pname,  String price, String quantity)
	//4
	{
	String output = "";
	try {
	Connection conn = connect();
	if (conn == null) {
	return "Error while connecting to the database for updating.";
	}

	// create a prepared statement
	String query = "UPDATE cart SET ptname=?,unitPrice=?,qty=? WHERE cID=?";
	PreparedStatement preparedStmt = conn.prepareStatement(query);
	//binding values

	preparedStmt.setString(1, pname);
	preparedStmt.setDouble(2, Double.parseDouble(price));
	preparedStmt.setInt(3,Integer.parseInt(quantity) );
	preparedStmt.setInt(4, Integer.parseInt(ID));
	//execute the statement
	preparedStmt.execute();
	conn.close();
	String newCarts = readCarts();
	 output =  "{\"status\":\"success\", \"data\": \"" + 
			 newCarts + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while Updating the cart item.\"}";  
	
	System.err.println(e.getMessage());
	}
	return output;
	}


}

