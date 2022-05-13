$(document).ready(function() 
{ 
if ($("#alertSuccess").text().trim() == "") 
 { 
 $("#alertSuccess").hide(); 
 } 
 $("#alertError").hide(); 
}); 

//save
$(document).on("click", "#btnSave", function(event) 
{ 
// Clear alerts---------------------
 $("#alertSuccess").text(""); 
 $("#alertSuccess").hide(); 
 $("#alertError").text(""); 
 $("#alertError").hide(); 
// Form validation-------------------
var status = validateItemForm(); 

if (status != true) 
 { 
 $("#alertError").text(status); 
 $("#alertError").show(); 
 return; 
 } 
// If valid------------------------
var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT"; 
 $.ajax( 
 { 
 url : "BillAPI", 
 type : type, 
 data : $("#formItem").serialize(), 
 dataType : "text", 
 complete : function(response, status) 
 { 
 onItemSaveComplete(response.responseText, status); 
 } 
 }); 
});



// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) 
{ 
$("#hidItemIDSave").val($(this).data("itemid"));
 
 $("#acno").val($(this).closest("tr").find('td:eq(0)').text()); 
 $("#year").val($(this).closest("tr").find('td:eq(1)').text()); 
  $("#month").val($(this).closest("tr").find('td:eq(2)').text()); 
 $("#due").val($(this).closest("tr").find('td:eq(7)').text()); 
 $("#noofunits").val($(this).closest("tr").find('td:eq(3)').text()); 
});
 
 //delete
 $(document).on("click", ".btnRemove", function(event) 
{ 
 $.ajax( 
 { 
 url : "BillAPI", 
 type : "DELETE", 
 data : "itemID=" + $(this).data("itemid"),
 dataType : "text", 
 complete : function(response, status) 
 { 
 onItemDeleteComplete(response.responseText, status); 
 } 
 }); 
});
 



 
// CLIENT-MODEL================================================================
function validateItemForm() 
{    
    // Category
	if ($("#category").val().trim() == "") 
	 { 
	 return "Insert category."; 
	 } 
	 var categoryString=$("#category").val().trim() ;
	 if(!(categoryString == "religious" || categoryString=="other" )){
	  return "insert a valid category (religious or other)";
	 }
	
	// ac no
	if ($("#acno").val().trim() == "") 
	 { 
	 return "Insert acno."; 
	 } 
	 var acno = $("#acno").val().trim();
	 if(acno.length!=10){
	  return "insert an account number with length 10";
	 }
	 
	// year
	if ($("#year").val().trim() == "") 
	 { 
	 return "Insert year."; 
	 }
	 // is numerical value
	var yearNumber = $("#year").val().trim(); 
	if (!$.isNumeric(yearNumber)) 
	 { 
	 return "Insert a numerical value for year."; 
	 } 
	 if(!(yearNumber>=2022 && yearNumber < 2100)){
	  return "Insert a valid year (2022 or after).";
	 }
	 
	 // month
	if ($("#month").val().trim() == "") 
	 { 
	 return "Insert month."; 
	 }
	  // is numerical value
	var monthNumber = $("#month").val().trim(); 
	if (!(monthNumber % 1 === 0)) 
	 { 
	 return "Insert a integer value for month."; 
	 } 
	 if(!(monthNumber>0 && monthNumber < 12)){
	  return "Insert a valid month ";
	 }
	 
	 
	 
	 
	 
	// due-------------------------------
	if ($("#due").val().trim() == "") 
	 { 
	 return "Insert due."; 
	 } 
	// is numerical value and valid value
	var dueval = $("#due").val().trim(); 
	if (!$.isNumeric(dueval)) 
	 { 
	 return "Insert a numerical value for due."; 
	 } 
	if(dueval<0){
	 return "Insert a valid value for due."; 
	}
	// convert to decimal price
	 $("#due").val(parseFloat(dueval).toFixed(2));
	 
	  
	// no of units------------------------
	if ($("#noofunits").val().trim() == "") 
	 { 
	 return "Insert no of units."; 
	 } 
	 
	 // is numerical value
	var noofunit = $("#noofunits").val().trim(); 
	if (!$.isNumeric(noofunit)) 
	 { 
	 return "Insert a numerical value for no of units."; 
	 }
	 if(noofunit<0){
	 return "insert a valid number of unit ";
	 }
	 // convert to decimal no of units
	 $("#noofunits").val(parseFloat(noofunit).toFixed(2)); 
	 
return true; 
}

//onsave
function onItemSaveComplete(response, status) 
{ 
	if (status == "success") 
	 { 
	 var resultSet = JSON.parse(response); 
	 if (resultSet.status.trim() == "success") 
	 { 
	 $("#alertSuccess").text("Successfully saved."); 
	 $("#alertSuccess").show(); 
	 $("#divItemsGrid").html(resultSet.data); 
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

   $("#hidItemIDSave").val(""); 
   $("#formItem")[0].reset(); 
}


//ondelete
function onItemDeleteComplete(response, status) 
{ 
if (status == "success") 
 { 
 var resultSet = JSON.parse(response); 
 if (resultSet.status.trim() == "success") 
 { 
 $("#alertSuccess").text("Successfully deleted."); 
 $("#alertSuccess").show(); 
 $("#divItemsGrid").html(resultSet.data); 
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
