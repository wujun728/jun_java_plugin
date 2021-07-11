package com.tfnico.examples.guava;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.common.io.Closeables;
import com.google.common.io.Files;
import com.google.common.io.Flushables;
import com.google.common.io.Resources;

public class IoTest {

    @Test
    public void messAroundWithFile() {
        File file = new File("woop.txt");
        try {
            Files.touch(file);

            Files.write("Hey sailor!\n hello li", file, Charsets.UTF_8);

            // Breakpoint here.. have a look at the file..

            Files.toByteArray(file);
            Files.newInputStreamSupplier(file);
            assertEquals("Hey sailor!",
                    Files.readFirstLine(file, Charsets.UTF_8));
            assertEquals("Hey sailor!\n hello li", Files.toString(file, Charsets.UTF_8));
            List<String> lines = Files.readLines(file, Charsets.UTF_8);
            assertEquals("Hey sailor!", lines.get(0));
            assertEquals(" hello li", lines.get(1));

            assertEquals("txt", Files.getFileExtension(file.getName()));
            file.delete();
            // guava has abandoned this method 
//            Files.deleteRecursively(file);

        } catch (IOException e) {
            Throwables.propagate(e);
        }
    }

    @Test
    public void closingAndFlushing() {
        InputStream inputStream = System.in;
        try {
            inputStream.close();// The old way
        } catch (IOException e) {
            Throwables.propagate(e);
        }
        Closeables.closeQuietly(inputStream); // The new way

        // Or flush:
        PrintStream outputStream = System.out;
        Flushables.flushQuietly(outputStream);
    }

    @Test
    public void classPathResources() {
        // This:
        Resources.getResource("com/tfnico/examples/guava/BaseTest.class");

        // instead of this:
        String location = "com/tfnico/examples/guava/BaseTest.class";
        URL resource2 = this.getClass().getClassLoader().getResource(location);
        Preconditions.checkArgument(resource2 != null, "resource %s not found",
                location);
    }

}
