<?php
// this is a utility to put the table create statements into a file. 

$dbs = array(
'IASimpelRDMS' => array(
	'host' => '172.18.0.3',
	'username' => 'root',
	'password' => "virtual",
	'database' => 'simplehr',
	),
'BICakeRDMS' => array(
	'host' => '127.0.0.1',
	'username' => 'root',
	'password' => "zhirou",
	'database' => 'simplehr',
	),
);

foreach ($dbs as $db)
{
	dumpCreateStatement($db['host'], $db['username'], $db['password'], $db['database']);
}

function dumpCreateStatement($host, $username, $password, $database)
{
	$keydb = 'Tables_in_' . $database;
	$keytable = 'Create Table';
	$tables = array();
	$statements = array();
	
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
	$query  = 'SHOW TABLES FROM ' . $database;
	$result = mysql_query($query);
	while ($line = mysql_fetch_array($result, MYSQL_ASSOC)) 
	{
		$tables[] = $line[$keydb];
	}

	// list statements
	foreach ($tables as $table)
	{
		$query  = 'SHOW CREATE TABLE ' . $table;
		$result = mysql_query($query);
		while ($line = mysql_fetch_array($result, MYSQL_ASSOC)) 
		{
			$statements[] = $line[$keytable];
		}
	}
	
	// close connection
	mysql_close($link);
	
	// write statements into file	
	$fp = fopen($host . '-' . $database . '.txt', "w");
	foreach($statements as $statement)
	{
		fwrite($fp, $statement);
		fwrite($fp, ";");
		fwrite($fp, "\n\n");
	}
	fclose($fp);

}

?>