package com.nmuzychuk;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Programmer implements Rockstar {
    private static final int level = 42;
    private static Random rand = new Random();
    private static List<String> COMMIT_MESSAGES = new ArrayList<>(
            Arrays.asList("Update some files", "Fix a bug", "Bugfix", "Commit",
                    "Update component", "Hotfix", "Fix the fix",
                    "Add a feature")
    );
    private static File repo = new File("java-repo");

    public void make(File repo) {
        try {
            init(repo);

            File file = repo.toPath().resolve("Class.java").toFile();
            Files.createFile(file.toPath());

            for (int days = 5; days > 0; days--) {
                work(file, LocalDateTime.now().minusDays(days));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void init(File repo) throws IOException, InterruptedException {
        FileUtils.deleteDirectory(repo);
        Files.createDirectories(repo.toPath());

        new ProcessBuilder()
                .directory(repo)
                .command("sh", "-c", "git init").inheritIO().start().waitFor();
    }

    private static void work(File file, LocalDateTime dateTime) throws IOException, InterruptedException {
        for (int i = rand.nextInt(level); i >= 0; i -= 7) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                writer.write(rand.nextInt());
            }

            new ProcessBuilder()
                    .directory(file.toPath().getParent().toFile())
                    .command("sh", "-c", String.format("git add %s; git commit -m '%s' --date=%s",
                            file.getName(),
                            COMMIT_MESSAGES.get(rand.nextInt(COMMIT_MESSAGES.size())),
                            dateTime.plusMinutes(i).toString()))
                    .inheritIO().start().waitFor();
        }
    }

    public static void main(String[] args) {
        if (args.length >= 2) throw new IllegalArgumentException();
        if (args.length == 1) repo = new File(args[0]);

        new Programmer().make(repo);
    }

}
