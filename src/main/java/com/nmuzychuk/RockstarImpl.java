package com.nmuzychuk;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class RockstarImpl implements Rockstar {

    public void make() {
        try {
            Files.createDirectories(Paths.get("./test-repo"));
            new ProcessBuilder()
                    .directory(new File("./test-repo"))
                    .command("sh", "-c", "pwd").inheritIO().start();
            new ProcessBuilder()
                    .directory(new File("./test-repo"))
                    .command("sh", "-c", "git init").inheritIO().start();
            Files.createFile(new File("./test-repo/test-file").toPath());
            new ProcessBuilder()
                    .directory(new File("./test-repo"))
                    .command("sh", "-c", "git add test-file").inheritIO().start();
            new ProcessBuilder()
                    .directory(new File("./test-repo"))
                    .command("sh", "-c", String.format("git commit -m fix --date=%s", LocalDateTime.now().minusDays(1).toString()))
                    .inheritIO().start();
//            new ProcessBuilder()
//                    .command("sh", "-c", "rm -rf ./test-repo");

        } catch (IOException e) {
            //
        }

    }

    public static void main(String[] args) {
        new RockstarImpl().make();
    }

}
