const year = document.getElementById("year");
const month = document.getElementById("month");
const day = document.getElementById("day");
	
const date = new Date();
	
year.textContent = date.getFullYear();
month.textContent = (function( name ){
	return ["Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dev"][name];
})( date.getMonth() );

day.textContent = date.getDate();