$(document).ready(function()
	{
	if ($("#alertSuccess").text().trim() == "")
	{
	$("#alertSuccess").hide();
	}
	$("#alertError").hide();
	});
	
// SAVE ============================================
	$(document).on("click", "#btnSave", function(event)
	{
		// Clear alerts---------------------
		$("#alertSuccess").text("");
		$("#alertSuccess").hide();
		$("#alertError").text("");
		$("#alertError").hide();
		
		// Form validation-------------------
	    var status = validateCartForm();
		if (status != true)
		{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
		}
		
		 // If valid------------------------
		 var type = ($("#hidCartIDSave").val() == "") ? "POST" : "PUT"; 
		 $.ajax( 
		 { 
		 url : "CartAPI", 
		 type : type, 
		 data : $("#formCart").serialize(), 
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onCartSaveComplete(response.responseText, status); 
		 } 
 	}); 
});
		
// UPDATE==========================================
	$(document).on("click", ".btnUpdate", function(event)
	{
	$("#hidCartIDSave").val($(this).data("cid"));
	$("#ptname").val($(this).closest("tr").find('td:eq(0)').text());
	$("#unitPrice").val($(this).closest("tr").find('td:eq(1)').text());
	$("#qty").val($(this).closest("tr").find('td:eq(2)').text());
	});
	
// DELETE===========================================
	$(document).on("click", ".btnRemove", function(event)
	{ 
	 $.ajax( 
	 { 
	 url : "CartAPI", 
	 type : "DELETE", 
	 data : "cID=" + $(this).data("cid"),
	 dataType : "text", 
	 complete : function(response, status) 
	 { 
	 onCartDeleteComplete(response.responseText, status); 
	 } 
	 }); 
});
// CLIENT-MODEL================================================================
function validateCartForm()
	{
	// Pname
	if ($("#ptname").val().trim() == "")
	{
	return "Insert Product Name.";
	}
	// uPrice
	if ($("#unitPrice").val().trim() == "")
	{
	return "Insert Product Price.";
	}

	// qty-------------------------------
	if ($("#qty").val().trim() == "")
	{
	return "Insert Quantity.";
	}
	// is numerical value
	var tmpPrice = $("#unitPrice").val().trim();
	if (!$.isNumeric(tmpPrice))
	{
	return "Insert a numerical value for Product Price.";
	}
	// convert to decimal price
	$("#unitPrice").val(parseFloat(tmpPrice).toFixed(2));
	
	return true;
}

function onCartSaveComplete(response, status)
	{ 
	if (status == "success") 
	 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
	 $("#alertSuccess").text("Successfully saved."); 
	 $("#alertSuccess").show();
	 $("#divCartsGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
	 $("#alertError").text(resultSet.data); 
	 $("#alertError").show(); 
	 } 
	 } else if (status == "error") 
	 { 
	 $("#alertError").text("Error while saving."); 
	 $("#alertError").show(); 
	 } else
	 { 
	 $("#alertError").text("Unknown error while saving.."); 
	 $("#alertError").show(); 
	 } 
	 $("#hidCartIDSave").val(""); 
	 $("#formCart")[0].reset(); 
}

function onCartDeleteComplete(response, status)
	{ 
	if (status == "success") 
	 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
	 $("#alertSuccess").text("Successfully deleted."); 
	 $("#alertSuccess").show();
	 $("#divCartsGrid").html(resultSet.data); 
	 } else if (resultSet.status.trim() == "error") 
	 { 
	 $("#alertError").text(resultSet.data); 
	 $("#alertError").show(); 
	 } 
	 } else if (status == "error") 
	 { 
	 $("#alertError").text("Error while deleting."); 
	 $("#alertError").show(); 
	 } else
	 { 
	 $("#alertError").text("Unknown error while deleting.."); 
	 $("#alertError").show(); 
 } 
}




