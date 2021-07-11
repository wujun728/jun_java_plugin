package org.li.examples.guava;

import static java.lang.System.out;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Class whose sole reason for existence is to demonstrate Guava's Collections2
 * class.
 * 
 * @author Dustin
 * @see http://marxsoftware.blogspot.com/2011/10/filtering-and-transforming-java.html
 */
public class GuavaCollections2 {

    /**
     * Provides a Set of Strings.
     * 
     * @return Set of strings representing some programming languages.
     */
    private static Set<String> buildSetStrings() {
        final Set<String> strings = new HashSet<String>();
        strings.add("Java");
        strings.add("Groovy");
        strings.add("Jython");
        strings.add("JRuby");
        strings.add("Python");
        strings.add("Ruby");
        strings.add("Perl");
        strings.add("C");
        strings.add("C++");
        strings.add("C#");
        strings.add("Pascal");
        strings.add("Fortran");
        strings.add("Cobol");
        strings.add("Scala");
        strings.add("Clojure");
        strings.add("Basic");
        strings.add("PHP");
        strings.add("Flex/ActionScript");
        strings.add("JOVIAL");
        return strings;
    }

    /**
     * Demonstrate Guava's Collections2.filter method. Filter String beginning
     * with letter 'J'.
     */
    public static void demonstrateFilter() {
        printHeader("Collections2.filter(Collection,Predicate): 'J' Languages");
        final Set<String> strings = buildSetStrings();
        out.println("\nOriginal Strings (pre-filter):\n\t" + strings);
        final Collection<String> filteredStrings = Collections2.filter(strings,
                Predicates.containsPattern("^J"));
        out.println("\nFiltered Strings:\n\t" + filteredStrings);
        out.println("\nOriginal Strings (post-filter):\n\t" + strings);
    }

    /**
     * Demonstrate Guava's Collections2.transform method. Transform input
     * collection's entries to uppercase form.
     */
    public static void demonstrateTransform() {
        printHeader("Collections2.transform(Collection,Function): Uppercase");
        final Set<String> strings = buildSetStrings();
        out.println("\nOriginal Strings (pre-transform):\n\t" + strings);
        final Collection<String> transformedStrings = Collections2.transform(
                strings, new UpperCaseFunction<String, String>());
        out.println("\nTransformed Strings:\n\t" + transformedStrings);
        out.println("\nOriginal Strings (post-transform):\n\t" + strings);
    }

    /**
     * Print a separation header including the provided text.
     * 
     * @param headerText
     *            Text to be included in separation header.
     */
    private static void printHeader(final String headerText) {
        out.println("\n==========================================================");
        out.println("== " + headerText);
        out.println("==========================================================");
    }

    /**
     * Main function for demonstrating Guava's Collections2 class.
     * 
     * @param arguments
     */
    public static void main(final String[] arguments) {
        demonstrateFilter();
        demonstrateTransform();
    }

}
