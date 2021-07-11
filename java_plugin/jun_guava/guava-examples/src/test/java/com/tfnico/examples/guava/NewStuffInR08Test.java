package com.tfnico.examples.guava;

import static com.google.common.collect.Collections2.filter;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.Test;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;


public class NewStuffInR08Test {

	@Test
	public void makeALineBreakWithAscii(){
		System.out.print("Carriage return:");
		System.out.print(new String(new byte[] {Ascii.CR} ));
		System.out.print("Second line!");
	}
	
	@Test
	public void filterVowelsInSomeChars() {
		CharMatcher vowelMatcher = CharMatcher.anyOf("aeiouy");
		assertTrue(vowelMatcher.matches('a'));
		
		ImmutableList<Character> someChars = ImmutableList.of('a','b','c','d','e');
		Collection<Character> filter = filter(someChars, vowelMatcher);
		
		ImmutableList<Character> result = ImmutableList.of('a','e');
		assertThat(charArray(filter), is(charArray(result)));
	}
	
	@Test
	public void makeAnIngredientsFromEachSupplier(){
		Supplier<Ingredients> factory1 = new IngredientsFactory();
		Supplier<Ingredients> factory2 = new IngredientsFactory();
		
		Function<Supplier<Ingredients>, Ingredients> supplierFunction = Suppliers.supplierFunction();
		
		ImmutableList<Supplier<Ingredients>> twoFactories = ImmutableList.of(factory1,factory2);
		Collection<Ingredients> twoIngredients = Collections2.transform(twoFactories, supplierFunction);
	}

	@Test
	public void filterAwayNullMapValues() {
		SortedMap<String, String> map = new TreeMap<String, String>();
		map.put("1","one");
		map.put("2","two");
		map.put("3",null);
		map.put("4","four");
		
//		SortedMap<String, String> filtered = Collections2.filter(map, Predicates.notNull());
//		assertThat(filtered.size(), is(3)); //null entry for "3" is gone!
	}
	
	
	
	static Character[] charArray(Collection<Character> filter) {
		return filter.toArray(new Character[0]);
	}
	
}