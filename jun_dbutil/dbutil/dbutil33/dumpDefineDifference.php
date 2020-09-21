<?php
// this is a utility to compare two database define. 

$dblist = array
(
	"simpleasset",
	"simpleauth",
	"simplefile",
	"simplehr",
	"simplepermission",
	"simpleproduct",
	"simpleproject",
	"simplesystem",
	"simpleworkflow",
);

$dbOld = array(
	'host' => '172.18.0.223',
	'username' => 'root',
	'password' => "zhirou",
	'database' => 'mysql', // default value, will be overwritten
	);
$dbNew = array(
	'host' => '127.0.0.1',
	'username' => 'root',
	'password' => "zhirou",
	'database' => 'mysql', // default value, will be overwritten
	);
/*	
$dbOld = array(
	'host' => '127.0.0.1',
	'username' => 'root',
	'password' => "zhirou",
	'database' => 'simplehraaaa',
	);
*/

foreach ($dblist as $key => $dbname)
{
	$dbOld['database'] = $dbname;
	$dbNew['database'] = $dbname;
	
	dumpDbDifference($dbOld, $dbNew);
}

function getDbDefine($host, $username, $password, $database)
{
	$columnNameOfShowTable = 'Tables_in_' . $database;
	$tables = array();
	
	$dbInfo = array();
	
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
		$tables[] = $line[$columnNameOfShowTable];
	}

	// list statements
	foreach ($tables as $table)
	{
		$columns = array();
		$query  = 'SHOW FULL COLUMNS FROM ' . $table;
		$result = mysql_query($query);
		while ($line = mysql_fetch_array($result, MYSQL_ASSOC)) 
		{
			$columns[$line['Field']] = $line;
		}
		$dbInfo[$table] = $columns;
	}
	
	// close connection
	mysql_close($link);
	
	return $dbInfo;
}

function compareColumns($oldColumns, $newColumns)
{
	$difference = "";
	$columnsOnlyInNew = "";
	foreach ($newColumns as $columnName => $columnInfo)
	{
		if (array_key_exists($columnName, $oldColumns))
		{
			$oldColumnInfo = $oldColumns[$columnName];
			if ($columnInfo['Type'] != $oldColumnInfo['Type'])
			{
				$difference .= " [" . $columnName . "]<Type>:" . "\n";
				$difference .= "  Old --- " . $oldColumnInfo['Type'] . "\n";
				$difference .= "  New --- " . $columnInfo['Type'] . "\n";
			}
			if ($columnInfo['Collation'] != $oldColumnInfo['Collation'])
			{
				$difference .= " [" . $columnName . "]<Collation>:" . "\n";
				$difference .= "  Old --- " . $oldColumnInfo['Collation'] . "\n";
				$difference .= "  New --- " . $columnInfo['Collation'] . "\n";
			}
			if ($columnInfo['Null'] != $oldColumnInfo['Null'])
			{
				$difference .= " [" . $columnName . "]<Null>:" . "\n";
				$difference .= "  Old --- " . $oldColumnInfo['Null'] . "\n";
				$difference .= "  New --- " . $columnInfo['Null'] . "\n";
			}
			if ($columnInfo['Key'] != $oldColumnInfo['Key'])
			{
				$difference .= " [" . $columnName . "]<Key>:" . "\n";
				$difference .= "  Old --- " . $oldColumnInfo['Key'] . "\n";
				$difference .= "  New --- " . $columnInfo['Key'] . "\n";
			}
			if ($columnInfo['Default'] != $oldColumnInfo['Default'])
			{
				$difference .= " [" . $columnName . "]<Default>:" . "\n";
				$difference .= "  Old --- " . $oldColumnInfo['Default'] . "\n";
				$difference .= "  New --- " . $columnInfo['Default'] . "\n";
			}
			if ($columnInfo['Extra'] != $oldColumnInfo['Extra'])
			{
				$difference .= " [" . $columnName . "]<Extra>:" . "\n";
				$difference .= "  Old --- " . $oldColumnInfo['Extra'] . "\n";
				$difference .= "  New --- " . $columnInfo['Extra'] . "\n";
			}
		}
		else
		{
			$columnsOnlyInNew .= " [". $columnName . "]\n";
		}
	}

	$columnsOnlyInOld = "";
	foreach ($oldColumns as $columnName => $columnInfo)
	{
		if (array_key_exists($columnName, $newColumns))
		{
			// nothing to do here.
		}
		else
		{
			$columnsOnlyInOld .= " [" . $columnName . "]\n";
		}
	}
	
	$difference = "Columns with difference:\n" . $difference;
	$columnsOnlyInNew = "Columns only in new:\n" . $columnsOnlyInNew;
	$columnsOnlyInOld = "Columns only in old:\n" . $columnsOnlyInOld;
	
	return $difference . $columnsOnlyInNew . $columnsOnlyInOld;
}

function dumpDbDifference($dbOld, $dbNew)
{
	$oldDBInfo = getDbDefine($dbOld['host'], $dbOld['username'], $dbOld['password'], $dbOld['database']);
	$newDBInfo = getDbDefine($dbNew['host'], $dbNew['username'], $dbNew['password'], $dbNew['database']);

	$difference = "";
	$tablesOnlyInNew = "";
   	foreach ($newDBInfo as $table => $talbeInfo)
	{
		if (array_key_exists($table, $oldDBInfo))
		{
			$difference .= "Table {" . $table . "} difference:\n";
			$difference .= compareColumns($oldDBInfo[$table], $newDBInfo[$table]);
			$difference .= "\n";
		}
		else
		{
			$tablesOnlyInNew .= " {" . $table . "}\n";
		}
	}
	
	$tablesOnlyInOld = "";
   	foreach ($oldDBInfo as $table => $talbeInfo)
	{
		if (array_key_exists($table, $newDBInfo))
		{
			// nothing to do here
		}
		else
		{
			$tablesOnlyInOld .= " {" . $table . "}\n";
		}
	}

	// write statements into file
	$tablesOnlyInNew = "Tables only in new:\n" . $tablesOnlyInNew;
	$tablesOnlyInOld = "Tables only in old:\n" . $tablesOnlyInOld;
	$difference .= "\n" . $tablesOnlyInNew . "\n" . $tablesOnlyInOld;
	$fileName = 'diff ' . $dbOld['host'] . '-' . $dbOld['database'] . ' and ' . $dbNew['host'] . '-' . $dbNew['database'] . '.txt';
	$fp = fopen($fileName, "w");
	fwrite($fp, $difference);
	fclose($fp);
}

?>