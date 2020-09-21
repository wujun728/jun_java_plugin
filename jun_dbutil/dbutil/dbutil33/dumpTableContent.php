<?php
// this is a utility to put the table create statements into a file. 

$tbs = array(
'IASimpelRDMS' => array(
	'host' => '172.18.0.3',
	'username' => 'root',
	'password' => "virtual",
	'database' => 'CakeRDMS',
	'table' => 'sysvals',
	'sortby' => 'sysval_title',
	),
'BICakeRDMS' => array(
	'host' => '172.18.0.3',
	'username' => 'root',
	'password' => "virtual",
	'database' => 'CakeRDMSBI',
	'table' => 'sysvals',
	'sortby' => 'sysval_title',
	),
);

foreach ($tbs as $tb)
{
	dumpTable($tb['host'], $tb['username'], $tb['password'], $tb['database'], $tb['table'], $tb['sortby']);
}

function dumpTable($host, $username, $password, $database, $table, $sortby)
{
	$contents = array();
	
	//prepare connection
	$link = 0;
	$link = mysql_connect($host, $username, $password);
	if (!$link)
	{
	  die('Could not connect: ' . $host . " error:" . mysql_error());
	}
	else
	{
		echo "connected to host " . $host . "\n"; 
	}
	$db_selected = mysql_select_db($database, $link);
	if (!$db_selected)
	{
	  die ("Could not select db: " . $database . " error:" . mysql_error());
	}
	
	// list tables
	$query  = 'SELECT * FROM ' . $table;
	if (!empty($sortby))
	{
		$query .= ' ORDER BY ' . $sortby;
	}
	$result = mysql_query($query);
	$lineContent = '';
	while ($row = mysql_fetch_array($result, MYSQL_ASSOC)) 
	{
		$lineContent = "{\n";
		foreach ($row as $fieldName => $fieldValue)
		{
			$lineContent .= "  " . $fieldName . '=' . $fieldValue . "\n";
		}
		$lineContent .= "\n}";
		$contents[] = $lineContent;
	}
	
	// close connection
	mysql_close($link);
	
	// write contents into file	
	$fp = fopen($host . '-' . $database . '-' . $table . '.txt', "w");
	foreach($contents as $content)
	{
		fwrite($fp, $content);
		fwrite($fp, "\n\n");
	}
	fclose($fp);

}

?>