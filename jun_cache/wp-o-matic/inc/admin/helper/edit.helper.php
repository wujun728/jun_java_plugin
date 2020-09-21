<?php

function _wpo_edit_cat_row($category, $level, &$data)
{  
	$category = get_category( $category );
	$name = $category->cat_name;
	
	echo '
	<li class="required pad'.$level.'">
    ' . checkbox_tag('campaign_categories[]', $category->cat_ID, in_array($category->cat_ID, $data['categories']), 'id=category_' . $category->cat_ID) . '
    ' . label_for('category_' . $category->cat_ID, $name) . '</li>';
}