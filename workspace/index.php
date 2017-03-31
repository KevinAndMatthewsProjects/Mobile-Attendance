<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<?php
  require_once('cps_simple.php');
  require_once('cps_api.php');
  $connectionStrings = array(
	'tcp://cloud-us-0.clusterpoint.com:9007',
	'tcp://cloud-us-1.clusterpoint.com:9007',
	'tcp://cloud-us-2.clusterpoint.com:9007',
	'tcp://cloud-us-3.clusterpoint.com:9007'
);
// Creating a CPS_Connection instance
$cpsConn = new CPS_Connection(
	new CPS_LoadBalancer($connectionStrings), 'DATABASENAME', 'USERNAME', 'PASSWORD', 'document',
	'//document/id', array('account' => ID)
);
$cps = new CPS_Simple($cpsConn);
$cpsSimple = new CPS_Simple($cpsConn);
 
  // search for items with category == 'cars' and car_params/year >= 2010
  $query = CPS_Term('*'); 
  // return documents starting with the first one - offset 0
  $offset = 0;
  // return not more than 5 documents
  $docs = 5;
  // return these fields from the documents

  // order by year,Lfrom largest to smallest
  $documents = $cpsSimple->search($query, $offset, $docs);
?>

<html>
	<head>
		<link rel="Stylesheet" media="screen"
		   type="text/css" href="Design1.css" />
		<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Raleway">
		<style>
body,h1,h2,h3,h4,h5,h6 {font-family: "Raleway", Arial, Helvetica, sans-serif}

ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #333;
}

li {
    float: left;
}

li a {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}

li a:hover {
    background-color: #111;
}
table {
    border-collapse: collapse;
    width: 100%;
}

th, td {
    text-align: left;
    padding: 8px;
}

tr:nth-child(even){background-color: #f2f2f2}

th {
    background-color: #9A10B3;
    color: white;
}
</style>
		<title>School Attendance</title>
		<META HTTP-EQUIV="refresh" CONTENT="3">
		    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
	</head>
	<nav>

</nav>
	<body>

		<header>Mobile Attendance Tracker</header>
		<hr>
		<ul>
 <li><a href="#home" class="active">Home</a></li>
 <li><a href="#news">Attendance</a></li>
 <li><a href="Login.html">Log Out</a></li>
</ul>
		<h1><center>Attendance Log</center></h1>
		<section><a></a></section>
		<aside></aside>
		<?php
		  echo("<center><table><th>Last Name</th><th>First Name</ht><th>Grade</th><th>Period 1</th><th>Period 2</th><th>Period 3</th>");
 
 for ($i =1;$i<=4;$i++){
     echo("<tr>");
 echo ("<td>".substr($documents[$i]->LName,1). "</td>");
  echo ("<td>".substr($documents[$i]->FName,1). "</td>");
  echo ("<td>".substr($documents[$i]->Grade,1). "</td>");

  ?>
  
     <?php
  echo ("<td>".substr($documents[$i]->P1,1). "</td>");?>

    <?php
  echo ("<td>".substr($documents[$i]->P2,1). "</td>");
  echo ("<td>".substr($documents[$i]->P3,1). "</td>");
  echo("</tr>");
 }
echo("</table></center>");?>

</body>