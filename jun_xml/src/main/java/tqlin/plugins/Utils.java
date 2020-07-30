package tqlin.plugins;

import java.io.IOException;
import java.io.InputStream;

/**
 * Entry point for all plugins package tests.
 *
 * @author Simon Kitching
 */
public class Utils {

    /**
     * Return an appropriate InputStream for the specified test file (which must be inside our current package.
     *
     * @param caller is always "this" for the calling object.
     * @param name   is the test file we want
     * @throws IOException if an input/output error occurs
     */
    public static InputStream getInputStream(Object caller, String name)
            throws IOException {
        return (caller.getClass().getClassLoader().getResourceAsStream(name));
    }
}