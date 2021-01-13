<?php
$xml = $_REQUEST['url'];

$result = array();

$xmlDoc = new DOMDocument();
$xmlDoc->load($xml);

$x=$xmlDoc->getElementsByTagName('item');
$length = $x->length;

for ($i=0; $i<$length; $i++){
	$item_title=$x->item($i)->getElementsByTagName('title')
		->item(0)->childNodes->item(0)->nodeValue;
	$item_link=$x->item($i)->getElementsByTagName('link')
		->item(0)->childNodes->item(0)->nodeValue;
	$item_desc=$x->item($i)->getElementsByTagName('description')
		->item(0)->childNodes->item(0)->nodeValue;
	$item_pubdate=$x->item($i)->getElementsByTagName('pubDate')
		->item(0)->childNodes->item(0)->nodeValue;
  
	$row = array('title'=>$item_title,'link'=>$item_link,'description'=>$item_desc,'pubdate'=>$item_pubdate);
	array_push($result, $row);
}

echo json_encode($result);

?> 