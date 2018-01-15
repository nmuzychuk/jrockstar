package com.nmuzychuk;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class ProgrammerTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void setUp() {
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testApp() throws IOException {
        final File repo = new File("test-app");
        new Programmer().make(repo);
        FileUtils.deleteDirectory(repo);
    }

}
