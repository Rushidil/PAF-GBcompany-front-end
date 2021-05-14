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
	    var status = validateOrderForm();
		if (status != true)
		{
		$("#alertError").text(status);
		$("#alertError").show();
		return;
		}
		
		 // If valid------------------------
		 var type = ($("#hidOrderIDSave").val() == "") ? "POST" : "PUT"; 
		 $.ajax( 
		 { 
		 url : "OrderAPI", 
		 type : type, 
		 data : $("#formOrder").serialize(), 
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onOrderSaveComplete(response.responseText, status); 
		 } 
 	}); 
});
		
// UPDATE==========================================
	$(document).on("click", ".btnUpdate", function(event)
	{
	$("#hidOrderIDSave").val($(this).data("oid"));
	$("#date").val($(this).closest("tr").find('td:eq(0)').text());
	$("#cname").val($(this).closest("tr").find('td:eq(1)').text());
	$("#phone").val($(this).closest("tr").find('td:eq(2)').text());
	$("#address").val($(this).closest("tr").find('td:eq(3)').text());
	$("#email").val($(this).closest("tr").find('td:eq(4)').text());
	});
	
// DELETE===========================================
	$(document).on("click", ".btnRemove", function(event)
	{ 
	 $.ajax( 
	 { 
	 url : "OrderAPI", 
	 type : "DELETE", 
	 data : "oID=" + $(this).data("oid"),
	 dataType : "text", 
	 complete : function(response, status) 
	 { 
	 onOrderDeleteComplete(response.responseText, status); 
	 } 
	 }); 
});
// CLIENT-MODEL================================================================
function validateOrderForm()
	{
	// Date
	if ($("#date").val().trim() == "")
	{
	return "Insert Date.";
	}
	// NAME
	if ($("#cname").val().trim() == "")
	{
	return "Insert  Name.";
	}

	// Phone-------------------------------
	if ($("#phone").val().trim() == "")
	{
	return "Insert Phone Number.";
	}
	
	// Address------------------------
	if ($("#address").val().trim() == "")
	{
	return "Insert Address.";
	}
	
	if ($("#email").val().trim() == "")
	{
	return "Insert Email.";
	}
	return true;
}

function onOrderSaveComplete(response, status)
	{ 
	if (status == "success") 
	 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
	 $("#alertSuccess").text("Successfully saved."); 
	 $("#alertSuccess").show();
	 $("#divOrdersGrid").html(resultSet.data); 
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
	 $("#hidOrderIDSave").val(""); 
	 $("#formOrder")[0].reset(); 
}

function onOrderDeleteComplete(response, status)
	{ 
	if (status == "success") 
	 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
	 $("#alertSuccess").text("Successfully deleted."); 
	 $("#alertSuccess").show();
	 $("#divOrdersGrid").html(resultSet.data); 
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




